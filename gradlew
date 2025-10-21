#!/usr/bin/env sh
##############################################################################
# Gradle start up script for UN*X
##############################################################################
# Copyright 2012-2024 the original authors
# Licensed under the Apache License, Version 2.0
##############################################################################

# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
DEFAULT_JVM_OPTS="-Xmx1g"

APP_NAME="Gradle"
APP_BASE_NAME=`basename "$0"`

# Resolve links - $0 may be a link
PRG="$0"
while [ -h "$PRG" ]; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`"/""$link"
  fi
done

SAVED="`pwd`"
cd "`dirname "$PRG"`/.."
PRGDIR=`pwd -P`
cd "$SAVED"

# Determine Java command to use to start the JVM.
if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
        JAVACMD="$JAVA_HOME/jre/sh/java"
    else
        JAVACMD="$JAVA_HOME/bin/java"
    fi
    if [ ! -x "$JAVACMD" ] ; then
        echo "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME" 1>&2
        echo "Please set the JAVA_HOME variable in your environment to match the
location of your Java installation." 1>&2
        exit 1
    fi
else
    JAVACMD="java"
    which java > /dev/null 2>&1 || {
        echo "ERROR: Java not found. Please set JAVA_HOME or install Java." 1>&2
        exit 1
    }
fi

# Use the maximum available, or set MAX_FD != -1 to use that value.
MAX_FD="maximum"

CLASSPATH="${CLASSPATH}" \
"$JAVACMD" ${DEFAULT_JVM_OPTS} -classpath "$PRGDIR/gradle/wrapper/gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain "$@"

# End of script