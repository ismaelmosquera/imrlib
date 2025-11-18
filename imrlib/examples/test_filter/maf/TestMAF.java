/*
 * Copyright (c) 2025 Ismael Mosquera Rivera
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
* TestMAF.java
*
* Author: Ismael Mosquera Rivera
*
*/

import imr.util.FloatBuffer;
import imr.util.ByteBuffer;
import imr.util.iArray;
import imr.sound.audio.AudioFileIO;
import imr.sound.audio.FrameFactory;
import imr.sound.audio.filter.MovingAverageFilter;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax .sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;

/*
* This example demonstrates the class MovingAverageFilter.
*
*/
public class TestMAF
{
public static void main(String[] args)
{
int frame_size = FRAME_SIZE;
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
byte[] frame = new byte[frame_size];
System.out.println("Filtering ...");
MovingAverageFilter filter = new MovingAverageFilter(2);
// byte buffer to save filtered frames
ByteBuffer buffer = new ByteBuffer();
FloatBuffer fbuffer = new FloatBuffer();
int bytesRead = 0;
float[] x = null;
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
	}
	fbuffer.add(FrameFactory.getFrame(frame));
}
x = filter.filter(fbuffer.toFloatArray());
// and add frame to the byte buffer
	buffer.add(FrameFactory.getFrame(x));
// prepare to save synthesized audio file

filter.reset();

System.out.println("Saving audio file ... -> ../../sound/maf_audio_test.wav");
// build an AudioInputStream object using our computed data
AudioInputStream output = new AudioInputStream(new ByteArrayInputStream(buffer.toByteArray()), input.getFormat(),
	buffer.size() / input.getFormat().getFrameSize());
	try
	{
	AudioFileIO.store(output, "../../sound/maf_audio.wav");
}
catch(UnsupportedAudioFileException e)
{
	e.printStackTrace();
}

}


private static final int FRAME_SIZE = 1024;

}

// END
