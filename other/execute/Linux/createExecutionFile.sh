#!/bin/sh
#set jar file path and jvm parameter
JAR_PATH=`which "$0" 2>/dev/null`
JVM_PARM='-Dfile.encoding=utf-8'

#set java exec path 
java=java
if test -n "$JAVA_HOME"; then
    java="$JAVA_HOME/bin/java"
fi

#exec jar file
echo "java $JVM_PARM  -jar $JAR_PATH $@"
exec "$java" ${JVM_PARM}  -jar $JAR_PATH $@
exit 1
