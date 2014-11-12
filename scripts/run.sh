#!/bin/sh
##############################################################################
#
PROGRAM_PATH=`dirname $0`

#JAVA_HOME=/opt/jdk1.6.0_38/
JAVA_HOME=/opt/jdk1.7.0_45
TMP_DIR=/var/tmp
CLASSPATH=\
$JAVA_HOME/lib/tools.jar:
CLASSPATH=\
$PROGRAM_PATH/../dist/TestSQLiteORM.jar

# seteando el classpath
. $PROGRAM_PATH/env.sh

# echo $CLASSPATH
#
# Start program
#
$JAVA_HOME/bin/java \
-Djava.io.tmpdir=$TMP_DIR \
-Dlog4j.debug=true \
-Dlog4j.configuration=file:$PROGRAM_PATH/../etc/log4j.xml \
-Dpackage_name=cl.dsoft.car.mobile.db \
-Dorg.apache.commons.logging.Log=org.apache.commons.logging.impl.Log4JLogger \
-Dconfig_file=$PROGRAM_PATH/../etc/config.ini \
-Ddata_file=$PROGRAM_PATH/../data/car.db3 \
-Doutput_dir=$PROGRAM_PATH/../output/src/cl/dsoft/car/mobile/db \
-cp $CLASSPATH \
TestSQLiteORM

#$JAVACMD LoadCaf $@
