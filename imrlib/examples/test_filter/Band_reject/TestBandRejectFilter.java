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
* TestBandRejectFilter.java
*
* Author: Ismael Mosquera Rivera
*
*/

import imr.math.Range;
import imr.util.ByteBuffer;
import imr.util.iArray;
import imr.sound.audio.AudioFileIO;
import imr.sound.audio.FrameFactory;
import imr.sound.audio.analysis.FrameShifter;
import imr.sound.audio.filter.Filter;
import imr.sound.audio.filter.BandRejectFilter;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax .sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;

/*
* This example demonstrates the class BandRejectFilter.
* Input signal => 100hz, 200Hz, 300Hz, 400Hz, 500Hz, 600Hz, 700Hz, 800Hz, 900Hz, 1000Hz
* Band = [300Hz .. 800Hz]
* Output signal => 100Hz, 200Hz, 300Hz, -- , -- , -- , -- , 800Hz, 900Hz, 1000Hz
*
* Every signal chunk has a duration of 500ms.
*
*/
public class TestBandRejectFilter
{
public static void main(String[] args)
{
// initial frame size ( first frame )
int frame_length = FRAME_SIZE;
int hop_size = HOP_SIZE;
// we substract the hop size for the rest of frames
int frame_size = (frame_length - hop_size);
System.out.println("Loading audio file ... <- ../../sound/audio_test.wav");
// load audio file to filter
AudioInputStream input = null;
try
{
input = AudioFileIO.load("../../sound/audio_test.wav");
}
catch(UnsupportedAudioFileException e)
{
e.printStackTrace();
}

float sample_rate = input.getFormat().getSampleRate();

// first frame
byte[] frame = new byte[frame_length];
// frame shifter has always FRAME_SIZE constant value
FrameShifter shifter = new FrameShifter(frame_length);
System.out.println("Filtering ...");
// read first audio frame
try
{
input.read(frame, 0, frame.length);
}
catch(IOException e)
{
e.printStackTrace();
}
// add first frame
shifter.add(frame);
// now, set hop size to shift
shifter.setShiftSize(hop_size);
// byte buffer to save filtered frames
ByteBuffer buffer = new ByteBuffer();

// Filter first frame
Filter filter = new BandRejectFilter();
filter.setSize(FILTER_SIZE);
filter.setSampleRate(sample_rate);
filter.setBand(new Range(300.0f, 800.0f));
float[] x = filter.filter(FrameFactory.getFrame(shifter.getFrame()));
// add first frame to the byte bbuffer
buffer.add(FrameFactory.getFrame(x));
// Process the rest of frames
int bytesRead = 0;
// rest of frames have FRAME_SIZE - HOP_SIZE length
// since we shift hop size samples, we always have FRAME_SIZE samples.
frame = new byte[frame_size];
// read the rest of audio data
while(true)
{
	try
	{
	bytesRead = input.read(frame, 0, frame.length);
}
catch(IOException e)
{
e.printStackTrace();
}
// exit loop condition
	if(bytesRead == -1 || bytesRead == 0) break;
	// the last frame could be smaller
	if(bytesRead < frame_size)
	{
		// in that case, update frame size
	frame = (byte[])iArray.resize(frame, bytesRead);
	shifter.setFrameSize(hop_size+bytesRead);
	}
	shifter.add(frame);
	x = filter.filter(FrameFactory.getFrame(shifter.getFrame()));
	// take out HOP_SIZE samples to undo hop size
	x = (float[])iArray.get(x, hop_size, x.length-1);
	// and add frame to the byte buffer
	buffer.add(FrameFactory.getFrame(x));
}
// prepare to save synthesized audio file
System.out.println("Saving audio file ... -> ../../sound/band_reject.wav");
// build an AudioInputStream object using our computed data
AudioInputStream output = new AudioInputStream(new ByteArrayInputStream(buffer.toByteArray()), input.getFormat(),
	buffer.size() / input.getFormat().getFrameSize());
	try
	{
	AudioFileIO.store(output, "../../sound/band_reject.wav");
}
catch(UnsupportedAudioFileException e)
{
	e.printStackTrace();
}

}



private static final int FRAME_SIZE = 1024;
private static final int HOP_SIZE = 512;
private static final int FILTER_SIZE = FRAME_SIZE*3/4+1;

}

// END
