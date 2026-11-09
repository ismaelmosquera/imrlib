/*
 * Copyright (c) 2026 Ismael Mosquera Rivera
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
* iDistribution.java
*
* Author: Ismael Mosquera Rivera
*
*/

import imr.util.Convert;
import imr.util.Pair;
import imr.math.Range;
import imr.math.Point2D;
import imr.plot.*;
import imr.math.stat.*;
import imr.math.polynomial.Polynomial;

import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.util.Locale;
import java.lang.NumberFormatException;
import java.util.LinkedList;
import java.util.ListIterator;
import java.io.File;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;

/*
* This example demonstrates some classes in the imr.math.stat package. Actually, related to probability distributions.
*
*/
public class iDistribution
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
iDistributionGUI gui = new iDistributionGUI();
	gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	gui.showGUI();
}
}

@SuppressWarnings("unchecked")
class iDistributionGUI extends JFrame
{

@SuppressWarnings("removal")
public iDistributionGUI()
{
this.setTitle("iDistribution");

// init some members
_hasPmf = false;
_hasCdf = false;
_spinnersEnabled = false;
_currentMean = 0.0;
_currentVar = 1.0;
_yAxisMax = 1.0;
_yMaxValue = 0.0;
_pmfArray = null;
_cdfArray = null;
_pmf = null;
_cdf = null;

// Build visualizers
_pmfPlot = new SignalPlot();
_pmfPlot.addRenderer("XAxis", new LineRenderer());
_pmfPlot.addRenderer("YAxis", new LineRenderer());
_pmfPlot.addRenderer("ZMean", new LineRenderer());
_pmfPlot.addRenderer("ZVar", new LineRenderer());
_pmfPlot.getRenderer("Signal").setColor(Color.BLUE);
_pmfPlot.getRenderer("XAxis").setColor(Color.BLACK);
_pmfPlot.getRenderer("YAxis").setColor(Color.BLACK);
_pmfPlot.getRenderer("ZMean").setColor(Color.RED);
_pmfPlot.getRenderer("ZVar").setColor(Color.GREEN);

_pmfPlot.setVBounds(0.0f, 0.5f);
_pmfPlot.setHBounds(0.0f, 1.0f);

((LineRenderer)_pmfPlot.getRenderer("XAxis")).setPoints(new Point2D(0.0, 0.0), new Point2D(1.0, 0.0));
((LineRenderer)_pmfPlot.getRenderer("YAxis")).setPoints(new Point2D(0.0, 0.0), new Point2D(0.0, 0.5));

_cdfPlot = new SignalPlot();
_cdfPlot.addRenderer("XAxis", new LineRenderer());
_cdfPlot.addRenderer("YAxis", new LineRenderer());
_cdfPlot.getRenderer("Signal").setColor(Color.BLUE);
_cdfPlot.getRenderer("XAxis").setColor(Color.BLACK);
_cdfPlot.getRenderer("YAxis").setColor(Color.BLACK);
_cdfPlot.setVBounds(0.0f, 1.0f);
_cdfPlot.setHBounds(0.0f, 1.0f);

((LineRenderer)_cdfPlot.getRenderer("XAxis")).setPoints(new Point2D(0.0, 0.0), new Point2D(1.0, 0.0));
((LineRenderer)_cdfPlot.getRenderer("YAxis")).setPoints(new Point2D(0.0, 0.0), new Point2D(0.0, 1.0));

// Set grid layout
JPanel pane = new JPanel(new GridBagLayout());
pane.setFocusable(true);
// grid constrains
GridBagConstraints c = new GridBagConstraints();


// app fonts
Font groupLabelFont = new Font("Arial", Font.BOLD, 12);
Font appLabelFont = new Font("Arial", Font.PLAIN, 12);
Font smallFont = new Font("Arial", Font.PLAIN, 10);

// Add components
JLabel pmfGroupLabel = new JLabel("PMF", SwingConstants.CENTER);
pmfGroupLabel.setFont(groupLabelFont);
c.gridx = 0;
c.gridy = 0;
c.gridwidth = 1;
c.gridheight = 1;
pane.add(pmfGroupLabel, c);


// parameters
_param1 = new JTextField();
_param1.setText("");
_param1.getAccessibleContext().setAccessibleName("param1");
_param1.setFocusable(true);
c.gridx = 1;
c.gridy = 0;
c.gridwidth = 1;
c.gridheight = 1;
pane.add(_param1, c);

_param2 = new JTextField();
_param2.setText("");
_param2.getAccessibleContext().setAccessibleName("param2");
_param2.setFocusable(true);
c.gridx = 2;
c.gridy = 0;
c.gridwidth = 1;
c.gridheight = 1;
pane.add(_param2, c);

_param3 = new JTextField();
_param3.setText("");
_param3.getAccessibleContext().setAccessibleName("param3");
_param3.setFocusable(true);
c.gridx = 3;
c.gridy = 0;
c.gridwidth = 1;
c.gridheight = 1;
pane.add(_param3, c);

JLabel pmfIndexLabel = new JLabel("Index", SwingConstants.CENTER);
pmfIndexLabel.setFont(smallFont);
c.gridx = 0;
c.gridy = 1;
c.gridwidth = 1;
c.gridheight = 1;
pane.add(pmfIndexLabel, c);

_pmfIndex = new JSpinner();
_pmfIndex.getAccessibleContext().setAccessibleName("pmf index spinner");
_pmfIndex.setToolTipText(" pmf vector index ");
_pmfIndex.setFocusable(true);
_pmfIndex.addChangeListener(new ChangeListener()
{
public void stateChanged(ChangeEvent e)
{
JSpinner source = (JSpinner)e.getSource();
int value = (int)source.getValue();
_pmfValue.setText(_pmf[value]);
}
	});
c.gridx = 1;
c.gridy = 1;
c.gridwidth = 1;
c.gridheight = 1;
pane.add(_pmfIndex, c);

_pmfValue = new JLabel("undef", SwingConstants.CENTER);
_pmfValue.setFont(smallFont);
_pmfValue.setToolTipText(" pmf value ");
_pmfValue.setFocusable(true);
c.gridx = 2;
c.gridy = 1;
c.gridwidth = 1;
c.gridheight = 1;
pane.add(_pmfValue, c);

JButton applyDistribution = new JButton("Apply");
applyDistribution.setFont(appLabelFont);
applyDistribution.getAccessibleContext().setAccessibleName("apply changes");
applyDistribution.setToolTipText(" Apply Distribution Changes ");
applyDistribution.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent event)
		{
if(!apply(_currentWorkspace)) showErrorMessage("sorry, bad or malformed parameter.");
	}
		});
