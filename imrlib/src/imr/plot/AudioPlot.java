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
* AudioPlot.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.plot;

import imr.sound.audio.FrameFactory;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;

/**
* The <code>AudioPlot</code> class implements a visualizer suitable to plot audio data.
* <p>
* @see imr.plot.Plot the base class
* @see imr.plot.DataRenderer the renderer used to draw audio data.
* <p>
* @author Ismael Mosquera Rivera
*
*/
public final class AudioPlot extends Plot
{
/**
* Constructor.
* <p>
* Makes a new instance for a <code>AudioPlot</code> object.
*
*/
public AudioPlot()
{
super();
addRenderer("Axis", new ZeroAxisRenderer());
addRenderer("Data", new DataRenderer());
getRenderer("Axis").setColor(Color.BLUE);
getRenderer("Data").setColor(Color.BLUE);
getRenderer("Data").setVBounds(-1.0f, 1.0f);
((DataRenderer)getRenderer("Data")).setHugeMode(true);
refresh();
}

/**
* Sets the audio data.
* <p>
* @param audio the audio data contained in an <code>AudioInputStream</code> object.
* <p>
* @see javax.sound.sampled.AudioInputStream the data passed as parameter.
*
*/
public void setData(AudioInputStream audio)
{
((DataRenderer)getRenderer("Data")).setData(getSamples(audio));
refresh();
}


private float[] getSamples(AudioInputStream input)
{
ByteArrayOutputStream samples = new ByteArrayOutputStream();
byte[] data = new byte[22100];
int bytesRead = 0;
while(true)
{
	try
	{
		bytesRead = input.read(data, 0, data.length);
		if(bytesRead == 0 || bytesRead == -1) break;
		samples.write(data, 0, bytesRead);
	}
	catch(IOException e)
	{
		e.printStackTrace();
	}
}
return FrameFactory.getFrame(samples.toByteArray());
}

}

// END
