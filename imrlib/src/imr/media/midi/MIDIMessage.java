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
* MIDIMessage.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.media.midi;

/**
* A <code>MIDIMessage</code> has 3 bytes as much:
* <ul>
* <li>status</li>
* <li>data1</li>
* <li>data2</li>
* <ul>
* The status byte encapsulates the type of message and the channel to be sent.
* <p>
Message type: bits 7..4
* <p>
Channel: bits 3..0
* <p>
* The most significant bit of the status byte is always set to 1,
* sso there are 2^3=8 different type of messages,
* and 16 channels available.
* <p>
* Example:
* <p>
* To send a NoteOn message to channel 10: 9A in hexadecimal, 10011010 in binary.
* <p>
* This class also defines static methods to get each of this messages easily.
* <ul>
* <li>NoteOff</li>
* <li>NoteOn</li>
* <li>PolyAftertouch</li>
* <li>ControlChange</li>
* <li>ProgramChange</li>
* <li>ChannelAftertouch</li>
* <li>PichBend</li>
* </ul>
* For more information see the MIDI protocol specification.
* @author Ismael Mosquera Rivera
*/
public final class MIDIMessage
{

/**
* Makes a new instance of a <code>MIDIMessage</code> object.
* @param status the status byte.
*/
	public MIDIMessage(byte status)
	{
	msg[0]=status;
	msg[1]=0;
	msg[2]=0;
	}

/**
* Makes a new instance of a <code>MIDIMessage</code> object.
* @param status the status byte.
* @param data1 the data1 byte.
*/
public MIDIMessage(byte status,byte data1)
{
	this(status);
msg[1]=data1;
}

/**
* Makes a new instance of a <code>MIDIMessage</code> object.
* @param status  the status byte.
* @param data1 the data1 byte.
* @param data2 the data2 byte.
*/
public MIDIMessage(byte status,byte data1,byte data2)
{
this(status,data1);
msg[2]=data2;
}

/**
* Gets a concret byte from a <code>MIDIMessage</code> object.
* @param i index 0..2.
* @return the byte stored in the concrete index.
*/
public byte get(int i)
{
return msg[i];
}

/**
* Sets a concrete position in a <code>MIDIMessage</code> object.
* @param i index 0..2.
* @param b data to set.
*/
public void set(int i,byte b)
{
msg[i]=b;
}

/**
* Gets a NoteOff message.
* @param channel
* @param pitch
* @param velocity
* @return a NoteOff message.
*/
public static MIDIMessage noteOff(int channel,int pitch,int velocity)
{
byte status = (byte)(0x80 | (channel & 0x0f));
return new MIDIMessage(status,(byte)pitch,(byte)velocity);
}

/**
* Gets a NoteOn message.
* @param channel
* @param pitch
* @param velocity
* @return a NoteOn message.
*/
public static MIDIMessage noteOn(int channel,int pitch,int velocity)
{
	byte status = (byte)(0x90 | (channel & 0x0f));
	return new MIDIMessage(status,(byte)pitch,(byte)velocity);
}

/**
* Gets a PolyAftertouch message.
* @param channel
* @param pitch
* @param pressure
* @return a PolyAftertouch message.
*/
public static MIDIMessage polyAftertouch(int channel,int pitch,int pressure)
{
byte status = (byte)(0xa0 | (channel & 0x0f));
return new MIDIMessage(status,(byte)pitch,(byte)pressure);
}

/**
* Gets a ControlChange message.
* @param channel
* @param controlType
* @param value
* @return a ControlChange message.
*/
public static MIDIMessage controlChange(int channel,int controlType,int value)
{
byte status = (byte)(0xb0 | (channel & 0x0f));
return new MIDIMessage(status,(byte)controlType,(byte)value);
}

/**
* Gets a ProgramChange message.
* @param channel
* @param program
* @return a ProgramChage message.
*/
public static MIDIMessage programChange(int channel,int program)
{
byte status = (byte)(0xc0 | (channel & 0x0f));
	return new MIDIMessage(status,(byte)program);
}

/**
* Gets a ChannelAftertouch message.
* @param channel
* @param pressure
* @return a ChannelAftertouch message.
*/
public static MIDIMessage channelAftertouch(int channel,int pressure)
{
byte status = (byte)(0xd0 | (channel & 0x0f));
return new MIDIMessage(status,(byte)pressure);
}

/**
* Gets a PichBend message.
* @param channel
* @param msByte
* @param lsByte
* @return a PichBend message.
*/
public static MIDIMessage pitchBend(int channel,int msByte,int lsByte)
{
	byte status = (byte)(0xe0 | (channel & 0x0f));
	return new MIDIMessage(status,(byte)msByte,(byte)lsByte);
}

{
msg = new byte[3];
}

private byte[] msg;
}

// END
