#
# Simple script to run test analyzer example
#
# The -ea modifier enables assertions
#
cls
cd bin
java -classpath .:../../../ext/mp3plugin.jar:../../../lib/imr-lib.jar -ea TestAnalyzer 
cd ..
