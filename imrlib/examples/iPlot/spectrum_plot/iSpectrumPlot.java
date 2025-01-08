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
* iSpectrumPlot.java ( spectrum plot demo )
*
* Author: Ismael Mosquera rivera
*/

import imr.plot.*;
import imr.sound.audio.FrameFactory;
import imr.sound.audio.analysis.*;
import imr.sound.audio.window.WindowType;
import imr.sound.audio.synthesis.Oscillator;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
* This example shows how to use the SpectrumPlot class.
* For more details about this example look at the readme.txt file in this folder.
*
*/
public class iSpectrumPlot
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
SpectrumPlotGUI plotGUI = new SpectrumPlotGUI();
	plotGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	plotGUI.run();
}
}


class SpectrumPlotGUI extends JFrame
{

public SpectrumPlotGUI()
{
this.setTitle("SpectrumPlot");

initData();
plot = new SpectrumPlot();
this.getContentPane().add(plot);
((SpectrumPlot)plot).setData(spec[0]);

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

JMenu freqMenu = new JMenu("Frequency");
freqMenu.getAccessibleContext().setAccessibleName("frequency");

zeroMenuItem = new JCheckBoxMenuItem("125 Hz", true);
zeroMenuItem.getAccessibleContext().setAccessibleName("125 hertz");
zeroMenuItem.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent event)
	{
	boolean state = zeroMenuItem.getState();
	if(state)
	{
		firstMenuItem.setState(false);
		secondMenuItem.setState(false);
		thirdMenuItem.setState(false);

		((SpectrumPlot)plot).setData(spec[0]);
	}
	else
	{
		thirdMenuItem.setState(true);
				firstMenuItem.setState(false);
				secondMenuItem.setState(false);

		((SpectrumPlot)plot).setData(spec[3]);
	}
	}
	});

firstMenuItem = new JCheckBoxMenuItem("250 Hz", false);
firstMenuItem.getAccessibleContext().setAccessibleName("250 hertz");
firstMenuItem.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent event)
	{
	boolean state = firstMenuItem.getState();
	if(state)
	{
		zeroMenuItem.setState(false);
		secondMenuItem.setState(false);
		thirdMenuItem.setState(false);

		((SpectrumPlot)plot).setData(spec[1]);
	}
	else
	{
		thirdMenuItem.setState(true);
				zeroMenuItem.setState(false);
				secondMenuItem.setState(false);

		((SpectrumPlot)plot).setData(spec[3]);
	}
	}
	});

secondMenuItem = new JCheckBoxMenuItem("500 Hz", false);
secondMenuItem.getAccessibleContext().setAccessibleName("500 hertz");
secondMenuItem.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent event)
	{
	boolean state = secondMenuItem.getState();
	if(state)
	{
		zeroMenuItem.setState(false);
		firstMenuItem.setState(false);
		thirdMenuItem.setState(false);

		((SpectrumPlot)plot).setData(spec[2]);
	}
	else
	{
		thirdMenuItem.setState(true);
				zeroMenuItem.setState(false);
				firstMenuItem.setState(false);

		((SpectrumPlot)plot).setData(spec[3]);
	}
	}
	});

thirdMenuItem = new JCheckBoxMenuItem("1000 Hz", false);
thirdMenuItem.getAccessibleContext().setAccessibleName("1000 hertz");
thirdMenuItem.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent event)
	{
	boolean state = thirdMenuItem.getState();
	if(state)
	{
		zeroMenuItem.setState(false);
		firstMenuItem.setState(false);
		secondMenuItem.setState(false);

		((SpectrumPlot)plot).setData(spec[3]);
	}
	else
	{
		zeroMenuItem.setState(true);
				firstMenuItem.setState(false);
				secondMenuItem.setState(false);

		((SpectrumPlot)plot).setData(spec[0]);
	}
	}
	});

freqMenu.add(zeroMenuItem);
freqMenu.add(firstMenuItem);
freqMenu.add(secondMenuItem);
freqMenu.add(thirdMenuItem);

JMenuBar menuBar = new JMenuBar();
this.setJMenuBar(menuBar);
menuBar.add(fileMenu);
menuBar.add(freqMenu);

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
	spec = new Spectrum[4];
	byte[] frame = new byte[1024];
	Oscillator osc = new Oscillator(0.8f, 125.0f, 44100.0f);
	SpectralAnalyzer analyzer = new SpectralAnalyzer(osc.getSampleRate());
analyzer.setWindowType(WindowType.wndBlackmanHarris92);

	osc.read(frame);
	spec[0] = analyzer.analyze(FrameFactory.getFrame(frame));
	osc.setFrequency(250.0f);
	osc.read(frame);
		spec[1] = analyzer.analyze(FrameFactory.getFrame(frame));
	osc.setFrequency(500.0f);
	osc.read(frame);
		spec[2] = analyzer.analyze(FrameFactory.getFrame(frame));
	osc.setFrequency(1000.0f);
	osc.read(frame);
		spec[3] = analyzer.analyze(FrameFactory.getFrame(frame));
}


private Spectrum[] spec;
private Plot plot;

private JCheckBoxMenuItem zeroMenuItem;
private JCheckBoxMenuItem firstMenuItem;
private JCheckBoxMenuItem secondMenuItem;
private JCheckBoxMenuItem thirdMenuItem;

private static final int XPOS = 200;
private static final int YPOS = 100;
private static final int WIDTH = 800;
private static final int HEIGHT = 500;
}

// END
