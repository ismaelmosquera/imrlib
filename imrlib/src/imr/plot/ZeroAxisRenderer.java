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
* ZeroAxisRenderer.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.plot;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Float;

/**
* The <code>ZeroAxisRenderer</code> class just draws a horizontal line from left to right in the middle height.
* <p>
* This class is useful to show the zero axis for signals which vertical bounds are in the [-1 .. 1] range.
* <p>
* @see imr.plot.AudioPlot a visualizer that uses this renderer.
* <p>
* @author Ismael Mosquera Rivera
*
*/
public class ZeroAxisRenderer extends Renderer
{

/**
* Constructor.
* <p>
* Makes a new instance for a <code>ZeroAxisRenderer</code> object.
*
*/
public ZeroAxisRenderer()
{
super();
}

/**
* This method does drawing.
* <p>
* @param g the graphics object to paint.
* @param width the width of the area to paint.
* @param height the height to the area to paint.
* <p>
* @see imr.plot.Renderer the subclass.
*
*/
public void render(Graphics g, int width, int height)
{
	if(g == null) return;
float w = (float)width;
float h = (float)height / 2.0f;
Graphics2D g2D = (Graphics2D)g;
g2D.setColor(_color);
g2D.setClip(0, 0, width, height);
g2D.draw(new Line2D.Float(0.0f, h, w, h));
}

/*
* Since these protected methods are not needed here
* we return the same x and y values passed as parameter.
* Anyway, we must implement them since they are abstract in their base class.
*/
protected float getX(float x, int width)
{
return x;
}

protected float getY(float y, int height)
{
return y;
}

}

// END

