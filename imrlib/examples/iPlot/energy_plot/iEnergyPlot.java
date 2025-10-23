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
* iEnergyPlot.java ( energy plot demo )
*
* Author: Ismael Mosquera rivera
*/

import imr.util.FloatBuffer;
import imr.util.iArray;
import imr.sound.audio.FrameFactory;
import imr.sound.audio.analysis.FrameShifter;
import imr.sound.audio.analysis.SpectralAnalyzer;
import imr.sound.audio.analysis.Spectrum;
import imr.sound.audio.window.WindowType;
import imr.plot.*;
import imr.sound.audio.AudioFileIO;

import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/*
* This example demonstrates audio energy computation.
* For more details about this example look at the readme.txt file in this folder.
*
*/
public class iEnergyPlot
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
EnergyPlotGUI plotGUI = new EnergyPlotGUI();
	plotGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	plotGUI.run();
}
}


class EnergyPlotGUI extends JFrame
{

public EnergyPlotGUI()
{
this.setTitle("EnergyPlot");

audioPlot = new AudioPlot();
audioPlot.setPreferredSize(new Dimension(800, 300));
energyPlot = new SignalPlot();
energyPlot.setPreferredSize(new Dimension(800, 300));
energyPlot.getRenderer("Signal").setVBounds(0.0f, 1.0f);
energyPlot.getRenderer("Signal").setColor(Color.RED);
((DataRenderer)energyPlot.getRenderer("Signal")).setHugeMode(true);

this.getContentPane().add(audioPlot, BorderLayout.PAGE_START);
this.getContentPane().add(energyPlot, BorderLayout.PAGE_END);

audioPlot.setToolTipText(" Audio Signal ");
energyPlot.setToolTipText(" Energy ");

input = null;
try
{
input = AudioFileIO.load("audio.wav");
}
catch(UnsupportedAudioFileException e)
{
e.printStackTrace();
}

// Create menu
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

JMenuBar menuBar = new JMenuBar();
this.setJMenuBar(menuBar);
menuBar.add(fileMenu);

	this.pack();
}

public void run()
{
this.setVisible(true);
this.setLocation(XPOS, YPOS);
this.setSize(WIDTH, HEIGHT);
this.setResizable(false);
// Set plot data
((AudioPlot)audioPlot).setData(input);
((SignalPlot)energyPlot).setData(computeEnergy());
audioPlot.refresh();
energyPlot.refresh();
}


private float[] computeEnergy()
{
// initial frame size ( first frame )
int frame_length = FRAME_SIZE;
int hop_size = HOP_SIZE;
// we substract the hop size for the rest of frames
int frame_size = (frame_length - hop_size);
// load audio file to analyze
AudioInputStream input = null;
try
{
input = AudioFileIO.load("audio.wav");
}
catch(UnsupportedAudioFileException e)
{
e.printStackTrace();
}

float sample_rate = input.getFormat().getSampleRate();
// first frame
byte[] frame = new byte[frame_length];
SpectralAnalyzer analyzer = new SpectralAnalyzer(sample_rate);
analyzer.setWindowType(WindowType.wndHamming);
// frame shifter has always FRAME_SIZE constant value
FrameShifter shifter = new FrameShifter(frame_length);
// FloatBuffer to store energy
FloatBuffer energy = new FloatBuffer();
// read first audio frame
try
{
input.read(frame, 0, frame.length);
}
catch(IOException e)
{
e.printStackTrace();
}
// add first frame
shifter.add(frame);
// now, set hop size to shift
shifter.setShiftSize(hop_size);
// compute the first Specturm
Spectrum spec = analyzer.analyze(FrameFactory.getFrame(shifter.getFrame()));
energy.add(spec.getEnergy());
int bytesRead = 0;
// rest of frames have FRAME_SIZE - HOP_SIZE length
// since we shift hop size samples, we always have FRAME_SIZE samples.
frame = new byte[frame_size];
// read the rest of audio data
while(true)
{
	try
	{
	bytesRead = input.read(frame, 0, frame.length);
}
catch(IOException e)
{
e.printStackTrace();
}
// exit loop condition
	if(bytesRead == -1 || bytesRead == 0) break;
	// the last frame could be smaller
	if(bytesRead < frame_size)
	{
		// in that case, update frame size
	frame = (byte[])iArray.resize(frame, bytesRead);
	shifter.setFrameSize(hop_size+bytesRead);
	}
	shifter.add(frame);
	spec = analyzer.analyze(FrameFactory.getFrame(shifter.getFrame()));
	energy.add((float[])iArray.get(spec.getEnergy(), hop_size, spec.size()-1));
}
return energy.toFloatArray();
}


private AudioInputStream input;
private Plot audioPlot;
private Plot energyPlot;

private static final int XPOS = 200;
private static final int YPOS = 100;
private static final int WIDTH = 800;
private static final int HEIGHT = 600;

private static final int FRAME_SIZE = 1024;
private static final int HOP_SIZE = 512;

}

// END
