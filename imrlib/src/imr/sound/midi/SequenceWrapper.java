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
* SequenceWrapper.java
*
* imr-lib
*
* Author: Ismael Mosquera rivera
*/

package imr.sound.midi;

import javax.sound.midi.Track;
import javax.sound.midi.Sequence;
import javax.sound.midi.InvalidMidiDataException;
import java.util.ListIterator;

/**
* The wrap method of the <code>SequenceWrapper</code> class
* takes a <code>imr.media.midi.MIDISong</code>object  as parameter
* and returns a <code>javax.sound.midi.Sequence</code> object
* @author Ismael Mosquera Rivera
*/
public class SequenceWrapper
{

/**
 * @param song a <code>MIDISong</code> object
* @return a <code>Sequence</code> object
*/
public static Sequence wrap(MIDISong song)
{
int tracks = song.tracks();
int resolution = song.getTicksPerQ();
Sequence sequence;
try
{
sequence = new Sequence(Sequence.PPQ,resolution,tracks);
}
catch(InvalidMidiDataException e)
{
e.printStackTrace();
return null;
}
int i = 0;
Track[] trackList = sequence.getTracks();
ListIterator<MIDITrack> sit = song.getIterator();
while(sit.hasNext())
{
MIDITrack trk = sit.next();
ListIterator<MIDIEvent> it = trk.getIterator();
while(it.hasNext())
{
MIDIEvent e = it.next();
trackList[i].add(MidiEventWrapper.wrap(e));
}
i++;
}
return sequence;
}
}

// END
