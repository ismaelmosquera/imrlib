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
* Renderer.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.plot;

import java.awt.Graphics;
import java.awt.Color;

/**
* The <code>Renderer</code> class is the base class from you can derive your concrete subclasses to draw.
* <p>
* This abstract class implements some common methods common to any renderer
* <p>
* and declares some abstract methods which must be implemented in the concrete subclass.
* <p>
* @see imr.plot.Plot the plot base class where renderers do paint.
* <p>
* @author Ismael Mosquera Rivera
*
*/
public abstract class Renderer
{

/**
* Constructor.
*
*/
public Renderer()
{
_xmin = 0.0f;
_xmax = 1.0f;
_ymin = 0.0f;
_ymax = 1.0f;
_color = Color.BLUE;
}

/**
* Sets the color for this renderer.
* <p>
* @param c color for this renderer.
* <p>
* @see java.awt.Color a suitable <code>Color</code> class from java.awt
*
*/
public void setColor(Color c)
{
_color = c;
}

/**
* Sets the horizontal bounds for this renderer.
* <p>
* @param xmin minimum value.
* @param xmax maximum value.
*
*/
public void setHBounds(float xmin, float xmax)
{
_xmin = xmin;
_xmax = xmax;
}

/**
* Sets the vertical bounds for this renderer.
* <p>
* @param ymin minimum value.
* @param ymax maximum value.
*
*/
public void setVBounds(float ymin, float ymax)
{
_ymin = ymin;
_ymax = ymax;
}

/**
* This abstract method must be implemented for any subclass.
* <p>
* Determines the content for the renderer to draw.
* <p>
* @param g the graphics object to draw.
* @param width the width value for the area to paint.
* @param height the height value for the area to paint.
* <p>
* @see java.awt.Graphics a graphics object to do the paint.
*
*/
public abstract void render(Graphics g, int width, int height);


protected float _xmin;
protected float _xmax;
protected float _ymin;
protected float _ymax;

protected Color _color;
}

// END
