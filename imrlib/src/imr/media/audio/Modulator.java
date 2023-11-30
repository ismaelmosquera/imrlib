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
* The <code>Modulator</code> abstract class acts as a base class to implement modulation synthesis.
* It encapsulates an <code>Oscillator</code> object as a carrier and its associated <code>Envelope</code> object.
* In order to perform modulation, an inner class was implemented.
* That class acts as a modulator for the signal produced by the oscillator.
* The oscillator generates a pure sinusoid to be modulated by a periodic signal.
* You have the following kind of signals to modulate such a sinusoid:
* <ul>
* <li>Saw</li>
* <li>Sine</li>
* <li>Square</li>
* <li>Triangular</li>
* </ul>
* So, you can choose different kind of signals from the modulator.
*
* @see imr.media.audio.Envelope
* @see imr.media.audio.Oscillator
* @see imr.media.audio.WaveType
*
* @author Ismael Mosquera Rivera
*
*/
public abstract class Modulator
{

/**
* Constructor.
* Makes a new instance object of this class.
* This is the default constructor.
* Actually, it calls the other constructor with default parameters.
*/
public Modulator()
{
this(1.0f, 65.4f, 44100.0f);
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
_carrier = new Oscillator(amp, freq, sr);
_modulator = new XModulator(amp, freq, _carrier.getSampleRate());
_cenv = new Envelope(1.0f, 20, 20, 50, 10, _carrier.getSampleRate(), 0.5f, 500.0f);
setModulationIndex(1.0f);
applyCarrierEnvelope(true);
applyModulatorEnvelope(true);
}

/**
* This method determines whether the envelope is applied to the carrier signal or not.
* @param apply If apply = true, then the envelope is applied; otherwise its not.
*/
public void applyCarrierEnvelope(boolean apply)
{
_applyCEnv = apply;
}

/**
* This method determines whether the envelope is applied to the modulator signal or not.
* @param apply If apply = true, then the envelope is applied; otherwise its not.
*/
public void applyModulatorEnvelope(boolean apply)
{
_modulator.applyEnvelope(apply);
}

/**
* Gets the carrier amplitude.
*
* @return amplitude value
*/
public float getCarrierAmplitude()
{
return _carrier.getAmplitude();
}

/**
* Gets the carrier frequency.
*
* @return frequency value
*/
public float getCarrierFrequency()
{
return _carrier.getFrequency();
}

/**
* Gets the current modulation index.
*
* @return Modulation index.
*
*/
public float getModulationIndex()
{
	return _modulator.getMIndex();
}

/**
* Sets the modulation index.
* @param index Modulation index.
*
*/
public void setModulationIndex(float index)
{
	_modulator.setMIndex(index);
}

/**
* Gets the sample rate for this object.
*
* @return sample rate value
*/
public float getSampleRate()
{
return _carrier.getSampleRate();
}

/**
* This method performs the synthesis.
* Since it is an abstract method, you must implement it for your concrete modulation type.
* @param frame A byte data frame
*
*/
public abstract void synth(byte[] frame);

/**
* Sets the amplitude value for the carrier.
* @param amp Amplitude value
*
*/
public void setCarrierAmplitude(float amp)
{
_carrier.setAmplitude(amp);
}

/**
* Sets the frequency value for the carrier.
* @param freq Frequency value
*
*/
public void setCarrierFrequency(float freq)
{
_carrier.setFrequency(freq);
}

/**
* Sets the amplitude value for the modulator.
* @param amp Amplitude value
*
*/
public void setModulatorAmplitude(float amp)
{
_modulator.setAmplitude(amp);
}

/**
* Sets the frequency value for the modulator.
* @param freq Frequency value
*
*/
public void setModulatorFrequency(float freq)
{
_modulator.setFrequency(freq);
}

/**
* Sets the sample rate for this object.
* @param sr Sample rate value
*
*/
public void setSampleRate(float sr)
{
_carrier.setSampleRate(sr);
_cenv.setSampleRate(sr);
_modulator.setSampleRate(sr);
}

/**
* Sets the amplitude value for the carrier envelope.
* @param amp Amplitude value
*
*/
public void setCarrierEnvAmplitude(float amp)
{
_cenv.setAmplitude(amp);
}

/**
* Sets the ADSR values for the carrier envelope.
* @param attack Attack percentage respect to the duration.
* @param decay Decay percentage respect to the duration.
* @param sustain Sustain percentage respect to the duration.
* @param release Release percentage respect to the duration.
*
*/
public void setCarrierEnvADSR(int attack, int decay, int sustain, int release)
{
_cenv.setADSR(attack, decay, sustain, release);
}

/**
* Sets the sustain level for the carrier envelope.
* @param level Sustain level.
*
*/
public void setCarrierEnvSustainLevel(float level)
{
_cenv.setSustainLevel(level);
}

/**
* Sets the amplitude value for the modulator envelope.
* @param amp Amplitude value
*
*/
public void setModulatorEnvAmplitude(float amp)
{
_modulator.setEnvAmplitude(amp);
}

/**
* Sets the ADSR values for the modulator envelope.
* @param attack Attack percentage respect to the duration.
* @param decay Decay percentage respect to the duration.
* @param sustain Sustain percentage respect to the duration.
* @param release Release percentage respect to the duration.
*
*/
public void setModulatorEnvADSR(int attack, int decay, int sustain, int release)
{
_modulator.setEnvADSR(attack, decay, sustain, release);
}

/**
* Sets the sustain level for the modulator envelope.
* @param level Sustain level.
*
*/
public void setModulatorEnvSustainLevel(float level)
{
_modulator.setEnvSustainLevel(level);
}

/**
* Sets the wave type for the modulator object.
* @param type Wave type.
*
* @see imr.media.audio.WaveType
*/
public void setModulatorWaveType(int type)
{
_modulator.setWaveType(type);
}

protected int getCEnvDuration(int size)
{
float t = ((float)size * 1000.0f) / (_carrier.getSampleRate() * 2.0f);
return Math.round(t);
}


protected boolean _applyCEnv;
protected float _modulationIndex;
protected Envelope _cenv;
protected Oscillator _carrier;
protected XModulator _modulator;


/**
* The <code>XModulator</code> class implements a signal modulator.
* This class is needed to perform modulation synthesis.
* It encapsulates an <code>Envelope</code> object, and a <code>Wave</code> object to do the task.
* The wave can be one of the following types:
* <ul>
* <li>SawWave</li>
* <li>SineWave</li>
* <li>SquareWave</li>
* <li>TriangularWave</li>
	* </ul>
*
* It has methods to set the needed parameters to its wave and its envelope.
*
* @author Ismael Mosquera Rivera.
*
*/
protected class XModulator
{

/**
* Constructor.
* Makes a new instance object of this class.
* This is the default constructor.
* Actually, it calls the other constructor with default parameters.
*/
public XModulator()
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
public XModulator(float amp, float freq, float sr)
{
	_wave = null;
_wSaw = new SawWave(amp, freq, sr);
_wSine = new SineWave(amp, freq, sr);
_wSquare = new SquareWave(amp, freq, sr);
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
* Gets the current modulation index.
*
* @return Modulation index.
*
*/
public float getMIndex()
{
return _modulationIndex;
}

/**
* Sets the modulation index.
* @param index Modulation index.
*
*/
public void setMIndex(float index)
{
_modulationIndex = index;
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
* This method builds the modulator signal to perform fm synthesis.
* @param data Data float array.
*/
public void getFrequencyModulator(float[] data)
{
	if(_wave.getAmplitude() == 0.0f)
	{
		for(int i = 0; i < data.length; i++) data[i] = 0.0f;
		return;
	}
byte[] m = new byte[data.length];
_wave.get(m);
if(_applyEnv)
{
_env.setDuration((float)getEnvDuration(m.length));
_env.apply(m);
}
float max = getMax(m);
for(int i = 0; i < data.length; i++)
{
data[i] = _modulationIndex * ((float)m[i]/max);
}
}

/**
* This method modulates in amplitude the signal passed as parameter.
* @param data Data byte array of the signal to be modulated.
*/
public void applyAmplitudeModulation(byte[] data)
{
	if(_wave.getAmplitude() == 0.0f) return;
	byte[] tmp = FrameFactory.cloneFrame(data);
float max = getMax(tmp);
float[] z = new float[tmp.length];
for (int i = 0; i < tmp.length; i++)
{
z[i] = (float)tmp[i] / max;
}

byte[] m = new byte[data.length];
_wave.get(m);
if(_applyEnv)
{
_env.setDuration((float)getEnvDuration(m.length));
_env.apply(m);
}
float[] x = new float[m.length];
max = getMax(m);
for(int i = 0; i < x.length; i++)
{
x[i] = _modulationIndex * ((float)m[i]/max);
}
for(int i = 0; i < z.length; i++)
{
z[i] *= x[i];
z[i] *= 127.0f;
}

for(int i = 0; i < x.length; i++)
{
x[i] = (float)tmp[i] + z[i];
}
max = getMax(x);
for(int i = 0; i < data.length; i++)
{
	data[i] = (byte)((x[i]/max) * 127.0f);
}
}

/**
* This method performs ring modulation to the signal passed as parameter.
* @param data Data byte array of the signal to be modulated.
*/
public void applyRingModulation(byte[] data)
{
	if(_wave.getAmplitude() == 0.0f || _modulationIndex == 0.0f) return;
	byte[] tmp = FrameFactory.cloneFrame(data);
float max = getMax(tmp);
float[] z = new float[tmp.length];
for (int i = 0; i < tmp.length; i++)
{
z[i] = (float)tmp[i] / max;
}

byte[] m = new byte[data.length];
_wave.get(m);
if(_applyEnv)
{
_env.setDuration((float)getEnvDuration(m.length));
_env.apply(m);
}

float[] x = new float[m.length];
max = getMax(m);
for(int i = 0; i < x.length; i++)
{
x[i] = _modulationIndex * ((float)m[i]/max);
}

for(int i = 0; i < z.length; i++)
{
z[i] *= Math.abs(x[i]);
}

max = getMax(z);
for(int i = 0; i < z.length; i++)
{
	data[i] = (byte)((z[i]/max) * 127.0f);
}
}

/**
* Sets the amplitude value for this modulator signal.
*
* @param amp Amplitude.
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
* @see WaveType
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
case WaveType.wSquare:
if(_wave != null)
{
_wSquare.setAmplitude(_wave.getAmplitude());
_wSquare.setFrequency(_wave.getFrequency());
_wSquare.setSampleRate(_wave.getSampleRate());
}
_wave = _wSquare;
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

private float getMax(float[] x)
{
	if(x.length == 0) return 1.0f;
float max = Math.abs(x[0]);
for(int i = 1; i < x.length; i++)
{
	if(Math.abs(x[i]) > max) max = Math.abs(x[i]);
}
return max;
}

private int getEnvDuration(int size)
{
float t = ((float)size * 1000.0f) / (_wave.getSampleRate() * 2.0f);
return Math.round(t);
}


private boolean _applyEnv;
private float _modulationIndex;
private Wave _wave;
private Wave _wSaw;
private Wave _wSine;
private Wave _wSquare;
private Wave _wTriangular;
private Envelope _env;
}
}

// END
