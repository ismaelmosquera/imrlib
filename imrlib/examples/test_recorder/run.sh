#
# Simple script to run test recorder
#
# The -ea modifier enables assertions
#
cls
cd bin
java -classpath .:../../../lib/imr-lib.jar -ea TestAudioRecorder
