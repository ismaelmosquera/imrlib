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
* Signal3.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.sigslot;

/**
* The <code>Signal3</code> class can emit a signal with three parameters.
* <p>
* This signal must have a <code>Slot3</code> object associated in order to do some task when the signal emits.
* <p>
*
* @see imr.sigslot.Slot3 associated slot.
*
* @author Ismael Mosquera Rivera
*
*/
public class Signal3<F,S,T>
{

/**
* Constructor.
* <p>
* Makes a new instance of a <code>Signal3</code> object.
* <p>
* Since there is not an associated slot yet, the encapsulated one is set to <code>null</code>
*
*/
public Signal3()
{
_s = null;
}

/**
* Constructor.
* <p>
* Makes a new instance of a <code>Signal3</code> object.
* @param s Associated <code>Slot3</code> object.
*
*/
public Signal3(Slot3<F,S,T> s)
{
_s = s;
}

/**
* Emits a signal.
* <p>
* The emitted signal is catched by its associated slot in order to do some task.
* @param f A generic type.
* @param s A generic type
* @param t A generic type
*
*/
public void emit(F f,S s,T t)
{
if(_s ==null) return;
_s.receive(f,s,t);
}

/**
* Wraps a <code>Slot3</code> object to associate with this signal.
* @param s A <code>Slot3</code> object.
*
*/
public void wrap(Slot3<F,S,T> s)
{
_s = s;
}


private Slot3<F,S,T> _s;
}

// END
