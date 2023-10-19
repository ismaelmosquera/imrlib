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
* SoundPlayer.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.media;

import imr.media.Player;
import imr.media.audio.AudioPlayer;
import imr.media.midi.MIDIPlayer;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;

/**
* The <code>SoundPlayer</code> allows playing sound files supporting
* the following extensions:
/ <ul>
* <li>mp3</li>
* <li>mid</li>
* <li>wav</li>
* <li>aif</li>
* <li>au</li>
* <li>m3u</li>
* </ul>
* So an instance of this class can play MIDI files and several
* audio file formats.
* this class also provides support to play lists if
* the set method is called passing a path to a
* directory or a *.m3u file as parameter
* having files supported by this player.
* This class also provides methods to loop and shuffle.
* @author Ismael Mosquera Rivera
*/
public final class SoundPlayer implements Player
{

	/**
	* Makes a new instance of a <code>SoundPlayer</code> object.
	*/
public SoundPlayer()
{
	playNext = false;
	loopRequested = false;
	shuffleRequested = false;
	playingListCount = 0;
	currentFile = null;
player = null;
audioPlayer = new AudioPlayer(new AudioLineListener());
midiPlayer = new MIDIPlayer(new MIDIPlaybackListener());
playingList = new PlayingList();
listenerList = new ArrayList<PlayingListListener>();
}

/**
* Adds a <code>PlayingListListener</code> to this <code>SoundPlayer</code>
* @param listener a <code>PlayingListListener</code> object to be added.
* @see imr.media.PlayingListListener
*/
public void addPlayingListListener(PlayingListListener listener)
{
listenerList.add(listener);
}

/**
* Sets the data to be played.
* @param fileName path to a regular file or to a directory
* having an arbitrary number of files supported by this class.
*/
public void set(String fileName)
{
currentFile = null;
if(player != null) player.stop();
File f = new File(fileName);
if(!f.exists()) return;
if(!f.isDirectory())
{
int index = fileName.lastIndexOf(".")+1;
String ext = fileName.substring(index).toLowerCase();
if(ext.equals("m3u"))
{
	buildM3u(fileName);
}
else
{
int fileType = getFileType(fileName);
if(fileType == AUDIO_FILE)
{
player = audioPlayer;
}
else if(fileType == MIDI_FILE)
{
player = midiPlayer;
}
else
{
return;
}
currentFile = fileName;
player.set(fileName);
if(playNext) play();
}
}
else if(f.isDirectory())
{
	buildPlayingList(f);
}
}

/**
* Sets the current file in the play list by index.
* @param index a valid index in the playing list.
* @return <code>true</code> if there was a success or <code>false</code> otherwise.
*/
public boolean set(int index)
{
if(playingList.empty()) return false;
if(index < 0 || index > playingList.size()-1) return false;
playNext = false;
playingListCount = playingList.size()-(index+1);
playingList.setCurrentIndex(index);
set(playingList.next().getPath());
notifyAllPlayListListeners();
return true;
}

/**
* Starts playing data.
*/
public void play()
{
	if(player == null) return;
if(currentFile == null) return;
	if(!playingList.empty()) playNext = true;
player.play();
}

/**
* Pauses playing data.
*/
public void pause()
{
	if(player == null) return;
if(currentFile == null) return;
player.pause();
}

/**
* Stops playing data.
*/
public void stop()
{
	if(player == null) return;
if(currentFile == null) return;
	if(!playingList.empty())
	{
		playNext = false;
		playingListCount = playingList.size();
		playingList.reset();
		if(shuffleRequested)
		{
			int i = (int)(playingList.size()*Math.random());
			player.set(playingList.get(i).getPath());
		}
		else
		{
			playingListCount--;
		set(playingList.next().getPath());
	}
	}
	notifyAllPlayListListeners();
player.stop();
}

/**
* Sets loop to <code>true</code> or <code>false</code>
* @param b a boolean value, default is false.
*/
public void loop(boolean b)
{
loopRequested = b;
}

/**
* Sets shuffle to <code>true</code> or <code>false</code>
* if shuffle is set to tru, files in the list are played random.
* @param b a boolean value, default is false.
* Is shuffle is set to false the files in the list are played sequentially.
*/
public void shuffle(boolean b)
{
shuffleRequested = b;
}

/**
* Retrieves a list containing the files in the playing list.
* @return an array having all the file names in the playing list.
*/
public String[] files()
{
if(currentFile == null) return new String[0];
String[] s = null;
if(playingList.empty())
{
	File f = new File(currentFile);
s = new String[1];
s[0] = f.getName();
}
else
{
int length = playingList.size();
s = new String[length];
for(int i=0;i<s.length;i++)
{
	s[i] = playingList.get(i).getName();
}
}
return s;
}

/**
* Clears the playing list for this player.
* If you call the set method of the player with a path
* to a regular file as parameter, you must call this method before so the playing list if any is cleared
* and the player just have the current regular file.
* To avoid unexpected behaviour is better that you call this method
* always before calling the set method.
*/
public void clear()
{
playingListCount = 0;
playingList.reset();
playingList.clear();
}

/**
* Returns the first indexafter loading files.
* @return first index in the list.
*/
public int firstIndex()
{
return playingList.getCurrentIndex();
}

private void buildPlayingList(File dir)
{
clear();
try
{
String[] files = dir.list();
for(int i=0;i < files.length;i++)
{
	File f = new File(dir.getPath(), files[i]);
	if(f.isDirectory()) continue;
	playingList.add(f.getPath());
}
}
catch(Exception e)
{
	System.out.println("SoundPlayer::buildPlayingList(File dir): "+e);
}
initPlayback();
}

private void buildM3u(String fileName)
{
clear();
	File inputFile = new File(fileName);
	String parent = inputFile.getParent();
Scanner in = null;
try
{
in = new Scanner(new File(fileName));
while(in.hasNextLine())
{
	String file = in.nextLine();
File f = new File(parent, file);
	playingList.add(f.getPath());
}
}
catch(FileNotFoundException e)
{
	System.out.println("SoundPlayer::buildM3u(String fileName): "+e);
}
finally
{
	if(in != null) in.close();
}
initPlayback();
}

private void initPlayback()
{
	if(!playingList.empty())
	{
		playingListCount = playingList.size();
		if(shuffleRequested)
		{
			int i = (int)(playingList.size()*Math.random());
			set(playingList.get(i).getPath());
		}
		else
		{
			playingListCount--;
		set(playingList.next().getPath());
	}
	notifyAllPlayListListeners();
	}
}

private int getFileType(String fileName)
{
	int index = fileName.lastIndexOf(".") + 1;
String ext = fileName.substring(index).toLowerCase();
if(ext.equals("mp3") || ext.equals("wav") || ext.equals("aif") || ext.equals("au"))
{
return AUDIO_FILE;
}
else if(ext.equals("mid"))
{
return MIDI_FILE;
}
return -1;
}

private void handlePlayback()
{
if(shuffleRequested)
{
int i =  (int)(playingList.size()*Math.random());
set(playingList.get(i).getPath());
}
else
{
if(playingListCount > 0)
				{
					playNext = true;
					playingListCount--;
				}
				else
				{
					playNext = (loopRequested) ? true : false;
					playingListCount = playingList.size()-1;
					playingList.reset();
				}
				set(playingList.next().getPath());
			}
			notifyAllPlayListListeners();
}

private void notifyAllPlayListListeners()
{
if(playingList.empty()) return;
	int index = playingList.getCurrentIndex();
	String fileName = playingList.get(index).getName();
for(int i= 0;i < listenerList.size(); i++)
{
listenerList.get(i).listItemChanged(index, fileName);
}
}

private boolean playNext;
private boolean loopRequested;
private boolean shuffleRequested;
private int playingListCount;
private String currentFile;
private Player player;
private Player audioPlayer;
private Player midiPlayer;
private PlayingList playingList;
private ArrayList<PlayingListListener> listenerList;
private static final int AUDIO_FILE = 0;
private static final int MIDI_FILE = 1;

private class AudioLineListener implements LineListener
{
public void update(LineEvent event)
{
	if(event.getType() == LineEvent.Type.STOP)
	{
		if(((AudioPlayer)player).completed())
		{
			if(playingList.empty())		{
				if(loopRequested) play();
			}
			else
			{
				handlePlayback();
			}
			}
	}
}

}

private class MIDIPlaybackListener implements MetaEventListener
{
public void meta(MetaMessage event)
{
	if(event.getType() == END_OF_TRACK)
	{
if(playingList.empty())
{
	if(loopRequested) play();
}
else
{
	handlePlayback();
}
	}
}

private static final int END_OF_TRACK = 47;
}

private class PlayingList
{
public PlayingList()
{
	index = 0;
	currentIndex = 0;
	list = new ArrayList<File>();
}

public boolean add(String fileName)
{
	File f = new File(fileName);
	if(!f.exists()) return false;
String ext = getFileExtension(fileName);
	if(!isFileSupported(ext)) return false;
list.add(f);
	return true;
}

public File next()
{
if(index == list.size()) index = 0;
currentIndex = index;
return list.get(index++);
}

public File get(int i)
{
	currentIndex = i;
return list.get(i);
}

public int getCurrentIndex()
{
return currentIndex;
}

public void setCurrentIndex(int i)
{
index = i;
currentIndex = index;
}

public void clear()
{
list.clear();
}

public void reset()
{
index = 0;
}

public int size()
{
return list.size();
}

public boolean empty()
{
return (size() == 0);
}

private boolean isFileSupported(String fileExtension)
{
for(int i=0;i<supportedFiles.length;i++)
{
String s = fileExtension.toLowerCase();
if(supportedFiles[i].equals(s)) return true;
}
return false;
}

private String getFileExtension(String fileName)
{
int i = fileName.lastIndexOf(".")+1;
return fileName.substring(i).toLowerCase();
}

private int index;
private int currentIndex;
private ArrayList<File> list;
private String[] supportedFiles = {"mp3","mid","wav","aif","au"};
}

}

// END
