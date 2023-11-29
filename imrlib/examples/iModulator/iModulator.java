/*
 * Copyright (c) 2023-2024 Ismael Mosquera Rivera
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
* iModulator.java
*
* Author: Ismael Mosquera Rivera
*/

import imr.media.audio.FrameFactory;
import imr.media.audio.WaveType;
import imr.media.audio.Modulator;
import imr.media.audio.AmplitudeModulator;
import imr.media.audio.FrequencyModulator;
import imr.media.audio.RingModulator;
import imr.media.audio.RawDataPlayer;
import imr.media.audio.RawDataStorage;

import java.util.Hashtable;
import java.io.File;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;

import javax.sound.sampled.UnsupportedAudioFileException;


/*
* This example, actually, demonstrates several classes in the imr.media.audio package.
* The example shows how the Modulator and its subclasses:
* - AmplitudeModulator
* - FrequencyModulator
* - RingModulator
*
* work.
*
* The classes RawDataPlayer and RawDataStorage
* are also used in this example.
* The example runs with a GUI and is so intuitive.
* It allows you to test the modulators and, if you wish, save the result
* in an audio file.
* You can set all the parameters you need through the GUI application.
* Place the cursor over the different components to see a tooltip
* describing its purpose.
* Take in account that this GUI was builded in the darkness
* since the author of all this work is completely blind.
* We think that it can be useful for
* anyone interested in audio processing and topics related.
*
*/
public class iModulator
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
iModulatorGUI gui = new iModulatorGUI();
	gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	gui.showGUI();
}
}

