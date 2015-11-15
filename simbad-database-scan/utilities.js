(function() {
  var CoordinateSet, ScanRange, Scanner, formatTime, generateScanTasks, timeRemaining;

  Scanner = require("./scanner");

  timeRemaining = function(startTime, requests, totalTasks) {
    var currentTime, elapsed, hrTime, millisLeft, rate;
    hrTime = process.hrtime();
    currentTime = hrTime[0] * 1000 + hrTime[1] / 100000;
    elapsed = currentTime - startTime;
    rate = requests / elapsed;
    millisLeft = totalTasks / rate - elapsed;
    return formatTime(millisLeft);
  };

  formatTime = function(millis) {
    var hrs, mins, secs;
    if (millis !== millis || millis === Infinity) {
      return "Infinity";
    }
    hrs = Math.floor(millis / (3600 * 1000));
    if (!(hrs >= 1)) {
      hrs = 0;
    }
    millis = millis - hrs * 3600 * 1000;
    mins = Math.floor(millis / (60 * 1000));
    if (!(mins >= 1)) {
      mins = 0;
    }
    millis = millis - mins * 60 * 1000;
    secs = Math.ceil(millis / 1000);
    return hrs + ":" + (("0" + mins).slice(-2)) + ":" + (("0" + secs).slice(-2));
  };

  CoordinateSet = (function() {
    function CoordinateSet(min, max) {
      this.min = min;
      this.max = max;
    }

    return CoordinateSet;

  })();

  ScanRange = (function() {
    function ScanRange(id, ra1, dec1) {
      this.id = id;
      this.ra = ra1;
      this.dec = dec1;
    }

    return ScanRange;

  })();

  generateScanTasks = function(scanRange, deltaRa, deltaDec, resultDb, writeStatus) {
    var dec, decMin, ra, raMin, range, tasks;
    tasks = [];
    decMin = scanRange.dec.min;
    raMin = scanRange.ra.min;
    while (decMin < scanRange.dec.max) {
      while (raMin < scanRange.ra.max) {
        ra = new CoordinateSet(raMin, raMin + deltaRa);
        dec = new CoordinateSet(decMin, decMin + deltaDec);
        range = new ScanRange(decMin + "." + raMin, ra, dec);
        tasks.push(new Scanner(range, resultDb, writeStatus));
        raMin += deltaRa;
      }
      raMin = scanRange.ra.min;
      decMin += deltaDec;
    }
    return tasks;
  };

  module.exports = {
    timeRemaining: timeRemaining,
    formatTime: formatTime,
    CoordinateSet: CoordinateSet,
    ScanRange: ScanRange,
    generateScanTasks: generateScanTasks
  };

}).call(this);