c.gridx = 3;
c.gridy = 1;
c.gridwidth = 1;
c.gridheight = 1;
pane.add(applyDistribution, c);

// Distribution list
String[] distributions = {"Bernoulli", "Binomial", "Geometric", "Neg Binomial", "Normal", "Pascal", "Poisson", "Uniform"};
JComboBox distributionTypeSelector = new JComboBox(distributions);
distributionTypeSelector.setFont(appLabelFont);
distributionTypeSelector.getAccessibleContext().setAccessibleName("select distribution type");
distributionTypeSelector.setToolTipText(" select distribution type ");
distributionTypeSelector.setFocusable(true);
distributionTypeSelector.setSelectedIndex(0);
distributionTypeSelector.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent event)
		{
JComboBox source = (JComboBox)event.getSource();
int w = (int)source.getSelectedIndex();
setWorkspace(w);
		}
		});
c.gridx = 0;
c.gridy = 2;
c.gridwidth = 3;
c.gridheight = 1;
pane.add(distributionTypeSelector, c);

JButton savePmf = new JButton("Save");
savePmf.setFont(appLabelFont);
savePmf.getAccessibleContext().setAccessibleName("save pmf");
savePmf.setToolTipText(" Save Pmf ");
savePmf.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent event)
		{
if(!_hasPmf)
{
	showErrorMessage("Sorry, pmf not computed yet.");
}
else
{
JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

			FileFilter filter = new FileNameExtensionFilter("*.dat Test files","dat");

			chooser.setFileFilter(filter);

			int retVal = chooser.showSaveDialog(null);
			if(retVal == JFileChooser.APPROVE_OPTION)
			{
			File file = chooser.getSelectedFile();
			String fileName = file.getAbsolutePath();
			try
			{
				Polynomial.Storage.store(_pmfArray, fileName);
			}
			catch(Exception e)
			{
showErrorMessage("sorry, something was wrong.");
			}
		}
		}
		}
		});
c.gridx = 3;
c.gridy = 2;
c.gridwidth = 1;
c.gridheight = 1;
pane.add(savePmf, c);

// add pmf plot
_pmfPlot.setToolTipText(" PMF Plot ");
c.gridx = 4;
c.gridy = 0;
c.gridwidth = 4;
c.gridheight = 3;
pane.add(_pmfPlot, c);

// Separator
JLabel hLabel = new JLabel("Probability Distributions", SwingConstants.CENTER);
hLabel.setFont(groupLabelFont);
c.gridx = 0;
c.gridy = 3;
c.gridwidth = 8;
c.gridheight = 1;
pane.add(hLabel, c);

// Set cdf group
JLabel cdfGroupLabel = new JLabel("CDF", SwingConstants.CENTER);
cdfGroupLabel.setFont(groupLabelFont);
c.gridx = 0;
c.gridy = 4;
c.gridwidth = 1;
c.gridheight = 1;
pane.add(cdfGroupLabel, c);

JLabel cdfIndexLabel = new JLabel("Index", SwingConstants.CENTER);
cdfIndexLabel.setFont(smallFont);
c.gridx = 1;
c.gridy = 4;
c.gridwidth = 1;
c.gridheight = 1;
pane.add(cdfIndexLabel, c);

_cdfIndex = new JSpinner();
_cdfIndex.getAccessibleContext().setAccessibleName("cdf index spinner");
_cdfIndex.setToolTipText(" cdf vector index ");
_cdfIndex.setFocusable(true);
_cdfIndex.addChangeListener(new ChangeListener()
{
public void stateChanged(ChangeEvent e)
{
JSpinner source = (JSpinner)e.getSource();
int value = (int)source.getValue();
_cdfValue.setText(_cdf[value]);
}
	});
c.gridx = 2;
c.gridy = 4;
c.gridwidth = 1;
c.gridheight = 1;
pane.add(_cdfIndex, c);

