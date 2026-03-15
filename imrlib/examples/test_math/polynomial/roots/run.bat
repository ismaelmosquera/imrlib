@echo off
cls
cd bin
java -ea -cp .;..\..\..\..\..\lib\imr-lib.jar TestRoots > out.txt
echo Output saved to bin\out.txt
