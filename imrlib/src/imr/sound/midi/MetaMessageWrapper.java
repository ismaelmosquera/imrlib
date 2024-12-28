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
* MetaMessageWrapper.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.sound.midi;

import javax.sound.midi.MetaMessage;
import javax.sound.midi.InvalidMidiDataException;

/**
* The wrap method of the <code>MetaMessageWrapper</code> class
* takes a <code>imr.sound.midi.MIDIMetaEvent</code> as parameter
* and returns a <code>javax.sound.midi.MetaMessage</code> object
* @author Ismael Mosquera Rivera
*/
public class MetaMessageWrapper
{

/**
* @param midiEvent a <code>MIDIMetaEvent</code> object
* @return a <code>MetaMessage</code> object
* Note that MIDIEvent is a super class of MIDIMetaEvent
*/
public static MetaMessage wrap(MIDIEvent midiEvent)
{
	if(!(midiEvent instanceof MIDIMetaEvent)) return null;
	MIDIMetaEvent metaEvent = (MIDIMetaEvent)midiEvent;
MetaMessage m;
try
{
m = new MetaMessage();
m.setMessage(metaEvent.get(1)		,metaEvent.data,metaEvent.data.length);
}
catch(InvalidMidiDataException e)
{
e.printStackTrace();
return null;
}
return m;
}
}

// END
