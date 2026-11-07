/*
 * Copyright (c) 2026 Ismael Mosquera Rivera
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
* LineRenderer.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.plot;

import imr.math.Point2D;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Float;

/**
* The <code>LineRenderer</code> class just draws a line according to the p0 and p1 required points.
* <p>
* @author Ismael Mosquera Rivera
*
*/
public class LineRenderer extends Renderer
{

/**
* Constructor.
* <p>
* Makes a new instance for a <code>LineRenderer</code> object.
*
*/
public LineRenderer()
{
super();
}

/**
* Method to set the left and right points to draw the required line. <p>
* @param p0
* A <code>Point2D</code> object to set the left point.
* <p>
* @param p1
* A <code>Point2D</code> object to set the right point.
*
*/
public void setPoints(Point2D p0, Point2D p1)
{
_p0 = (Point2D)p0.clone();
_p1 = (Point2D)p1.clone();
_hasData = true;
}

/**
* This method does drawing.
* <p>
* @param g
* the graphics object to paint.
* <p>
* @param width
* the width of the area to paint.
* <p>
* @param height
* the height to the area to paint.
* <p>
* @see imr.plot.Renderer the subclass.
*
*/
public void render(Graphics g, int width, int height)
{
	if(!_hasData || g == null) return;
Graphics2D g2D = (Graphics2D)g;
g2D.setColor(_color);
g2D.setClip(0, 0, width, height);
g2D.draw(new Line2D.Float(getX((float)_p0.getX(), width), getY((float)_p0.getY(), height), getX((float)_p1.getX(), width), getY((float)_p1.getY(), height)));
}


// Data members
private Point2D _p0;
private Point2D _p1;

}

// END

