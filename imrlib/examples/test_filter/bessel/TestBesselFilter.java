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
* TestLowPassFilter.java
*
* Author: Ismael Mosquera Rivera
*
*/

import imr.util.Convert;
import imr.util.ByteBuffer;
import imr.math.Range;
import imr.sound.audio.FrameFactory;
import imr.sound.audio.synthesis.Oscillator;
import imr.sound.audio.filter.iir.FilterType;
import imr.sound.audio.filter.iir.Filter;
import imr.sound.audio.filter.iir.BesselFilter;
import imr.sound.audio.RawDataPlayer;
import imr.sound.audio.RawDataStorage;

import javax.sound.sampled.UnsupportedAudioFileException;


public class TestBesselFilter
{
public static void main(String[] args)
{
ByteBuffer buffer = new ByteBuffer();
	Oscillator osc = new Oscillator(0.8f, F0, 22050.0f);
int bufferSize = (int)osc.getSampleRate();
bufferSize -= bufferSize % 4;
RawDataStorage saver = new RawDataStorage(osc.getSampleRate());
Filter filter = new BesselFilter(2, FilterType.BAND_REJECT, 700.0f, new Range(300.0, 800.0), osc.getSampleRate());
double[] y = null;
byte[] frame = new byte[bufferSize];
int i = 0;
while(true)
{
osc.read(frame);
buffer.add(frame);
if(i > f.length-1) break;
osc.setFrequency(f[i]);
i++;
}
y = filter.filter(Convert.toDoubleArray(FrameFactory.getFrame(buffer.toByteArray())));
saver.add(FrameFactory.getFrame(Convert.toFloatArray(y)));

byte[] silence = new byte[bufferSize*2];
for(int k = 0; k < silence.length; k++) silence[k] = (byte)0x00;
saver.add(silence);
System.out.println("Saving audio file ...");
try
{
saver.store("bessel_low_pass.wav");
}
catch(UnsupportedAudioFileException e)
{
	System.out.println(e);
	}
	System.out.println("bessel_low_pass.wav file stored successfully.");
}

private static float[] f = {200.0f, 300.0f, 400.0f, 500.0f, 600.0f, 700.0f, 800.0f, 900.0f, 1000.0f};
private static final float F0 = 100.0f;
}

// END

