@echo off
cls
cd bin
java -ea -cp .;..\..\..\..\..\lib\imr-lib.jar TestLagrange > out.txt
echo Output saved to bin\out.txt
