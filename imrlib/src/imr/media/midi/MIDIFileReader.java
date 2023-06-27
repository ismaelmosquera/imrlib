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
* MIDIFileReader.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.media.midi;

import java.io.RandomAccessFile;
import java.io.IOException;

/**
* A <code>MIDIFileReader</code> parses a MIDI file
* and stores the result into a <code>MIDISong</code> object.
* @see MIDISong
* @author Ismael Mosquera Rivera
*/
public final class MIDIFileReader extends MIDIFile
{

/**
* Makes a new instance of a <code>MIDIFileReader</code> object.
*/
public MIDIFileReader()
{
hasFile = false;
}

/**
* Makes a new instance of a <code>MIDIFileReader</code> object
* and inits it to process the file passed as parameter.
* @param fileName a file to be process.
*/
public MIDIFileReader(String fileName)
{
	init(fileName);
}

/**
* Inits to process the file passed as parameter.
* @param fileName a file to be process.
* @return <code>true</code> if there was a success or <code>false</code> otherwise.
*/
public boolean init(String fileName)
{
bytesRead = 0;
try
{
	file=null;
file = new RandomAccessFile(fileName,"r");
}
catch(IOException e)
{
e.printStackTrace();
return false;
}
hasFile = true;
return true;
}

/**
* Reads a MIDI file and stores the result into the <code>MIDISong</code> object passed as parameter.
* @param song a <code>MIDISong</code> object to be filled.
* @return <code>true</code> if there was a success or <code>false</code> otherwise.
* @see MIDISong
*/
public boolean run(MIDISong song)
{
if(!hasFile) return false;
	song.clear();
MIDIChunkType chunkType = getChunkType();
int headerLength;
short format;
short tracks;
if(!chunkType.equals(MThd)) return false;
headerLength = readInt();
if(headerLength != 6) return false;
format = readShort();
if((format!=0) && (format!=1)) return false;
tracks = readShort();
song.setTicksPerQ(readShort());
for(int i=0;i<tracks;i++)
{
chunkType = getChunkType();
if(!chunkType.equals(MTrk)) return false;
int chunkTypeLength = readInt();
byte runningStatus = 0;
long t=0; // ticks
MIDITrack track = new MIDITrack();
bytesRead = 0;
while(bytesRead != chunkTypeLength)
{
	long dt = readVarLen();
	t += dt;
byte b = readByte();
if((b & (byte)0x80) != 0)
{
int type = (byte)((b>>4)&0x07);
if(type==7)
{
if(b==(byte)0xff)
{
byte metaType = readByte();
int length = (int)readVarLen();
if(metaType != 0x2f)
{
MIDIMetaEvent e = new MIDIMetaEvent(new MIDIMessage(b,metaType),t,length);
for(int j=0;j<length;j++) e.data[j] = readByte();
track.addEvent(e);
if(metaType==3)
{
char[] name = new char[length];
for(int k=0;k<length;k++) name[k]=(char)e.data[k];
track.setName(name);
}
}
}
else if(b==(byte)0xf0 || b==(byte)0xf7)
{
int length = (int)readVarLen();
MIDISysExEvent sysex = new MIDISysExEvent(new MIDIMessage(b),t,length+1);
for(int q=0;q<sysex.data.length;q++)
{
sysex.data[q]=readByte();
}
track.addEvent(sysex);
if(sysex.data[length] != (byte)0xf7)
{
return false; // SysEx events terminate with 0xf7
}
}
else
{
return false; // unknow message, cannot handle it
}
runningStatus = 0;
}
else
{
if(bytesPerMsg[type]==2)
{
byte b1 = readByte();
track.addEvent(new MIDIEvent(new MIDIMessage(b,b1),t));
}
else
{
byte b1 = readByte();
byte b2 = readByte();
track.addEvent(new MIDIEvent(new MIDIMessage(b,b1,b2),t));
}
runningStatus = b;
}
}
else
{
	int type = (byte)((runningStatus>>4)&0x07);
	if(bytesPerMsg[type]==2)
	{
	track.addEvent(new MIDIEvent(new MIDIMessage(runningStatus,b),t));
	}
	else
	{
		byte b2 = readByte();
	track.addEvent(new MIDIEvent(new MIDIMessage(runningStatus,b,b2),t));
	}
}
}
song.addTrack(track);
}
fclose();
return true;
}

private byte readByte()
{
byte b=0;
try
{
	b = (byte)file.readUnsignedByte();
}
catch(IOException e)
{
	e.printStackTrace();
	return b;
}
bytesRead++;
return b;
}

private int readInt()
{
	int val=0;
	try
	{
		val = file.readInt();
	}
	catch(IOException e)
	{
	e.printStackTrace();
	return val;
	}
	bytesRead += 4;
	return val;
}

private short readShort()
{
short val=0;
try
{
	val = file.readShort();
}
catch(IOException e)
{
e.printStackTrace();
return val;
}
bytesRead +=2;
return val;
}

private MIDIChunkType getChunkType()
{
	byte[] bytes = new byte[4];
	for(int i=0;i<4;i++) bytes[i] = readByte();
	return new MIDIChunkType(bytes);
}

private long readVarLen()
{
long ret = 0;
			Byte tmp;

			tmp = readByte();
			ret = tmp&(byte)0x7F;
			while ((tmp&(byte)0x80) != 0)
			{
				tmp = readByte();
				ret <<= 7;
				ret |= (tmp&(byte)0x7F);
			}
			return ret;
}

private int bytesRead;
private static final char[] MThd = {'M','T','h','d'};
private static final char[] MTrk = {'M','T','r','k'};

private class MIDIChunkType
{
public MIDIChunkType(byte[] bytes)
{
for(int i=0;i<4;i++) data[i]=bytes[i];
}

public boolean equals(char[] str)
{
for(int i=0;i<4;i++)
{
if(data[i] != (byte)str[i]) return false;
}
return true;
}

{
data = new byte[4];
}

private byte[] data;
}

}

// END
