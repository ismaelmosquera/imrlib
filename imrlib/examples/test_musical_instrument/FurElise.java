/*
 * Copyright (c) 2023 Ismael Mosquera Rivera
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
* FurElise.java
*
* Author: Ismael Mosquera Rivera
*/

import imr.media.audio.FrameFactory;
import imr.media.audio.Synthesizer;
import imr.media.audio.MusicalInstrument;
import imr.media.audio.RawDataPlayer;
import imr.media.audio.RawDataStorage;

import javax.sound.sampled.UnsupportedAudioFileException;

/*
* This class implements a synthesis for the song named Fur Elise.
* We have the amplitude, time and piano key numbers to compose such a musical piece.
* We made the performance using a kind of synthesizer: <code>MusicalInstrument</code> object.
* To generate the data frames according to its duration, we used a <code>FrameFactory</code>
* To playback each synthesized frame, we use a <code>RawDataPlayer</code>
* Finally, to save the composition into an audio file, we used a <code>RawDataStorage</code>
* After all the playback is done, we store the song in 'fur_elise.wav' archive inside the test_musical_instrument/bin/ folder.
*
* Author: Ismael Mosquera Rivera
*/
public class FurElise
{
public FurElise()
{
_wave = new MusicalInstrument(1.0f, 65.4f, 44100.0f);
_player = new RawDataPlayer(_wave.getSampleRate());
_saver = new RawDataStorage(_wave.getSampleRate());
initData();
}

public void play()
{
_player.start();

_wave.setEnvADSR(10, 20, 60, 10);
int srate = (int)_wave.getSampleRate();
byte[] frame;
for(int i = 0; i < f.length; i++)
{
frame = FrameFactory.getFrame(srate, t[i]);
_wave.setAmplitude(a[i]);
_wave.setFrequency(f[i]);
_wave.synth(frame);
_player.play(frame);
_saver.add(frame);
}

try
{
Thread.sleep(1500);
}
catch(InterruptedException e){}

_player.stop();
frame = FrameFactory.getFrame(srate, 1500);
_saver.add(frame);
System.out.println("Saving audio file ...");
try
{
	_saver.store("fur_elise.wav");
}
catch(UnsupportedAudioFileException e)
{
	System.out.println(e);
}
System.out.println("fur_elise.wav file stored successfully.");
}


private float pianoKeyToFrequency(int key)
{
	int A4 = 49;
int dx = key - A4;
 return 440.0f*(float)Math.pow(2.0,(double)dx/12.0);
}

private void initData()
{
f = new float[k.length];
for(int i = 0; i < f.length; i++) f[i] = pianoKeyToFrequency(k[i]);
}


private Synthesizer _wave;
private RawDataPlayer _player;
private RawDataStorage _saver;

private float[] f;

private int[] k =
{56, 55, 56, 55, 56, 51, 54, 52, 49, 0, 40, 44, 49, 51, 0, 44, 48, 51, 52, 0,
44, 56, 55, 56, 55, 56, 51, 54, 52, 49, 0, 40, 44, 49, 51, 0, 44, 52, 51, 49, 0, 51, 52,
54, 56, 47, 57, 56, 54, 45, 56, 54, 52, 44, 54, 52, 51, 0, 44, 56, 0, 0, 56, 68, 0, 0,
55, 56, 0, 0, 55, 56, 55, 56, 55, 56, 51, 54, 52, 49, 0, 40, 44, 49, 51, 0, 44, 48, 51,
52, 0, 44, 56, 55, 56, 55, 56, 51, 54, 52, 49, 0, 40, 44, 49, 51, 0, 44, 52, 51, 49};

private float[] a =
{0.25f, 0.25f, 0.25f, 0.5f, 0.75f, 1.0f, 0.75f, 0.5f, 0.75f, 0.0f, 0.75f, 0.75f, 0.75f, 1.0f, 0.0f, 0.75f,
0.75f, 0.75f, 1.0f, 0.0f, 0.75f, 0.75f, 0.5f, 0.25f, 0.5f, 0.75f, 1.0f, 0.75f, 0.5f, 0.75f, 0.0f, 0.75f, 0.75f, 0.75f, 1.0f, 0.0f,
0.75f, 0.75f, 0.75f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.75f, 0.5f,
0.5f, 0.0f, 0.5f, 0.75f, 0.0f, 0.0f, 0.5f, 0.5f, 0.0f, 0.0f, 0.5f, 0.75f, 0.0f, 0.0f, 0.25f, 0.25f, 0.25f, 0.25f, 0.5f, 0.75f, 1.0f,
0.75f, 0.5f, 0.75f, 0.0f, 0.75f, 0.75f, 0.75f, 1.0f, 0.0f, 0.75f, 0.75f, 0.75f, 1.0f, 0.0f, 0.75f, 0.75f, 0.5f, 0.25f, 0.5f, 0.75f,
1.0f, 0.75f, 0.5f, 0.75f, 0.0f, 0.75f, 0.75f, 0.75f, 1.0f, 0.0f, 0.75f, 0.75f, 0.75f, 1.0f};

private int[] t =
{500, 500, 500, 500, 500, 500, 500, 500, 1000, 500, 500, 500, 500, 1000, 500, 500, 500, 500, 1000, 500,
500, 500, 500, 500, 500, 500, 500, 500, 500, 1000, 500, 500, 500, 500, 1000, 500, 500, 500, 500, 1000,
500, 500, 500, 500, 1500, 500, 500, 500, 1500, 500, 500, 500, 1500, 500, 500, 500, 1000, 500, 500, 500,
500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 1000,
500, 500, 500, 500, 1000, 500, 500, 500, 500, 1000, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500,
1000, 500, 500, 500, 500, 1000, 500, 500, 500, 500, 2000};

}

// END
