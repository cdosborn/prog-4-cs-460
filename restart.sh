#!/bin/bash
/usr/local/jdk/bin/javac -cp root/WEB-INF/classes/:../lib/servlet-api.jar root/WEB-INF/classes/*/*java
../bin/shutdown.sh;
../bin/startup.sh;
