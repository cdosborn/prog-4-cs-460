#!/bin/bash
/usr/local/jdk/bin/javac -cp ~/tomcat/lib/*: ROOT/WEB-INF/classes/controller/*java
~/tomcat/bin/shutdown.sh;
~/tomcat/bin/startup.sh;
