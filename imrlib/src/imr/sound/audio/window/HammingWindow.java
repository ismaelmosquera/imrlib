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
* HammingWindow.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.sound.audio.window;

/**
* This class implements a Hamming window.
*
* @see imr.sound.audio.window.Window
* @see imr.sound.audio.window.WindowType
*
* @author Ismael Mosquera Rivera
*
*/
public final class HammingWindow extends Window
{

/**
* Constructor.
* Makes a new instance for a <code>HammingWindow</code> object.
*
*/
public HammingWindow() {}

/**
* This method implements a Hamming window.
* @param w Floating point vector to be filled with the window information data
*
*/
public void get(float[] w)
{
int size = w.length;
int half_size = (int)((double)size / 2.0);
if(size % 2 != 0)
{
	w[half_size] = (float)(0.54 - 0.46 * Math.cos(2.0*Math.PI* (double)half_size / (double)(size-1)));
}
for(int i = 0; i < half_size; i++)
{
	w[i] = w[size - (i+1)] = (float)(0.54 - 0.46 * Math.cos(2.0 * Math.PI * (double)i / (double)(size-1)));
}
}

}

// END
