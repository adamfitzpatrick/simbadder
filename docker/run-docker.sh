#!/usr/bin/env bash
docker kill simbadder
docker rm simbadder
docker build -t adamfitzpatrick/simbadder .
docker run -d -p 7901:7901 --name simbadder adamfitzpatrick/simbadder
