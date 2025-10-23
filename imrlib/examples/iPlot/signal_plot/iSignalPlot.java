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
* iSignalPlot.java ( signal plot demo )
*
* Author: Ismael Mosquera rivera
*/

import imr.plot.*;
import imr.sound.audio.FrameFactory;
import imr.sound.audio.synthesis.Wave;
import imr.sound.audio.synthesis.SawWave;
import imr.sound.audio.synthesis.SineWave;
import imr.sound.audio.synthesis.SquareWave;
import imr.sound.audio.synthesis.TriangularWave;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
* This example shows how to use the SignalPlot class.
* For more details about this example look at the readme.txt file in this folder.
*
*/
public class iSignalPlot
{
public static void main(String[] args)
{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

javax.swing.SwingUtilities.invokeLater( new Runnable()
{
public void run()
{
	createAndShowGUI();
}
});

}

private static void createAndShowGUI()
{
SignalPlotGUI plotGUI = new SignalPlotGUI();
	plotGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	plotGUI.run();
}
}


class SignalPlotGUI extends JFrame
{

public SignalPlotGUI()
{
this.setTitle("SignalPlot");

initData();
plot = new SignalPlot();
plot.addRenderer("Axis", new ZeroAxisRenderer());
plot.getRenderer("Axis").setColor(Color.BLUE);
plot.getRenderer("Axis").setVBounds(-1.0f, 1.0f);
plot.getRenderer("Signal").setVBounds(-1.0f, 1.0f);
this.getContentPane().add(plot);
((SignalPlot)plot).setData(wSaw);

JMenu fileMenu = new JMenu("File");
 fileMenu.getAccessibleContext().setAccessibleName("file");
JMenuItem quit = new JMenuItem("Exit");
	quit.getAccessibleContext().setAccessibleName("exit");
	quit.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent event)
		{
			System.exit(0);
		}
		});
	fileMenu.add(quit);

JMenu waveMenu = new JMenu("Wave");
waveMenu.getAccessibleContext().setAccessibleName("wave");

sawMenuItem = new JCheckBoxMenuItem("Saw", true);
sawMenuItem.getAccessibleContext().setAccessibleName("saw");
sawMenuItem.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent event)
	{
	boolean state = sawMenuItem.getState();
	if(state)
	{
		sineMenuItem.setState(false);
		squareMenuItem.setState(false);
		triangularMenuItem.setState(false);

		((SignalPlot)plot).setData(wSaw);
	}
	else
	{
		sineMenuItem.setState(true);
				squareMenuItem.setState(false);
				triangularMenuItem.setState(false);

		((SignalPlot)plot).setData(wSine);
	}
	}
	});

sineMenuItem = new JCheckBoxMenuItem("Sine", false);
sineMenuItem.getAccessibleContext().setAccessibleName("sine");
sineMenuItem.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent event)
	{
	boolean state = sineMenuItem.getState();
	if(state)
	{
		sawMenuItem.setState(false);
		squareMenuItem.setState(false);
		triangularMenuItem.setState(false);

		((SignalPlot)plot).setData(wSine);
	}
	else
	{
		sawMenuItem.setState(true);
				squareMenuItem.setState(false);
				triangularMenuItem.setState(false);

		((SignalPlot)plot).setData(wSaw);
	}
	}
	});

squareMenuItem = new JCheckBoxMenuItem("Square", false);
squareMenuItem.getAccessibleContext().setAccessibleName("square");
squareMenuItem.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent event)
	{
	boolean state = squareMenuItem.getState();
	if(state)
	{
		sawMenuItem.setState(false);
		sineMenuItem.setState(false);
		triangularMenuItem.setState(false);

		((SignalPlot)plot).setData(wSquare);
	}
	else
	{
		sawMenuItem.setState(true);
				sineMenuItem.setState(false);
				triangularMenuItem.setState(false);

		((SignalPlot)plot).setData(wSaw);
	}
	}
	});

triangularMenuItem = new JCheckBoxMenuItem("Triangular", false);
triangularMenuItem.getAccessibleContext().setAccessibleName("triangular");
triangularMenuItem.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent event)
	{
	boolean state = triangularMenuItem.getState();
	if(state)
	{
		sawMenuItem.setState(false);
		sineMenuItem.setState(false);
		squareMenuItem.setState(false);

		((SignalPlot)plot).setData(wTriangular);
	}
	else
	{
		sawMenuItem.setState(true);
				sineMenuItem.setState(false);
				squareMenuItem.setState(false);

		((SignalPlot)plot).setData(wSaw);
	}
	}
	});

waveMenu.add(sawMenuItem);
waveMenu.add(sineMenuItem);
waveMenu.add(squareMenuItem);
waveMenu.add(triangularMenuItem);

JMenuBar menuBar = new JMenuBar();
this.setJMenuBar(menuBar);
menuBar.add(fileMenu);
menuBar.add(waveMenu);

	this.pack();
}


public void run()
{
this.setVisible(true);
this.setLocation(XPOS, YPOS);
this.setSize(WIDTH, HEIGHT);
plot.refresh();
}



private void initData()
{
byte[] frame = new byte[1024];
wave = new SawWave(0.7f, 100.0f, 44100.0f);
wave.get(frame);
wSaw = FrameFactory.getFrame(frame);
wave = new SineWave(0.7f, 100.0f, 44100.0f);
wave.get(frame);
wSine = FrameFactory.getFrame(frame);
wave = new SquareWave(0.7f, 100.0f, 44100.0f);
wave.get(frame);
wSquare = FrameFactory.getFrame(frame);
wave = new TriangularWave(0.7f, 100.0f, 44100.0f);
wave.get(frame);
wTriangular = FrameFactory.getFrame(frame);
}


private float[] wSaw;
private float[] wSine;
private float[] wSquare;
private float[] wTriangular;

private Plot plot;
private Wave wave;

private JCheckBoxMenuItem sawMenuItem;
private JCheckBoxMenuItem sineMenuItem;
private JCheckBoxMenuItem squareMenuItem;
private JCheckBoxMenuItem triangularMenuItem;

private static final int XPOS = 200;
private static final int YPOS = 100;
private static final int WIDTH = 800;
private static final int HEIGHT = 500;
}

// END
