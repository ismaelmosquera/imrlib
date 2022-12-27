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
* PlayingListListener interface
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.media;

/**
* The <code>PlayingListListener</code> interface provides a method
* to check when a playing list item change.
* an item in the list changes when a sound file is completely played.
* So you can retrieve the current item in the list for a <code>SounPlayer</code> object.
* <p>
* Example:
* <p>
* <code>
* <pre>
* Player player = new SoundPlayer();
((SoundPlayer)player).addPlayingListListener(new PlayingListListener()
{
	public void listItemChanged(int currentIndex,String currentFile)
	{
		System.out.println("index="+currentIndex+", file="+currentFile);
	}
	});
* </pre>
* </code>
* @author Ismael Mosquera Rivera
*/
public interface PlayingListListener
{

/**
* A class implementing this interface must define this method.
* @param currentIndex current index in the playind list.
* @param currentFile current file name.
*/
void listItemChanged(int currentIndex,String currentFile);
}

// END
