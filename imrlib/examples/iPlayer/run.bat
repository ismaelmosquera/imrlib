@echo off
cls
cd bin
java -ea -cp .;..\..\..\ext\mp3plugin.jar;..\..\..\lib\imr-lib.jar iPlayer 
cd..
