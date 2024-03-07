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
* TestAnalyzer.java
*
* Author: Ismael Mosquera Rivera
*
*/

import imr.util.ByteBuffer;
import imr.util.iArray;
import imr.sound.audio.AudioFileIO;
import imr.sound.audio.FrameFactory;
import imr.sound.audio.analysis.FrameShifter;
import imr.sound.audio.analysis.SpectralAnalyzer;
import imr.sound.audio.synthesis.SpectralSynthesizer;
import imr.sound.audio.analysis.Spectrum;
import imr.sound.audio.analysis.SpectrumList;
import imr.sound.audio.window.WindowType;

import java.util.ListIterator;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax .sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;

/*
* This example loads an audio file ( starwars.mp3 ), performs the analysis/synthesis
* and stores it in another audio file ( starwars.wav ).
* We coded this example to illustrate how to make analysis and synthesis ( using hop size, etc. ) so, we do nothing with the spectrums,
* just synthesize the previously analyzed audio frames.
*
* Please, read the comments in the code to know more about this subject.
*
* Author: Ismael Mosquera Rivera
*
*/
public class TestAnalyzer
{
public static void main(String[] args)
{
	// initial frame size ( first frame )
int frame_length = FRAME_SIZE;
int hop_size = HOP_SIZE;
// we sumbstract the hop size for the rest of frames
int frame_size = (frame_length - hop_size);
System.out.println("Loading audio file ... <- starwars.mp3");
// load audio file to analyze
AudioInputStream input = null;
try
{
input = AudioFileIO.load("starwars.mp3");
}
catch(UnsupportedAudioFileException e)
{
e.printStackTrace();
}

float sample_rate = input.getFormat().getSampleRate();
// first frame
byte[] frame = new byte[frame_length];
SpectralAnalyzer analyzer = new SpectralAnalyzer(sample_rate);
analyzer.setWindowType(WindowType.wndBlackmanHarris92);
// frame shifter has always FRAME_SIZE constant value
FrameShifter shifter = new FrameShifter(frame_length);
SpectrumList specList = new SpectrumList();

System.out.println("Analysis ...");
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
// compute the first spectrum
specList.addSpectrum(analyzer.analyze(FrameFactory.getFrame(shifter.getFrame())));
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
	specList.addSpectrum(analyzer.analyze(FrameFactory.getFrame(shifter.getFrame())));
}

System.out.println("Synthesis ...");
// prepare to synthesize the previously analyzed data
ByteBuffer buffer = new ByteBuffer();
SpectralSynthesizer synthesizer = new SpectralSynthesizer();
// set the same window type, so that we can undo windowing
// the spectral synthesizer does that automatically
synthesizer.setWindowType(WindowType.wndBlackmanHarris92);
// get an iterator to traverse the spectrum list
ListIterator<Spectrum> it = specList.getIterator();
// synthesize the first frame from the first spectrum
float[] x = synthesizer.synthesize(it.next());
// add first frame to the byte bbuffer
buffer.add(FrameFactory.getFrame(x));
// synthesize the rest of frames
Spectrum s = null;
while(it.hasNext())
{
	s= it.next();
x = synthesizer.synthesize(s);
// take out HOP_SIZE samples to undo hop size
x = (float[])iArray.get(x, hop_size, x.length-1);
// and add frame to the byte buffer
buffer.add(FrameFactory.getFrame(x));
}
// prepare to save synthesized audio file
System.out.println("Saving audio file ... -> starwars.wav");
// build an AudioInputStream object using our computed data
AudioInputStream output = new AudioInputStream(new ByteArrayInputStream(buffer.toByteArray()), input.getFormat(),
	buffer.size() / input.getFormat().getFrameSize());
	try
	{
	AudioFileIO.store(output, "starwars.wav");
}
catch(UnsupportedAudioFileException e)
{
	e.printStackTrace();
}
}


private static final int FRAME_SIZE = 1024;
private static final int HOP_SIZE = 64;
}

// END
