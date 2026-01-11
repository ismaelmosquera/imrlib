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
* iInterDec.java
*
* Author: Ismael Mosquera Rivera
*
*/

import imr.util.iArray;
import imr.sound.audio.FrameFactory;
import imr.sound.audio.RawDataPlayer;
import imr.sound.audio.RawDataStorage;

import imr.sound.audio.synthesis.Envelope;
import imr.sound.audio.synthesis.Wave;
import imr.sound.audio.synthesis.SawWave;
import imr.sound.audio.synthesis.SineWave;
import imr.sound.audio.synthesis.SquareWave;
import imr.sound.audio.synthesis.TriangularWave;

import imr.sound.audio.window.Window;
import imr.sound.audio.window.BlackmanHarris92Window;
import imr.sound.audio.window.GaussianWindow;
import imr.sound.audio.window.HammingWindow;
import imr.sound.audio.window.TriangularWindow;

import imr.plot.Plot;
import imr.plot.SignalPlot;
import imr.plot.ZeroAxisRenderer;

import imr.sigslot.Signal1;
import imr.sigslot.Slot1;

import imr.sound.audio.filter.LinearSampler;

import java.util.Hashtable;
import java.io.File;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;

import javax.sound.sampled.UnsupportedAudioFileException;


/*
* This example demonstrates several classes in this library.
* A decimator and an interpolator implemented as static methods in the LinearSampler class.
* We also test the SignalPlot class adding two objects to the interface.
*
* - A plot to show ADSR or Window envelopes.
* - A plot to show a signal ( saw, sine, square or triangular ).
*
* Each time you change the frequency using its slider, a randomly selected scale is generated.
* There are vector of semitones for the following tones:
* - F# minor.
* - A major.
* - G minor.
* - Bb major.
* - A minor.
* - C major.
* - B minor.
* - D major.
*
* You can apply an ADSR or a Window envelope to the generated signal,
* both of them or just nothing.
*
* You also have the option of recording it and save the result to an audio file.
*
* We expect that you enjoy this application example.
*
*/
public class iInterDec
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
iInterDecGUI gui = new iInterDecGUI();
	gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	gui.showGUI();
}
}

