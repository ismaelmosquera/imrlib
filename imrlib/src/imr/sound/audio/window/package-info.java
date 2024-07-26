/**
* The imr.sound.audio.window package contains classes to generate windows ready to apply when necessary.
* <p>
* <code>Window</code>
* This is an abstract class from you can derive any kind of window.
* <p>
* There are already implemented the following window types:
* <ul>
* <li>BlackmanHarris92Window</li>
* <li>GaussianWindow</li>
* <li>HammingWindow</li>
* <li>TriangularWindow</li>
* </ul>
* <p>
* The <code>WindowType</code> class just has several constants which can be used to set the wished window type.
* <p>
* There is also an abstract class from you can derive another ones needing windowing:
* <code>Windowing</code>
* For instance, the classes <code>SpectralAnalyzer</code> and <code>SpectralSynthesizer</code>
* <p>
* inherit from <code>Window</code> since they need to perform windowing.
*
* @author Ismael Mosquera Rivera
*
*/

package imr.sound.audio.window;
