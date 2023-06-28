
## Java IMR-LIV


	This is a Java implementation as a library having three packages: imr.matrix, imr.media, imr.media.audio, imr.media.midi and imr.util.
	In the imr.util package, we can find the iArray class with static methods to clone, resize and get chunks from arrays of any kind of object, including the Java primitive types using reflection to achieve the goal.
	The imr.matrix package offers functionality to perform the most common operations applied to matrices and vectors, and several ways to solve linear systems of NxN equations.
	The imr.media.audio package has an audio player with support to play, pause and stop playback; in the imr.media.midi package there are classes to manage MIDI data with the equivalent wrapper ones for the classes in the javax.sound.midi package; this package also has support to load and store Standard MIDI Files ( SMF ), and a MIDI player suitable to play, pause and stop like in the case of the audio player in the imr.media.audio package. In the imr.media package, you can find two interfaces and a SoundPlayer class which uses and audio and a midi player to have support to play audio and MIDI files; this sound player has some extra features to loop, shuffle and select a concrete song from a list to be played.
	There is also a �doc� folder with the API documentation generated using the �javadoc� tool.
	We decided to name this library �Java IMR-LIB�, where IMR stands for Ismael Mosquera Rivera.


Fixed: There was a bug in the AudioPlayer.java file causing problems loading
*.aif and *.au files. It was already fixed.

The documentation is now placed in a compressed file ( doc.zip ).

New feature: In the imr.media.audio package an audio recorder was added ( AudioRecorder.java ). It has the following functionalities:

Two constructors:
AudioRecorder() default sample rate = 44100.0f
AudioRecorder(float sampleRate) Allowed values: 11025.0f, 22050.0f and 44100.0f

public methods:
void rec() Starts recording.
void play() Starts playing if some audio is already recorded.
void stop() Stops recording or playing.
void save(String filename) Stores the current recorded audio into a file.
Supported audio formats:
WAVE ( *.wav ).
AIFF ( *.aif ).
AU ( *.au ).

New example: test_recorder folder.
A command line aplication to test the new feature added.

Please, look at the readme.txt files to know more about each subject.
