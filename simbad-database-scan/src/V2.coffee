concurrencyLimit = 5

deltaRa = 1
deltaDec = 5
raLimit = 10
decLimit = -85
decStart = -90
raStart = 0
decMin = -90
raMin = 0

class CoordinateSet
  constructor: (@min, @max) ->

class ScanRange
  constructor: (@id, @ra, @dec) ->
  setResult: (@status, @reason) ->

idGen = (->
    id = 0
    loop
      yield id++
    return)()
nextId = -> idGen.next().value

concurrency = 0
scanTasks = []
finishedTasks = []

while decMin < decLimit
  while raMin < raLimit
    ra = new CoordinateSet raMin, raMin + deltaRa
    dec = new CoordinateSet decMin, decMin + deltaDec
    range = new ScanRange nextId(), ra, dec
    scanTasks.push range
    raMin += deltaRa
  raMin = raStart
  decMin += deltaDec

runner = (task, callback) ->
  delay = Math.random() * 10000
  status = if Math.random() > 0.5 then 200 else 404
  reason = if status is 200 then "" else "BAD!"
  task.setResult status, reason
  finishedTasks.push task
  setTimeout ->
    callback()
  , delay

logAll = -> finishedTasks.forEach (task) -> console.log task
interval = setInterval ->
  if concurrency < concurrencyLimit
    concurrency++
    callback = -> concurrency--

    runner scanTasks.shift(), callback
    if scanTasks.length is 0
      clearInterval(interval)
      logAll()
, 100
