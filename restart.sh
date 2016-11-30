#!/bin/bash
/usr/local/jdk/bin/javac -cp ../lib/*: root/WEB-INF/classes/controller/*java
../bin/shutdown.sh;
../bin/startup.sh;
