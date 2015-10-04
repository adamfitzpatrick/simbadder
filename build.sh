#!/usr/bin/env bash
#./gradlew clean build

cd simbadder
gradle bootRun &> /dev/null & PID=$!

echo -n "Starting application (PID $PID)"
let n="0"
let maxn="500"
let taskOk="0"
while [ "$n" -lt "$maxn" ] ; do
  wget -t 1 -q -O "/dev/null" "http://localhost:7901/"
  if [ "$?" = "0" ] ; then
    echo "Startup complete."
    let taskOk="1"
    break
  fi
  sleep 0.25
  let n=${n}+1
  echo -n "."
done
if [ "$taskOk" = "0" ] ; then
  echo "Unable to start application. Exiting build."
  exit 1
fi

cd ../simbadder-system-tests
./gradlew bootRun

let taskOk="0"
echo -n "Terminating application"
kill $PID
while [ "$n" -lt "$maxn" ] ; do
  wget -t 1 -q -O "/dev/null" "http://localhost:7901/"
  if [ "$?" != "0" ] ; then
    echo "Application terminated."
    let taskOk="1"
    break
  fi
  sleep 0.25
  let n=${n}+1
  echo -n "."
done
if [ "$taskOk" = "0" ] ; then
  echo "Warning: Failed to terminate application."
fi