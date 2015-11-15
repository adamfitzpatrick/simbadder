(function() {
  var CoordinateSet, ScanRange, concurrency, concurrencyLimit, dec, decLimit, decMin, decStart, deltaDec, deltaRa, finishedTasks, idGen, interval, logAll, nextId, ra, raLimit, raMin, raStart, range, runner, scanTasks;

  concurrencyLimit = 5;

  deltaRa = 1;

  deltaDec = 5;

  raLimit = 10;

  decLimit = -85;

  decStart = -90;

  raStart = 0;

  decMin = -90;

  raMin = 0;

  CoordinateSet = (function() {
    function CoordinateSet(min, max) {
      this.min = min;
      this.max = max;
    }

    return CoordinateSet;

  })();

  ScanRange = (function() {
    function ScanRange(id1, ra1, dec1) {
      this.id = id1;
      this.ra = ra1;
      this.dec = dec1;
    }

    ScanRange.prototype.setResult = function(status1, reason1) {
      this.status = status1;
      this.reason = reason1;
    };

    return ScanRange;

  })();

  idGen = (function*() {
    var id;
    id = 0;
    while (true) {
      (yield id++);
    }
  })();

  nextId = function() {
    return idGen.next().value;
  };

  concurrency = 0;

  scanTasks = [];

  finishedTasks = [];

  while (decMin < decLimit) {
    while (raMin < raLimit) {
      ra = new CoordinateSet(raMin, raMin + deltaRa);
      dec = new CoordinateSet(decMin, decMin + deltaDec);
      range = new ScanRange(nextId(), ra, dec);
      scanTasks.push(range);
      raMin += deltaRa;
    }
    raMin = raStart;
    decMin += deltaDec;
  }

  runner = function(task, callback) {
    var delay, reason, status;
    delay = Math.random() * 10000;
    status = Math.random() > 0.5 ? 200 : 404;
    reason = status === 200 ? "" : "BAD!";
    task.setResult(status, reason);
    finishedTasks.push(task);
    return setTimeout(function() {
      return callback();
    }, delay);
  };

  logAll = function() {
    return finishedTasks.forEach(function(task) {
      return console.log(task);
    });
  };

  interval = setInterval(function() {
    var callback;
    if (concurrency < concurrencyLimit) {
      concurrency++;
      callback = function() {
        return concurrency--;
      };
      runner(scanTasks.shift(), callback);
      if (scanTasks.length === 0) {
        clearInterval(interval);
        return logAll();
      }
    }
  }, 100);

}).call(this);