_cdfValue = new JLabel("undef", SwingConstants.CENTER);
_cdfValue.setFont(smallFont);
_cdfValue.setToolTipText(" cdf value ");
_cdfValue.setFocusable(true);
c.gridx = 3;
c.gridy = 4;
c.gridwidth = 1;
c.gridheight = 1;
pane.add(_cdfValue, c);

_yAxisRange = new JLabel("PMF Y-Axis [0.0 .. 1.00]", SwingConstants.CENTER);
_yAxisRange.setFont(smallFont);
_yAxisRange.setToolTipText(" Current Y-Axis Range ");
_yAxisRange.setFocusable(true);
c.gridx = 0;
c.gridy = 5;
c.gridwidth = 2;
c.gridheight = 1;
pane.add(_yAxisRange, c);

_yMax = new JLabel("max = undef", SwingConstants.CENTER);
_yMax.setFont(smallFont);
_yMax.setToolTipText(" Current Y maximum value ");
_yMax.setFocusable(true);
c.gridx = 2;
c.gridy = 5;
c.gridwidth = 1;
c.gridheight = 1;
pane.add(_yMax, c);

JButton saveCdf = new JButton("Save");
saveCdf.setFont(appLabelFont);
saveCdf.getAccessibleContext().setAccessibleName("save cdf");
saveCdf.setToolTipText(" Save Cdf ");
saveCdf.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent event)
		{
if(!_hasCdf)
{
	showErrorMessage("Sorry, cdf not computed yet.");
}
else
{
JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

			FileFilter filter = new FileNameExtensionFilter("*.dat Test files","dat");

			chooser.setFileFilter(filter);

			int retVal = chooser.showSaveDialog(null);
			if(retVal == JFileChooser.APPROVE_OPTION)
			{
			File file = chooser.getSelectedFile();
			String fileName = file.getAbsolutePath();
			try
			{
				Polynomial.Storage.store(_cdfArray, fileName);
			}
			catch(Exception e)
			{
showErrorMessage("sorry, something was wrong.");
			}
		}
		}
		}
		});
c.gridx = 3;
c.gridy = 5;
c.gridwidth = 1;
c.gridheight = 1;
pane.add(saveCdf, c);

JLabel logoLabel = new JLabel("IMR", SwingConstants.CENTER);
logoLabel.setFont(groupLabelFont);
logoLabel.setToolTipText(" Author: Ismael Mosquera Rivera ");
c.gridx = 0;
c.gridy = 6;
c.gridwidth = 1;
c.gridheight = 1;
pane.add(logoLabel, c);

_mean = new JLabel("mean = undef", SwingConstants.CENTER);
_mean.setFont(smallFont);
_mean.setToolTipText(" mean value for this distribution ");
_mean.setFocusable(true);
c.gridx = 1;
c.gridy = 6;
c.gridwidth = 1;
c.gridheight = 1;
pane.add(_mean, c);

_var = new JLabel("var = undef", SwingConstants.CENTER);
_var.setFont(smallFont);
_var.setToolTipText(" variance value for this distribution ");
_var.setFocusable(true);
c.gridx = 2;
c.gridy = 6;
c.gridwidth = 1;
c.gridheight = 1;
pane.add(_var, c);

_std = new JLabel("std = undef", SwingConstants.CENTER);
_std.setFont(smallFont);
_std.setToolTipText(" standard deviation value for this distribution ");
_std.setFocusable(true);
c.gridx = 3;
c.gridy = 6;
c.gridwidth = 1;
c.gridheight = 1;
pane.add(_std, c);

// Add cdf plot
_cdfPlot.setToolTipText(" CDF Plot ");
c.gridx = 4;
c.gridy = 4;
c.gridwidth = 4;
c.gridheight = 3;
pane.add(_cdfPlot, c);

// set content pane
this.getContentPane().setLayout(null);
this.setContentPane(pane);

// plot adjustment
_pmfPlot.setVisible(true);
_pmfPlot.setSize(400, 300);
_pmfPlot.setPreferredSize(new Dimension(400, 300));

_cdfPlot.setVisible(true);
_cdfPlot.setSize(400, 300);
_cdfPlot.setPreferredSize(new Dimension(400, 300));

// Create Menus
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

// Create background color menu item
JMenu backgroundMenu = new JMenu("Background");
backgroundMenu.getAccessibleContext().setAccessibleName("background");

backgroundBlackMenuItem = new JCheckBoxMenuItem("Black",false);
backgroundBlackMenuItem.getAccessibleContext().setAccessibleName(" set background black ");
backgroundBlackMenuItem.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent event)
	{
	boolean state = backgroundBlackMenuItem.getState();
	if(state)
	{
		backgroundWhiteMenuItem.setState(false);
		backgroundYellowMenuItem.setState(false);
		setBackgroundBlack();
	}
	else
	{
backgroundWhiteMenuItem.setState(true);
backgroundYellowMenuItem.setState(false);
setBackgroundWhite();
	}
	}
	});

backgroundWhiteMenuItem = new JCheckBoxMenuItem("White",true);
backgroundWhiteMenuItem.getAccessibleContext().setAccessibleName(" set background white ");
backgroundWhiteMenuItem.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent event)
	{
	boolean state = backgroundWhiteMenuItem.getState();
	if(state)
	{
	backgroundBlackMenuItem.setState(false);
		backgroundYellowMenuItem.setState(false);
		setBackgroundWhite();
	}
	else
	{
		backgroundBlackMenuItem.setState(true);
backgroundYellowMenuItem.setState(false);
setBackgroundBlack();
	}
	}
	});

