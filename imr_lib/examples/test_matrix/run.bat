@echo off
cls
cd bin
java -cp .;..\..\..\lib\imr-lib.jar TestMatrix > out.txt > out.txt
cd..
