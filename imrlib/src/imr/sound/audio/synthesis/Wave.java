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
* Wave.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.sound.audio.synthesis;

import java.io.ByteArrayOutputStream;

/**
* The <code>Wave</code> abstract class has all the functionallity needed to synthesize any kind of wave.
* Classes derived from this one must implement its protected <code>buildWave()</code> abstract method
* in order to synthesize the desired type of wave.
*
* @author Ismael Mosquera Rivera
*/
public abstract class Wave
{

/**
* Constructor.
* Makes a new instance of a <code>Wave</code> object.
* since this is an abstract class, it cannot be instantiated directly, but through some derived class.
* This constructor calls the other one with default parameters.
*
*/
public Wave()
{
this(0.8f, 440.0f, 22050.0f);
}

/**
* Constructor.
* Makes a new instance of a <code>Wave</code> object.
* since this is an abstract class, it cannot be instantiated directly, but through some derived class.
*@param amp Amplitude
*@param freq Frequency
* @param sr Sample rate
*
*/
public Wave(float amp, float freq, float sr)
{
setAmplitude(amp);
setFrequency(freq);
setSampleRate(sr);
}

/**
* Gets the wave.
*
* @param data Byte array to be filled.
*
* The length of the byte array passed as parameter must be according to the desired duration.
*
* @see imr.sound.audio.FrameFactory
*
* @return number of bytes read
*/
public int get(byte[] data)
{
	if(_mustUpdate) buildWave();
ByteArrayOutputStream buffer = new ByteArrayOutputStream();
int nchuncks = data.length / _wave.length;
for(int i = 0; i < nchuncks; i++) buffer.write(_wave, 0, _wave.length);
int tmpLen = nchuncks * _wave.length;
if(tmpLen < data.length)
{
byte[] end_frame = new byte[data.length - tmpLen];
for(int i = 0; i < end_frame.length; i++) end_frame[i] = _wave[i];
buffer.write(end_frame, 0, end_frame.length);
}
byte[] b = buffer.toByteArray();
for(int i = 0; i < b.length; i++) data[i] = b[i];
return b.length;
}

/**
* Gets the amplitude of the wave.
*
* @return Amplitude
*/
public float getAmplitude()
{
	return _amplitude;
}

/**
* Gets the frequency of the wave.
*
* @return Frequency
*/
public float getFrequency()
{
	return _frequency;
}

/**
* Gets the sample rate of the wave.
*
* @return Sample rate
*/
public float getSampleRate()
{
	return _sampleRate;
}

/**
* Sets the amplitude of the wave.
*
* @param amp Amplitude
*
*/
public void setAmplitude(float amp)
{
assert (amp >= 0.0f && amp <= 1.0f): "bad amplitude; allowed values: ( 0.0f .. 1.0f ).";
_amplitude = amp;
_mustUpdate = true;
}

/**
* Sets the frequency of the wave.
*
* @param freq Frequency
*
*/
public void setFrequency(float freq)
{
assert (freq > 0.0f): "bad value for frequency; it must be greater than zero.";
_frequency = freq;
_mustUpdate = true;
}

/**
* Sets the sample rate of the wave.
*
* @param sr Sample rate
*
*/
public void setSampleRate(float sr)
{
boolean condition = ((int)sr == 11025 || (int)sr == 22050 || (int)sr == 44100);
assert condition: "bad value for sample rate; allowed values: 11025, 22050 and 44100";
_sampleRate = sr;
_mustUpdate = true;
}


/**
* Builds a single oscillation of the desired wave type.
* This abstract method must be implemented by any derived class from this one.
* The concrete single oscillation is replicated by the <code>int get(byte[] data)</code> method since its a periodic wave.
*
* @see imr.sound.audio.synthesis.SawWave
* @see imr.sound.audio.synthesis.SineWave
* @see imr.sound.audio.synthesis.SquareWave
* @see imr.sound.audio.synthesis.TriangularWave
* @see imr.sound.audio.synthesis.MusicalNote
*
*/
protected abstract void buildWave();


protected float _amplitude;
protected float _frequency;
protected float _sampleRate;
protected boolean _mustUpdate;

protected byte[] _wave;

}

// END

