
## **Java IMR-LIV**


This is a Java implementation as a library having the following packages:  
>  
> - imr.matrix  
> - imr.plot  
> - imr.sigslot  
> - imr.sound  
> - imr.sound.audio  
> - imr.sound.audio.analysis  
> - imr.sound.audio.filter  
> - imr.sound.audio.synthesis  
> - imr.sound.audio.window  
> - imr.sound.midi  
> - imr.util  
>  
	  
	  In the imr.util package, we can find the iArray class with static methods to clone, resize and get chunks from arrays of any Java primitive  
type using reflection to achieve the goal.  
There is also a random number generator and some new added classes  
as ComplexNumber and ByteBuffer.  
	The imr.matrix package offers functionality to perform the most common operations applied to matrices and vectors  
	and several ways to solve linear systems of NxN equations.  
It also have functionallity to perform QR factorization.  
	The imr.sound.audio package has an audio player with support to play, pause and stop playback.  
	Supported audio formats for the audio player:  
>  
> - mp3  
> - wav  
> - aif  
> - au  
>  
	  
	  In the imr.sound.midi package there are classes to manage MIDI data with the equivalent wrapper ones for the classes  
	in the javax.sound.midi package.  
	This package also has support to load and store Standard MIDI Files ( SMF ),  
	and a MIDI player suitable to play, pause and stop like in the case of the audio player in the imr.sound.audio package.  
	In the imr.sound package, you can find two interfaces and a SoundPlayer class which uses and audio and a midi player  
	to have support to play audio and MIDI files.  
	This sound player has some extra features to loop, shuffle and select a concrete song from a list to be played.  
You can also play an entire folder containing supported sound files.  
Supported extensions for the sound player:  
>  
> - mp3  
> - mid  
> - m3u ( lists )  
> - wav  
> - aiff  
> - au  
>  
	  
	  There is also a �doc� folder with the API documentation generated using the �javadoc� tool.  
	We decided to name this library �Java IMR-LIB�, where IMR stands for Ismael Mosquera Rivera.  
	

Fixed: There was a bug in the AudioPlayer.java file causing problems loading  
*.aif and *.au files. It was already fixed.  

The documentation is now placed in a compressed file ( doc.zip ).  

New feature: In the imr.sound.audio package an audio recorder was added ( AudioRecorder.java ).  
It has the following functionalities:  
Two constructors:  
AudioRecorder() default sample rate = 44100.0f  
AudioRecorder(float sampleRate) Allowed values: 11025.0f, 22050.0f and 44100.0f  

public methods:  
void rec() Starts recording.  
void play() Starts playing if some audio is already recorded.  
void stop() Stops recording or playing.  
void save(String filename) Stores the current recorded audio into a file.  

Supported audio formats:  
>  
> - WAVE ( *.wav ).  
> - AIFF ( *.aif ).  
> - AU ( *.au ).  
>  

New example: test_recorder folder.  
A command line aplication to test the new feature added.  

Please, look at the readme.txt files to know more about each subject.  

New audio features added to imr.sound.audio package:  
  
#### **AudioFileIO**  
    
    A class to load and store audio files.  
  
#### **RawDataPlayer.java**  

A player suitable to reproduce raw audio data, for instance,  
the data generated by an oscillator.  

#### **RawDataStorage.java**  

Using this class, you can save raw audio data in an audio file.  
Supported audio formats:  
>  
> - WAVE ( *.wav ).  
> - AIFF ( *.aif ).  
> - AU ( *au ).  
>  
  
#### **FrameFactory.java**  

A class to produce empty frames at any duration.  
And functionallity to clone data frames among other things.    

#### **AudioMixer.java**  

This class allows you to mix n-tracks into just one.  
Input: n-traccks to be mixed. Output: one mixed track.  
  
Package imr.sound.audio.analysis:  
  
#### **Fourier.java**  
  
This class has static methods to perform FFT and IFFT.  
  
#### **FrameShifter.java**  
  
A class to manage hop size to perform spectral analysis.  
  
#### **SpectralAnalyzer**  
  
  A class to perform spectral analysis.  
Input <- a floating point vector with audio signal data.  
Output -> a Spectrum object with the resulting analysis.  
  #### **Spectrum.java**  
  
A class to keep data resulting of spectral analysis.  
  
#### **SpectrumList.java**  
  
Container to store Spectrum objects.  
  
Package imr.sound.audio.synthesis:  
  
#### **Envelope.java**  

An ADSR envelope.  

#### **Oscillator.java**  

An oscillator to generate sinusoidal waves oscillating at any frequency.  

#### **Wave.java**

An abstract class from you can derive any kined of wave.  
>  
> - SawWave.java: Derived from Wave.  
> - SineWave.java: Derived from Wave.  
> - SquareWave.java: Derived from Wave.  
> - TriangularWave.java: Derived from Wave.  
> - MusicalNote.java: A 4-harmonic musical note derived from Wave.  
>  

#### **Synthesizer.java**  

An abstract class having an envelope and a wave, from you can derive  
a subclass just setting its wave type.  
>  
> - MusicalSynthesizer.java: Derived from Synthesizer having a MusicalNote.  
>  

#### **Modulator.java**  

An abstract class to modulate another signal. In addition to an envelope,  
you can choose the kind of wave to modulate a signal.  
Wave types to choose:  
>  
> - SawWave.  
> - SineWave.  
> - SquareWave.  
> - TriangularWave.  
>  

#### **AmplitudeModulator.java**  

A class suitable to perform AM synthesis.  
Derived from Modulator.  

#### **FrequencyModulator.java**  

A class suitable to perform FM synthesis.  
Derived from Modulator.  

#### **RingModulator.java**  

A class suitable to perform ring modulation.  
Derived from Modulator.  

#### **WaveType.java**  

A class with public static fields useful, for example,  
to set the wave type in a modulator.  
  
#### **SpectralSynthesizer**  
  
A class suitable to perform spectral synthesis.  
Input <- a Spectrum object.  
Output -> a floating point vector with the resulting synthesized audio signal.  
  
#### **RandomNoise.java**  
  
A random noise signal generator.  
  
Package imr.sound.audio.window:  
  
This package contains classes implementing several kind of windows useful to perform  
spectral analysis.  
Implemented window types:  
  
>  
> - BlackmanHarris92dB.  
> - Gaussian.  
> - Hamming.  
> - Triangular.  
>  
  
There is also an abstract class that could be extended by any other in order to have windowing support.  
For instance, the SpectralAnalyzer and SpectralSynthesizer extend from this class since they need do windowing.  
The analyzer does windowing and the synthesizer undoes it.  
  
Package imr.plot  
  
This new added package implements a base subsystem to code visualizers for your data.  
There are some visualizers already implemented:  
>  
> - AudioPlot  
> - SignalPlot  
> - SpectrumPlot  
>  
  
  Package imr.sigslot:  
    
    A package implementing a signal/slot subsystem with signals and slots with capabilities to manage   
    emition and reception for 0, 1, 2, and 3 generic data.  
  
Package imr.sound.audio.filter:  
  
A package implementing FIR ( Finite impulsional Response ) filters using the windowed/sinc strategy.  
Implemented filters:  
>  
> - Low-pass.  
> - High-pass.  
> - Band-pass.  
> - Band-reject.  
>  
  
See the API documentation for more details.  
  
We also added examples to test all of these new components.  
We encourage you to run the examples to know more about them.  
The API documentation was also updated and placed in the 'doc.zip' compressed file.  

We hope to continue adding more features to this library.  
Any contribution is welcome.  