@SuppressWarnings("unchecked")
class iModulatorGUI extends JFrame
{

public iModulatorGUI()
{
this.setTitle("iModulator");

_hasRecord = false;
_mustReset = false;
_stopRequested = false;
_recRequested = false;

_ampModulator = new AmplitudeModulator();
_freqModulator = new FrequencyModulator();
_ringModulator = new RingModulator();
_modulator = _freqModulator;

_player = new RawDataPlayer(_modulator.getSampleRate());
_saver = new RawDataStorage(_modulator.getSampleRate());

modulator_wave_type = WaveType.wSine;
c_attack = 10;
c_decay = 20;
c_sustain = 60;
c_release = 10;
m_attack = 10;
m_decay = 20;
m_sustain = 60;
m_release = 10;

cenvAmplitude = 0.7f;
cenvSustainLevel = 0.5f;
menvAmplitude = 0.7f;
menvSustainLevel = 0.5f;

_modulator.setCarrierAmplitude(0.5f);
_modulator.setCarrierFrequency(100.0f);
_modulator.setCarrierEnvAmplitude(cenvAmplitude);
_modulator.setCarrierEnvSustainLevel(cenvSustainLevel);
_modulator.setCarrierEnvADSR(c_attack, c_decay, c_sustain, c_release);
_modulator.setModulatorAmplitude(0.5f);
_modulator.setModulatorFrequency(64.0f);
_modulator.setModulatorEnvAmplitude(menvAmplitude);
_modulator.setModulatorEnvSustainLevel(menvSustainLevel);
_modulator.setModulatorEnvADSR(m_attack, m_decay, m_sustain, m_release);
_modulator.setModulationIndex(5.0f);
_modulator.setModulatorWaveType(modulator_wave_type);

JPanel pane = new JPanel(new GridBagLayout());
pane.setFocusable(true);
GridBagConstraints c = new GridBagConstraints();
// app fonts
Font groupLabelFont = new Font("Arial", Font.BOLD, 12);
Font appLabelFont = new Font("Arial", Font.PLAIN, 10);
// slider label tables
Hashtable asSliderLabels = new Hashtable();
asSliderLabels.put(new Integer(0), new JLabel("0.0"));
asSliderLabels.put(new Integer(5), new JLabel("0.5"));
asSliderLabels.put(new Integer(10), new JLabel("1.0"));

Hashtable freqSliderLabels = new Hashtable();
freqSliderLabels.put(new Integer(1), new JLabel("1"));
freqSliderLabels.put(new Integer(1000), new JLabel("1000"));
freqSliderLabels.put(new Integer(2000), new JLabel("2000"));
freqSliderLabels.put(new Integer(3000), new JLabel("3000"));

Hashtable mIndexSliderLabels = new Hashtable();
mIndexSliderLabels.put(new Integer(0), new JLabel("0"));
mIndexSliderLabels.put(new Integer(20), new JLabel("5"));
mIndexSliderLabels.put(new Integer(40), new JLabel("10"));
mIndexSliderLabels.put(new Integer(60), new JLabel("15"));
mIndexSliderLabels.put(new Integer(80), new JLabel("20"));

// begin instantiating and adding components
// Carrier group label
JLabel carrierGroupLabel = new JLabel("Carrier", SwingConstants.CENTER);
carrierGroupLabel.setFont(groupLabelFont);
c.gridx = 0;
c.gridy = 0;
c.gridwidth = 1;
c.gridheight = 1;
pane.add(carrierGroupLabel, c);

// Carrier envelope amplitude slider
JSlider carrierEnvAmplitude = new JSlider(JSlider.HORIZONTAL, 0, 10, 7);
carrierEnvAmplitude.setFont(appLabelFont);
carrierEnvAmplitude.getAccessibleContext().setAccessibleDescription("carrier envelope amplitude slider");
carrierEnvAmplitude.setToolTipText("Carrier envelope amplitude");
carrierEnvAmplitude.setFocusable(true);
carrierEnvAmplitude.setMajorTickSpacing(5);
carrierEnvAmplitude.setMinorTickSpacing(1);
carrierEnvAmplitude.setLabelTable(asSliderLabels);
carrierEnvAmplitude.setPaintTicks(true);
carrierEnvAmplitude.setPaintLabels(true);
carrierEnvAmplitude.addChangeListener(new ChangeListener()
{
public void stateChanged(ChangeEvent e)
{
JSlider source = (JSlider)e.getSource();
int value = (int)source.getValue();
cenvAmplitude = (float)value * 0.1f;
_modulator.setCarrierEnvAmplitude(cenvAmplitude);
}
	});
c.gridx = 1;
c.gridy = 0;
c.gridwidth = 3;
c.gridheight = 1;
pane.add(carrierEnvAmplitude, c);
// Modulator group label
JLabel modulatorGroupLabel = new JLabel("Modulator", SwingConstants.CENTER);
modulatorGroupLabel.setFont(groupLabelFont);
c.gridx = 4;
c.gridy = 0;
c.gridwidth = 1;
c.gridheight = 1;
pane.add(modulatorGroupLabel, c);
// Modulator envelope amplitude slider
JSlider modulatorEnvAmplitude = new JSlider(JSlider.HORIZONTAL, 0, 10, 7);
modulatorEnvAmplitude.setFont(appLabelFont);
modulatorEnvAmplitude.getAccessibleContext().setAccessibleDescription("Modulator envelope amplitude slider");
modulatorEnvAmplitude.setToolTipText("Modulator envelope amplitude");
modulatorEnvAmplitude.setFocusable(true);
modulatorEnvAmplitude.setMajorTickSpacing(5);
modulatorEnvAmplitude.setMinorTickSpacing(1);
modulatorEnvAmplitude.setLabelTable(asSliderLabels);
modulatorEnvAmplitude.setPaintTicks(true);
modulatorEnvAmplitude.setPaintLabels(true);
modulatorEnvAmplitude.addChangeListener(new ChangeListener()
{
public void stateChanged(ChangeEvent e)
{
JSlider source = (JSlider)e.getSource();
int value = (int)source.getValue();
menvAmplitude = (float)value * 0.1f;
_modulator.setModulatorEnvAmplitude(menvAmplitude);
}
	});
c.gridx = 5;
c.gridy = 0;
c.gridwidth = 3;
c.gridheight = 1;
pane.add(modulatorEnvAmplitude, c);
// Apply carrier envelope button
JButton applyCarrierEnv = new JButton("Apply");
applyCarrierEnv.setFont(appLabelFont);
applyCarrierEnv.getAccessibleContext().setAccessibleName("apply carrier ADSR");
applyCarrierEnv.getAccessibleContext().setAccessibleDescription("apply carrier ADSR button");
applyCarrierEnv.setToolTipText("Apply Carrier ADSR");
applyCarrierEnv.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent event)
		{
int sum = c_attack+c_decay+c_sustain+c_release;
if(sum <= 100)
{
	_modulator.setCarrierEnvADSR(c_attack, c_decay, c_sustain, c_release);
}
else
{
	JOptionPane.showMessageDialog(null,"Vad parameters: A+D+S+R must be in the range [0 .. 100]");
}
		}
		});