backgroundYellowMenuItem = new JCheckBoxMenuItem("Yellow",false);
backgroundYellowMenuItem.getAccessibleContext().setAccessibleName(" set background yellow ");
backgroundYellowMenuItem.addActionListener(new ActionListener()
{
	public void actionPerformed(ActionEvent event)
	{
	boolean state = backgroundYellowMenuItem.getState();
	if(state)
	{
		backgroundWhiteMenuItem.setState(false);
		backgroundBlackMenuItem.setState(false);
		setBackgroundYellow();
	}
	else
	{
backgroundWhiteMenuItem.setState(true);
backgroundBlackMenuItem.setState(false);
setBackgroundWhite();
	}
	}
	});

backgroundMenu.add(backgroundBlackMenuItem);
backgroundMenu.add(backgroundWhiteMenuItem);
backgroundMenu.add(backgroundYellowMenuItem);

// Create main menu bar and add items
JMenuBar menuBar = new JMenuBar();
this.setJMenuBar(menuBar);
// Add menus to the main menu bar
menuBar.add(fileMenu);
menuBar.add(backgroundMenu);

this.pack();
}


public void showGUI()
{
this.setVisible(true);
this.setLocation(XPOS, YPOS);
this.setSize(WIDTH, HEIGHT);
this.setResizable(false);
setWorkspace(Workspace.BERNOULLI);
refresh();
}

// Workspace settings
private void setWorkspace(int w)
{
switch(w)
{
	case Workspace.BERNOULLI:
	bernoulli();
	break;
	case Workspace.BINOMIAL:
	binomial();
	break;
	case Workspace.GEOMETRIC:
	geometric();
	break;
	case Workspace.NEGATIVE:
	negative();
	break;
	case Workspace.NORMAL:
	normal();
	break;
	case Workspace.PASCAL:
	pascal();
	break;
	case Workspace.POISSON:
	poisson();
	break;
	case Workspace.UNIFORM:
	uniform();
	break;
	default:
	// do nothing
}
}

// Apply result for current workspace
private boolean apply(int workspace)
{
switch(workspace)
{
	case Workspace.BERNOULLI:
	return applyBernoulli();
	case Workspace.BINOMIAL:
	return applyBinomial();
	case Workspace.GEOMETRIC:
	return applyGeometric();
	case Workspace.NEGATIVE:
	return applyNegative();
	case Workspace.NORMAL:
	return applyNormal();
	case Workspace.PASCAL:
	return applyPascal();
	case Workspace.POISSON:
	return applyPoisson();
	case Workspace.UNIFORM:
	return applyUniform();
	default:
	return false;
}
}

// Methods to set each workspace
private void bernoulli()
{
	_param1.setText("");
_param1.setToolTipText(" Probability of success [0 .. 1] ");
_param2.setToolTipText("");
_param3.setToolTipText("");
_param2.setEnabled(false);
_param3.setEnabled(false);
_pmfIndex.setEnabled(false);
_cdfIndex.setEnabled(false);
_pmfPlot.clear();
_cdfPlot.clear();
_pmfPlot.setHBounds(0.0f, 1.0f);
_pmfPlot.setVBounds(0.0f, 1.0f);
_cdfPlot.setHBounds(0.0f, 1.0f);
_cdfPlot.setVBounds(0.0f, 1.0f);
setUndefLabels();
resetAxis();
refresh();
_hasPmf = false;
_hasCdf = false;
_spinnersEnabled = false;
_currentWorkspace = Workspace.BERNOULLI;
}

private void binomial()
{
	_param1.setText("");
_param1.setToolTipText(" Number of trials -> k = 0, 1, 2 ... N ");
_param2.setText("");
_param2.setToolTipText(" Probability of success [0 .. 1] ");
_param3.setToolTipText("");
_param2.setEnabled(true);
_param3.setEnabled(false);
_pmfIndex.setEnabled(false);
_cdfIndex.setEnabled(false);
_pmfPlot.clear();
_cdfPlot.clear();
_pmfPlot.setHBounds(0.0f, 1.0f);
_pmfPlot.setVBounds(0.0f, 1.0f);
_cdfPlot.setHBounds(0.0f, 1.0f);
_cdfPlot.setVBounds(0.0f, 1.0f);
setUndefLabels();
resetAxis();
refresh();
_hasPmf = false;
_hasCdf = false;
_spinnersEnabled = false;
_currentWorkspace = Workspace.BINOMIAL;
}

private void geometric()
{
	// same workspace than for Bernoulli distribution
bernoulli();
_currentWorkspace = Workspace.GEOMETRIC;
}

private void negative()
{
// First set for binomial
binomial();
	// adjust
	_param1.setToolTipText(" double value > 0.0 ");
_currentWorkspace = Workspace.NEGATIVE;
}

private void normal()
{
// first set binomial
binomial();
	// adjust
	_param1.setToolTipText(" mean: double value >= 0.0 ");
	_param2.setToolTipText(" variance: double value > 0.0 ");
	_pmfPlot.getRenderer("YAxis").clear();
_currentWorkspace = Workspace.NORMAL;
}

