/*
 * Copyright (c) 2023 Ismael Mosquera Rivera
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
* RawDataStorage.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.media.audio;

import java.io.File;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
* The class <code>RawDataStorage</code> can be useful to accumulate and, finally, store raw audio data in an audio file.
* Supported audio file formats:
* <ul>
* <li>wav</li>
* <li>aif</li>
* <li>au</li>
* </ul>
*
* Using this class is so simple. Just make an instance for a <code>RawDataStorage</code> object
* and call its <code>add</code> method to add data to store.
* When all the raw data to store has been added, call the <code>store</code> method to store
* your data in an audio file for any of the audio file formats supported.
* If you wish to start adding data to store another file, call the <code>reset</code> method first.
* That will cause that all the data in the buffer be discarded and set the begin of the buffer to the first position.
*
* This class uses assertions.
* So, you have to use the '-ea' modifier to enable assertions.
* Example:
* <code>java -ea MyApp</code>
*
* @see imr.media.audio.RawDataPlayer
* @see imr.media.audio.Oscillator
*
* @author Ismael Mosquera Rivera
*/
public class RawDataStorage
{
/**
* Constructor.
* Makes a new instance of a new <code>RawDataStorage</code> object.
* Actually, this constructor calls the one passing as parameter a 44100 value for sample rate.
* So, we can say that 44100 is the default value for sample rate.
*
*/
public RawDataStorage()
{
this(44100.0f);
}

/**
* Constructor.
* Makes a new instance of a new <code>RawDataStorage</code> object.
* @param sr Sample rate.
*
* Allowed values: 11025, 22050 and 44100.
*
*/
public RawDataStorage(float sr)
{
	boolean condition = ((int)sr == 11025 || (int)sr == 22050 || (int)sr == 44100);
assert condition: "Allowed sample rate values: 11025 | 22050 | 44100.";
_format = new AudioFormat(
	AudioFormat.Encoding.PCM_SIGNED,
		sr,
		BITS_PER_SAMPLE,
		NUM_CHANNELS,
		NUM_CHANNELS * 2,
		sr,
		false);
_dataBuffer = new ByteArrayOutputStream();
}

/**
* Adds a data frame to the buffer for storage.
* @param data Byte array with data to store.
*
*/
public void add(byte[] data)
{
_dataBuffer.write(data, 0, data.length);
}

/**
* Resets the buffer to the first position.
* That causes the buffer to discard all the data previously added.
*
*/
public void reset()
{
_dataBuffer.reset();
}

/**
* Stores the current data in the buffer to an audio file.
* @param filename Name of the file to be stored.
* You must add the file extension to the file name.
* The extension must be any of the supported ones, otherwise, this method will launch an exception.
*
* @throws javax.sound.sampled.UnsupportedAudioFileException Not supported audio format
*/
public void store(String filename) throws UnsupportedAudioFileException
{
AudioFileFormat.Type fileType = getFileType(filename);
	if(fileType == null) throw new UnsupportedAudioFileException(filename + " cannot be saved. Supported extensions: *.aif, *.au, *.wav");
	AudioInputStream audio = new AudioInputStream(new ByteArrayInputStream(_dataBuffer.toByteArray()), _format,
	_dataBuffer.toByteArray().length / _format.getFrameSize());
	try
	{
AudioSystem.write(audio, fileType, new File(filename));
}
catch(IOException e)
{
	System.out.println(e);
}
}


private AudioFileFormat.Type getFileType(String audioFile)
{
int i = audioFile.lastIndexOf(".")+1;
	String ext = audioFile.substring(i).toLowerCase();
	if(ext.equals("wav"))
		{
	return new AudioFileFormat.Type("WAVE", "wav");
		}
		else if(ext.equals("aif"))
	{
return new AudioFileFormat.Type("AIFF", "aif");
	}
	else if(ext.equals("au"))
	{
return new AudioFileFormat.Type("AU", "au");
	}
	return null;
}


private ByteArrayOutputStream _dataBuffer;
private AudioFormat _format;

private static final int NUM_CHANNELS = 2;
private static final int BITS_PER_SAMPLE = 16;
}

// END
