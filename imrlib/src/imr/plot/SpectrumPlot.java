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
* SpectrumPlot.java
*
* imr-lib
*
* Author: Ismael Mosquera rivera
*/

package imr.plot;

import imr.sound.audio.analysis.Spectrum;
import java.awt.Color;

/**
* The <code>SpectrumPlot</code> class has functionallity to visualize spectral data.
* <p>
* @see imr.plot.Plot the superclass.
* <p>
* @author Ismael Mosquera Rivera.
*
*/
public final class SpectrumPlot extends Plot
{

/**
* Constructor.
* <p>
* Makes a new instance for a <code>SpectrumPlot</code> object.
*
*/
public SpectrumPlot()
{
super();
addRenderer("Spectrum", new DataRenderer());
getRenderer("Spectrum").setVBounds(-150.0f, 0.0f);
getRenderer("Spectrum").setColor(Color.BLUE);
((DataRenderer)getRenderer("Spectrum")).setHugeMode(false);
refresh();
}

/**
* Set the spectral data to visualize.
* <p>
* @param spec the spectrum to render.
* <p>
* @see imr.sound.audio.analysis.Spectrum the spectrum class.
*
*/
public void setData(Spectrum spec)
{
((DataRenderer)getRenderer("Spectrum")).setData(spec.getMagnitude2dBSpectrum());
refresh();
}

}

// END