private void pascal()
{
// first set binomial
binomial();
// adjust
_param1.setToolTipText(" Integer value > 0 ");
_currentWorkspace = Workspace.PASCAL;
}

private void poisson()
{
// first set bernoulli
bernoulli();
// adjust
_param1.setToolTipText(" double value > 0.0 -> average or rate for an occurrence ");
_currentWorkspace = Workspace.POISSON;
}

private void uniform()
{
// first set binomial
binomial();
// adjust
_param1.setToolTipText(" Integer value> 0 -> number of samples ");
_param2.setToolTipText(" Integer [0 .. n] min value for uniform random selection ");
_param3.setEnabled(true);
_param3.setText("");
_param3.setToolTipText(" Integer [1 .. n] -> max value for uniform random selection ");
_currentWorkspace = Workspace.UNIFORM;
}

// Methods to apply distribution settings
private boolean applyBernoulli()
{
try
   {
     double p = Double.parseDouble(_param1.getText());
if(p < 0.0 || p > 1.0) return false;
// apply settings
_pmfArray = BernoulliDistribution.pmf(p);
_cdfArray = BernoulliDistribution.cdf(p);
_hasPmf = true;
_hasCdf = true;
_pmf = getDistributionLabels(_pmfArray);
_cdf = getDistributionLabels(_cdfArray);
_currentMean = BernoulliDistribution.mean(p);
_currentVar = BernoulliDistribution.var(p);
_yAxisMax = 1.0;
_yMaxValue = max(_pmfArray);
updateSpinners();
setLabelValues();
((SignalPlot)_pmfPlot).setData(Convert.toFloatArray(_pmfArray));
((SignalPlot)_cdfPlot).setData(Convert.toFloatArray(_cdfArray));
refresh();
   }
   catch(NumberFormatException e)
   {
     return false;
   }
   return true;
}

private boolean applyBinomial()
{
try
   {
	   int n = Integer.parseInt(_param1.getText());
     double p = Double.parseDouble(_param2.getText());
if(n < 1 || p < 0.0 || p > 1.0) return false;
// apply settings
_pmfArray = BinomialDistribution.pmf(n, p);
_cdfArray = BinomialDistribution.cdf(n, p);
_hasPmf = true;
_hasCdf = true;
_pmf = getDistributionLabels(_pmfArray);
_cdf = getDistributionLabels(_cdfArray);
_currentMean = BinomialDistribution.mean(n, p);
_currentVar = BinomialDistribution.var(n, p);
_yMaxValue = max(_pmfArray);
if(n > 1)
{
checkPmfPlotVBounds(_yMaxValue);
_yAxisMax = yMax(_yMaxValue);
}
else
{
	_yAxisMax = 1.0;
}
	updateSpinners();
setLabelValues();
((SignalPlot)_pmfPlot).setData(Convert.toFloatArray(_pmfArray));
((SignalPlot)_cdfPlot).setData(Convert.toFloatArray(_cdfArray));
refresh();
   }
   catch(NumberFormatException e)
   {
     return false;
   }
   return true;
}

private boolean applyGeometric()
{
try
   {
     double p = Double.parseDouble(_param1.getText());
if(p < 0.0 || p > 1.0) return false;
// apply settings
_pmfArray = GeometricDistribution.pmf(p);
_cdfArray = GeometricDistribution.cdf(p);
_hasPmf = true;
_hasCdf = true;
_pmf = getDistributionLabels(_pmfArray);
_cdf = getDistributionLabels(_cdfArray);
_currentMean = GeometricDistribution.mean(p);
_currentVar = GeometricDistribution.var(p);
_yMaxValue = max(_pmfArray);
_yAxisMax = yMax(_yMaxValue);
checkPmfPlotVBounds(_yMaxValue);
updateSpinners();
setLabelValues();
((SignalPlot)_pmfPlot).setData(Convert.toFloatArray(_pmfArray));
((SignalPlot)_cdfPlot).setData(Convert.toFloatArray(_cdfArray));
refresh();
   }
   catch(NumberFormatException e)
   {
     return false;
   }
   return true;
}

private boolean applyNegative()
{
try
   {
	   double r = Double.parseDouble(_param1.getText());
     double p = Double.parseDouble(_param2.getText());
if(r <= 0.0 || p < 0.0 || p > 1.0) return false;
// apply settings
_pmfArray = NegativeBinomialDistribution.pmf(r, p);
_cdfArray = NegativeBinomialDistribution.cdf(r, p);
_hasPmf = true;
_hasCdf = true;
_pmf = getDistributionLabels(_pmfArray);
_cdf = getDistributionLabels(_cdfArray);
_currentMean = NegativeBinomialDistribution.mean(r, p);
_currentVar = NegativeBinomialDistribution.var(r, p);
_yMaxValue = max(_pmfArray);
_yAxisMax = yMax(_yMaxValue);
checkPmfPlotVBounds(_yMaxValue);
updateSpinners();
setLabelValues();
((SignalPlot)_pmfPlot).setData(Convert.toFloatArray(_pmfArray));
((SignalPlot)_cdfPlot).setData(Convert.toFloatArray(_cdfArray));
refresh();
   }
   catch(NumberFormatException e)
   {
     return false;
   }
   return true;
}