c.gridx = 0;
c.gridy = 1;
c.gridwidth = 1;
c.gridheight = 1;
pane.add(applyCarrierEnv, c);
// Carrier envelope sustain level slider
JSlider carrierEnvSustainLevel = new JSlider(JSlider.HORIZONTAL, 0, 10, 5);
carrierEnvSustainLevel.setFont(appLabelFont);
carrierEnvSustainLevel.getAccessibleContext().setAccessibleDescription("Carrier envelope sustain level slider");
carrierEnvSustainLevel.setToolTipText("Carrier envelope sustain level.");
carrierEnvSustainLevel.setFocusable(true);
carrierEnvSustainLevel.setMajorTickSpacing(5);
carrierEnvSustainLevel.setMinorTickSpacing(1);
carrierEnvSustainLevel.setLabelTable(asSliderLabels);
carrierEnvSustainLevel.setPaintTicks(true);
carrierEnvSustainLevel.setPaintLabels(true);
carrierEnvSustainLevel.addChangeListener(new ChangeListener()
{
public void stateChanged(ChangeEvent e)
{
JSlider source = (JSlider)e.getSource();
int value = (int)source.getValue();
cenvSustainLevel = (float)value * 0.1f;
_modulator.setCarrierEnvSustainLevel(cenvSustainLevel);
}
	});
c.gridx = 1;
c.gridy = 1;
c.gridwidth = 3;
c.gridheight = 1;
pane.add(carrierEnvSustainLevel, c);
// Apply modulator ADSR button
JButton applyModulatorEnv = new JButton("Apply");
applyModulatorEnv.setFont(appLabelFont);
applyModulatorEnv.getAccessibleContext().setAccessibleName("apply Modulator ADSR");
applyModulatorEnv.getAccessibleContext().setAccessibleDescription("apply Modulator ADSR button");
applyModulatorEnv.setToolTipText("Apply Modulator ADSR");
applyModulatorEnv.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent event)
		{
int sum = m_attack+m_decay+m_sustain+m_release;
if(sum <= 100)
{
	_modulator.setModulatorEnvADSR(m_attack, m_decay, m_sustain, m_release);
}
else
{
	JOptionPane.showMessageDialog(null,"Vad parameters: A+D+S+R must be in the range [0 .. 100]");
}
		}
		});
c.gridx = 4;
c.gridy = 1;
c.gridwidth = 1;
c.gridheight = 1;
pane.add(applyModulatorEnv, c);
// Modulator envelope sustain level slider
JSlider modulatorEnvSustainLevel = new JSlider(JSlider.HORIZONTAL, 0, 10, 5);
modulatorEnvSustainLevel.setFont(appLabelFont);
modulatorEnvSustainLevel.getAccessibleContext().setAccessibleDescription("Modulator envelope sustain level slider");
modulatorEnvSustainLevel.setToolTipText("Modulator envelope sustain level.");
modulatorEnvSustainLevel.setFocusable(true);
modulatorEnvSustainLevel.setMajorTickSpacing(5);
modulatorEnvSustainLevel.setMinorTickSpacing(1);
modulatorEnvSustainLevel.setLabelTable(asSliderLabels);
modulatorEnvSustainLevel.setPaintTicks(true);
modulatorEnvSustainLevel.setPaintLabels(true);
modulatorEnvSustainLevel.addChangeListener(new ChangeListener()
{
public void stateChanged(ChangeEvent e)
{
JSlider source = (JSlider)e.getSource();
int value = (int)source.getValue();
menvSustainLevel = (float)value * 0.1f;
_modulator.setModulatorEnvSustainLevel(menvSustainLevel);
}
	});
c.gridx = 5;
c.gridy = 1;
c.gridwidth = 3;
c.gridheight = 1;
pane.add(modulatorEnvSustainLevel, c);
// build and add ADSR spinners
JSpinner caSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 100, 1));
caSpinner.getAccessibleContext().setAccessibleName("carrier envelope attack %");
caSpinner.setToolTipText("Carrier envelope attack %");
caSpinner.setFocusable(true);
caSpinner.addChangeListener(new ChangeListener()
{
public void stateChanged(ChangeEvent e)
{
JSpinner source = (JSpinner)e.getSource();
int value = (int)source.getValue();
c_attack = value;
}
	});
