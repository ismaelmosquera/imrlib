@echo off
rd /s /q lib
mkdir lib
xcopy /s /q src lib
cd lib
javac imr\matrix\*.java imr\media\*.java imr\media\audio\*.java imr\media\midi\*.java imr\util\*.java
del imr\matrix\*.java imr\media\*.java imr\media\audio\*.java imr\media\midi\*.java imr\util\*.java
jar cvf imr-lib.jar imr
rd /s /q imr
cd..