private boolean applyNormal()
{
try
   {
	   double m = Double.parseDouble(_param1.getText());
     double v = Double.parseDouble(_param2.getText());
if(m < 0.0 || v <= 0.0) return false;
// apply settings
_pmfArray = NormalDistribution.pdf(m, v, SPAN, 1);
_cdfArray = NormalDistribution.cdf(m, v, SPAN, 1);
_hasPmf = true;
_hasCdf = true;
_currentMean = m;
_currentVar = v;
double scaleFactor = 1.0/Math.sqrt(_currentVar);
_yMaxValue = max(_pmfArray);
_yAxisMax = yMax(_yMaxValue);
_pmf = getPdfLabels(_pmfArray);
_cdf = getPdfLabels(_cdfArray);
checkPmfPlotVBounds(_yMaxValue);
updateSpinners();
setLabelValues();
_pmfPlot.getRenderer("ZMean").setHBounds((float)(m-((double)SPAN)), (float)(m+((double)SPAN)));
_pmfPlot.getRenderer("ZVar").setHBounds((float)(m-((double)SPAN)), (float)(m+((double)SPAN)));
double ymean = NormalDistribution.pr(m, m, v) * scaleFactor;
double yvar = NormalDistribution.pr(m+v/2.0, m, v) * scaleFactor; double x1 = m-v/2.0;
double x2 = m+v/2.0;
((LineRenderer)_pmfPlot.getRenderer("ZMean")).setPoints(new Point2D(m, 0.0), new Point2D(m, ymean));
((LineRenderer)_pmfPlot.getRenderer("ZVar")).setPoints(new Point2D(x1, yvar), new Point2D(x2, yvar));
	((SignalPlot)_pmfPlot).setData(Convert.toFloatArray(normalPdf(_currentMean, _currentVar, SPAN, 2)));
((SignalPlot)_cdfPlot).setData(Convert.toFloatArray(NormalDistribution.cdf(_currentMean, _currentVar, SPAN, 2)));
refresh();
   }
   catch(NumberFormatException e)
   {
     return false;
   }
   return true;
}

private boolean applyPascal()
{
try
   {
	   int r = Integer.parseInt(_param1.getText());
     double p = Double.parseDouble(_param2.getText());
if(r < 1 || p < 0.0 || p > 1.0) return false;
// apply settings
_pmfArray = PascalDistribution.pmf(r, p);
_cdfArray = PascalDistribution.cdf(r, p);
_hasPmf = true;
_hasCdf = true;
_pmf = getDistributionLabels(_pmfArray);
_cdf = getDistributionLabels(_cdfArray);
_currentMean = PascalDistribution.mean(r, p);
_currentVar = PascalDistribution.var(r, p);
_yMaxValue = max(_pmfArray);
_yAxisMax = yMax(_yMaxValue);
checkPmfPlotVBounds(_yMaxValue);
updateSpinners();
setLabelValues();
((SignalPlot)_pmfPlot).setData(Convert.toFloatArray(_pmfArray));
((SignalPlot)_cdfPlot).setData(Convert.toFloatArray(_cdfArray));
refresh();
   }
   catch(NumberFormatException e)
   {
     return false;
   }
   return true;
}

private boolean applyPoisson()
{
	try
	   {
	     double lambda = Double.parseDouble(_param1.getText());
	if(lambda <= 0.0) return false;
	// apply settings
	_pmfArray = PoissonDistribution.pmf(lambda);
	_cdfArray = PoissonDistribution.cdf(lambda);
	_hasPmf = true;
	_hasCdf = true;
	_pmf = getDistributionLabels(_pmfArray);
	_cdf = getDistributionLabels(_cdfArray);
	_currentMean = lambda;
	_currentVar = lambda;
	_yMaxValue = max(_pmfArray);
	_yAxisMax = yMax(_yMaxValue);
	checkPmfPlotVBounds(_yMaxValue);
	updateSpinners();
	setLabelValues();
	((SignalPlot)_pmfPlot).setData(Convert.toFloatArray(_pmfArray));
	((SignalPlot)_cdfPlot).setData(Convert.toFloatArray(_cdfArray));
	refresh();
	   }
	   catch(NumberFormatException e)
	   {
	     return false;
	   }
	   return true;
}

private boolean applyUniform()
{
try
   {
	   int samples = Integer.parseInt(_param1.getText());
     int min = Integer.parseInt(_param2.getText());
     int max = Integer.parseInt(_param3.getText());
if(samples < 2 || min < 0 || min >= max) return false;
// apply settings
int[] nSamples = UniformDistribution.samples(samples, min, max);
UniformDistribution u = new UniformDistribution();
u.compute(nSamples);
_pmfArray = getUniformDistributionArray(u.pdf());
_cdfArray = getUniformDistributionArray(u.cdf());
_hasPmf = true;
_hasCdf = true;
_pmf = getUniformLabels(u.pdf());
_cdf = getUniformLabels(u.cdf());
_currentMean = u.mean();
_currentVar = u.var();
_yMaxValue = max(_pmfArray);
_yAxisMax = yMax(_yMaxValue);
checkPmfPlotVBounds(_yMaxValue);
updateSpinners();
setLabelValues();
((SignalPlot)_pmfPlot).setData(Convert.toFloatArray(_pmfArray));
((SignalPlot)_cdfPlot).setData(Convert.toFloatArray(_cdfArray));
refresh();
   }
   catch(NumberFormatException e)
   {
     return false;
   }
   return true;
}