c.gridx = 0;
c.gridy = 2;
c.gridwidth = 1;
c.gridheight = 1;
pane.add(caSpinner, c);
JSpinner cdSpinner = new JSpinner(new SpinnerNumberModel(20, 0, 100, 1));
cdSpinner.getAccessibleContext().setAccessibleName("carrier envelope decay %");
cdSpinner.setToolTipText("Carrier envelope decay %");
cdSpinner.setFocusable(true);
cdSpinner.addChangeListener(new ChangeListener()
{
public void stateChanged(ChangeEvent e)
{
JSpinner source = (JSpinner)e.getSource();
int value = (int)source.getValue();
c_decay = value;
}
	});
c.gridx = 1;
pane.add(cdSpinner, c);
JSpinner csSpinner = new JSpinner(new SpinnerNumberModel(60, 0, 100, 1));
csSpinner.getAccessibleContext().setAccessibleName("carrier envelope sustain %");
csSpinner.setToolTipText("Carrier envelope sustain %");
csSpinner.setFocusable(true);
csSpinner.addChangeListener(new ChangeListener()
{
public void stateChanged(ChangeEvent e)
{
JSpinner source = (JSpinner)e.getSource();
int value = (int)source.getValue();
c_sustain = value;
}
	});
c.gridx = 2;
pane.add(csSpinner, c);
JSpinner crSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 100, 1));
crSpinner.getAccessibleContext().setAccessibleName("carrier envelope release %");
crSpinner.setToolTipText("Carrier envelope release %");
crSpinner.setFocusable(true);
crSpinner.addChangeListener(new ChangeListener()
{
public void stateChanged(ChangeEvent e)
{
JSpinner source = (JSpinner)e.getSource();
int value = (int)source.getValue();
c_release = value;
}
	});
c.gridx = 3;
pane.add(crSpinner, c);
JSpinner maSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 100, 1));
maSpinner.getAccessibleContext().setAccessibleName("modulator envelope attack %");
maSpinner.setToolTipText("Modulator envelope attack %");
maSpinner.setFocusable(true);
maSpinner.addChangeListener(new ChangeListener()
{
public void stateChanged(ChangeEvent e)
{
JSpinner source = (JSpinner)e.getSource();
int value = (int)source.getValue();
m_attack = value;
}
	});
c.gridx = 4;
pane.add(maSpinner, c);
JSpinner mdSpinner = new JSpinner(new SpinnerNumberModel(20, 0, 100, 1));
mdSpinner.getAccessibleContext().setAccessibleName("modulator envelope decay %");
mdSpinner.setToolTipText("Modulator envelope decay %");
mdSpinner.setFocusable(true);
mdSpinner.addChangeListener(new ChangeListener()
{
public void stateChanged(ChangeEvent e)
{
JSpinner source = (JSpinner)e.getSource();
int value = (int)source.getValue();
m_decay = value;
}
	});
c.gridx = 5;
pane.add(mdSpinner, c);
JSpinner msSpinner = new JSpinner(new SpinnerNumberModel(60, 0, 100, 1));
msSpinner.getAccessibleContext().setAccessibleName("modulator envelope sustain %");
msSpinner.setToolTipText("Modulator envelope sustain %");
msSpinner.setFocusable(true);
msSpinner.addChangeListener(new ChangeListener()
{
public void stateChanged(ChangeEvent e)
{
JSpinner source = (JSpinner)e.getSource();
int value = (int)source.getValue();
m_sustain = value;
}
	});
c.gridx = 6;
pane.add(msSpinner, c);
JSpinner mrSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 100, 1));
mrSpinner.getAccessibleContext().setAccessibleName("modulator envelope release %");
mrSpinner.setToolTipText("Modulator envelope release %");
mrSpinner.setFocusable(true);
mrSpinner.addChangeListener(new ChangeListener()
{
public void stateChanged(ChangeEvent e)
{
JSpinner source = (JSpinner)e.getSource();
int value = (int)source.getValue();
m_release = value;
}
	});
c.gridx = 7;
pane.add(mrSpinner, c);
// wave and process lables
JLabel caLabel = new JLabel("Amplitude");
caLabel.setFont(appLabelFont);
c.gridx = 0;
c.gridy = 3;
pane.add(caLabel, c);
JLabel cfLabel = new JLabel("Frequency");
cfLabel.setFont(appLabelFont);
c.gridx = 1;
pane.add(cfLabel, c);
JLabel pLabel = new JLabel("Process");
pLabel.setFont(appLabelFont);
c.gridx = 2;
c.gridwidth = 2;
pane.add(pLabel, c);
JLabel maLabel = new JLabel("Amplitude");
maLabel.setFont(appLabelFont);
c.gridx = 4;
c.gridwidth = 1;
pane.add(maLabel, c);
JLabel mfLabel = new JLabel("Frequency");
mfLabel.setFont(appLabelFont);
c.gridx = 5;
pane.add(mfLabel, c);
JLabel miLabel = new JLabel("M. Index");
miLabel.setFont(appLabelFont);
c.gridx = 6;
pane.add(miLabel, c);
JLabel wtLabel = new JLabel("Wave Type");
wtLabel.setFont(appLabelFont);
c.gridx = 7;
pane.add(wtLabel, c);

