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
* BlackmanHarris92Window.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.sound.audio.window;

/**
* This class implements a Blackman Harris 92 dB window.
*
* @see imr.sound.audio.window.Window
* @see imr.sound.audio.window.WindowType
*
* @author Ismael Mosquera Rivera
*
*/
public final class BlackmanHarris92Window extends Window
{

/**
* Constructor.
*<p>
* Makes a new instance for a <code>BlackmanHarris92Window</code> object.
*
*/
public BlackmanHarris92Window() {}

/**
* This method implements a Blackman Harris 92 dB window.
* @param w Floating point vector to be filled with the window information data.
*
*/
public void get(float[] w)
{
int size = w.length;
int half_size = (int)((double)size/2.0);
double c = (2.0*Math.PI) / (double)(size-1);
double p0 = 0.35875;
double p1 = 0.48829;
double p2 = 0.14128;
double p3 = 0.01168;
if(size % 2 != 0)
{
w[half_size] = (float)(p0-p1*Math.cos(c*((int)(size/2)))+p2*Math.cos(c*2*((int)(size/2)))-p3*Math.cos(c*3*((int)(size/2))));
}
for(int i = 0; i < half_size; i++)
{
	w[i] = w[size-(i+1)] = (float)(p0-p1*Math.cos(c*i)+p2*Math.cos(c*2*i)-p3*Math.cos(c*3*i));
}
}

}

// END
