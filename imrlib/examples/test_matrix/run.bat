@echo off
cls
cd bin
java -cp .;..\..\..\lib\imr-lib.jar TestMatrix > out.txt
echo Output saved to bin\out.txt
