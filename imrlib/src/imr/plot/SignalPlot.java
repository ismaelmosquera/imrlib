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
* SignalPlot.java
*
* imr-lib
*
* Author: Ismael Mosquera rivera
*/

package imr.plot;

import java.awt.Color;

/**
* The <code>SignalPlot</code> class is useful to draw an arbitrary signal.
* <p>
* We encourage you to run the signal plot example to know more about what this plot can do.
* <p>
* @see imr.plot.Plot the base class.
* <p>
* @author Ismael Mosquera Rivera
*
*/
public final class SignalPlot extends Plot
{

/**
* Constructor.
* <p>
* Makes a new instance for a <code>SignalPlot</code> object.
*
*/
public SignalPlot()
{
super();
addRenderer("Signal", new DataRenderer());
getRenderer("Signal").setColor(Color.BLUE);
refresh();
}

/**
* Sets the data to visualize.
* <p>
* @param signal the floating point vector containing the data to render.
*
*/
public void setData(float[] signal)
{
((DataRenderer)getRenderer("Signal")).setData(signal);
refresh();
}

}

// END
