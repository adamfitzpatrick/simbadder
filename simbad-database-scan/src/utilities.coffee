Scanner = require "./scanner"

timeRemaining = (startTime, requests, totalTasks)->
  hrTime = process.hrtime()
  currentTime = hrTime[0] * 1000 + hrTime[1] / 100000
  elapsed = currentTime - startTime
  rate = requests/elapsed
  millisLeft = totalTasks/rate - elapsed
  formatTime millisLeft

formatTime = (millis) ->
  return "Infinity" if millis isnt millis or millis is Infinity
  hrs = Math.floor millis/(3600*1000)
  hrs = 0 unless hrs >= 1
  millis = millis - hrs * 3600 * 1000
  mins = Math.floor millis/(60*1000)
  mins = 0 unless mins >= 1
  millis = millis - mins * 60 * 1000
  secs = Math.ceil millis/1000;
  "#{hrs}:#{("0" + mins).slice(-2)}:#{("0" + secs).slice(-2)}"

class CoordinateSet
  constructor: (@min, @max) ->

class ScanRange
  constructor: (@id, @ra, @dec) ->

generateScanTasks = (scanRange, deltaRa, deltaDec, resultDb, writeStatus) ->
  tasks = []
  decMin = scanRange.dec.min
  raMin = scanRange.ra.min
  while decMin < scanRange.dec.max
    while raMin < scanRange.ra.max
      ra = new CoordinateSet raMin, raMin+deltaRa
      dec = new CoordinateSet decMin, decMin + deltaDec
      range = new ScanRange "#{decMin}.#{raMin}", ra, dec
      tasks.push new Scanner range, resultDb, writeStatus
      raMin += deltaRa
    raMin = scanRange.ra.min
    decMin += deltaDec
  tasks

module.exports =
  timeRemaining: timeRemaining
  formatTime: formatTime
  CoordinateSet: CoordinateSet
  ScanRange: ScanRange
  generateScanTasks: generateScanTasks