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
* Envelope.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.sound.audio.synthesis;

/**
* The <code>Envelope</code> class implements a 4 segments ADSR envelope.
* You can use an object of this class, for instance, to apply to waves produced by some kind of wave generators.
*
* This class uses assertions. So, you have to run your application with
* the '-ea' modifier in order to enable assertions.
*
* Example:
* <code>java -ea MyApp</code>
*
* @author Ismael Mosquera Rivera
*
*/
public class Envelope
{
/**
* Constructor.
* Makes a new instance object of the class <code>Envelope</code>.
* Actually, what this constructor does, is to call another constructor with default values.
* That is:
* <code>this(0.8f, 20, 20, 50, 10, 22050.0f, 0.5f, 500.0f);</code>
* To know about the parameters, see the constructor with parameters explanation.
*
*/
public Envelope()
{
this(0.8f, 20, 20, 50, 10, 22050.0f, 0.5f, 500.0f);
}

/**
* Constructor.
* Makes a new instance object of the class <code>Envelope</code>.
* @param amp. Amplitude.
* @param attack Percentage of attack with respect to the duration.
* @param decay Percentage of decay with respect to the duration
* @param sustain Percentage of sustain with respect to the duration
* @param release Percentage of release with respect to the duration
* @param sr Sample rate.
* @param sLevel Sustain level.
* @param duration Duration for the envelope expressed in ms. ( milliseconds )
*
*/
public Envelope(float amp, int attack, int decay, int sustain, int release, float sr, float sLevel, float duration)
{
	_pattack = attack;
	_pdecay = decay;
	_psustain = sustain;
	_prelease = release;

	setSampleRate(sr);
setAmplitude(amp);
setSustainLevel(sLevel);
setDuration(duration);
}

/**
* Sets the amplitude for this envelope.
* @param amp Amplitude value
*
*/
public void setAmplitude(float amp)
{
assert (amp>=0.0f && amp<=1.0f): "bad value for amplitude: amplitude value must be in the range [0..1].";
_amplitude = amp;
_mustUpdate = true;
}

/**
* Sets the ADSR percentage values for this envelope.
* @param attack Percentage of attack with respect to the duration.
* @param decay Percentage of decay with respect to the duration
* @param sustain Percentage of sustain with respect to the duration
* @param release Percentage of release with respect to the duration
*
*/
public void setADSR(int attack, int decay, int sustain, int release)
{
int sum = attack+decay+sustain+release;
assert (sum>0 && sum<=100): "ADSR: bad parameters. The sum of the 4 parameters must be > 0 and <= 100.";

_pattack = attack;
	_pdecay = decay;
	_psustain = sustain;
	_prelease = release;

_attack_t = (_duration*(float)attack/100.0f) / 1000.0f;
if(_attack_t <= 0.0f) _attack_t = 1.0f / _sampleRate;
_decay_t = (_duration*(float)decay/100.0f) / 1000.0f;
if(_decay_t <= 0.0f) _decay_t = 1.0f / _sampleRate;
_release_t = (_duration*(float)release/100.0f) / 1000.0f;
if(_release_t <= 0.0f) _release_t = 1.0f / _sampleRate;
_sustainSize = (int)(((_duration*(float)sustain/100.0f) * (_sampleRate*2.0f)) / 1000.0f);
_mustUpdate = true;
}

/**
* Sets the duration expressed in ms. ( milliseconds ) for this envelope.
* @param dur Duration in ms.
*
*/
public void setDuration(float dur)
{
	assert (dur > 0.0f): "setDuration: bad parameter. Duration must be > 0.";
	_duration = dur;
	updateDuration();
	_mustUpdate = true;
}

/**
* Sets the sample rate value for this envelope.
* @param sr Sample rate.
*
*/
public void setSampleRate(float sr)
{
	boolean condition = ((int)sr == 11025 || (int)sr == 22050 || (int)sr == 44100);
	assert condition: "setSampleRate: bad parameter. Allowed values 11025 | 22050 | 44100.";
	_sampleRate = sr;
	_mustUpdate = true;
}

/**
* Sets the sustain level for this envelope.
* @param level Sustain level.
*
*/
public void setSustainLevel(float level)
{
assert (level>=0.0f && level <=1.0f): "setSustainLevel: bad parameter. The value must be in the range [0..1].";
_sustainLevel = level;
_mustUpdate = true;
}

/**
* Applies this envelope to a byte array data frame.
* @param data Byte array data frame.
*
*/
public void apply(byte[] data)
{
if(_mustUpdate) buildEnvelope();
for(int i = 0; i < data.length; i++)
{
	data[i] = (byte)((float)data[i] * _env[i]);
}
}


private void buildEnvelope()
{
	_env = new float[getBufferSize()];
_state = ATTACK;
updateSlope();
for(int i = 0; i < _env.length; i++)
{
	_env[i] = _envSlope;
	_envSlope += _envSlopeInc;
	if(_state == SUSTAIN)
	{
		_sustainIndex++;
		if(_sustainIndex >= _sustainSize)
		{
			_state = RELEASE;
			updateSlope();
		}
	}
	else
	if(_state == ATTACK && _envSlope >= _amplitude)
	{
		_state = DECAY;
		updateSlope();
	}
	else if(_state == DECAY && _envSlope <= _sustainLevel)
	{
		_state = SUSTAIN;
		updateSlope();
	}
	else if(_state == RELEASE && _envSlope <= 0.0f)
	{
		_envSlope = 0.0f;
		_envSlopeInc = 0.0f;
		_state = END_ENV;
	}
}
_mustUpdate = false;
}

private void updateSlope()
{
switch(_state)
{
	case ATTACK:
	_envSlope = 0.0f;
	_envSlopeInc = _amplitude / (_attack_t * _sampleRate);
	break;
	case DECAY:
	_envSlopeInc = (_sustainLevel*_amplitude - _amplitude) / (_decay_t * _sampleRate);
	break;
	case SUSTAIN:
	_envSlopeInc = 0.0f;
	_sustainIndex = 1;
	break;
	case RELEASE:
	_envSlopeInc = -_envSlope / (_release_t * _sampleRate);
}
}

private int getBufferSize()
{
float samples_per_sec = _sampleRate*2.0f;
int retval = (int)(_duration*samples_per_sec/1000.0f);
retval -= (retval%4);
retval += 20;
return retval;
}

private void updateDuration()
{
	setADSR(_pattack, _pdecay, _psustain, _prelease);
}


private boolean _mustUpdate;
private int _bufferSize;
private int _sustainIndex;
private int _sustainSize;
private int _state;
	private float _envSlope;
	private float _envSlopeInc;
private float _sustainLevel;
private float _amplitude;
private float _attack_t;
private float _decay_t;
private float _release_t;
private float _sampleRate;
private float _duration;

private int _pattack;
private int _pdecay;
private int _psustain;
private int _prelease;

private float[] _env;

private static final int ATTACK = 0;
private static final int DECAY = 1;
private static final int SUSTAIN = 2;
private static final int RELEASE = 3;
private static final int END_ENV = 4;
}

// END
