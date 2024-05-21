/*
 * Copyright (c) 2021-2022 Ismael Mosquera Rivera
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
* imterface Player
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.sound;

/**
* A <code>Player</code> provides methods to set, play, pause and stop
* a sound player.
* A class implementing this interface must define this methods.
*
* @author Ismael Mosquera Rivera
*/
public interface Player
{

	/**
	* Sets the sound file to this player.
	* @param fileName path to a file.
	*/
void set(String fileName);

/**
* Starts playing data.
*/
void play();

/**
* Pause playing.
* Calling the play method after calling this method continues playing
* starting from the point reached when this method is called .
*/
void pause();

/**
* Stops playing.
* Calling the play method after calling this method
* starts playing from the begin of the file.
*/
void stop();
}

// END
