@echo off
cls
cd bin
java -cp .;..\..\..\ext\mp3plugin.jar;..\..\..\lib\imr-lib.jar -ea TestAnalyzer 
cd ..
