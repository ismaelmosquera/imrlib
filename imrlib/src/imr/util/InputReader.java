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
 InputReader.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.util;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.NumberFormatException;

/**
* The <code>InputReader</code> class defines static methods to get strings and any kind of
* atomic numeric data from the command line.
* @author Ismael Mosquera Rivera
*/
public class InputReader
{

/**
* Reads a string from the command line.
* @return the read string.
*/
  public static String readString()
  {
   String s = "";
   try
   {
    InputStreamReader is = new InputStreamReader(System.in);
    BufferedReader buff = new BufferedReader(is);
    s = buff.readLine();
   }
   catch(IOException e)
   {
     System.err.println("\nError: "+e.getMessage());
   }
   return s;
  }

/**
* gets a short value from the command line.
* @return the read short.
*/
  public static short readShort()
  {
   try
   {
    return Short.parseShort(readString());
   }
   catch(NumberFormatException e)
   {
     return Short.MIN_VALUE;
   }
  }

/**
* Reads an int value from the command line.
* @return the read int.
*/
  public static int readInt()
  {
   try
   {
     return Integer.parseInt(readString());
   }
   catch(NumberFormatException e)
   {
     return Integer.MIN_VALUE;
   }
  }

/**
* Reads a long value from the command line.
* @return the read long.
*/
  public static long readLong()
  {
   try
   {
     return Long.parseLong(readString());
   }
   catch(NumberFormatException e)
   {
    return Long.MIN_VALUE;
   }
  }

/**
* Reads a float value from the command line.
* @return the read float.
*/
  public static float readFloat()
  {
   try
   {
    return Float.parseFloat(readString());
   }
   catch(NumberFormatException e)
   {
     return Float.NaN;
   }
  }

/**
* Reads a double value from the command line.
* @return the read double.
*/
  public static double readDouble()
  {
   try
   {
     return Double.parseDouble(readString());
   }
   catch(NumberFormatException e)
   {
     return Double.NaN;
   }
  }

private InputReader(){}

}

// end