@SuppressWarnings("unchecked")
class iInterDecGUI extends JFrame
{

@SuppressWarnings("removal")
public iInterDecGUI()
{
this.setTitle("iInterDec");

_player = new RawDataPlayer(SAMPLE_RATE);
_saver = new RawDataStorage(SAMPLE_RATE);

_decimationValue = 0;
_interpolationValue = 0;

_isProcessing = false;
_stopRequested = false;
_recRequested = false;
_hasRecord = false;
_applyADSR = true;
_applyWindow = true;
_mustResetSaver = false;

_showADSR = true;
_showWindow = false;
_envChanged = true;
_windowChanged = false;

_scale = new float[st0.length+1];
_semitones = (int[])iArray.clone(st5);
composeScale(FIRST_FREQUENCY);

_sawWave = new SawWave(0.5f, FIRST_FREQUENCY, SAMPLE_RATE);
_sineWave = new SineWave(0.5f, FIRST_FREQUENCY, SAMPLE_RATE);
_squareWave = new SquareWave(0.5f, FIRST_FREQUENCY, SAMPLE_RATE);
_triangularWave = new TriangularWave(0.5f, FIRST_FREQUENCY, SAMPLE_RATE);
_wave = _sineWave;

_attack = 20;
_decay = 20;
_sustain = 50;
_release = 10;

_env = new Envelope(0.7f, _attack, _decay,_sustain, _release, SAMPLE_RATE, 0.5f, 500.0f);

buildWindows();

_envPlot = new SignalPlot();
_envPlot.getRenderer("Signal").setVBounds(0.0f, 1.0f);
_envPlot.getRenderer("Signal").setColor(Color.BLUE);

_wavePlot = new SignalPlot();
_wavePlot.addRenderer("Axis", new ZeroAxisRenderer());
_wavePlot.getRenderer("Axis").setVBounds(-1.0f, 1.0f);
_wavePlot.getRenderer("Signal").setVBounds(-1.0f, 1.0f);
_wavePlot.getRenderer("Axis").setColor(Color.BLUE);
_wavePlot.getRenderer("Signal").setColor(Color.BLUE);

_windowSignal = new Signal1<>();
_waveSignal = new Signal1<>();

_env.setSlot(new EnvSlot<float[]>());
_windowSignal.wrap(new WindowSlot<float[]>());
_waveSignal.wrap(new WaveSlot<float[]>());

JPanel pane = new JPanel(new GridBagLayout());
pane.setFocusable(true);
GridBagConstraints c = new GridBagConstraints();
// app fonts
Font appLabelFont = new Font("Arial", Font.PLAIN, 12);
Font sliderLabelFont = new Font("Arial", Font.PLAIN, 10);
// slider label tables
Hashtable asSliderLabels = new Hashtable();
asSliderLabels.put(new Integer(0), new JLabel("0.0"));
asSliderLabels.put(new Integer(5), new JLabel("0.5"));
asSliderLabels.put(new Integer(10), new JLabel("1.0"));

Hashtable freqSliderLabels = new Hashtable();
freqSliderLabels.put(new Integer(20), new JLabel("20"));
freqSliderLabels.put(new Integer(1000), new JLabel("1000"));
freqSliderLabels.put(new Integer(2000), new JLabel("2000"));

// Create and dispose graphical components
// envelope amplitude slider
JSlider envAmplitude = new JSlider(JSlider.HORIZONTAL, 0, 10, 7);
envAmplitude.setFont(sliderLabelFont);
envAmplitude.getAccessibleContext().setAccessibleDescription(" ADSR envelope amplitude slider ");
envAmplitude.setToolTipText(" ADSR envelope amplitude ");
envAmplitude.setFocusable(true);
envAmplitude.setMajorTickSpacing(5);
envAmplitude.setMinorTickSpacing(1);
envAmplitude.setLabelTable(asSliderLabels);
envAmplitude.setPaintTicks(true);
envAmplitude.setPaintLabels(true);
envAmplitude.addChangeListener(new ChangeListener()
{
public void stateChanged(ChangeEvent e)
{
JSlider source = (JSlider)e.getSource();
int value = (int)source.getValue();
_env.setAmplitude((float)value*0.1f);
_envChanged = true;
}
	});
c.gridx = 0;
c.gridy = 0;
c.gridwidth = 2;
c.gridheight = 1;
pane.add(envAmplitude, c);
// envelope sustain level slider
JSlider envSustainLevel = new JSlider(JSlider.HORIZONTAL, 0, 10, 5);
envSustainLevel.setFont(sliderLabelFont);
envSustainLevel.getAccessibleContext().setAccessibleDescription(" ADSR envelope sustain level slider ");
envSustainLevel.setToolTipText(" ADSR envelope sustain level ");
envSustainLevel.setFocusable(true);
envSustainLevel.setMajorTickSpacing(5);
envSustainLevel.setMinorTickSpacing(1);
envSustainLevel.setLabelTable(asSliderLabels);
envSustainLevel.setPaintTicks(true);
envSustainLevel.setPaintLabels(true);
envSustainLevel.addChangeListener(new ChangeListener()
{
public void stateChanged(ChangeEvent e)
{
JSlider source = (JSlider)e.getSource();
int value = (int)source.getValue();
_env.setSustainLevel((float)value*0.1f);
_envChanged = true;
}
	});
c.gridx = 0;
c.gridy = 1;
c.gridwidth = 2;
c.gridheight = 1;
pane.add(envSustainLevel, c);

// Add decimation and interpolation spinners
JSpinner decimatorSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 4, 1));
decimatorSpinner.getAccessibleContext().setAccessibleName("decimator");
decimatorSpinner.setToolTipText(" Decimator ");
decimatorSpinner.setFocusable(true);
decimatorSpinner.addChangeListener(new ChangeListener()
{
public void stateChanged(ChangeEvent e)
{
JSpinner source = (JSpinner)e.getSource();
int value = (int)source.getValue();
_decimationValue = value;
}
	});
c.gridx = 2;
c.gridy = 0;
c.gridwidth = 1;
c.gridheight = 1;
pane.add(decimatorSpinner, c);

