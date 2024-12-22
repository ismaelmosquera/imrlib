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
* MIDISong.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.sound.midi;

import java.util.LinkedList;
import java.util.ListIterator;

/**
* A <code>MIDISong</code> is a list of <code>MIDITrack</code> objects.
* @see imr.sound.midi.MIDITrack
* @author Ismael Mosquera Rivera
*/
public final class MIDISong
{

/**
* Makes a new instance of a <code>MIDISong</code> object.
*/
public MIDISong()
{
ticksPerQ=0;
trackList = new LinkedList<MIDITrack>();
}

/**
* Adds a track to the song.
* @param t a track to be added.
*/
public void addTrack(MIDITrack t)
{
trackList.add(t);
}

/**
* Gets an iterator to traverse the tracks in the song.
* @return list iterator to iterate the tracks in the song.
*/
public ListIterator<MIDITrack> getIterator()
{
return trackList.listIterator();
}

/**
* Gets the number of tracks in the song.
* @return the number of tracks in the song.
*/
public int tracks()
{
return trackList.size();
}

/**
* Gets the ticks per quarter for the song.
* @return ticks per quarter.
*/
public int getTicksPerQ()
{
return ticksPerQ;
}

/**
* Sets the ticks per quarter for the song.
* @param t ticks per quarter.
*/
public void setTicksPerQ(int t)
{
ticksPerQ=t;
}

/**
* Clears the song.
*/
public void clear()
{
ListIterator<MIDITrack> it = getIterator();
while(it.hasNext())
{
it.next().clear();
}
trackList.clear();
}

private int ticksPerQ;
private LinkedList<MIDITrack> trackList;
}

// END
