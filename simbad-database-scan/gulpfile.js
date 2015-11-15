var coffee = require("gulp-coffee");
var gulp = require("gulp");
var sleep = require("sleep");
var plumber = require("gulp-plumber");

function compile() {
    gulp.src("src/*.coffee")
        .pipe(plumber())
        .pipe(coffee())
        .pipe(gulp.dest("."));
}

gulp.task("default", compile);
gulp.task("watch", function () {
    gulp.watch("src/*.coffee", function () {
        console.log("Compiling coffee...");
        compile();
        sleep.sleep(1)
    });
});