JSpinner interpolatorSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 4, 1));
interpolatorSpinner.getAccessibleContext().setAccessibleName("interpolator");
interpolatorSpinner.setToolTipText(" Interpolator ");
interpolatorSpinner.setFocusable(true);
interpolatorSpinner.addChangeListener(new ChangeListener()
{
public void stateChanged(ChangeEvent e)
{
JSpinner source = (JSpinner)e.getSource();
int value = (int)source.getValue();
_interpolationValue = value;
}
	});
c.gridx = 3;
c.gridy = 0;
c.gridwidth = 1;
c.gridheight = 1;
pane.add(interpolatorSpinner, c);

// Add apply envelope button
JButton applyEnv = new JButton("Apply");
applyEnv.setFont(appLabelFont);
applyEnv.getAccessibleContext().setAccessibleName("apply ADSR");
applyEnv.getAccessibleContext().setAccessibleDescription("apply ADSR button");
applyEnv.setToolTipText(" Apply ADSR ");
applyEnv.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent event)
		{
int sum = _attack+_decay+_sustain+_release;
if(sum <= 100)
{
	_env.setADSR(_attack, _decay, _sustain, _release);
	_envChanged = true;
}
else
{
	JOptionPane.showMessageDialog(null,"Vad parameters: A+D+S+R must be in the range [0 .. 100]");
}
		}
		});
c.gridx = 2;
c.gridy = 1;
c.gridwidth = 2;
c.gridheight = 1;
pane.add(applyEnv, c);

// build and add ADSR spinners
JSpinner aSpinner = new JSpinner(new SpinnerNumberModel(_attack, 0, 100, 1));
aSpinner.getAccessibleContext().setAccessibleName("envelope attack %");
aSpinner.setToolTipText(" envelope attack % ");
aSpinner.setFocusable(true);
aSpinner.addChangeListener(new ChangeListener()
{
public void stateChanged(ChangeEvent e)
{
JSpinner source = (JSpinner)e.getSource();
int value = (int)source.getValue();
_attack = value;
}
	});
c.gridx = 0;
c.gridy = 2;
c.gridwidth = 1;
c.gridheight = 1;
pane.add(aSpinner, c);

JSpinner dSpinner = new JSpinner(new SpinnerNumberModel(_decay, 0, 100, 1));
dSpinner.getAccessibleContext().setAccessibleName("envelope decay %");
dSpinner.setToolTipText(" envelope decay % ");
dSpinner.setFocusable(true);
dSpinner.addChangeListener(new ChangeListener()
{
public void stateChanged(ChangeEvent e)
{
JSpinner source = (JSpinner)e.getSource();
int value = (int)source.getValue();
_decay = value;
}
	});
c.gridx = 1;
c.gridy = 2;
c.gridwidth = 1;
c.gridheight = 1;
pane.add(dSpinner, c);

JSpinner sSpinner = new JSpinner(new SpinnerNumberModel(_sustain, 0, 100, 1));
sSpinner.getAccessibleContext().setAccessibleName("envelope sustain %");
sSpinner.setToolTipText(" envelope sustain % ");
sSpinner.setFocusable(true);
sSpinner.addChangeListener(new ChangeListener()
{
public void stateChanged(ChangeEvent e)
{
JSpinner source = (JSpinner)e.getSource();
int value = (int)source.getValue();
_sustain = value;
}
	});
c.gridx = 2;
c.gridy = 2;
c.gridwidth = 1;
c.gridheight = 1;
pane.add(sSpinner, c);

JSpinner rSpinner = new JSpinner(new SpinnerNumberModel(_release, 0, 100, 1));
rSpinner.getAccessibleContext().setAccessibleName("envelope release %");
rSpinner.setToolTipText(" envelope release % ");
rSpinner.setFocusable(true);
rSpinner.addChangeListener(new ChangeListener()
{
public void stateChanged(ChangeEvent e)
{
JSpinner source = (JSpinner)e.getSource();
int value = (int)source.getValue();
_release = value;
}
	});
c.gridx = 3;
c.gridy = 2;
c.gridwidth = 1;
c.gridheight = 1;
pane.add(rSpinner, c);

