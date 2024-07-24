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
* Slot2.java
*
* imr-lib
*
* Author:Ismael Mosquera Rivera
*/

package imr.sigslot;

/**
* Interface for <code>Slot2</code>
* <p>
* Classes implementing this interface can be associated to <code>Signal2</code> objects.
* <p>
* You must implement its <code>void receive(F f, S s)</code> method in order to do some task when its associated <code>Signal2</code> emits.
* <p>
* Since it is a functional interface, you can use lambda expressions if you wish.
*
* @see imr.sigslot.Signal2 associated signal.
*
* @author Ismael Mosquera rivera
*
*/
public interface Slot2<F,S>
{

/**
* You must implement this method in order to do some task.
* <p>
* @param f Some generic type to receive
* @param s Some generic type to receive
*
*/
public void receive(F f,S s);

}

// END
