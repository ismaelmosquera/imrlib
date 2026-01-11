@echo off
cls
cd bin
java -cp .;..\..\..\..\..\lib\imr-lib.jar TestRuffini > out.txt
echo Output saved to bin\out.txt