// wave and process lables
JLabel aLabel = new JLabel("Amplitude", SwingConstants.CENTER);
aLabel.setFont(appLabelFont);
c.gridx = 0;
c.gridy = 3;
c.gridwidth = 1;
c.gridheight = 1;
pane.add(aLabel, c);
JLabel fLabel = new JLabel("Frequency Hz", SwingConstants.CENTER);
fLabel.setFont(appLabelFont);
c.gridx = 1;
pane.add(fLabel, c);
JLabel pLabel = new JLabel("Process", SwingConstants.CENTER);
pLabel.setFont(appLabelFont);
c.gridx = 2;
c.gridwidth = 2;
pane.add(pLabel, c);
JLabel vLabel = new JLabel("ADSR | Window -- Signal  Visualizers", SwingConstants.CENTER);
vLabel.setFont(appLabelFont);
vLabel.setToolTipText(" Author: Ismael Mosquera Rivera ");
c.gridx = 4;
c.gridy = 3;
c.gridwidth = 4;
c.gridheight = 1;
pane.add(vLabel, c);

// Add envelope / window plot
_envPlot.setToolTipText(" ADSR | Window Plot ");
c.gridx = 4;
c.gridy = 0;
c.gridwidth = 4;
c.gridheight = 3;
pane.add(_envPlot, c);

// Add amplitude and frequency sliders
JSlider amplitude = new JSlider(JSlider.VERTICAL, 0, 10, 5);
amplitude.setFont(sliderLabelFont);
amplitude.getAccessibleContext().setAccessibleDescription("wave amplitude slider");
amplitude.setToolTipText(" Wave amplitude ");
amplitude.setFocusable(true);
amplitude.setMajorTickSpacing(5);
amplitude.setMinorTickSpacing(1);
amplitude.setLabelTable(asSliderLabels);
amplitude.setPaintTicks(true);
amplitude.setPaintLabels(true);
amplitude.addChangeListener(new ChangeListener()
{
public void stateChanged(ChangeEvent e)
{
JSlider source = (JSlider)e.getSource();
int value = (int)source.getValue();
_wave.setAmplitude((float)value*0.1f);
}
	});
c.gridx = 0;
c.gridy = 4;
c.gridwidth = 1;
c.gridheight = 3;
pane.add(amplitude, c);

JSlider frequency = new JSlider(JSlider.VERTICAL, 20, 2000, 65);
frequency.setFont(sliderLabelFont);
frequency.getAccessibleContext().setAccessibleDescription("wave frequency slider");
frequency.setToolTipText(" 65 Hz ");
frequency.setFocusable(true);
frequency.setMajorTickSpacing(1000);
frequency.setMinorTickSpacing(1);
frequency.setLabelTable(freqSliderLabels);
frequency.setPaintTicks(false);
frequency.setPaintLabels(true);
frequency.addChangeListener(new ChangeListener()
{
public void stateChanged(ChangeEvent e)
{
JSlider source = (JSlider)e.getSource();
int value = (int)source.getValue();
Integer tooltip = new Integer(value);
source.setToolTipText(tooltip.toString()+" Hz");
setSemitones();
composeScale((float)value);
}
	});
c.gridx = 1;
pane.add(frequency, c);

// Add processing buttons
JButton startProcess = new JButton("Start");
startProcess.setFont(appLabelFont);
startProcess.getAccessibleContext().setAccessibleName("start process");
startProcess.getAccessibleContext().setAccessibleDescription("start process button");
startProcess.setToolTipText(" Start process ");
startProcess.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent event)
		{
			if(_isProcessing) return;
			(new Thread(new thread_code())).start();
		}
		});
c.gridx = 2;
c.gridy = 4;
c.gridwidth = 2;
c.gridheight = 1;
pane.add(startProcess, c);
JButton stopProcess = new JButton("Stop");
stopProcess.setFont(appLabelFont);
stopProcess.getAccessibleContext().setAccessibleName("stop process");
stopProcess.getAccessibleContext().setAccessibleDescription("stop process button");
stopProcess.setToolTipText(" Stop process ");
stopProcess.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent event)
		{
			_stopRequested = true;
		}
		});
		c.gridx = 2;
