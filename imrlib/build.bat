@echo off
rd /s /q lib
mkdir lib
xcopy /s /q src lib
cd lib
javac imr\matrix\*.java imr\sound\*.java imr\sound\audio\*.java imr\sound\audio\analysis\*.java imr\sound\audio\synthesis\*.java imr\sound\audio\window\*.java imr\sound\midi\*.java imr\util\*.java
del imr\matrix\*.java imr\sound\*.java imr\sound\audio\*.java imr\sound\audio\analysis\*.java imr\sound\audio\synthesis\*.java imr\sound\audio\window\*.java imr\sound\midi\*.java imr\util\*.java
jar cvf imr-lib.jar imr
rd /s /q imr
cd..
