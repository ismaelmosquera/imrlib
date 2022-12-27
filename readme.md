
## Java IMR-LIV


	This is a Java implementation as a library having three packages: imr.matrix, imr.media, imr.media.audio, imr.media.midi and imr.util.
	In the imr.util package, we can find the iArray class with static methods to clone, resize and get chunks from arrays of any kind of object, including the Java primitive types using reflection to achieve the goal.
	The imr.matrix package offers functionality to perform the most common operations applied to matrices and vectors, and several ways to solve linear systems of NxN equations.
	The imr.media.audio package has an audio player with support to play, pause and stop playback; in the imr.media.midi package there are classes to manage MIDI data with the equivalent wrapper ones for the classes in the java.sound package; this package also has support to load and store Standard MIDI Files ( SMF ), and a MIDI player suitable to play, pause and stop like in the case of the audio player in the imr.media.audio package. In the imr.media package, you can find two interfaces and a SoundPlayer class which uses and audio and a midi player to have support to play audio and MIDI files; this sound player has some extra features to loop, shuffle and select a concrete song from a list to be played.
	There is also a ‘doc’ folder with the API documentation generated using the ‘javadoc’ tool.
	We decided to name this library ‘Java IMR-LIB’, where IMR stands for Ismael Mosquera Rivera.