// Carrier amplitude slider
JSlider carrierAmplitude = new JSlider(JSlider.VERTICAL, 0, 10, 5);
carrierAmplitude.setFont(appLabelFont);
carrierAmplitude.getAccessibleContext().setAccessibleDescription("carrier amplitude slider");
carrierAmplitude.setToolTipText("Carrier amplitude");
carrierAmplitude.setFocusable(true);
carrierAmplitude.setMajorTickSpacing(5);
carrierAmplitude.setMinorTickSpacing(1);
carrierAmplitude.setLabelTable(asSliderLabels);
carrierAmplitude.setPaintTicks(true);
carrierAmplitude.setPaintLabels(true);
carrierAmplitude.addChangeListener(new ChangeListener()
{
public void stateChanged(ChangeEvent e)
{
JSlider source = (JSlider)e.getSource();
int value = (int)source.getValue();
float amp = (float)value * 0.1f;
_modulator.setCarrierAmplitude(amp);
}
	});
c.gridx = 0;
c.gridy = 4;
c.gridwidth = 1;
c.gridheight = 3;
pane.add(carrierAmplitude, c);
// Carrier frequency slider
JSlider carrierFrequency = new JSlider(JSlider.VERTICAL, 1, 3000, 100);
carrierFrequency.setFont(appLabelFont);
carrierFrequency.getAccessibleContext().setAccessibleDescription("carrier frequency slider");
carrierFrequency.setToolTipText("100 Hz");
carrierFrequency.setFocusable(true);
carrierFrequency.setMajorTickSpacing(1000);
carrierFrequency.setMinorTickSpacing(1);
carrierFrequency.setLabelTable(freqSliderLabels);
carrierFrequency.setPaintTicks(false);
carrierFrequency.setPaintLabels(true);
carrierFrequency.addChangeListener(new ChangeListener()
{
public void stateChanged(ChangeEvent e)
{
JSlider source = (JSlider)e.getSource();
int value = (int)source.getValue();
Integer tooltip = new Integer(value);
source.setToolTipText(tooltip.toString()+" Hz");
float freq = (float)value;
_modulator.setCarrierFrequency(freq);
}
	});
