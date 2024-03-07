/**
* The imr.sound.audio.synthesis package provides some classes related to audio synthesis.
 *
 * <code>Oscillator</code>
 * With an instance object of this class you can generate sinusoidal waves oscillating at any frequency.
 *
 * <code>Envelope</code>
 * Using this class you can generate ADSR envelopes, for example, to apply to a wave generated by an <code>Oscillator</code> object.
 *
 * <code>Wave</code>
 * This is an abstract class with all the functionallity needed to generate waves of any type.
 * The folloeing classes are derived from <code>Wave</code>
 * <ul>
 * <li>SawWave</li>
 * <li>SineWave</li>
 * <li>SquareWave</li>
 * <li>TriangularWave</li>
 * <li>MusicalNote</li>
 * </ul>
 * You can guess what are the xxxWave classes; the <code>MusicalNote</code> one implements
 * a 4-harmonic musical note.
 *
 * <code>WaveType</code>
 * This class just have 4 public fields useful to set a possible desired wave.
 *
 * <code>Synthesizer</code>
 * This abstract class has all the needed functionallity to implement a basic synthesizer.
 * To subclass this class, you only need to implement its protected <code>setWave()</code> abstract method.
 * The following classes are derived from this one:
 * <ul>
 * <li>MusicalSynthesizer</li>
 * </ul>
 * The <code>MusicalSynthesizer</code> class implements a basic instrument.
 *
 * <code>Modulator</code>
 * This is an abstract class with functionallity to easy subclass any modulator.
 * The following classes are derived from it.
 * <ul>
 * <li>AmplitudeModulator</li>
 * <li>FrequencyModulator</li>
 * <li>RingModulator</li>
 * </ul>
 * You can set the modulator wave type to modulate the carrier.
 * That is: saw, sine, square or triangular.
 *
 * <code>RandomNoise</code>
 * A class to generate random noise signals.
 *
 * <code>SpectralSynthesizer</code>
 * A class suitable to perform spectral synthesis.
 *
 * Some classes in this package use assertions. so, you have to run them with the 'ea' modifier
 * which enables assertions.
 * Example:
 * <code>java -ea MyApp</code>
 *
 * @author Ismael Mosquera Rivera
 *
*/

package imr.sound.audio.synthesis;
