/*
 * Copyright (c) 2025 Ismael Mosquera Rivera
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
* Point2D.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*
*/

package imr.math;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

@SuppressWarnings("unchecked")

/**
* This class is an abstraction of a two-dimensional point.
* <p>
* There are basic methods to manage a point (x,y).
* <p>
* In addition, we added support to storage.
* <p>
* @author Ismael Mosquera rivera.
*
*/
public final class Point2D
{

/**
* Constructor.
* <p>
* Default constructor: (0,0).
*
*/
public Point2D()
{
this(0.0f, 0.0f);
}

/**
* Constructor.
* <p>
* @param x
* x coordinate.
* @param y
* y Coordinate.
*
*/
public Point2D(float x, float y)
{
_x = x;
_y = y;
}

/**
* Gets the X coordinate of this point.
* <p>
* @return X coordinate.
*
*/
public float getX()
{
return _x;
}

/**
* Gets the Y coordinate of this point.
* <p>
* @return Y coordinate.
*
*/
public float getY()
{
return _y;
}

/**
* Sets the X coordinate to this point.
* <p>
* @param x X coordinate.
*
*/
public void setX(float x)
{
_x = x;
}

/**
* Sets the Y coordinate for this point.
* <p>
* @param y Y coordinate.
*
*/
public void setY(float y)
{
_y = y;
}

/**
* Prints this point to the console.
* <p>
* The format is the same as the one returned by the
* <p>
* <code>String toString()</code> method overriding from the one inherited from Object.
* <p>
* (x,y)
*
*/
public void print()
{
System.out.println(this);
}

/**
* This static method prints a <code>Point2D</code> array to the console.
* <p>
* @param p
* A <code>Point2D</code> array.
*
*/
public static void print(Point2D[] p)
{
if(p == null)
{
System.out.println("[ null ]");
return;
}
int n = p.length;
System.out.print("[");
for(int i = 0; i < n; i++)
{
	if(i > 0) System.out.print(", ");
	System.out.print(p[i]);
}
System.out.println("]");
}

/**
* Loads a <code>Point2D</code> object from a file.
* <p>
* @param filename File from load the data.
* <p>
* Format:
* <p>
* x, y
* <p>
* example:
* <p>
* 2.0 9.0
* <p>
* We take the *.dat extension for that kind of file.
* <p>
* @return this
*
*/
public Point2D load(String filename)
{
Scanner in = null;
try
{
	in = new Scanner(new BufferedReader(new FileReader(filename)));
	in.useLocale(Locale.US);
	_x = in.nextFloat();
_y = in.nextFloat();
}
catch(FileNotFoundException e)
{
	System.err.printf("ComplexNumber FileNotFoundException: %s file not found.%n",filename);
}
finally
{
		if(in != null) in.close();
}
return this;
}

/**
* Stores this point to a file.
* <p>
* @param filename File to store the data.
* <p>
* Format:
* <p>
* x, y
* <p>
* example:
* <p>
* 0.5 1.0
* <p>
* We take the *.dat extension for that kind of file.
*
*/
public void store(String filename)
{
PrintWriter out = null;
try
{
	out = new PrintWriter(filename);
	out.println(_x+" "+_y);
}
catch(IOException e)
{
	System.err.println("ComplexNumber IOException: "+e);
}
finally
{
		if(out != null) out.close();
}
}

/**
* Adds this point to the one passed as parameter.
* <p>
* @param p A <code>Point2D</code> object.
* <p>
* @return this + p.
*
*/
public Point2D add(Point2D p)
{
return add(this, p);
}


/**
* Substracts this point to the one passed as parameter.
* <p>
* @param p A <code>Point2D</code> object.
* <p>
* @return this - p.
*
*/
public Point2D sub(Point2D p)
{
return sub(this, p);
}


/**
* Scales this point by the factor passed as parameter.
* <p>
* @param factor A float value to scale this point.
* <p>
* @return this point scaled by the factor passed as parameter.
*
*/
public Point2D scale(float factor)
{
return scale(this, factor);
}

// Static methods

/**
* Adds two points.
* <p>
* @param p1 A <code>Point2D</code> object.
* @param p2 A <code>Point2D</code> object.
* <p>
* @return p1 + p2
*
*/
public static Point2D add(Point2D p1, Point2D p2)
{
return new Point2D(p1.getX()+p2.getX(), p1.getY()+p2.getY());
}

/**
* Substracts two points.
* <p>
* @param p1 A <code>Point2D</code> object.
* @param p2 A <code>Point2D</code> object.
* <p>
* @return p1 - p2.
*
*/
public static Point2D sub(Point2D p1, Point2D p2)
{
	return new Point2D(p1.getX()-p2.getX(), p1.getY()-p2.getY());
}

/**
* Scales a point to a factor.
* <p>
* @param p A <code>Point2D</code> object.
* @param factor A float value to scale this point.
* <p>
* @return This point scaled by the factor passed as parameter.
*
*/
public static Point2D scale(Point2D p, float factor)
{
return new Point2D(p.getX()*factor, p.getY()*factor);
}

// Overrided methods from Object.

/**
* Evaluates for equality.
* <p>
* @param obj Object to compare.
* <p>
* @return true if equal or false otherwise.
*
*/
public boolean equals(Object obj)
{
	Point2D p = (Point2D)obj;
	return (_x == p.getX() && _y == p.getY());
}

/**
* Gets a clone of this point.
* <p>
* You must cast in order to get the expected result.
* <p>
* Example:
* <p>
* Point2D p = (Point2D)point.clone();
* <p>
* @return A clone of this point.
*
*/
public Object clone()
{
return new Point2D(_x, _y);
}

/**
* Gets a string representation of this point.
* <p>
* (x,y)
* <p>
* @return A string representation of this point.
*
*/
public String toString()
{
String s = "";
s += "("+_x+","+_y+")";
return s;
}


private float _x;
private float _y;
}

// END
