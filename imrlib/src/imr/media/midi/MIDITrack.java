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
* MIDITrack.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.media.midi;

import java.util.LinkedList;
import java.util.ListIterator;

/**
* A <code>MIDITrack</code> is a list of <code>MIDIEvent</code> objects.
* @see MIDIEvent
* @author Ismael Mosquera Rivera
*/
public final class MIDITrack
{

/**
* Makes a new instance of a <code>MIDITrack</code> object.
*/
public MIDITrack()
{
	name="";
eventList = new LinkedList<MIDIEvent>();
}

/**
* Sets a name for the <code>MIDITrack</code> object.
* @param aname a char aray with the name.
*/
public void setName(char[] aname)
{
	name = "";
	for(int i=0;i<aname.length;i++)
	{
		name += aname[i];
	}
}

/**
* Adds a new event to the track.
* @param e a <code>MIDIEvent</code> object to be added.
*/
public void addEvent(MIDIEvent e)
{
eventList.add(e);
}

/**
* Returns an iterator to iterate the <code>MIDIEvents</code> in the track..
* @return a list iterator to traverse the track.
*/
public ListIterator<MIDIEvent> getIterator()
{
return eventList.listIterator();
}

/**
* Checks if the track has tempo events.
* @return <code>true</code> if there are any tempo event in the list,
* or <code>false</code otherwise.
*/
public boolean hasTempoEvents()
{
if(eventList.size()<=0) return false;
ListIterator<MIDIEvent> it = getIterator();
while(it.hasNext())
{
MIDIEvent e = it.next();
if(e.get(0)==(byte)0xff && e.get(1)==(byte)0x51) return true;
}
return false;
}

/**
* Clears the track.
*/
public void clear()
{
eventList.clear();
}

private String name;
private LinkedList<MIDIEvent> eventList;
}

// END
