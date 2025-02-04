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
* MIDIFileWriter.java
*
* imr-lib
*
* Author: Ismael Mosquera rivera
*/

package imr.sound.midi;

import java.util.ListIterator;
import java.io.RandomAccessFile;
import java.io.IOException;

/**
* A <code>MIDIFileWriter</code> object writes a MIDI file
* according to the data stored in a <code>MIDISong</code> object.
* @see imr.sound.midi.MIDISong
* @author Ismael Mosquera Rivera
*/
public final class MIDIFileWriter extends MIDIFile
{

/**
* Makes a new instance of a <code>MIDIFileWriter</code> object.
*/
public MIDIFileWriter()
{
hasFile = false;
}

/**
* Makes a new instance of a <code>MIDIFileWriter</code> object
* and inits to process the file passed as parameter.
* @param fileName a file to write.
*/
public MIDIFileWriter(String fileName)
{
	init(fileName);
}

/**
* Inits to write the file passed as parameter.
* @param fileName a file to write.
* @return <code>true</code> if there was a success or <code>false</code> otherwise.
*/
public boolean init(String fileName)
{
bytesWritten = 0;
	try
	{
		file=null;
file = new RandomAccessFile(fileName,"rw");
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
* Writes a MIDI file according to the data
* stored in the <code>MIDISong</code> object passed as parameter.
* @param song a <code>MIDISong</code> object having the data to write.
* @return <code>true</code> if there was a success or <code>false</code> otherwise.
* @see imr.sound.midi.MIDISong
*/
public boolean run(MIDISong song)
{
if(!hasFile) return false;
tracks = song.tracks();
format = (tracks > 1) ? 1 : 0;
division = song.getTicksPerQ();
writeHeader();
long offset;
long end;
long t0,t1;
ListIterator<MIDITrack> songIterator = song.getIterator();
while(songIterator.hasNext())
{
	write32Bits(MTrk);
	offset = ftell();
	write32Bits(0);
bytesWritten = 0;
t0=t1=0;
MIDITrack t = songIterator.next();
ListIterator<MIDIEvent> it = t.getIterator();
while(it.hasNext())
{
MIDIEvent e = it.next();
t1 = e.getTicks();
int type = (byte)((e.get(0)>>4)&0x07);
if(type==7)
{
if(e.get(0)==(byte)0xff)
{
	writeVarLen(t1-t0);
	// write midi meta event information
	MIDIMetaEvent me = (MIDIMetaEvent)e;
writeByte(me.get(0));
writeByte(me.get(1));
writeVarLen(me.data.length);
for(int k=0;k<me.data.length;k++)
{
writeByte(me.data[k]);
}
}
else if(e.get(0)==(byte)0xf0 || e.get(0)==(byte)0xf7)
{
	writeVarLen(t1-t0);
	MIDISysExEvent sysex = (MIDISysExEvent)e;
	writeByte(sysex.get(0));
	writeVarLen(sysex.data.length-1);
	for(int q=0;q<sysex.data.length;q++)
	{
		writeByte(sysex.data[q]);
	}
}
}
else
{
writeVarLen(t1-t0); // write delta time
int msgLen = bytesPerMsg[type];
for(int j=0;j<msgLen;j++)
{
	writeByte(e.get(j));
}
}
t0=t1;
}
// write end of track
writeByte(0);
			writeByte(0xFF);
			writeByte(0x2f);
			writeByte(0);
end = ftell();
fseek(offset);
// write track length
write32Bits(bytesWritten);
// retrieve position
fseek(end);
}
fclose();
return true;
}

private void writeHeader()
{
write32Bits(MThd);
write32Bits(6);
write16Bits(format);
write16Bits(tracks);
write16Bits(division);
}

private void writeVarLen(long value)
{
long buffer;
		buffer = value & 0x7F;

		while((value >>= 7) != 0)
		{
			buffer <<= 8;
			buffer |= ((value & 0x7F) | 0x80);
		}

		while(true)
		{
			writeByte((byte)buffer);
			if((buffer & 0x80) != 0)
				buffer >>= 8;
			else
				break;
		}
	}

private void write32Bits(long data)
{
writeByte((byte)((data >> 24) & 0xff));
		writeByte((byte)((data >> 16) & 0xff));
		writeByte((byte)((data >> 8 ) & 0xff));
		writeByte((byte)(data & 0xff));
}

private void write16Bits(int data)
{
writeByte((byte)((data & 0xff00) >> 8));
		writeByte((byte)(data & 0xff));
}

private void writeByte(int b)
{
	try
	{
file.writeByte((byte)b);
}
catch(IOException e)
{
	e.printStackTrace();
}
bytesWritten++;
}

private long ftell()
{
	long offset=0;
try
{
	offset = file.getFilePointer();
}
catch(IOException e)
{
	e.printStackTrace();
	return 0;
}
return offset;
}

private void fseek(long offset)
{
try
{
	file.seek(offset);
}
catch(IOException e)
{
	e.printStackTrace();
}
}

private int bytesWritten;
private int tracks;
private int format;
private int division;
private final int MThd = 0x4d546864;
private final int MTrk = 0x4d54726b;

}

// END