c.gridx = 1;
pane.add(carrierFrequency, c);
// process buttons group
JButton startProcess = new JButton("Start");
startProcess.setFont(appLabelFont);
startProcess.getAccessibleContext().setAccessibleName("start process");
startProcess.getAccessibleContext().setAccessibleDescription("start process button");
startProcess.setToolTipText("Start process");
startProcess.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent event)
		{
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
stopProcess.setToolTipText("Stop process");
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
recProcess.setToolTipText("Request for recording");
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
// Modulator amplitude slider
JSlider modulatorAmplitude = new JSlider(JSlider.VERTICAL, 0, 10, 5);
modulatorAmplitude.setFont(appLabelFont);
modulatorAmplitude.getAccessibleContext().setAccessibleDescription("Modulator amplitude slider");
modulatorAmplitude.setToolTipText("Modulator amplitude");
modulatorAmplitude.setFocusable(true);
modulatorAmplitude.setMajorTickSpacing(5);
modulatorAmplitude.setMinorTickSpacing(1);
modulatorAmplitude.setLabelTable(asSliderLabels);
modulatorAmplitude.setPaintTicks(true);
modulatorAmplitude.setPaintLabels(true);
modulatorAmplitude.addChangeListener(new ChangeListener()
{
public void stateChanged(ChangeEvent e)
{
JSlider source = (JSlider)e.getSource();
int value = (int)source.getValue();
float amp = (float)value * 0.1f;
_modulator.setModulatorAmplitude(amp);
}
	});
c.gridx = 4;
c.gridy = 4;
c.gridwidth = 1;
c.gridheight = 3;
pane.add(modulatorAmplitude, c);
// Modulator frequency slider
JSlider modulatorFrequency = new JSlider(JSlider.VERTICAL, 1, 3000, 64);
modulatorFrequency.setFont(appLabelFont);
modulatorFrequency.getAccessibleContext().setAccessibleDescription("modulator frequency slider");
modulatorFrequency.setToolTipText("64 Hz");
modulatorFrequency.setFocusable(true);
modulatorFrequency.setMajorTickSpacing(1000);
modulatorFrequency.setMinorTickSpacing(1);
modulatorFrequency.setLabelTable(freqSliderLabels);
modulatorFrequency.setPaintTicks(false);
modulatorFrequency.setPaintLabels(true);
modulatorFrequency.addChangeListener(new ChangeListener()
{
public void stateChanged(ChangeEvent e)
{
JSlider source = (JSlider)e.getSource();
int value = (int)source.getValue();
Integer tooltip = new Integer(value);
source.setToolTipText(tooltip.toString()+" Hz");
float freq = (float)value;
_modulator.setModulatorFrequency(freq);
}
	});
c.gridx = 5;
pane.add(modulatorFrequency, c);
// Modulation index slider
JSlider modulationIndex = new JSlider(JSlider.VERTICAL, 0, 80, 20);
modulationIndex.setFont(appLabelFont);
modulationIndex.getAccessibleContext().setAccessibleDescription("modulation index slider");
modulationIndex.setToolTipText("Modulation index");
modulationIndex.setFocusable(true);
modulationIndex.setMajorTickSpacing(20);
modulationIndex.setMinorTickSpacing(1);
modulationIndex.setLabelTable(mIndexSliderLabels);
modulationIndex.setPaintTicks(true);
modulationIndex.setPaintLabels(true);
modulationIndex.addChangeListener(new ChangeListener()
{
public void stateChanged(ChangeEvent e)
{
JSlider source = (JSlider)e.getSource();
int value = (int)source.getValue();
float mIndex = (float)value * 0.25f;
_modulator.setModulationIndex(mIndex);
}
	});
c.gridx = 6;
pane.add(modulationIndex, c);
// Wave type selector
String[] waves = {"Saw", "Sine", "Square", "Triangular"};
JComboBox waveTypeSelector = new JComboBox(waves);
waveTypeSelector.setFont(appLabelFont);
waveTypeSelector.getAccessibleContext().setAccessibleDescription("modulator wave type selector");
waveTypeSelector.setToolTipText("Modulator wave type selector");
waveTypeSelector.setFocusable(true);
waveTypeSelector.setSelectedIndex(1);
waveTypeSelector.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent event)
		{
JComboBox source = (JComboBox)event.getSource();
String wt = (String)source.getSelectedItem();
modulator_wave_type = getWaveType(wt);
_modulator.setModulatorWaveType(modulator_wave_type);
		}
		});
c.gridx = 7;
c.gridy = 4;
c.gridwidth = 1;
c.gridheight = 1;
pane.add(waveTypeSelector, c);
// Authors's label
JLabel brandLabel = new JLabel("IMR", SwingConstants.CENTER);
brandLabel.setFont(groupLabelFont);
brandLabel.setToolTipText("Author: Ismael Mosquera Rivera");
c.gridx = 7;
c.gridy = 5;
c.gridwidth = 1;
c.gridheight = 2;
pane.add(brandLabel, c);
// Set content pane
this.setContentPane(pane);
// create menus
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

JMenu modulationMenu = new JMenu("Modulation");
modulationMenu.getAccessibleContext().setAccessibleName("modulation");

