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
* Plot.java
*
* imr-lib
*
* Author: Ismael Mosquera Rivera
*/

package imr.plot;

import java.util.Map;
import java.util.HashMap;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JComponent;


/**
* The <code>Plot</code> class is the base class from you can derive
* <p>
* your concrete subclasses in order to get your desired visualizer.
* <p>
* You can add and remove renderers to do that.
* <p>
* @see imr.plot.Renderer the renderer base class
* @see javax.swing.JComponent the superclass.
*
* @author Ismael Mosquera Rivera
*/
public abstract class Plot extends JComponent
{

/**
* Constructor.
* <p>
* Makes a new instance for a <code>Plot</code> class.
*
*/
public Plot()
{
	super();
	setOpaque(true);
	setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
setBackground(Color.WHITE);
setForeground(Color.BLUE);
_renderers = new HashMap<String, Renderer>();
}

/**
* Adds a new <code>Renderer</code> object to this plot.
* <p>
* @param name A name for the renderer.
* @param rend A renderer to add.
* <p>
* @see imr.plot.Renderer the renderer base class.
*
*/
public void addRenderer(String name, Renderer rend)
{
if(_renderers.containsKey(name)) return;
_renderers.put(name, rend);
}

/**
* Gets a previously added renderer.
* <p>
* @param name the registered name of the renderer to get.
* <p>
* @return the requested renderer or null if the renderer cannot be found.
*
*/
public Renderer getRenderer(String name)
{
if(!_renderers.containsKey(name)) return null;
return _renderers.get(name);
}

/**
* This method paints the content for this visualizer.
* <p>
* What it does is to call its registered renderers to do their concrete work.
* <p>
* This method overrides the one from it superclass.
* <p>
* @param g the Graphics object to paint.
*
*/
@Override
public void paint(Graphics g)
{
	if(g != null) g.clearRect(0, 0, getWidth(), getHeight());
	if(_renderers.isEmpty()) return;
String[] keys = getRenderers();
for(int i = 0; i < keys.length; i++)
{
	_renderers.get(keys[i]).render(g, getWidth(), getHeight());
}
}

/**
* Repaints the plot.
* <p>
* Actually, this method calls the paint method in order to refresh the content of this visualizer.
*
*/
public void refresh()
{
paint(getGraphics());
}

/**
* Removes a previously registered renderer.
* <p>
* @param name the name of the renderer to remove.
*
*/
public void removeRenderer(String name)
{
if(!_renderers.containsKey(name)) return;
	_renderers.remove(name);
}


private String[] getRenderers()
{
	Object[] obj = _renderers.keySet().toArray();
	String[] keys = new String[obj.length];
	for(int i = 0; i < obj.length; i++) keys[i] = (String)obj[i];
return keys;
}


private Map<String, Renderer> _renderers;

}

// END
