Reporter = require "./reporter"
utilities = require "./utilities"
db = require("monk") "localhost/simbadder"
scanResult = db.get "scanResult"
chalk = require "chalk"

progressBarLength = 50
descriptionLength = 30
reporter = new Reporter(["Running initial scan..."])

concurrencyLimit = 1000
requests = -1
outstanding = null
errors = 0
totalTasks = 0

deltaRa = 2
deltaDec = 2
raLimit = 360
decLimit = 90
decStart = -90
raStart = 0
concurrency = 0

CoordinateSet = utilities.CoordinateSet
ScanRange = utilities.ScanRange

interval = null

writeStatus = (err) ->
  requests++
  errors++ if err
  errorStr = chalk.red.bold "Errors: #{errors}"
  outstanding = totalTasks - requests
  reporter.setLine 1,
    "Completed: #{requests}  Outstanding: #{outstanding}  #{errorStr}  Concurrency: #{concurrency}"
  progressDescription = "Scan Progress:"
  progressDescription += " " until progressDescription.length < descriptionLength
  progress = Math.ceil(requests / totalTasks * progressBarLength) or 0
  progDec = ("0" + (requests / totalTasks * 100 or 0).toFixed(2)).slice -5
  progDec = "100" if requests is totalTasks
  progressBar = reporter.getProgressBar progressBarLength, progress
  reporter.setLine 2, "#{progressDescription} #{progressBar}"
  timeLeft = utilities.timeRemaining startTime, requests, totalTasks
  reporter.setLine 3,
    "Percent Complete: #{progDec}   Time Remaining: #{timeLeft}"

hrTime = process.hrtime()
startTime = hrTime[0] * 1000 + hrTime[1] / 1000000

raRange = new CoordinateSet raStart, raLimit
decRange = new CoordinateSet decStart, decLimit
scanRange = new ScanRange null, raRange, decRange
queuedTasks = utilities.generateScanTasks scanRange, deltaRa, deltaDec, scanResult, writeStatus
deltaRa = deltaRa / 2
deltaDec = deltaDec / 2

runScan = (scanTasks, refiner) ->
  totalTasks = scanTasks.length
  writeStatus false
  concurrency = 0
  refiner = refiner or ->
  interval = setInterval ->
    callback = ->
      concurrency--
      finish refiner if scanTasks.length is 0
    if concurrency < concurrencyLimit
      concurrency++
      scanTasks.shift().scan callback if scanTasks.length isnt 0
  , 200

refineScan = ->
  errors = 0
  requests = -1
  outstanding = 0
  concurrency = 0
  reporter.setLine 0, "Refining error results...#{deltaRa} deg resolution"
  queuedTasks = []
  result = (doc) ->
    queuedTasks =
      queuedTasks.concat utilities.generateScanTasks doc, deltaRa, deltaDec, scanResult, writeStatus
    scanResult.remove { _id: doc._id }
  scanResult.find { $and: [{ status: { $ne: 200 }}, { status: { $ne: 404 }}] }
    .each result
    .success ->
      if queuedTasks.length is 0
        reporter.setLine 0, "No errors."
        return finalize()
      if deltaRa > 0.25 then afterFn = refineScan else afterFn = finalize
      runScan queuedTasks, afterFn
      deltaRa = deltaRa / 2
      deltaDec = deltaDec / 2

finish = (refiner) ->
  clearInterval interval
  interval = setInterval ->
    if outstanding is 0
      refiner()
      clearInterval interval
  , 100

finalize = ->
  reporter.finish()
  db.close()

promise = scanResult.remove({})
promise.on "complete", -> runScan queuedTasks, refineScan

