#/bin/bash

gradle clean buildDocker
docker run -p 7901:7901 -t adamfitzpatrick/simbadder