c.gridy = 5;
c.gridwidth = 2;
c.gridheight = 1;
pane.add(stopProcess, c);
JButton recProcess = new JButton("Rec");
recProcess.setFont(appLabelFont);
recProcess.getAccessibleContext().setAccessibleName("request for recording");
recProcess.getAccessibleContext().setAccessibleDescription("start recording button");
recProcess.setToolTipText(" Request for recording ");
recProcess.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent event)
		{
			_recRequested = true;
		}
		});
		c.gridx = 2;
		c.gridy = 6;
		c.gridwidth = 2;
		c.gridheight = 1;
		pane.add(recProcess, c);

// Add wave plot
_wavePlot.setToolTipText(" Wave Plot ");
c.gridx = 4;
c.gridy = 4;
c.gridwidth = 4;
c.gridheight = 3;
pane.add(_wavePlot, c);

this.getContentPane().setLayout(null);
this.setContentPane(pane);

_envPlot.setVisible(true);
_envPlot.setSize(400, 300);
_envPlot.setPreferredSize(new Dimension(400, 300));

_wavePlot.setVisible(true);
_wavePlot.setSize(400, 300);
_wavePlot.setPreferredSize(new Dimension(400, 300));

// Create Menus
JMenu fileMenu = new JMenu("File");
 fileMenu.getAccessibleContext().setAccessibleName("file");

JMenuItem save = new JMenuItem("Save...");
save.getAccessibleContext().setAccessibleName("save");
save.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent event)
	{
		if(_hasRecord)
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
				_saver.store(fileName);
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
			_stopRequested = true;
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

// Wave selection menu
JMenu waveMenu = new JMenu("Wave");
waveMenu.getAccessibleContext().setAccessibleName("wave");

sawMenuItem = new JCheckBoxMenuItem("Saw", false);
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

		_wave = _sawWave;
	}
	else
	{
		sineMenuItem.setState(true);
				squareMenuItem.setState(false);
				triangularMenuItem.setState(false);

		_wave = _sineWave;
	}
	}
	});

sineMenuItem = new JCheckBoxMenuItem("Sine", true);
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

		_wave = _sineWave;
	}
	else
	{
		sawMenuItem.setState(false);
				squareMenuItem.setState(false);
				triangularMenuItem.setState(true);

		_wave = _triangularWave;
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

		_wave = _squareWave;
	}
	else
	{
		sawMenuItem.setState(false);
				sineMenuItem.setState(true);
				triangularMenuItem.setState(false);

		_wave = _sineWave;
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

		_wave = _triangularWave;
	}
	else
	{
		sawMenuItem.setState(false);
				sineMenuItem.setState(true);
				squareMenuItem.setState(false);

		_wave = _sineWave;
	}
	}
	});

JCheckBoxMenuItem applyEnvMenuItem = new JCheckBoxMenuItem("Apply ADSR",true);
applyEnvMenuItem.getAccessibleContext().setAccessibleName("apply ADSR envelope");
applyEnvMenuItem.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent event)
	{
	boolean state = applyEnvMenuItem.getState();
	_applyADSR = (state) ? true : false;
	}
	});

JCheckBoxMenuItem applyWindowMenuItem = new JCheckBoxMenuItem("Apply Window",true);
applyWindowMenuItem.getAccessibleContext().setAccessibleName("apply window");
applyWindowMenuItem.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent event)
	{
	boolean state = applyWindowMenuItem.getState();
	_applyWindow = (state) ? true : false;
	}
	});

waveMenu.add(sawMenuItem);
waveMenu.add(sineMenuItem);
waveMenu.add(squareMenuItem);
waveMenu.add(triangularMenuItem);
waveMenu.addSeparator();
waveMenu.add(applyEnvMenuItem);
waveMenu.add(applyWindowMenuItem);

// Window type selection menu
JMenu windowMenu = new JMenu("Window");
windowMenu.getAccessibleContext().setAccessibleName("window");

