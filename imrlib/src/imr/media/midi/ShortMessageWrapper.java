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
* ShortMessageWrapper.javas
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.media.midi;

import javax.sound.midi.ShortMessage;
import javax.sound.midi.InvalidMidiDataException;

/**
* The wrap method of the <code>ShortMessageWrapper</code> class
* takes a <code>imr.media.midi.MIDIMessage</code>object  as parameter
* and returns a <code>javax.sound.midi.ShortMessage</code> object
* @author Ismael Mosquera Rivera
*/
public class ShortMessageWrapper
{

/**
* @param msg a <code>MIDIMessage</code> object
* @return a <code>ShortMessage</code> object
*/
public static ShortMessage wrap(MIDIMessage msg)
{
ShortMessage m;
try
{
m = new ShortMessage();
m.setMessage(msg.get(0),msg.get(1),msg.get(2));
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
