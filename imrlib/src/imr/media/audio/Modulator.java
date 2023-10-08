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
* Modulator.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.media.audio;

/**
* The <code>Modulator</code> class implements a signal modulator.
* This class is needed to perform am synthesis.
* It encapsulates an <code>Envelope</code> object, and a <code>Wave</code> object to do the task.
* The wave can be one of the following types:
* <ul>
* <li>SawWave</li>
* <li>SineWave</li>
* <li>TriangularWave</li>
* </ul>
*
* It has methods to set the needed parameters to its wave and its envelope.
*
* @author Ismael Mosquera Rivera.
*/
public class Modulator
{

/**
* Constructor.
* Makes a new instance object of this class.
* This is the default constructor.
* Actually, it calls the other constructor with default parameters.
*/
public Modulator()
{
this(1.0f, 440.0f, 44100.0f);
}

/**
* constructor.
* Makes a new instance for an object of this class.
*
* @param amp Amplitude.
* @param freq Frequency.
* @param sr Sample rate.
*
*/
public Modulator(float amp, float freq, float sr)
{
	_wave = null;
_wSaw = new SawWave(amp, freq, sr);
_wSine = new SineWave(amp, freq, sr);
_wTriangular = new TriangularWave(amp, freq, sr);
setWaveType(WaveType.wSine);
_env = new Envelope(1.0f, 10, 30, 50, 10, _wave.getSampleRate(), 0.6f, 500.0f);
applyEnvelope(true);
}

/**
* This method determines whether the envelope is applied to the modulator signal or not.
*
* @param b If b = true, then the envelope is applied; otherwise its not.
*/
public void applyEnvelope(boolean b)
{
_applyEnv = b;
}

/**
* Gets the amplitude of the modulator signal.
*
* @return Amplitude.
*/
public float getAmplitude()
{
return _wave.getAmplitude();
}

/**
* Gets the frequency of this modulator signal.
*
* @return Frequency.
*/
public float getFrequency()
{
return _wave.getFrequency();
}

/**
* Gets the sample rate of this modulator signal.
*
* @return Sample rate.
*/
public float getSampleRate()
{
	return _wave.getSampleRate();
}

/**
* This method modulates the signal passed as parameter.
* @param data Data byte array of the signal to be modulated.
*/
public void modulate(byte[] data)
{
byte[] m = new byte[data.length];
_wave.get(m);
if(_applyEnv)
{
_env.setDuration((float)getEnvDuration(m.length));
_env.apply(m);
}
float[] x = new float[m.length];
float max = getMax(m);
float amp = _wave.getAmplitude();
for(int i = 0; i < x.length; i++)
{
x[i] = amp * ((float)m[i]/max);
}
for(int i = 0; i < data.length; i++)
{
	data[i] = (byte)((float)data[i]*Math.abs(x[i]));
}
}

/**
* Sets the amplitude value for this modulator signal.
*
* @param amp Amplitude
*
*/
public void setAmplitude(float amp)
{
_wave.setAmplitude(amp);
}

/**
* Sets the frequency value for this modulator signal.
* @param freq Frequency.
*/
public void setFrequency(float freq)
{
_wave.setFrequency(freq);
}

/**
* Sets the sample rate for this modulator signal.
* @param sr Sample rate.
*/
public void setSampleRate(float sr)
{
_wave.setSampleRate(sr);
_env.setSampleRate(sr);
}

/**
* Sets the amplitude value for the envelope of this modulator object.
* @param amp Amplitude.
*/
public void setEnvAmplitude(float amp)
{
	_env.setAmplitude(amp);
}

/**
* Sets the ADSR parameters to the envelope of this modulator object.
* @param attack Attack percentage respect to the duration of the envelope.
* @param decay Decay percentage respect to the duration of the envelope.
* @param sustain Sustain percentage respect to the duration of the envelope.
* @param release Release percentage respect to the duration of the envelope.
*/
public void setEnvADSR(int attack, int decay, int sustain, int release)
{
	_env.setADSR(attack, decay, sustain, release);
}

/**
* Sets the sustain level for the envelope of this modulator object.
* @param level Sustain level.
*/
public void setEnvSustainLevel(float level)
{
_env.setSustainLevel(level);
}

/**
* Sets the wave type of this modulator object.
* @param type Wave type.
*
* @see imr.media.audio.WaveType
*/
public void setWaveType(int type)
{
switch(type)
{
case WaveType.wSaw:
if(_wave != null)
{
_wSaw.setAmplitude(_wave.getAmplitude());
_wSaw.setFrequency(_wave.getFrequency());
_wSaw.setSampleRate(_wave.getSampleRate());
}
_wave = _wSaw;
break;
case WaveType.wSine:
if(_wave != null)
{
_wSine.setAmplitude(_wave.getAmplitude());
_wSine.setFrequency(_wave.getFrequency());
_wSine.setSampleRate(_wave.getSampleRate());
}
_wave = _wSine;
break;
case WaveType.wTriangular:
if(_wave != null)
{
_wTriangular.setAmplitude(_wave.getAmplitude());
_wTriangular.setFrequency(_wave.getFrequency());
_wTriangular.setSampleRate(_wave.getSampleRate());
}
_wave = _wTriangular;
break;
default:
if(_wave != null)
{
_wSine.setAmplitude(_wave.getAmplitude());
_wSine.setFrequency(_wave.getFrequency());
_wSine.setSampleRate(_wave.getSampleRate());
}
_wave = _wSine;
}
}


private float getMax(byte[] x)
{
	if(x.length == 0) return 1.0f;
	float max = Math.abs((float)x[0]);
	for(int i = 1; i < x.length; i++)
	{
		if(Math.abs((float)x[i]) > max) max = Math.abs((float)x[i]);
	}
	return max;
}

private int getEnvDuration(int size)
{
float t = ((float)size * 1000.0f) / (_wave.getSampleRate() * 2.0f);
return Math.round(t);
}


private boolean _applyEnv;
private Wave _wave;
private Wave _wSaw;
private Wave _wSine;
private Wave _wTriangular;
private Envelope _env;
}

// END
