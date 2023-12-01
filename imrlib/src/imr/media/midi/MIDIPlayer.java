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
* MIDIPlayer.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.media.midi;

import imr.media.Player;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.MetaEventListener;

/**
* The <code>MIDIPlayer</code> class provides functionality
* to play MIDI files.
* @author Ismael Mosquera Rivera
*/
public class MIDIPlayer implements Player
{

/**
* Makes a new instance of a <code>MIDIPlayer</code> object.
*/
public MIDIPlayer()
{
hasSequence = false;
isPaused = false;
try
{
sequencer = MidiSystem.getSequencer();
synth = MidiSystem.getSynthesizer();
sequencer.getTransmitter().setReceiver(synth.getReceiver());
}
catch(MidiUnavailableException e)
{
	e.printStackTrace();
}
}

/**
* Makes a new instance of a <code>MIDIPlayer</code> object.
* and sets a <code>MetaEventListener</code> to it.
* @param listener a <code>MetaEventListener</code> object.
 @see javax.sound.midi.MetaEventListener
*/
public MIDIPlayer(MetaEventListener listener)
{
	this();
	sequencer.addMetaEventListener(listener);
}

/**
* Sets a MIDI file to be played.
* @param fileName path to a MIDI file.
*/
public void set(String fileName)
{
	stop();
isPaused = false;
sequence = SequenceWrapper.wrap(getSong(fileName));
hasSequence = true;
}

/**
* Starts playing.
*/
public void play()
{
if(!hasSequence) return;
	if(sequencer.isRunning()) stop();

if(!isPaused)
{
		sequencer.setTickPosition(0);
	}
		isPaused = false;
	try
	{
	if(!sequencer.isOpen()) sequencer.open();
	if(!synth.isOpen())
{
synth.open();
synth.loadAllInstruments(synth.getDefaultSoundbank());
}
	sequencer.setSequence(sequence);
}
catch(MidiUnavailableException e)
{
	e.printStackTrace();
}
catch(InvalidMidiDataException i)
{
	i.printStackTrace();
}
sequencer.start();
}

/**
* Pauses playing.
*/
public void pause()
{
if(!hasSequence) return;
	if(sequencer.isRunning())
	{
		isPaused = true;
sequencer.stop();
}
}

/**
* Stops playing.
*/
public void stop()
{
if(!hasSequence) return;
	if(sequencer.isRunning()) sequencer.stop();
		isPaused = false;
	if(sequencer.isOpen()) sequencer.close();
	if(synth.isOpen()) synth.close();
}

private MIDISong getSong(String fileName)
{
MIDISong song = new MIDISong();
MIDIFile reader = new MIDIFileReader();
reader.init(fileName);
reader.run(song);
return song;
}

private boolean hasSequence;
private boolean isPaused;
private Sequence sequence;
private Sequencer sequencer;
private Synthesizer synth;
}

// END
