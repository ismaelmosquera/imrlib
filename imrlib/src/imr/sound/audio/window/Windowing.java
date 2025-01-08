/*
 * Copyright (c) 2023-2024 Ismael Mosquera Rivera
 *
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

/*
* Windowing.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.sound.audio.window;


/**
* The <code>Windowing</code> class acts as an abstract class wuitable to perform windowing.
* <p>
* Windowing is useful to perform spectral analysis.
* <p>
* You can choose among the following window types:
* <ul>
* <li>BlackmanHarris92Window</li>
* <li>GaussianWindow</li>
* <li>HammingWindow</li>
* <li>TriangularWindow</li>
* </ul>
* <p>
* to apply windowing to the input signal.
* <p>
* Default: apply window = true
*
* @see imr.sound.audio.analysis.SpectralAnalyzer
* @see imr.sound.audio.window.WindowType
*
* @author Ismael Mosquera Rivera.
*/
public abstract class Windowing
{

/**
* Constructor.
*
*/
public Windowing()
{
	_applyWindow = true;
_wndBlackmanHarris = new BlackmanHarris92Window();
_wndGaussian = new GaussianWindow();
_wndHamming = new HammingWindow();
_wndTriangular = new TriangularWindow();
_window = _wndHamming;
}

/**
* This method sets whether windowing is applied or not.
* <p>
* @param b Boolean value.
* <p>
* If b = true windowing is applied; otherwise is not.
* <p>
* Default = true
* @see imr.sound.audio.window.Window
* @see imr.sound.audio.window.WindowType
*
*/
public void applyWindow(boolean b)
{
_applyWindow = b;
}

/**
* Sets the window type to be applied to the incoming signal.
* <p>
* @param type Window type
*
* @see imr.sound.audio.window.WindowType
*
*/
public void setWindowType(int type)
{
	_wType = type;
	switch(_wType)
	{
		case WindowType.wndBlackmanHarris92:
		_window = _wndBlackmanHarris;
		break;
		case WindowType.wndGaussian:
		_window = _wndGaussian;
		break;
		case WindowType.wndHamming:
		_window = _wndHamming;
		break;
		case WindowType.wndTriangular:
		_window = _wndTriangular;
		break;
		default:
		_window = _wndHamming;
	}
}

/**
* Gets the current window type.
* <p>
* @return window type
*
* @see imr.sound.audio.window.WindowType
*
*/
public int getWindowType()
{
	return _wType;
}


/**
* This method must be implemented in subclasses of <code>Windowing</code>
* @param x Floating point vector to apply windowing.
*
*/
protected abstract void applyWindow(float[] x);


protected boolean _applyWindow;
private int _wType;

protected Window _window;
private BlackmanHarris92Window _wndBlackmanHarris;
private GaussianWindow _wndGaussian;
private HammingWindow _wndHamming;
private TriangularWindow _wndTriangular;
}

// END
