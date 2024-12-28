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
* AudioMixer.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.sound.audio;

import imr.util.iArray;

/**
* The <code>AudioMixer</code> class allows you to mix n-tracks into one.
* That is, you can the number of tracks you want to its
* <code>addTrack</code> method, and then call its <code>mix</code> method
* in order to mix all the previously added tracks into only one track.
* input = n-tracks; output = one mixed track.
*
* Take in account that once you call its <code>mix</code> method, the mixed track
* is returned and the objects resets to its original state,
* so, all the current data is discarded.
* If you need to have a copy of your mixed track, you can use the <code>cloneFrame</code> method
* implemented in the <code>FrameFactory</code> class.
*
* @see imr.sound.audio.FrameFactory
*
* @author Ismael Mosquera Rivera
*
*/
public class AudioMixer
{
/**
* Constructor.
* Makes a new <code>AudioMixer</code> instance object.
*
*/
public AudioMixer()
{
_count = 0;
_track_size = 0;
_mixer_table = null;
}

/**
* Adds a new track to the mixer.
* Normally, you add tracks with the same length one another, but in case that you add tracks
* of different lengths, the size of mixer table is automatically updated.
*
* @param track A track to add.
*
*/
public void addTrack(byte[] track)
{
	if(track == null || track.length == 0) return;
	int last_size = 0;
	if(track.length > _track_size)
	{
		last_size = _track_size;
		_track_size = track.length;
	}
if(_count == 0)
{
_mixer_table = new byte[1][_track_size];
}
else
{
_mixer_table = (byte[][])iArray.resize(_mixer_table, _count+1, _track_size);
if(last_size != 0)
{
	for(int i = 0; i < _count; i++)
	{
		for(int j = last_size; j < _track_size; j++)
		{
			_mixer_table[i][j] = (byte)0x00;
		}
	}
}
}
for(int i = 0; i < track.length; i++) _mixer_table[_count][i] = track[i];
if(track.length < _track_size)
{
	for(int i = track.length; i < _track_size; i++) _mixer_table[_count][i] = (byte)0x00;
}
_count++;
}

/**
* Performs the mix of the n-tracks previously added into just one.
* Take in account that once you call this method, the mixer resets,
* which causes the object to set to its original state,
* and all the current data is discarded.
* In case that you are interested, you can get a clone
* of your mixed track using the <code>cloneFrame</code> method, implemented
* in the <code>FrameFactory</code> class.
*
* @return the mixed track for the currently stored tracks.
*
*/
public byte[] mix()
{
if(_count == 0) return null;
float[] mixed_track = new float[_track_size];
float sum;
// add all tracks
for(int i = 0; i < _track_size; i++)
{
	sum = 0.0f;
for(int k = 0; k < _count; k++)
{
	sum += (float)_mixer_table[k][i];
}
mixed_track[i] = sum;
}
// normalized the result
float t = 1.0f/(float)_count;
for(int i = 0; i < _track_size; i++) mixed_track[i] *= t;
// build the mixed track
byte[] out_track = new byte[_track_size];
for(int i = 0; i < _track_size; i++) out_track[i] = (byte)mixed_track[i];
// set the mixer to its original state
reset();
return out_track;
}


private void reset()
{
_count = 0;
_track_size = 0;
_mixer_table = null;
}


private int _count;
private int _track_size;
private byte[][] _mixer_table;
}

// END
