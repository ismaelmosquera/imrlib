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
* AudioFileIO.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*
*/

package imr.sound.audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
* The <code>AudioFileIO</code> class has static methods to load and store audio files.
* <p>
* Supported audio file formats for loading:
* <ul>
* <li>MP3 ( *.mp3 )</li>
* <li>AIFF ( *.aif )</li>
* <li>AU ( *.au )</li>
* <li>WAVE ( *.wav )</li>
* </ul>
* <p>
* Supported audio file formats for storage:
* <ul>
* <li>AIFF ( *.aif )</li>
* <li>AU ( *.au )</li>
* <li>WAVE ( *.wav )</li>
* </ul>
* To load *.mp3 files you must have the mp3plugin.jar file in your classpath.
*
* @author Ismael Mosquera Rivera
*
*/
public final class AudioFileIO
{

/**
* Loads an audio file.
* @param filename Path to the file to be loaded.
* <p>
* Supported audio file formats for loading:
* <ul>
* <li>MP3 ( *.mp3 )</li>
* <li>AIFF ( *.aif )</li>
* <li>AU ( *.au )</li>
* <li>WAVE ( *.wav )</li>
* </ul>
* <p>
* To load *.mp3 files you must have the mp3plugin.jar file in your classpath.
*
* @see javax.sound.sampled.AudioInputStream
*
* @throws javax.sound.sampled.UnsupportedAudioFileException Not supported audio format.
*
* @return An <code>AudioInputStream</code> object containing the loaded data
*
*/
public static AudioInputStream load(String filename) throws UnsupportedAudioFileException
{
int i = filename.lastIndexOf(".")+1;
	String ext = filename.substring(i).toLowerCase();
if(!ext.equals("mp3") && !ext.equals("wav") && !ext.equals("aif") && !ext.equals("au")) throw new UnsupportedAudioFileException(filename + " cannot be loaded. Supported extensions: *.mp3, *.aif, *.au, *.wav");
AudioInputStream rawInput = null;
AudioFormat decodedFormat = null;
try
	{
	rawInput	= AudioSystem.getAudioInputStream(new File(filename));
	AudioFormat baseFormat = rawInput.getFormat();
	decodedFormat = new AudioFormat(
		AudioFormat.Encoding.PCM_SIGNED,
		baseFormat.getSampleRate(),
		16,
		baseFormat.getChannels(),
		baseFormat.getChannels()*2,
		baseFormat.getSampleRate(),
		false);
	}
	catch(IOException e)
	{
		e.printStackTrace();
	}
	return AudioSystem.getAudioInputStream(decodedFormat, rawInput);
}

/**
* Stores an audio file.
* @param audio An <code>AudioInputStream</code> object containing the audio file data to be stored.
* @param filename Path to where the file will be stored.
* <p>
* Supported audio file formats for storage:
* <ul>
* <li>AIFF ( *.aif )</li>
* <li>AU ( *.au )</li>
* <li>WAVE ( *.wav )</li>
* </ul>
*
* @see javax.sound.sampled.AudioInputStream
*
* @throws javax.sound.sampled.UnsupportedAudioFileException Not supported audio format.
*
*/
public static void store(AudioInputStream audio, String filename) throws UnsupportedAudioFileException
{
AudioFileFormat.Type fileType = getFileType(filename);
	if(fileType == null) throw new UnsupportedAudioFileException(filename + " cannot be saved. Supported extensions: *.aif, *.au, *.wav");
try
	{
AudioSystem.write(audio, fileType, new File(filename));
}
catch(IOException e)
{
	System.out.println(e);
}
}


private static AudioFileFormat.Type getFileType(String audioFile)
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


// private constructor, so that this class cannot be instantiated
private AudioFileIO() {}

}

// END