// Methods to set several background colors
private void setBackgroundBlack()
{
_pmfPlot.setBackgroundColor(Color.BLACK);
_pmfPlot.getRenderer("Signal").setColor(Color.GREEN);
_pmfPlot.getRenderer("XAxis").setColor(Color.CYAN);
_pmfPlot.getRenderer("YAxis").setColor(Color.CYAN);
_pmfPlot.getRenderer("ZMean").setColor(Color.RED);
_pmfPlot.getRenderer("ZVar").setColor(Color.CYAN);

_cdfPlot.setBackgroundColor(Color.BLACK);
_cdfPlot.getRenderer("Signal").setColor(Color.GREEN);
_cdfPlot.getRenderer("XAxis").setColor(Color.CYAN);
_cdfPlot.getRenderer("YAxis").setColor(Color.CYAN);

_pmfPlot.refresh();
_cdfPlot.refresh();
}

private void setBackgroundWhite()
{
_pmfPlot.setBackgroundColor(Color.WHITE);
_pmfPlot.getRenderer("Signal").setColor(Color.BLUE);
_pmfPlot.getRenderer("XAxis").setColor(Color.BLACK);
_pmfPlot.getRenderer("YAxis").setColor(Color.BLACK);
_pmfPlot.getRenderer("ZMean").setColor(Color.RED);
_pmfPlot.getRenderer("ZVar").setColor(Color.CYAN);

_cdfPlot.setBackgroundColor(Color.WHITE);
_cdfPlot.getRenderer("Signal").setColor(Color.BLUE);
_cdfPlot.getRenderer("XAxis").setColor(Color.BLACK);
_cdfPlot.getRenderer("YAxis").setColor(Color.BLACK);

_pmfPlot.refresh();
_cdfPlot.refresh();
}

private void setBackgroundYellow()
{
_pmfPlot.setBackgroundColor(Color.YELLOW);
_pmfPlot.getRenderer("Signal").setColor(Color.BLUE);
_pmfPlot.getRenderer("XAxis").setColor(Color.BLACK);
_pmfPlot.getRenderer("YAxis").setColor(Color.BLACK);
_pmfPlot.getRenderer("ZMean").setColor(Color.RED);
_pmfPlot.getRenderer("ZVar").setColor(Color.BLUE);

_cdfPlot.setBackgroundColor(Color.YELLOW);
_cdfPlot.getRenderer("Signal").setColor(Color.BLUE);
_cdfPlot.getRenderer("XAxis").setColor(Color.BLACK);
_cdfPlot.getRenderer("YAxis").setColor(Color.BLACK);

_pmfPlot.refresh();
_cdfPlot.refresh();
}

// Private method to compute normal distribution ading resolution in order to smooth the plot
private double[] normalPdf(double m, double v, int span, int step)
{
	assert(span > 0): "NormalDistribution -> pdf(...): Bad parameter; 'span' must be greater than 0";
	assert(step > 0): "NormalDistribution -> pdf(...): Bad parameter; 'step' must be greater than 0";
Range r = new Range(m-(double)span, m+(double)span);
double std = 1.0/Math.sqrt(v);
LinkedList<Double> list = new LinkedList<>();
double p = 0.0;
for(double x = r.getMin(); x <= r.getMax(); x += 1.0/(double)step)
{
p = NormalDistribution.pr(x, m, v)*std;
list.add(p);
}
int k = 0;
double[] _pdf = new double[list.size()];
ListIterator<Double> it = list.listIterator();
while(it.hasNext())
{
_pdf[k++] = it.next();
}
return _pdf;
}



// Helper method to get normal pdf formatted values
private String[] getPdfLabels(double[] x)
{
	int n = x.length;
	String[] s = new String[n];
double xPos = _currentMean - ((double)SPAN);
for(int i = 0; i < n; i++)
{
	s[i] = format(xPos, x[i]);
	xPos += 1.0;
}
	return s;
}

// Helper method to get pmf | cdf formatted values
private String[] getDistributionLabels(double[] x)
{
	int n = x.length;
	String[] s = new String[n];
for(int i = 0; i < n; i++)
{
	s[i] = format(x[i]);
}
	return s;
}

// Method to get labels for the uniform distribution
private String format(Pair<Integer, Double> p)
{
String s = "[";
s += p.getFirst();
s += ", ";
s += format(p.getSecond());
return s+"]";
}

// Method to build uniform distribution labels
private String[] getUniformLabels(Pair[] p)
{
	int n = p.length;
String[] s = new String[n];
for(int i = 0; i < n; i++)
{
s[i] = format(p[i]);
}
return s;
}

// Method to get uniform distribution values
private double[] getUniformDistributionArray(Pair[] p)
{
int n = p.length;
double[] a = new double[n];
for(int i = 0; i < n; i++)
{
a[i] = (double)p[i].getSecond();
}
return a;
}

// Method to adjust pmf plot verticqal bound if necessary
private void checkPmfPlotVBounds(double ymax)
{
	double y = yMax(ymax);
_pmfPlot.getRenderer("YAxis").setVBounds(0.0f, (float)y);
((LineRenderer)_pmfPlot.getRenderer("YAxis")).setPoints(new Point2D(0.0, 0.0), new Point2D(0.0, y));
}

