/**
* package imr.sound.audio.filter
* <p>
* This package implements the most common audio filters:
* <ul>
* <li>LowPassFilter</li>
* <li>HighPassFilter</li>
* <li>BandPassFilter</li>
* <li>BandRejectFilter</li>
* </ul>
*
* <p>
* The base class of all of them is <code>Filter</code>.
* They are FIR filters implemented using windowed / sinc strategy.
* <p>
* There are also a Moving Average Filter and a class with static methods to interpolate and decimate samples from a signal.
* <p>
* These classes work only with signals composed by pure independent sinusoids.
* <p>
* @author Ismael Mosquera Rivera
*
*/

package imr.sound.audio.filter;
