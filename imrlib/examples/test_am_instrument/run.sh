#
# Simple script to run test am instrument
#
# The -ea modifier enables assertions
#
cls
cd bin
java -classpath .:../../../lib/imr-lib.jar -ea TestAMInstrument
