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
* MIDIFile.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.media.midi;

import java.io.RandomAccessFile;
import java.io.IOException;

/**
* The <code>MIDIFile</code> abstract class provides methods
* to init an run a r/w process.
* Also defines a method to close a file.
* Classes that extends this one have to define the init and run methods that declares.
* @author Ismael Mosquera Rivera
*/
public abstract class MIDIFile
{

/**
* Prepares the process to handle the file passed as parameter.
* @param fileName a file name.
* @return <code>true</code> if there was a success or <code>false</code> otherwise.
*/
public abstract boolean init(String fileName);

/**
* Makes a r/w process and stores the result
* int the <code>MIDISong</code> object passed as parameter.
* @param song a <code>MIDISong</code> to fill.
* @return <code>true</code> if there was a success or <code>false</code> otherwise.
* @see MIDISong
*/
public abstract boolean run(MIDISong song);

protected void fclose()
{
try
{
file.close();
}
catch(IOException e)
{
e.printStackTrace();
}
}

protected boolean hasFile;
protected RandomAccessFile file;
protected static final int[] bytesPerMsg = {3,3,3,3,2,3,3};
}

// END