_ampMenuItem = new JCheckBoxMenuItem("Amplitude",false);
_ampMenuItem.getAccessibleContext().setAccessibleName("amplitude");
_ampMenuItem.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent event)
	{
	boolean state = _ampMenuItem.getState();
	if(state)
	{
		_freqMenuItem.setState(false);
		_ringMenuItem.setState(false);

		_ampModulator.setCarrierAmplitude(_modulator.getCarrierAmplitude());
		_ampModulator.setCarrierFrequency(_modulator.getCarrierFrequency());
		_ampModulator.setCarrierEnvAmplitude(cenvAmplitude);
		_ampModulator.setCarrierEnvSustainLevel(cenvSustainLevel);
		_ampModulator.setCarrierEnvADSR(c_attack, c_decay, c_sustain, c_release);
		_ampModulator.setModulatorEnvAmplitude(menvAmplitude);
		_ampModulator.setModulatorEnvSustainLevel(menvSustainLevel);
		_ampModulator.setModulatorEnvADSR(m_attack, m_decay, m_sustain, m_release);
		_ampModulator.setModulatorWaveType(modulator_wave_type);

		_modulator = _ampModulator;
	}
	else
	{
		_freqMenuItem.setState(true);
				_ringMenuItem.setState(false);

_freqModulator.setCarrierAmplitude(_modulator.getCarrierAmplitude());
		_freqModulator.setCarrierFrequency(_modulator.getCarrierFrequency());
		_freqModulator.setCarrierEnvAmplitude(cenvAmplitude);
				_freqModulator.setCarrierEnvSustainLevel(cenvSustainLevel);
		_freqModulator.setCarrierEnvADSR(c_attack, c_decay, c_sustain, c_release);
		_freqModulator.setModulatorEnvAmplitude(menvAmplitude);
				_freqModulator.setModulatorEnvSustainLevel(menvSustainLevel);
		_freqModulator.setModulatorEnvADSR(m_attack, m_decay, m_sustain, m_release);
		_freqModulator.setModulatorWaveType(modulator_wave_type);

		_modulator = _freqModulator;
	}
	}
	});

_freqMenuItem = new JCheckBoxMenuItem("Frequency",true);
_freqMenuItem.getAccessibleContext().setAccessibleName("frequency");
_freqMenuItem.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent event)
	{
	boolean state = _freqMenuItem.getState();
	if(state)
	{
		_ampMenuItem.setState(false);
		_ringMenuItem.setState(false);

_freqModulator.setCarrierAmplitude(_modulator.getCarrierAmplitude());
		_freqModulator.setCarrierFrequency(_modulator.getCarrierFrequency());
		_freqModulator.setCarrierEnvAmplitude(cenvAmplitude);
						_freqModulator.setCarrierEnvSustainLevel(cenvSustainLevel);
		_freqModulator.setCarrierEnvADSR(c_attack, c_decay, c_sustain, c_release);
		_freqModulator.setModulatorEnvAmplitude(menvAmplitude);
						_freqModulator.setModulatorEnvSustainLevel(menvSustainLevel);
		_freqModulator.setModulatorEnvADSR(m_attack, m_decay, m_sustain, m_release);
		_freqModulator.setModulatorWaveType(modulator_wave_type);

		_modulator = _freqModulator;
	}
	else
	{
		_ampMenuItem.setState(true);
				_ringMenuItem.setState(false);

		_ampModulator.setCarrierAmplitude(_modulator.getCarrierAmplitude());
		_ampModulator.setCarrierFrequency(_modulator.getCarrierFrequency());
		_ampModulator.setCarrierEnvAmplitude(cenvAmplitude);
								_ampModulator.setCarrierEnvSustainLevel(cenvSustainLevel);
		_ampModulator.setCarrierEnvADSR(c_attack, c_decay, c_sustain, c_release);
		_ampModulator.setModulatorEnvAmplitude(menvAmplitude);
								_ampModulator.setModulatorEnvSustainLevel(menvSustainLevel);
		_ampModulator.setModulatorEnvADSR(m_attack, m_decay, m_sustain, m_release);
		_ampModulator.setModulatorWaveType(modulator_wave_type);

		_modulator = _ampModulator;
	}
	}
	});

_ringMenuItem = new JCheckBoxMenuItem("Ring",false);
_ringMenuItem.getAccessibleContext().setAccessibleName("ring");
_ringMenuItem.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent event)
	{
	boolean state = _ringMenuItem.getState();
	if(state)
	{
		_ampMenuItem.setState(false);
		_freqMenuItem.setState(false);

_ringModulator.setCarrierAmplitude(_modulator.getCarrierAmplitude());
		_ringModulator.setCarrierFrequency(_modulator.getCarrierFrequency());
		_ringModulator.setCarrierEnvAmplitude(cenvAmplitude);
								_ringModulator.setCarrierEnvSustainLevel(cenvSustainLevel);
		_ringModulator.setCarrierEnvADSR(c_attack, c_decay, c_sustain, c_release);
		_ringModulator.setModulatorEnvAmplitude(menvAmplitude);
										_ringModulator.setModulatorEnvSustainLevel(menvSustainLevel);
		_ringModulator.setModulatorEnvADSR(m_attack, m_decay, m_sustain, m_release);
		_ringModulator.setModulatorWaveType(modulator_wave_type);

		_modulator = _ringModulator;
	}
	else
	{
		_ampMenuItem.setState(false);
		_freqMenuItem.setState(true);

_freqModulator.setCarrierAmplitude(_modulator.getCarrierAmplitude());
		_freqModulator.setCarrierFrequency(_modulator.getCarrierFrequency());
		_freqModulator.setCarrierEnvAmplitude(cenvAmplitude);
										_freqModulator.setCarrierEnvSustainLevel(cenvSustainLevel);
		_freqModulator.setCarrierEnvADSR(c_attack, c_decay, c_sustain, c_release);
		_freqModulator.setModulatorEnvAmplitude(menvAmplitude);
												_freqModulator.setModulatorEnvSustainLevel(menvSustainLevel);
		_freqModulator.setModulatorEnvADSR(m_attack, m_decay, m_sustain, m_release);
		_freqModulator.setModulatorWaveType(modulator_wave_type);

		_modulator = _freqModulator;
	}
	}
	});

