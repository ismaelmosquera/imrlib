/*
 * Copyright (c) 2021-2022 Ismael Mosquera Rivera
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
* AudioPlayer.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.sound.audio;

import imr.sound.Player;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineListener;

/**
* The <code>AudioPlayer</code> class provides funcionality
* to play digital audio for the following formats:
* <ul>
* <li>mp3</li>
* <li>wav</li>
* <li>aif</li>
* <li>au</li>
* </ul>
* Also provides a method to know if the audio data loaded
* was completely played.
* @author Ismael Mosquera Rivera
*/
public class AudioPlayer implements Player
{

/**
* Makes a new instance of an <code>AudioPlayer</code> object.
*/
public AudioPlayer()
{
bufferSize = 0;
hasAudio = false;
isCompleted = false;
isStopped = false;
newFileRequested = false;
currentFile = "";
lineListener = null;
}

/**
* Makes a new instance of an <code>AudioPlayer</code> object
* and sets a <code>LineListener</code> to it.
* @param listener a listener to this object.
@see javax.sound.sampled.LineListener
*/
public AudioPlayer(LineListener listener)
{
this();
lineListener = listener;
}

/**
* Sets the audio data to be played.
* @param audioFile path to an audio file.
*/
public void set(String audioFile)
{
newFileRequested = true;
stop();
	loadAudio(audioFile);
hasAudio = true;
currentFile = audioFile;
}

/**
* Starts playing data.
*/
public void play()
{
	if(!hasAudio) return;
	if(!isStopped) set(currentFile);
	isCompleted = false;
	isStopped = false;
	Thread t = new Thread(new thread_code());
	t.start();
}

/**
* Pauses playing data.
*/
public void pause()
{
if(!hasAudio) return;
isCompleted = false;
	isStopped = true;
	if(player.isActive()) player.stop();
}

/**
* Stops playing data.
*/
public void stop()
{
if(!hasAudio) return;
isCompleted = false;
isStopped = true;
if(player.isActive()) player.stop();
if(!newFileRequested)
{
loadAudio(currentFile);
}
newFileRequested = false;
}

/**
* Checks if all the data was completely played.
* In other words, if the player has reached the end of file.
* @return <code>true</code> if end of file was reached or <code>false</code> otherwise.
*/
public boolean completed()
{
return isCompleted;
}

private void loadAudio(String audioFile)
{
	int i = audioFile.lastIndexOf(".")+1;
	String ext = audioFile.substring(i).toLowerCase();
	try
	{
		if(hasAudio) player.flush();
	AudioInputStream rawInput	= AudioSystem.getAudioInputStream(new File(audioFile));
	AudioFormat baseFormat = rawInput.getFormat();
	AudioFormat decodedFormat = new AudioFormat(
		AudioFormat.Encoding.PCM_SIGNED,
		baseFormat.getSampleRate(),
		16,
		baseFormat.getChannels(),
		baseFormat.getChannels()*2,
		baseFormat.getSampleRate(),
		baseFormat.isBigEndian());
		bufferSize = (int)baseFormat.getSampleRate();
		bufferSize -= (bufferSize % 4);
		if(ext.equals("wav"))
		{
			audio = rawInput;
}
else
{
	audio = AudioSystem.getAudioInputStream(decodedFormat, rawInput);
}
	player = AudioSystem.getSourceDataLine(decodedFormat);
	if(lineListener != null) player.addLineListener(lineListener);
	player.open(decodedFormat);
}
catch(LineUnavailableException e)
{
System.out.println("AudioPlayer::loadAudio(String): player unavailable.");
System.out.println(e);
}
catch(IOException ioe)
	{
		System.out.println("AudioPlayer::loadAudio(String audioFile): no such file.");
		System.out.println(ioe);
	}
	catch(UnsupportedAudioFileException afe)
	{
		System.out.println("AudioPlayer::loadAudio(String audioFile): unsupported audio file.");
		System.out.println(afe);
	}
}

private int bufferSize;
private boolean hasAudio;
private boolean isStopped;
private boolean isCompleted;
private boolean newFileRequested;
private String currentFile;
private LineListener lineListener;
private SourceDataLine player;
private AudioInputStream audio;

private class thread_code implements Runnable
{
	public void run()
	{
player.start();
int bytesRead = 0;
byte[] data = new byte[bufferSize];
while(!isStopped)
{
	try
	{
	bytesRead = audio.read(data,0,data.length);
	if(bytesRead == -1) break;
		player.write(data,0,bytesRead);
}
catch(IOException e)
{
	System.out.println("AudioPlayer::play(): IOException");
	System.out.println(e);
}
}

try
{
	Thread.sleep(1000);
}
catch(InterruptedException e) {}

if(!isStopped) isCompleted = true;
if(player.isActive()) player.stop();
	}
}

}

// END
