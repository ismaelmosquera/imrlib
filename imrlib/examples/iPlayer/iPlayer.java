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
* iPlayer ( imr.sound.SoundPlayer demo ).
*
* Author: Ismael Mosquera Rivera
*/

import imr.sound.Player;
import imr.sound.SoundPlayer;
import imr.sound.PlayingListListener;

import java.io.File;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;

public class iPlayer
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
	iSoundPlayer player = new iSoundPlayer();
	player.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	player.run();
}
}

@SuppressWarnings("unchecked")
class iSoundPlayer extends JFrame
{
public iSoundPlayer()
{
this.setTitle("iPlayer");

this.hasSound = false;
this.player = new SoundPlayer();

((SoundPlayer)player).addPlayingListListener(new PlayingListListener()
{
	public void listItemChanged(int currentIndex, String currentFile)
	{
		list.setSelectedIndex(currentIndex);
	}
	});

this.list = new JList();
this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
this.list.setLayoutOrientation(JList.VERTICAL_WRAP);

JScrollPane listScroller = new JScrollPane(list);
listScroller.setPreferredSize(new Dimension(250,80));

this.getContentPane().add(listScroller);

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

FileFilter filter = new FileNameExtensionFilter("*.mp3 *.m3u *.mid *.wav *.aif *.au sound files","mp3","m3u","mid","wav","aif","au");

chooser.setFileFilter(filter);

int retVal = chooser.showOpenDialog(null);
if(retVal == JFileChooser.APPROVE_OPTION)
{
File file = chooser.getSelectedFile();
String fileName = file.getAbsolutePath();
((SoundPlayer)player).clear();
player.set(fileName);
files = ((SoundPlayer)player).files();
if(files.length > 0)
{
hasSound = true;
list.setListData(files);
list.setSelectedIndex(((SoundPlayer)player).firstIndex());
	player.play();
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
			player.stop();
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

shuffle = new JCheckBoxMenuItem("Shuffle",false);
shuffle.getAccessibleContext().setAccessibleName("shuffle");
shuffle.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent event)
	{
	boolean state = shuffle.getState();
	if(state)
	{
		loop.setState(false);
		((SoundPlayer)player).loop(false);
		((SoundPlayer)player).shuffle(true);
	}
	else
	{
		((SoundPlayer)player).shuffle(false);
	}
	}
	});

loop = new JCheckBoxMenuItem("Loop",false);
loop.getAccessibleContext().setAccessibleName("loop");
loop.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent event)
	{
	boolean state = loop.getState();
	if(state)
	{
		shuffle.setState(false);
		((SoundPlayer)player).shuffle(false);
		((SoundPlayer)player).loop(true);
	}
	else
	{
		((SoundPlayer)player).loop(false);
	}
	}
	});

	JMenuItem playSelection = new JMenuItem("Play Selection");
	playSelection.getAccessibleContext().setAccessibleName("play selection");
	playSelection.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent event)
		{
			int index = list.getSelectedIndex();
			if(index >= 0)
			{
				boolean b = ((SoundPlayer)player).set(index);
				if(b) player.play();
			}
		}
		});

playMenu.add(start);
playMenu.add(pause);
playMenu.add(stop);
playMenu.addSeparator();
playMenu.add(shuffle);
playMenu.add(loop);
playMenu.addSeparator();
playMenu.add(playSelection);

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
}

private boolean hasSound;
private String[] files;
private Player player;

private static final int XPOS = 200;
private static final int YPOS = 100;
private static final int WIDTH = 800;
private static final int HEIGHT = 600;
JCheckBoxMenuItem shuffle;
JCheckBoxMenuItem loop;
JList list;
}

// END
