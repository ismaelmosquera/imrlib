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
* Slot0.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.sigslot;

/**
* Interface for <code>Slot0</code>
* <p>
* Classes implementing this interface can be associated to <code>Signal0</code> objects.
* <p>
* You must implement its <code>void receive()</code> method in order to do some task when its associated <code>Signal0</code> emits.
* <p>
* Since it is a functional interface, you can use lambda expressions if you wish.
*
* @see imr.sigslot.Signal0 associated signal.
*
* @author Ismael Mosquera rivera
*
*/
public interface Slot0
{

/**
* You must implement this method in order to do some task.
*
*/
public void receive();

}

// END