blackmanWindowMenuItem = new JCheckBoxMenuItem("BlackmanHarris92", true);
blackmanWindowMenuItem.getAccessibleContext().setAccessibleName("BlackmanHarris92");
blackmanWindowMenuItem.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent event)
	{
	boolean state = blackmanWindowMenuItem.getState();
	if(state)
	{
		gaussianWindowMenuItem.setState(false);
		hammingWindowMenuItem.setState(false);
		triangularWindowMenuItem.setState(false);

		_window = (float[])iArray.clone(_wBlackman);
	}
	else
	{
		hammingWindowMenuItem.setState(true);
				gaussianWindowMenuItem.setState(false);
				triangularWindowMenuItem.setState(false);

		_window = (float[])iArray.clone(_wHamming);
	}
	_windowChanged = true;
	}
	});

gaussianWindowMenuItem = new JCheckBoxMenuItem("Gaussian", false);
gaussianWindowMenuItem.getAccessibleContext().setAccessibleName("gaussian");
gaussianWindowMenuItem.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent event)
	{
	boolean state = gaussianWindowMenuItem.getState();
	if(state)
	{
		blackmanWindowMenuItem.setState(false);
		hammingWindowMenuItem.setState(false);
		triangularWindowMenuItem.setState(false);

		_window = (float[])iArray.clone(_wGaussian);
	}
	else
	{
		blackmanWindowMenuItem.setState(true);
				hammingWindowMenuItem.setState(false);
				triangularWindowMenuItem.setState(false);

		_window = (float[])iArray.clone(_wBlackman);
	}
	_windowChanged = true;
	}
	});

hammingWindowMenuItem = new JCheckBoxMenuItem("Hamming", false);
hammingWindowMenuItem.getAccessibleContext().setAccessibleName("hamming");
hammingWindowMenuItem.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent event)
	{
	boolean state = hammingWindowMenuItem.getState();
	if(state)
	{
		blackmanWindowMenuItem.setState(false);
		gaussianWindowMenuItem.setState(false);
		triangularWindowMenuItem.setState(false);

		_window = (float[])iArray.clone(_wHamming);
	}
	else
	{
		blackmanWindowMenuItem.setState(true);
				gaussianWindowMenuItem.setState(false);
				triangularWindowMenuItem.setState(false);

		_window = (float[])iArray.clone(_wBlackman);
	}
	_windowChanged = true;
	}
	});

triangularWindowMenuItem = new JCheckBoxMenuItem("Triangular", false);
triangularWindowMenuItem.getAccessibleContext().setAccessibleName("triangular");
triangularWindowMenuItem.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent event)
	{
	boolean state = triangularWindowMenuItem.getState();
	if(state)
	{
		blackmanWindowMenuItem.setState(false);
		gaussianWindowMenuItem.setState(false);
		hammingWindowMenuItem.setState(false);

		_window = (float[])iArray.clone(_wTriangular);
	}
	else
	{
		blackmanWindowMenuItem.setState(true);
				gaussianWindowMenuItem.setState(false);
				hammingWindowMenuItem.setState(false);

		_window = (float[])iArray.clone(_wBlackman);
	}
	_windowChanged = true;
	}
	});

windowMenu.add(blackmanWindowMenuItem);
windowMenu.add(gaussianWindowMenuItem);
windowMenu.add(hammingWindowMenuItem);
windowMenu.add(triangularWindowMenuItem);

JMenu viewMenu = new JMenu("View");
viewMenu.getAccessibleContext().setAccessibleName("view");

viewEnvMenuItem = new JCheckBoxMenuItem("ADSR",true);
viewEnvMenuItem.getAccessibleContext().setAccessibleName("view ADSR envelope");
viewEnvMenuItem.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent event)
	{
	boolean state = viewEnvMenuItem.getState();
	if(state)
	{
		viewWindowMenuItem.setState(false);
		_showWindow = false;
		_windowChanged = false;
		_showADSR = true;
		_envChanged = true;
	}
	else
	{
viewWindowMenuItem.setState(true);
_showADSR = false;
_envChanged = false;
_showWindow = true;
_windowChanged = true;
	}
	}
	});

viewWindowMenuItem = new JCheckBoxMenuItem("Window",false);
viewWindowMenuItem.getAccessibleContext().setAccessibleName("view window ");
viewWindowMenuItem.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent event)
	{
	boolean state = viewWindowMenuItem.getState();
	if(state)
	{
		viewEnvMenuItem.setState(false);
		_showADSR = false;
				_envChanged = false;
		_showWindow = true;
		_windowChanged = true;
	}
	else
	{
viewEnvMenuItem.setState(true);
_showWindow = false;
_windowChanged = false;
_showADSR = true;
_envChanged = true;
	}
	}
	});

