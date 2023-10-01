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
* StarWars.java
*
* Author: Ismael Mosquera Rivera
*/

import imr.media.audio.FrameFactory;
import imr.media.audio.WaveType;
import imr.media.audio.FMInstrument;
import imr.media.audio.RawDataPlayer;
import imr.media.audio.RawDataStorage;

import javax.sound.sampled.UnsupportedAudioFileException;

/*
* This example demonstrates several classes:
* To illusttrate that, we composed a fragment of 'Stars Wars' and synthesized it using FM modulation.
* After the sinthesized song is played, we store the result in the 'star_wars.wav' file inside the 'bin' folder.
*
* Author: Ismael Mosquera Rivera
*/
public class StarWars
{
public StarWars()
{
_fm = new FMInstrument(1.0f, 20.0f, 44100.0f);
_player = new RawDataPlayer(_fm.getCarrier().getSampleRate());
_saver = new RawDataStorage(_fm.getCarrier().getSampleRate());
}

public void play()
{
	_fm.getCarrier().setEnvADSR(10, 20, 60, 10);
	_fm.getModulator().setAmplitude(1.0f);
	_fm.getModulator().setEnvADSR(5, 25, 60, 10);
	_fm.getModulator().setEnvSustainLevel(0.5f);
	_fm.getModulator().setWaveType(WaveType.wTriangular);

_player.start();

int srate = (int)_fm.getCarrier().getSampleRate();
byte[] frame;
for(int i = 0; i < f.length; i++)
{
frame = FrameFactory.getFrame(srate, t[i]);
if(a[i] != 0.0f)
{
_fm.getCarrier().setAmplitude(a[i]);
_fm.getCarrier().setFrequency(f[i]);
_fm.getModulator().setFrequency(f[i]*3.0f+5.0f);
_fm.synth(frame);
}
_player.play(frame);
_saver.add(frame);
}

try
{
Thread.sleep(2000);
}
catch(InterruptedException e) {}

_player.stop();
frame = FrameFactory.getFrame(srate, 2000);
_saver.add(frame);

System.out.println("Saving audio file ...");
try
{
	_saver.store("star_wars.wav");
}
catch(UnsupportedAudioFileException e)
{
System.out.println(e);
}
System.out.println("star_wars.wav file stored successfully.");
}


private FMInstrument _fm;
private RawDataPlayer _player;
private RawDataStorage _saver;


private float[] f =
{20.0f, 157.9f, 20.0f, 173.3f, 20.0f, 271.0f, 20.0f, 243.5f, 20.0f, 230.2f, 20.0f, 201.3f,
20.0f, 361.8f, 20.0f, 273.6f, 20.0f, 242.3f, 226.1f, 203.2f, 20.0f, 364.7f, 20.0f, 276.2f,
20.0f, 243.3f, 234.7f, 248.0f, 20.0f, 205.4f};

private float[] a =
{0.0f, 0.4f, 0.0f, 0.4f, 0.0f, 0.5f, 0.0f, 0.5f, 0.0f, 0.6f, 0.0f, 0.6f,
0.0f, 0.5f, 0.0f, 0.5f, 0.0f, 0.5f, 0.5f, 0.6f, 0.0f, 0.5f, 0.0f, 0.5f,
0.0f, 0.6f, 0.6f, 0.7f, 0.0f, 0.5f};

private int[] t =
{209, 420, 92, 1188, 92, 1745, 255, 444, 107, 218, 107, 220,
84, 1290, 126, 1650, 534, 303, 324, 256, 92, 1322, 95, 1556,
370, 418, 348, 307, 88, 1214};

}

// END
