/*
 * Copyright (c) 2024 Ismael Mosquera Rivera
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
* WindowType.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.sound.audio.window;

/**
* This class has just four constants used to set a window type properly.
* <p>
* Each constant is self explanatory.
*
* @see imr.sound.audio.window.Window
*
* @author Ismael Mosquera Rivera
*
*/
public final class WindowType
{

/**
* Constant to set a BlackmanHarris92Window
*
*/
public static final int wndBlackmanHarris92 = 0;

/**
* Constant to set a GaussianWindow
*
*/
public static final int wndGaussian = 1;

/**
* Constant to set a HammingWindow
*
*/
public static final int wndHamming = 2;

/**
* Constant to set a TriangularWindow
*
*/
public static final int wndTriangular = 3;


// private constructor, so that this class cannot be instantiated
private WindowType() {}
}

// END