// Method to reset axis points
private void resetAxis()
{
	((LineRenderer)_pmfPlot.getRenderer("XAxis")).setPoints(new Point2D(0.0, 0.0), new Point2D(1.0, 0.0));
	((LineRenderer)_pmfPlot.getRenderer("YAxis")).setPoints(new Point2D(0.0, 0.0), new Point2D(0.0, 1.0));
	((LineRenderer)_cdfPlot.getRenderer("XAxis")).setPoints(new Point2D(0.0, 0.0), new Point2D(1.0, 0.0));
	((LineRenderer)_cdfPlot.getRenderer("YAxis")).setPoints(new Point2D(0.0, 0.0), new Point2D(0.0, 1.0));
}

// Helper method to refresh plots
private void refresh()
{
_pmfPlot.refresh();
_cdfPlot.refresh();
}

// Helper method to get the maximum value in a distribution
private double max(double[] x)
{
	double _max = x[0];
for(int i = 1; i < x.length; i++)
{
if(_max < x[i]) _max = x[i];
}
return _max;
}

// Helper method to compute max vertical bound if necessary
private double yMax(double y)
{
if(y+0.2 >= 1.0) return 1.0;
double newY = y+0.2;
newY *= 10.0;
newY = (double)((int)newY);
newY /= 10.0;
return newY;
}

// Helper method to format a floating-point value
private String format(double x)
{
NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
DecimalFormat df = (DecimalFormat)formatter;
if(x != 0.0 && Math.abs(x) < 1E-2)
{
	df.applyPattern("0.#E0");
}
else
{
df.setMinimumFractionDigits(2);
	df.setMaximumFractionDigits(2);
}
return df.format(x);
}

// Method to format a pair of floating-point coordinates
private String format(double x, double y)
{
	String s = "(";
s += format(x);
s += ", ";
s += format(y);
	return s +")";
}

// Set value labels undefined
private void setUndefLabels()
{
_pmfValue.setText("undef");
_cdfValue.setText("undef");
_mean.setText("mean = undef");
_var.setText("var = undef");
_std.setText("std = undef");
_yAxisRange.setText("PMF Y-Axis [0.0 .. 1.00]");
_yMax.setText("max = undef");
}

// Set label values
private void setLabelValues()
{
_mean.setText("mean = "+format(_currentMean));
_var.setText("var = "+format(_currentVar));
_std.setText("std = "+format(Math.sqrt(_currentVar)));
_yAxisRange.setText("PMF Y-Axis [0.0 .. "+format(_yAxisMax));
_yMax.setText("max = "+format(_yMaxValue));
}

// Update spinners
private void updateSpinners()
{
	if(!_spinnersEnabled) enableSpinners();
	_pmfIndex.setModel(new SpinnerNumberModel(0, 0, _pmf.length-1, 1));
	_cdfIndex.setModel(new SpinnerNumberModel(0, 0, _cdf.length-1, 1));
	_pmfValue.setText(_pmf[0]);
	_cdfValue.setText(_cdf[0]);
}

// Enable spinners
private void enableSpinners()
{
_pmfIndex.setEnabled(true);
_cdfIndex.setEnabled(true);
_spinnersEnabled = true;
}

// Method to show an error message
private void showErrorMessage(String message)
{
JOptionPane.showMessageDialog(null, message);
}


// Data members
private boolean _hasPmf;
private boolean _hasCdf;
private boolean _spinnersEnabled;
private int _currentWorkspace;
private double _currentMean;  // current mean value for normal distribution
private double _currentVar;  // current variance value for normal distribution
private double _yAxisMax;
private double _yMaxValue;

private double[] _pmfArray;  // pmf as a double floating-point vector
private double[] _cdfArray;  // cdf as a double floating-point vector
private String[] _pmf;  // current formatted pmf
private String[] _cdf;  // current formatted cdf

private Plot _pmfPlot;
private Plot _cdfPlot;

private JCheckBoxMenuItem backgroundBlackMenuItem;
private JCheckBoxMenuItem backgroundWhiteMenuItem;
private JCheckBoxMenuItem backgroundYellowMenuItem;

private JSpinner _pmfIndex;
private JSpinner _cdfIndex;

private JTextField _param1;
private JTextField _param2;
private JTextField _param3;

private JLabel _mean;  // show mean
private JLabel _var;  // show variance
private JLabel _std;  // show standard deviation

private JLabel _pmfValue;  // show current pmf value
private JLabel _cdfValue;  // show current cdf value
private JLabel _yAxisRange;
private JLabel _yMax;

// Some symbolic constants
private static final int SPAN = 5;
private static final int XPOS = 200;
private static final int YPOS = 50;
private static final int WIDTH = 800;
private static final int HEIGHT = 700;

// Helper inner class
private static final class Workspace
{

public static final int BERNOULLI = 0;
public static final int BINOMIAL = 1;
public static final int GEOMETRIC = 2;
public static final int NEGATIVE = 3;
public static final int NORMAL = 4;
public static final int PASCAL = 5;
public static final int POISSON = 6;
public static final int UNIFORM = 7;

}

}

// END

