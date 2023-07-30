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
* AudioRecorder.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.media.audio;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
* The <code>AudioRecorder</code> class provides funcionality
* to record digital audio for the following formats:
* <ul>
* <li>wav</li>
* <li>aif</li>
* <li>au</li>
* </ul>
* You can record audio, listen the recorded audio using the play method,
* and save your recorded audio in a file ( *.wav, *.aif, *.au ).
* @author Ismael Mosquera Rivera
*/
public class AudioRecorder
{

/**
* Constructor.
* Makes a new instance of an AudioRecorder object.
* Using this constructor the default value for sample rate is 44100.f
*/
public AudioRecorder()
{
this(44100.0f);
}

/**
* Constructor.
* Makes a new instance of an AudioRecorder object.
* @param sampleRate The sample rate value for recording.
* Allowed sr values: 11025.0f, 22050.0f, 44100.0f
*/
public AudioRecorder(float sampleRate)
{
	assert ((int)sampleRate == 11025 || (int)sampleRate == 22050 || (int)sampleRate == 44100): "Allowed sample rate values: 11025 | 22050 | 44100.";
_sampleRate = sampleRate;
_hasRecordedAudio = false;
_stopRequested = false;
_isRecording = false;
_isPlaying = false;
_totalBytesRec = 0;

try
{
AudioFormat format = new AudioFormat(
	AudioFormat.Encoding.PCM_SIGNED,
		_sampleRate,
		BITS_PER_SAMPLE,
		NUM_CHANNELS,
		NUM_CHANNELS * 2,
		_sampleRate,
		false);
if((int)_sampleRate == 11025)
{
_bufferSize = 11024;
}
else if((int)_sampleRate == 22050)
{
_bufferSize = 22100;
}
else
{
_bufferSize = 44100;
}
_recorder = AudioSystem.getTargetDataLine(format);
_player = AudioSystem.getSourceDataLine(format);
_player.open(format, _bufferSize);
_recorder.open(format, _bufferSize);
}
catch(LineUnavailableException e)
{
	System.out.println("AudioRecorder: Unable to set recorder.");
	System.out.println(e);
}
}

/**
* Starts Recording.
* To finish, call the stop method.
*/
public void rec()
{
if(_isPlaying || _isRecording) return;
(new Thread(new rec_thread_code())).start();
}

/**
* Starts playing if there is recorded audio.
* To stop playback call the stop method.
*/
public void play()
{
	if(!_hasRecordedAudio) return;
if(_isPlaying || _isRecording) return;
(new Thread(new play_thread_code())).start();
}

/**
* Stops if playing or recording.
*/
public void stop()
{
_stopRequested = true;
}

/**
* Stores the recorded audio.
* @param filename The file name where to store the audio data.
* Allowed audio file extensions: *.wav, *.aif, *.au
*
* @throws UnsupportedAudioFileException
*/
public void save(String filename) throws UnsupportedAudioFileException
{
	AudioFileFormat.Type fileType = getFileType(filename);
	if(fileType == null) throw new UnsupportedAudioFileException(filename + " cannot be saved. Supported extensions: *.aif, *.au, *.wav");
	AudioInputStream audio = new AudioInputStream(new ByteArrayInputStream(_recBuffer.toByteArray()), _recorder.getFormat(),
	_recBuffer.toByteArray().length / _recorder.getFormat().getFrameSize());
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


private int _bufferSize;
private long _totalBytesRec;
private float _sampleRate;
private boolean _hasRecordedAudio;
private boolean _stopRequested;
private boolean _isRecording;
private boolean _isPlaying;

private ByteArrayOutputStream _recBuffer;
private TargetDataLine _recorder;
private SourceDataLine _player;


private class rec_thread_code implements Runnable
{
public void run()
{
	_recorder.flush();
_recorder.start();
_isRecording = true;
_stopRequested = false;

int bytesRead = 0;
byte[] data = new byte[_bufferSize];
_totalBytesRec = 0;
_recBuffer = new ByteArrayOutputStream();
while(!_stopRequested)
{
bytesRead = _recorder.read(data, 0, _bufferSize);
if(bytesRead > 0) _recBuffer.write(data, 0, bytesRead);
_totalBytesRec += bytesRead;
}
if(_recorder.isActive()) _recorder.stop();
_isRecording = false;
_hasRecordedAudio = true;
}
}

private class play_thread_code implements Runnable
{
public void run()
{
	_player.flush();
_player.start();
_isPlaying = true;
_stopRequested = false;

int bytesRead = 0;
long totalBytesRead = 0;
byte[] data = new byte[_bufferSize];
ByteArrayInputStream input = new ByteArrayInputStream(_recBuffer.toByteArray());
while(totalBytesRead < _totalBytesRec && !_stopRequested)
{
	bytesRead = input.read(data,0,_bufferSize);
		if(bytesRead > 0) _player.write(data,0,bytesRead);
		totalBytesRead += bytesRead;
}
if(_player.isActive()) _player.stop();
_isPlaying = false;
}
}

private static final int NUM_CHANNELS = 2;
private static final int BITS_PER_SAMPLE = 16;
}

// END
