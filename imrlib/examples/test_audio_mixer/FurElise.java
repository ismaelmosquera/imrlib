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

import imr.media.audio.AudioMixer;
import imr.media.audio.FrameFactory;
import imr.media.audio.Synthesizer;
import imr.media.audio.MusicalInstrument;
import imr.media.audio.RawDataPlayer;
import imr.media.audio.RawDataStorage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.sound.sampled.UnsupportedAudioFileException;

/*
* This class implements a synthesis for the song named Fur Elise.
* We have the amplitude, time and piano key numbers to compose such a musical piece.
* We made the performance using a kind of synthesizer: <code>MusicalInstrument</code> object.
* To generate the data frames according to its duration, we used a <code>FrameFactory</code>
* To playback each synthesized frame, we use a <code>RawDataPlayer</code>
* Finally, to save the composition into an audio file, we used a <code>RawDataStorage</code>
* After all the playback is done, we store the song in 'fur_elise.wav' archive inside the test_audio_mixer/bin/ folder.
*
* The difference with the example to test the <code>MusicalInstrument</code> class
* is that, in this case, we mix two tracks into just one using an <code>AudioMixer</code> object.
* That is, a trebble line track and a bass line track.
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
_mixer = new AudioMixer();
_track = new ByteArrayOutputStream();
initData();
}

public void play()
{
_player.start();

_wave.setEnvADSR(10, 20, 60, 10);
((MusicalInstrument)_wave).oddHarmonics();

int srate = (int)_wave.getSampleRate();
byte[] frame;
// synthesize the trebble line first
for(int i = 0; i < f.length; i++)
{
frame = FrameFactory.getFrame(srate, t[i]);
_wave.setAmplitude(a[i]);
_wave.setFrequency(f[i]);
((MusicalInstrument)_wave).setHarmonicAmplitudes(_wave.getAmplitude()/4.0f, _wave.getAmplitude()/8.0f, _wave.getAmplitude()/16.0f);
_wave.synth(frame);
_track.write(frame, 0, frame.length);
}
// add the track to the audio mixer
_mixer.addTrack(_track.toByteArray());
// reset the track in order to prepare it to compose the next track
_track.reset();
// envelope to apply to this track
_wave.setEnvADSR(5, 25, 60, 10);
((MusicalInstrument)_wave).allHarmonics();
// compose the bass line track
for(int i = 0; i < bf.length; i++)
{
frame = FrameFactory.getFrame(srate, bt[i]);
_wave.setAmplitude(ba[i]);
_wave.setFrequency(bf[i]);
((MusicalInstrument)_wave).setHarmonicAmplitudes(_wave.getAmplitude()/2.0f, _wave.getAmplitude()/4.0f, _wave.getAmplitude()/8.0f);
_wave.synth(frame);
_track.write(frame, 0, frame.length);
}
// add the bass track to the audio mixer
_mixer.addTrack(_track.toByteArray());
// play and save
// make an instance for a ByteArrayInputStream object
// passing the mixed track as parameter to its constructor
ByteArrayInputStream song = new ByteArrayInputStream(_mixer.mix());
// play and save each frame
frame = FrameFactory.getFrame(srate, 1000);
int bytesRead = 0;
while(true)
{
bytesRead = song.read(frame, 0, frame.length);
if(bytesRead == -1) break;
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

bf = new float[bk.length];
for(int i = 0; i < bf.length; i++) bf[i] = pianoKeyToFrequency(bk[i]);
}


private Synthesizer _wave;
private AudioMixer _mixer;
private RawDataPlayer _player;
private RawDataStorage _saver;
private ByteArrayOutputStream _track;
private float[] f;
private float[] bf;

// trebble line vectors
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

// bas line vectors
private int[] bk =
{0, 0, 25, 32, 37, 0, 0, 20, 32, 36, 0, 0, 25, 32, 37, 0, 0, 0, 25, 32, 37, 0, 0,
20, 32, 36, 0, 0, 25, 32, 37, 0, 28, 35, 40, 0, 0, 23, 35, 39, 0, 0, 25, 32, 37, 0, 0, 20,
32, 44, 0, 44, 56, 0, 0, 55, 56, 0, 0, 55, 56, 0, 0, 0, 25, 32, 37, 0, 0, 20, 32, 36, 0, 0,
25, 32, 37, 0, 0, 0, 25, 32, 37, 0, 0, 20, 32, 36, 0, 0, 25, 32, 37, 0};

private float[] ba =
{0.0f, 0.0f, 0.4f, 0.4f, 0.4f, 0.0f, 0.0f, 0.35f, 0.25f, 0.15f, 0.0f, 0.0f, 0.15f, 0.25f, 0.1f, 0.0f, 0.0f, 0.0f, 0.4f,
0.4f, 0.4f, 0.0f, 0.0f, 0.35f, 0.3f, 0.2f, 0.0f, 0.0f, 0.25f, 0.3f, 0.2f, 0.0f, 0.3f, 0.3f, 0.3f, 0.0f, 0.0f, 0.3f, 0.3f, 0.3f,
0.0f, 0.0f, 0.3f, 0.3f, 0.3f, 0.0f, 0.0f, 0.3f, 0.3f, 0.3f, 0.0f, 0.4f, 0.4f, 0.0f, 0.0f, 0.4f, 0.4f, 0.0f, 0.0f, 0.4f, 0.4f, 0.0f, 0.0f, 0.0f, 0.4f,
0.4f, 0.4f, 0.0f, 0.0f, 0.35f, 0.3f, 0.2f, 0.0f, 0.0f, 0.25f, 0.2f, 0.15f, 0.0f, 0.0f, 0.0f, 0.4f, 0.4f, 0.4f, 0.0f, 0.0f, 0.35f,
0.3f, 0.2f, 0.0f, 0.0f, 0.35f, 0.3f, 0.2f, 0.0f};

private int[] bt =
{1000, 3000, 500, 500, 500, 500, 1000, 500, 500, 500, 500, 1000, 500, 500, 500, 500, 1000, 3000, 500,
500, 500, 500, 1000, 500, 500, 500, 500, 1000, 500, 500, 500, 1500, 500, 500, 500, 500, 1000, 500, 500,
500, 500, 1000, 500, 500, 500, 500, 1000, 500, 500, 500, 1000, 500, 500, 500, 500, 500, 500, 500, 500,
500, 500, 500, 1000, 3000, 500, 500, 500, 500, 1000, 500, 500, 500, 500, 1000, 500, 500, 500, 500, 1000, 3000,
500, 500, 500, 500, 1000, 500, 500, 500, 500, 1000, 500, 500, 500, 500};

}

// END
