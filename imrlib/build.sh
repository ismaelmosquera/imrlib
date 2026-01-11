#
# Simple script to build imr-lib.jar
#
rm -r lib
mkdir lib
cp -r src/imr lib
cd lib
javac imr/math/*.java imr/math/matrix/*.java imr/math/polynomial/*.java
javac imr/plot/*.java imr/sigslot/*.java imr/sound/*.java
javac imr/sound/audio/*.java imr/sound/audio/analysis/*.java imr/sound/audio/filter/*.java
javac imr/sound/audio/synthesis/*.java imr/sound/audio/window/*.java imr/sound/midi/*.java imr/util/*.java
rm imr/math/*.java imr/math/matrix/*.java imr/math/polynomial/*.java
rm imr/plot/*.java imr/sigslot/*.java imr/sound/*.java
rm imr/sound/audio/*.java imr/sound/audio/analysis/*.java imr/sound/audio/filter/*.java
rm imr/sound/audio/synthesis/*.java imr/sound/audio/window/*.java imr/sound/midi/*.java imr/util/*.java
jar cvf imr-lib.jar imr
rm -r imr
cd ..
