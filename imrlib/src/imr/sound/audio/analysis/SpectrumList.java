/*
 * Copyright (c) 2023-2024 Ismael Mosquera Rivera
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
* SpectrumList.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.sound.audio.analysis;

import java.util.LinkedList;
import java.util.ListIterator;


/**
* The <code>SpectrumList</code> class acts as a container for <code>Spectrum</code> objects.
* <p>
* It offers methods to add spectrums, clear the container, get the size ( number of elements in the container )
* <p>
* and an iterator to traverse the list.
* <p>
* @see java.util.LinkedList
* @see imr.sound.audio.analysis.Spectrum
*
* @author Ismael Mosquera Rivera
*
*/
public class SpectrumList
{

/**
* Constructor.
* Makes a new instance for a <code>SpectrumList</code> object.
*
*/
public SpectrumList()
{
_specLst = new LinkedList<Spectrum>();
}

/**
* Adds a new spectrum to the container.
* <p>
* @param spec A spectrum to be added to the list.
*
*/
public void addSpectrum(Spectrum spec)
{
_specLst.add(spec);
}

/**
* Gets an iterator suitable to traverse the list forward.
* <p>
* @see java.util.ListIterator
*
* @return An iterator to traverse the list forward
*
*/
public ListIterator<Spectrum> getIterator()
{
return _specLst.listIterator();
}

/**
* Clears the list, which cause that all its elements be discarded.
* <p>
* So, the list will become empty.
*
*/
public void clear()
{
_specLst.clear();
}

/**
* Gets the number of elements in the list.
* <p>
* @return Number of elements in the list
*
*/
public int size()
{
return _specLst.size();
}


private LinkedList<Spectrum> _specLst;
}

// END
