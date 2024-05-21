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
* iRecorder.java ( audio recorder demo )
*
* Author: Ismael Mosquera rivera
*/

import imr.sound.audio.AudioRecorder;
import imr.sigslot.Slot1;
import imr.plot.Plot;
import imr.plot.SignalPlot;
import imr.plot.DataRenderer;
import imr.plot.ZeroAxisRenderer;

import java.io.File;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;

import javax.sound.sampled.UnsupportedAudioFileException;

/*
* This example demonstrates the imr.sound.audio.AudioRecorder class through a GUI ( Graphical User Interface ) application.
* The imr.sigslot.Signal1 and the imr.sigslot.slot1 classes are also demonstrated.
* We expect that you enjoy this application example.
*
*/
public class iRecorder
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
RecorderGUI recorderGUI = new RecorderGUI();
	recorderGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	recorderGUI.run();
}
}


class RecorderGUI extends JFrame
{

public RecorderGUI()
{
this.setTitle("iRecorder");

plot = new SignalPlot();
plot.addRenderer("Axis", new ZeroAxisRenderer());
plot.getRenderer("Axis").setVBounds(-1.0f, 1.0f);
plot.getRenderer("Signal").setVBounds(-1.0f, 1.0f);
plot.getRenderer("Axis").setColor(Color.BLUE);
plot.getRenderer("Signal").setColor(Color.BLUE);
this.getContentPane().add(plot);

recorder = new AudioRecorder(11025.0f);
recorder.setSlot(new Slot<float[]>());
hasRecordedAudio = false;
isPlaying = false;
isRecording = false;

// create menus
JMenu fileMenu = new JMenu("File");
 fileMenu.getAccessibleContext().setAccessibleName("file");

JMenuItem save = new JMenuItem("Save...");
save.getAccessibleContext().setAccessibleName("save");
save.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent event)
	{
		if(hasRecordedAudio)
		{
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

			FileFilter filter = new FileNameExtensionFilter("*.wav *.aif *.au Audio files","wav","aif","au");

			chooser.setFileFilter(filter);

			int retVal = chooser.showSaveDialog(null);
			if(retVal == JFileChooser.APPROVE_OPTION)
			{
			File file = chooser.getSelectedFile();
			String fileName = file.getAbsolutePath();
			try
			{
				recorder.save(fileName);
			}
			catch(UnsupportedAudioFileException e)
			{
JOptionPane.showMessageDialog(null,"Sorry, something was wrong.");
			}
		}
}
else
{
JOptionPane.showMessageDialog(null,"Sorry, there is not record yet.");
}
	}
	});

JMenuItem quit = new JMenuItem("Exit");
	quit.getAccessibleContext().setAccessibleName("exit");
	quit.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent event)
		{
			recorder.stop();
			try
			{
				Thread.sleep(500);
			}
			catch(InterruptedException e) {}
			System.exit(0);
		}
		});
		fileMenu.add(save);
		fileMenu.addSeparator();
		fileMenu.add(quit);

JMenu recorderMenu = new JMenu("Recorder");
recorderMenu.getAccessibleContext().setAccessibleName("recorder");

JMenuItem play = new JMenuItem("Play");
play.getAccessibleContext().setAccessibleName("play");
play.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent event)
	{
if(!hasRecordedAudio)
{
JOptionPane.showMessageDialog(null,"Sorry, there is not record yet.");
}
else if(isPlaying || isRecording)
{
	JOptionPane.showMessageDialog(null,"Sorry, busy device: select Recorder -> stop to finish.");
}
else
{
	isPlaying = true;
isRecording = false;
recorder.play();
}
	}
	});

 JMenuItem rec = new JMenuItem("Rec");
 rec.getAccessibleContext().setAccessibleName("start recording");
rec.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent event)
	{
if(isPlaying || isRecording)
{
	JOptionPane.showMessageDialog(null,"Sorry, busy device: select Recorder -> stop to finish.");
}
else
{
	isRecording = true;
	isPlaying = false;
	recorder.rec();
}

	}
	});

 JMenuItem stop = new JMenuItem("Stop");
 stop.getAccessibleContext().setAccessibleName("stop");
stop.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent event)
	{
recorder.stop();
isPlaying = false;
if(isRecording)
{
	isRecording = false;
	hasRecordedAudio = true;
}
	}
	});

recorderMenu.add(play);
recorderMenu.add(rec);
recorderMenu.add(stop);

JMenuBar menuBar = new JMenuBar();
this.setJMenuBar(menuBar);
menuBar.add(fileMenu);
menuBar.add(recorderMenu);

this.pack();
}

public void run()
{
this.setVisible(true);
this.setLocation(XPOS, YPOS);
this.setSize(WIDTH, HEIGHT);
plot.refresh();
}


private boolean hasRecordedAudio;
private boolean isPlaying;
private boolean isRecording;

private AudioRecorder recorder;
private Plot plot;

private static final int XPOS = 200;
private static final int YPOS = 100;
private static final int WIDTH = 800;
private static final int HEIGHT = 500;
private static final int FRAME_SIZE = 11025;

private class Slot<T> implements Slot1<T>
{

@SuppressWarnings("unchecked")
public void receive(T t)
{
	float[] x = (float[])t;
if(x == null) return;
boolean mode = (x.length > FRAME_SIZE) ? true : false;
if(mode)
{
isPlaying = false;
isRecording = false;
}
((DataRenderer)plot.getRenderer("Signal")).setHugeMode(mode);
((SignalPlot)plot).setData(x);
}
}

}

// END
