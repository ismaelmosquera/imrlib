
## **Java IMR-LIV**


This is a Java implementation as a library having the following packages:  
>  
> - imr.math  
> - imr.math.matrix  
> - imr.math.matrix.complex  
> - imr.math.polynomial  
> - imr.math.polynomial.division  
> - imr.plot  
> - imr.sigslot  
> - imr.sound  
> - imr.sound.audio  
> - imr.sound.audio.analysis  
> - imr.sound.audio.filter  
> - imr.sound.audio.synthesis  
> - imr.sound.audio.window  
> - imr.sound.midi  
> - imr.util  
>  
  
where IMR stands for Ismael Mosquera Rivera.  
  
Following, a brief explanation about the functionallity offered for each package  
  
#### imr.math  
This package has useful classes to do some math computation, like complex number, random number generation and so on.  
  
#### imr.matrix  
This package has to do with linear algebra.  
Here, you can find classes to perform the most common operations applied to matrices and vectors,  
and solve NxN linear equations systems.  
The techniques used are as follows:  
>  
> - Cramer's rule.  
> - Gaussian elimination.  
> - LU decomposition.  
> - QR factorization.  
>  
  
Where numerical analysis skills are used to implement the concrete algorithms.  
You can also find eigen values and its associated eigen-vectors by using the Power method the QR algorithm or computing roots for the characteristic polynomial.  
The package has support to perform SVD ( Singular Value Decomposition ), and several applications related to it.  
There are support to perform matrix diagonalization.  
  
#### imr.math.matrix.complex  
  
This package has almost the same functionallity offered by the package imr.math.matrix,  
but this time dealing with complex number coefficient matrices and vectors.  
  
#### imr.math.polynomial  
  
Here you can know how to do polynomial Lagrange interpolation, compute Ruffini's rule and polynomial operations like:  
>  
> - Arithmetic.  
> - Derivation and integration.  
> - Roots finding.  
> - Compute Characteristic polynomial for square real and complex matrices.  
> - Cyclotomic polynomial computation for Pn(X) where n is a natural number, that is: n greater 0.  
>  

  
All of them are available for any type of numeric coefficient:  
>  
> - Integer.  
> - Real.  
> - Complex.  
>  
  
#### imr.math.polynomial.division  
  
This subpackage has been created to have all about polynomial division ( except for the Ruffini's rule  )  
There are classes to perform polynomial division for any numerical type:  
>  
> - Integer.  
> - Rational.  
> - Real.  
> - Complex.  
>  
  
Integer polynomial division is computed by converting an integer polynomial into a real one, and then apply float polynomial division.  
  
#### imr.plot  
  
This package offers a basic visualization system from you can easily code your own plots.  
There are already implemented visualizers, see the comcrete API documentation to know more about it.  
  
#### imr.sigslot  
  
A useful system of signals and slots for generic objects.  
A signal emits some kind of data, which is received by a slot to do a concrete task.  
There are support for signals and slots for 0, 1, 2, and 3 parameters of generic data.  
  
#### imr.sound  
  
This package offers, mainly, a sound player suitable to play digital audio and MIDI.  
You can reproduce digital audio for several audio file formats, and for SMF ( Standard MIDI File ),  
with the functionallity offered by a comprehensive sound player.  
  
#### imr.sound.audio  
  
With the features offered by this package, you can manage audio devices and audio file I/O among other things.  
  
#### imr.sound.audio.analysis  
  
This package has support to perform spectral analysis.  
  
#### imr.sound.audio.filter  
  
This package implements FIR ( Finite Impulse Response ) filters.  
The implemented skill is through the sinc function.  
Implemented filters:
>  
> - Low pass.  
> - High pass.  
> - Band pass.  
> - Band reject.  
>  
  
#### imr.sound.audio.synthesis  
  
This package has functionallity to generate audio waves ( Saw, sine, Square, Triangular ), ADSR ( Attack, Decay, Sustain, Release ) envelopes,  
several kind of modulation ( Amplitude, Frequency, Ring ), random noise signals and so on.  
There is support to spectral synthesis too.  
  
#### imr.sound.audio.window  
  
Here you can generate some kind of useful windows to do analysis, synthesis, and filtering.  
  
#### imr.sound.midi  
  
This package has support to manage MIDI data structures, MIDI file I/O and MIDI playing.  
  
#### imr.util  
  
This package offers some utility classes like some kind of buffers and a so useful iArray class to deal with  
1, 2, and 3-dimensional arrays for any atomic type.  
  
The programming interface is so intuitive and easy to use.  
The examples folder has a lot of examples to know how to use the classes offered by this Java code library, we encourage you to run them.  
there is also an API documentation compressed in a doc.zip archive.  
This piece of code is devoted to my loved assistant dog Inuit, he passed away on 12/08/2025 ( day/month/year ).  
Hope that you enjoy and appreciate this work made for free.  
  
Latest revision: 19/04/2026 ( day/month/year )  
  
