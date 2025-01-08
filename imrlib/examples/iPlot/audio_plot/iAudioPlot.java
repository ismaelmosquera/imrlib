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
* iAudioPlot.java ( audio plot demo )
*
* Author: Ismael Mosquera rivera
*/

import imr.plot.Plot;
import imr.plot.AudioPlot;
import imr.sound.audio.AudioFileIO;
import imr.sound.Player;
import imr.sound.audio.AudioPlayer;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;


/*
* This example shows how to use the AudioPlot class.
* For more details about this example look at the readme.txt file in this folder.
*
*/
public class iAudioPlot
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
AudioPlotGUI plotGUI = new AudioPlotGUI();
	plotGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	plotGUI.run();
}
}


class AudioPlotGUI extends JFrame
{

public AudioPlotGUI()
{
this.setTitle("AudioPlot");

player = new AudioPlayer();
plot = new AudioPlot();
this.getContentPane().add(plot);

JMenu fileMenu = new JMenu("File");
 fileMenu.getAccessibleContext().setAccessibleName("file");

JMenuItem open = new JMenuItem("Open...");
open.getAccessibleContext().setAccessibleName("open");
open.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent event)
	{
JFileChooser chooser = new JFileChooser();
chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

FileFilter filter = new FileNameExtensionFilter("*.mp3 *.wav *.aif *.au audio files","mp3","wav","aif","au");

chooser.setFileFilter(filter);

int retVal = chooser.showOpenDialog(null);
if(retVal == JFileChooser.APPROVE_OPTION)
{
File file = chooser.getSelectedFile();
String fileName = null;
fileName = file.getAbsolutePath();
if(fileName != null)
{
	hasSound = true;
player.set(fileName);
AudioInputStream input = null;
try
{
	input = AudioFileIO.load(fileName);
}
catch(UnsupportedAudioFileException e)
{
	e.printStackTrace();
}
((AudioPlot)plot).setData(input);
}
else
{
	hasSound = false;
	JOptionPane.showMessageDialog(null,"Cannot open "+fileName);
}
}
	}
	});

JMenuItem quit = new JMenuItem("Exit");
	quit.getAccessibleContext().setAccessibleName("exit");
	quit.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent event)
		{
			System.exit(0);
		}
		});

	fileMenu.add(open);
			fileMenu.addSeparator();
	fileMenu.add(quit);

JMenu playMenu = new JMenu("Play");
playMenu.getAccessibleContext().setAccessibleName("play");

JMenuItem start = new JMenuItem("Start");
start.getAccessibleContext().setAccessibleName("start");
start.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent event)
	{
if(!hasSound) return;
player.play();
	}
	});
 JMenuItem pause = new JMenuItem("Pause");
 pause.getAccessibleContext().setAccessibleName("pause");
pause.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent event)
	{
if(!hasSound) return;
player.pause();
	}
	});
 JMenuItem stop = new JMenuItem("Stop");
 stop.getAccessibleContext().setAccessibleName("stop");
stop.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent event)
	{
if(!hasSound) return;
player.stop();
	}
	});

playMenu.add(start);
playMenu.add(pause);
playMenu.add(stop);

JMenuBar menuBar = new JMenuBar();
this.setJMenuBar(menuBar);
menuBar.add(fileMenu);
menuBar.add(playMenu);

	this.pack();
}


public void run()
{
this.setVisible(true);
this.setLocation(XPOS, YPOS);
this.setSize(WIDTH, HEIGHT);
plot.refresh();
}


private boolean hasSound;
private Plot plot;
private Player player;

private static final int XPOS = 200;
private static final int YPOS = 100;
private static final int WIDTH = 800;
private static final int HEIGHT = 500;
}

// END
