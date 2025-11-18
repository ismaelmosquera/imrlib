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
* Synthesizer.java
*
* imr-lib
*
* Author: Ismael Mosquera rivera
*/

package imr.sound.audio.synthesis;

/**
* The <code>Synthesizer</code> abstract class has almost all the functionallity needed for a simple synthesizer.
* It encapsulates a <code>Envelope</code> object and also a <code>Wave</code> object.
* Its abstract <code>setWave()</code> method must be implemented in order to set the wanted wave type.
*
* @see imr.sound.audio.synthesis.Wave
*
* @author Ismael Mosquera Rivera
*/
public abstract class Synthesizer
{

/**
* Constructor.
* Makes a new instance of a <code>Synthesizer</code> object.
* Since this is an abstract class, it cannot be instantiated directly, but through a class derived from this one.
* This constructor calls the one with default parameters.
*
* @see imr.sound.audio.synthesis.Wave
*/
public Synthesizer()
{
this(1.0f, 440.0f, 44100.0f);
}

/**
* Constructor.
* Makes a new instance of a <code>Synthesizer</code> object.
* Since this is an abstract class, it cannot be instantiated directly, but through a class derived from this one.
*
* @param amp Amplitude.
* @param freq Frequency
* @param sr Sample rate
*
* @see imr.sound.audio.synthesis.Wave
*/
public Synthesizer(float amp, float freq, float sr)
{
	setWave(amp, freq, sr);
_env = new Envelope(1.0f, 10, 30, 50, 10, _wave.getSampleRate(), 0.6f, 500.0f);
applyEnvelope(true);
}

/**
* Determines if the enveloped is apply to the generated wave or not.
*
* @param b true = apply, false = not apply
*
*/
public void applyEnvelope(boolean b)
{
_applyEnv = b;
}

/**
* Gets the amplitude of the wave.
*
* @return Amplitude
*/
public float getAmplitude()
{
return _wave.getAmplitude();
}

/**
* Gets the frequency of the wave.
*
* @return Frequency
*/
public float getFrequency()
{
return _wave.getFrequency();
}

/**
* Gets the sample rate of the wave.
*
* @return Sample rate
*/
public float getSampleRate()
{
	return _wave.getSampleRate();
}

/**
* Sets the amplitude of the wave.
*
* @param amp Amplitude
*
*/
public void setAmplitude(float amp)
{
_wave.setAmplitude(amp);
}

/**
* Sets the frequency of the wave.
*
* @param freq Frequency
*
*/
public void setFrequency(float freq)
{
_wave.setFrequency(freq);
}

/**
* Sets the sample rate of the wave.
*
* @param sr Sample rate
*
* This method also updates the sample rate of the envelope.
*
*/
public void setSampleRate(float sr)
{
_wave.setSampleRate(sr);
_env.setSampleRate(sr);
}

/**
* Sets the amplitude of the envelope.
*
* @param amp Amplitude
*
*/
public void setEnvAmplitude(float amp)
{
_env.setAmplitude(amp);
}

/**
* Sets the ADSR of the envelope.
*
* @param attack Attack percentage respect to the duration
* @param decay Decay percentage respect to the duration
* @param sustain Sustain percentage respect to the duration
* @param release Release percentage respect to the duration
*
*/
public void setEnvADSR(int attack, int decay, int sustain, int release)
{
_env.setADSR(attack, decay, sustain, release);
}

/**
* Sets the sustain level of the envelope.
*
* @param level Sustain level
*
*/
public void setEnvSustainLevel(float level)
{
_env.setSustainLevel(level);
}

/**
* Synthesizes the sound according its wave and envelope.
*
* @param data Byte array to be filled.
*
* You must passed as parameter an array with the number of samples according to the wanted duration.
*
* @see imr.sound.audio.FrameFactory
*/
public void synth(byte[] data)
{
_wave.get(data);
if(_applyEnv)
{
_env.setDuration(getEnvDuration(data.length));
_env.apply(data);
}
}


protected abstract void setWave(float amp, float freq, float sr);

private int getEnvDuration(int size)
{
float t = ((float)size * 1000.0f) / (_wave.getSampleRate() * 2.0f);
return Math.round(t);
}


protected Wave _wave;

private boolean _applyEnv;
private Envelope _env;
}

// END
