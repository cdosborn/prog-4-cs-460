#!/bin/bash
/usr/local/jdk/bin/javac -cp root/WEB-INF/classes/ root/WEB-INF/classes/*/*.java
../bin/shutdown.sh;
../bin/startup.sh;
