#!/bin/bash
/usr/local/jdk/bin/javac -cp ../lib/*: ROOT/WEB-INF/classes/controller/*java
../bin/shutdown.sh;
../bin/startup.sh;
