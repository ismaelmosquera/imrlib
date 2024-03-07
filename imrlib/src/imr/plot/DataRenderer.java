/*
 * Copyright (c) 2024 Ismael Mosquera Rivera
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
* DataRenderer.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.plot;

import imr.util.iArray;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Float;

/**
* The <code>DataRenderer</code> class renders data contained in a floating point vector.
* <p>
* This class is useful to render audio, spectrum and signal data among others.
* <p>
* @see imr.plot.Renderer the superclass.
* @see imr.plot.Plot where renderers draw.
* <p>
* @author Ismael Mosquera Rivera
*
*/
public class DataRenderer extends Renderer
{

/**
* Constructor.
* <p>
* Makes a new instance for a <code>DataRenderer</code> object.
*
*/
public DataRenderer()
{
super();
_data = null;
_hugeMode = false;
_lastWidth = -1;
_newData = false;
}

/**
* Sets the data to draw.
* <p>
* @param data contains the data to paint.
*
*/
public void setData(float[] data)
{
	if(data == null)
	{
		_data = null;
		return;
	}
	_data = (float[])iArray.clone(data);
	_newData = true;
	if(!_hugeMode) setHBounds(0.0f, (float)_data.length-1);
}

/**
* Draws the data.
* <p>
* @param g the graphics object to paint.
* @param width the width of the area to paint.
* @param height the height of the area to paint.
* <p>
* @see imr.plot.Renderer the subclass .
*
*/
public void render(Graphics g, int width, int height)
{
	if(g == null) return;
	if(_data == null) return;
	Graphics2D g2D = (Graphics2D)g;
g2D.setColor(_color);
g2D.setClip(0, 0, width, height);
if(_hugeMode)
{
	setHBounds(0.0f, (float)width);
	if(_newData || _lastWidth != width)
	{
		buildMinMaxArrays(width);
		_lastWidth = width;
		_newData = false;
	}
	g2D.draw(new Line2D.Float(0.0f, getY(_max[0], height), 0.0f, getY(_min[0], height)));
	for(int i = 1; i < _min.length; i++)
	{
		if(i % 2 == 0)
		{
			g2D.draw(new Line2D.Float((float)(i-1), getY(_max[i-1], height), (float)i, getY(_min[i], height)));
		}
		else
		{
			g2D.draw(new Line2D.Float((float)(i-1), getY(_min[i-1], height), (float)i, getY(_max[i], height)));
		}
	}
}
else
{
int n = _data.length-1;
for(int i = 0; i < n; i++)
{
	g2D.draw(new Line2D.Float(getX((float)i, width), getY(_data[i], height), getX((float)(i+1), width), getY(_data[i+1], height)));
}
}
}

/**
* Sets the huge mode for this data renderer.
* <p>
* The huge mode must to be set if the size of the data to render is too large.
* <p>
* @param hm if true the huge mode is enabled, otherwise it is not.
*
*/
public void setHugeMode(boolean hm)
{
_hugeMode = hm;
}

/**
* Maps the x coordinate from logical to phisical.
* <p>
* @param x the x coordinate to be mapped.
* @param width the width value for the area to map.
* <p>
* @return the mapped coordinate.
*
*/
protected float getX(float x, int width)
{
return (x - _xmin) / (_xmax - _xmin) * (float)width;
}

/**
* Maps the y coordinate from logical to phisical.
* <p>
* @param y the y coordinate to be mapped.
* @param height the height value for the area to map.
* <p>
* @return the mapped coordinate.
*
*/
protected float getY(float y, int height)
{
return (1.0f - (y - _ymin) / (_ymax - _ymin)) * (float)height;
}


private float[] getMinMax(float[] v)
{
if(v == null) return null;
float[] min_max = new float[2];
float min = 0.0f;
float max = 0.0f;
for(int i = 0; i < v.length; i++)
{
if(v[i] < min) min = v[i];
if(v[i] > max) max = v[i];
}
min_max[0] = min;
min_max[1] = max;
return min_max;
}

private void buildMinMaxArrays(int width)
{
int chunkSize = (int)(_data.length / width);
int lastChunkSize = (_data.length % width);
int firstIndex = 0;
int lastIndex = chunkSize;
_min = new float[width];
_max = new float[width];
float[] minmax = null;
float[] chunk = null;
int n = (lastChunkSize == 0) ? _max.length : _max.length-1;
for(int i = 0; i < n; i++)
{
chunk = (float[])iArray.get(_data, firstIndex, lastIndex-1);
minmax = getMinMax(chunk);
_min[i] = minmax[0];
_max[i] = minmax[1];
firstIndex = lastIndex;
lastIndex += chunkSize;
}
if(lastChunkSize != 0)
{
	lastIndex = firstIndex + lastChunkSize;
	chunk = (float[])iArray.get(_data, firstIndex,lastIndex-1);
	minmax = getMinMax(chunk);
	_min[width-1] = minmax[0];
	_max[width-1] = minmax[1];
}
}


private boolean _newData;
private boolean _hugeMode;
private int _lastWidth;

private float[] _data;
private float[] _min;
private float[] _max;
}

// END
