@echo off
rd /s /q lib
mkdir lib
xcopy /s /q src lib
cd lib
javac imr\math\*.java imr\math\matrix\*.java imr\math\polynomial\*.java
javac imr\plot\*.java imr\sigslot\*.java imr\sound\*.java
javac imr\sound\audio\*.java imr\sound\audio\analysis\*.java imr\sound\audio\filter\*.java
javac imr\sound\audio\synthesis\*.java imr\sound\audio\window\*.java imr\sound\midi\*.java imr\util\*.java
del imr/math/*.java imr/math/matrix/*.java imr/math/polynomial/*.java
del imr\plot\*.java imr\sigslot\*.java imr\sound\*.java
del imr\sound\audio\*.java imr\sound\audio\analysis\*.java imr\sound\audio\filter\*.java
del imr\sound\audio\synthesis\*.java imr\sound\audio\window\*.java imr\sound\midi\*.java imr\util\*.java
jar cvf imr-lib.jar imr
rd /s /q imr
cd..