viewMenu.add(viewEnvMenuItem);
viewMenu.add(viewWindowMenuItem);

// Create main menu bar and add items
JMenuBar menuBar = new JMenuBar();
this.setJMenuBar(menuBar);
// Add menus to the main menu bar
menuBar.add(fileMenu);
menuBar.add(waveMenu);
menuBar.add(windowMenu);
menuBar.add(viewMenu);

this.pack();
}

public void showGUI()
{
this.setVisible(true);
this.setLocation(XPOS, YPOS);
this.setSize(WIDTH, HEIGHT);
this.setResizable(false);
_envPlot.refresh();
_wavePlot.refresh();
}


private void setSemitones()
{
	int n = (int)(Math.random()*N_SCALES);
	switch(n)
	{
		case 0:
		_semitones = (int[])iArray.clone(st0);
		break;
		case 1:
				_semitones = (int[])iArray.clone(st1);
				break;
		case 2:
				_semitones = (int[])iArray.clone(st2);
				break;
		case 3:
				_semitones = (int[])iArray.clone(st3);
				break;
		case 4:
				_semitones = (int[])iArray.clone(st4);
				break;
		case 5:
				_semitones = (int[])iArray.clone(st5);
				break;
		case 6:
				_semitones = (int[])iArray.clone(st6);
				break;
		case 7:
				_semitones = (int[])iArray.clone(st7);
				break;
		default:
		_semitones = (int[])iArray.clone(st5);
	}
}

private void composeScale(float freq)
{
	float f = freq;
	_scale[0] = f;
for(int i = 1; i < _scale.length; i++)
{
for(int j = 0; j < _semitones[i-1]; j++)
{
	if(i < 8)
	{
		f *= PITCH_INC_FACTOR;
	}
	else
	{
	f /= PITCH_INC_FACTOR;
	}
}
_scale[i] = f;
}
}

private void buildWindows()
{
float[] w = new float[FRAME_SIZE];
Window blackman = new BlackmanHarris92Window();
Window gaussian = new GaussianWindow();
Window hamming = new HammingWindow();
Window tri = new TriangularWindow();

blackman.get(w);
_wBlackman = (float[])iArray.clone(w);
gaussian.get(w);
w = FrameFactory.normalizeFrame(w);
_wGaussian = (float[])iArray.clone(w);
hamming.get(w);
_wHamming = (float[])iArray.clone(w);
tri.get(w);
_wTriangular = (float[])iArray.clone(w);
_window = (float[])iArray.clone(_wBlackman);
}

private void applyWindow(float[] x)
{
for(int i = 0; i < x.length; i++) x[i] *= _window[i];
}


private int _decimationValue;
private int _interpolationValue;

private boolean _isProcessing;
private boolean _stopRequested;
private boolean _recRequested;
private boolean _hasRecord;
private boolean _mustResetSaver;

private boolean _applyADSR;
private boolean _applyWindow;

private boolean _showADSR;
private boolean _showWindow;
private boolean _envChanged;
private boolean _windowChanged;

private Wave _wave;
private Wave _sawWave;
private Wave _sineWave;
private Wave _squareWave;
private Wave _triangularWave;

private float[] _window;
private float[] _wBlackman;
private float[] _wGaussian;
private float[] _wHamming;
private float[] _wTriangular;

private Envelope _env;

private int _attack;
private int _decay;
private int _sustain;
private int _release;

private float[] _scale;
private int[] _semitones;

private Plot _envPlot;
private Plot _wavePlot;

private Signal1<float[]> _windowSignal;
private Signal1<float[]> _waveSignal;

private RawDataPlayer _player;
private RawDataStorage _saver;

private JCheckBoxMenuItem sawMenuItem;
private JCheckBoxMenuItem sineMenuItem;
private JCheckBoxMenuItem squareMenuItem;
private JCheckBoxMenuItem triangularMenuItem;

private JCheckBoxMenuItem blackmanWindowMenuItem;
private JCheckBoxMenuItem gaussianWindowMenuItem;
private JCheckBoxMenuItem hammingWindowMenuItem;
private JCheckBoxMenuItem triangularWindowMenuItem;

