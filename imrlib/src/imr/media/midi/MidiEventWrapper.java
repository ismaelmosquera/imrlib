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
* MidiEventWrapper.java
*
* imr-lib
*
* Author: Ismael Mosquera rivera
*/

package imr.media.midi;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;

/**
* The wrap method of the <code>MidiEventWrapper</code> class
* takes a <code>imr.media.midi.MIDIEvent</code>object  as parameter
* and returns a <code>javax.sound.midi.MidiEvent</code> object
* @author Ismael Mosquera Rivera
*/
public class MidiEventWrapper
{

/**
* @param midiEvent a <code>MIDIEvent</code> object
* @return a <code>MidiEvent</code> object
*/
public static MidiEvent wrap(MIDIEvent midiEvent)
{
	MidiMessage msg;
	long t = midiEvent.getTicks();
	if(midiEvent instanceof MIDIMetaEvent)
	{
		msg = MetaMessageWrapper.wrap(midiEvent);
	}
	else if(midiEvent instanceof MIDISysExEvent)
	{
		msg = SysexMessageWrapper.wrap(midiEvent);
	}
	else
	{
		MIDIMessage m = new MIDIMessage(midiEvent.get(0),midiEvent.get(1),midiEvent.get(2));
		msg = ShortMessageWrapper.wrap(m);
	}
	return new MidiEvent(msg,t);
}
}

// END