JCheckBoxMenuItem cenvMenuItem = new JCheckBoxMenuItem("Carrier Envelope",true);
cenvMenuItem.getAccessibleContext().setAccessibleName("carrier envelope");
cenvMenuItem.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent event)
	{
	boolean state = cenvMenuItem.getState();
	if(state)
	{
		_modulator.applyCarrierEnvelope(true);
	}
	else
	{
		_modulator.applyCarrierEnvelope(false);
	}
	}
	});

JCheckBoxMenuItem menvMenuItem = new JCheckBoxMenuItem("Modulator Envelope",true);
menvMenuItem.getAccessibleContext().setAccessibleName("modulator envelope");
menvMenuItem.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent event)
	{
	boolean state = menvMenuItem.getState();
	if(state)
	{
		_modulator.applyModulatorEnvelope(true);
	}
	else
	{
		_modulator.applyModulatorEnvelope(false);
	}
	}
	});

modulationMenu.add(_ampMenuItem);
modulationMenu.add(_freqMenuItem);
modulationMenu.add(_ringMenuItem);
modulationMenu.addSeparator();
modulationMenu.add(cenvMenuItem);
modulationMenu.add(menvMenuItem);

JMenuBar menuBar = new JMenuBar();
this.setJMenuBar(menuBar);

menuBar.add(fileMenu);
menuBar.add(modulationMenu);
this.pack();
}

public void showGUI()
{
this.setVisible(true);
this.setLocation(XPOS, YPOS);
this.setSize(WIDTH, HEIGHT);
this.setResizable(false);
}


private int getWaveType(String str)
{
if(str.equals("Saw")) return WaveType.wSaw;
if(str.equals("Sine")) return WaveType.wSine;
if(str.equals("Square")) return WaveType.wSquare;
if(str.equals("Triangular")) return WaveType.wTriangular;
return WaveType.wSine;
}


private int modulator_wave_type;
private int c_attack;
private int c_decay;
private int c_sustain;
private int c_release;
private int m_attack;
private int m_decay;
private int m_sustain;
private int m_release;

private float cenvAmplitude;
private float cenvSustainLevel;
private float menvAmplitude;
private float menvSustainLevel;

private boolean _hasRecord;
private boolean _mustReset;
private boolean _stopRequested;
private boolean _recRequested;

private Modulator _modulator;
private AmplitudeModulator _ampModulator;
private FrequencyModulator _freqModulator;
private RingModulator _ringModulator;

private RawDataPlayer _player;
private RawDataStorage _saver;

private JCheckBoxMenuItem _ampMenuItem;
private JCheckBoxMenuItem _freqMenuItem;
private JCheckBoxMenuItem _ringMenuItem;

private static final int XPOS = 200;
private static final int YPOS = 50;
private static final int WIDTH = 800;
private static final int HEIGHT = 700;

private class thread_code implements Runnable
{
public void run()
{
_hasRecord = false;
_stopRequested = false;
if(_mustReset)
{
_saver.reset();
_mustReset = false;
}
_player.start();

int srate = (int)_modulator.getSampleRate();
byte[] frame = FrameFactory.getFrame(srate, 500);
while(!_stopRequested)
{
_modulator.synth(frame);
_player.play(frame);
if(_recRequested)
{
_saver.add(frame);
}
}
try
{
Thread.sleep(1000);
}
catch(InterruptedException e) {}
_player.stop();
if(_recRequested)
{
frame = FrameFactory.getFrame(srate, 1000);
_saver.add(frame);
_hasRecord = true;
_mustReset = true;
_recRequested = false;
}
}
}
}

// END