private JCheckBoxMenuItem viewEnvMenuItem;
private JCheckBoxMenuItem viewWindowMenuItem;

private static final int[] st0 = {2, 1, 2, 2, 1, 2, 2, 2, 2, 1, 2, 2, 1, 2};
private static final int[] st1 = {2, 2, 1, 2, 2, 2, 1, 1, 2, 2, 2, 1, 2, 2};
private static final int[] st2 = {2, 1, 2, 2, 1, 2, 2, 2, 2, 1, 2, 2, 1, 2};
private static final int[] st3 = {2, 2, 1, 2, 2, 2, 1, 1, 2, 2, 2, 1, 2, 2};
private static final int[] st4 = {2, 1, 2, 2, 1, 2, 2, 2, 2, 1, 2, 2, 1, 2};
private static final int[] st5 = {2, 2, 1, 2, 2, 2, 1, 1, 2, 2, 2, 1, 2, 2};
private static final int[] st6 = {2, 1, 2, 2, 1, 2, 2, 2, 2, 1, 2, 2, 1, 2};
private static final int[] st7 = {2, 2, 1, 2, 2, 2, 1, 1, 2, 2, 2, 1, 2, 2};

private static final int FRAME_SIZE = 11024;
private static final float SAMPLE_RATE = 11025f;
private static final int N_SCALES = 8;
private static final float PITCH_INC_FACTOR = (float)Math.pow(2.0, 1.0 / 12.0);
private static final float FIRST_FREQUENCY = 65.0f;

private static final int XPOS = 200;
private static final int YPOS = 50;
private static final int WIDTH = 800;
private static final int HEIGHT = 700;


private class EnvSlot<T> implements Slot1<T>
{
public void receive(T data)
{
	if(_showADSR && _envChanged)
	{
		((SignalPlot)_envPlot).setData((float[])data);
		_envChanged = false;
	}
}
}

private class WindowSlot<T> implements Slot1<T>
{
public void receive(T data)
{
	if(_showWindow && _windowChanged)
	{
		((SignalPlot)_envPlot).setData((float[])data);
		_windowChanged = false;
	}
}
}

private class WaveSlot<T> implements Slot1<T>
{
public void receive(T data)
{
		((SignalPlot)_wavePlot).setData((float[])data);
	}
}

private class thread_code implements Runnable
{
public void run()
{
_isProcessing = true;
_stopRequested = false;
_recRequested = false;
_hasRecord = false;
if(_mustResetSaver)
{
_saver.reset();
_mustResetSaver = false;
}

_player.start();

byte[] frame = new byte[FRAME_SIZE];
float[] s = new float[FRAME_SIZE]; // silence
for(int i = 0; i < s.length; i++) s[i] = 0.0f;
float[] x = null;
float[] t = null;
while(!_stopRequested)
{
for(int i = 0; i < _scale.length; i++)
{
	t = (float[])iArray.clone(s);
	_wave.setFrequency(_scale[i]);
_wave.get(frame);
if(_applyADSR) _env.apply(frame);
x = FrameFactory.getFrame(frame);
if(_applyWindow) applyWindow(x);
_windowSignal.emit(_window);
if(_decimationValue > 0)
{
x = LinearSampler.decimate(x, _decimationValue);
t = LinearSampler.decimate(t, _decimationValue);
}
if(_interpolationValue > 0)
{
x = LinearSampler.interpolate(x, _interpolationValue);
t = LinearSampler.interpolate(t, _interpolationValue);
}
_player.play(FrameFactory.getFrame(x));
_player.play(FrameFactory.getFrame(t));
_waveSignal.emit(x);
_waveSignal.emit(t);
if(_recRequested)
{
	_saver.add(FrameFactory.getFrame(x));
	_saver.add(FrameFactory.getFrame(t));
}
if(_stopRequested) break;
}
}
_player.stop();
_isProcessing = false;
if(_recRequested)
{
_saver.add(FrameFactory.getFrame((int)SAMPLE_RATE, 1000));
_hasRecord = true;
_mustResetSaver = true;
_recRequested = false;
}
}
}

}

// END
