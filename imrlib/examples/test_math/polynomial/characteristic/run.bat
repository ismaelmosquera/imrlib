@echo off
cls
cd bin
java -cp .;..\..\..\..\..\lib\imr-lib.jar TestCharacteristic > out.txt
echo Output saved to bin\out.txt
