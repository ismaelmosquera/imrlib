This example demonstrates several classes in this library.
A decimator and an interpolator implemented as static methods in the LinearSampler class.
We also test the SignalPlot class adding two objects to the interface.

- A plot to show ADSR or Window envelopes.
- A plot to show a signal ( saw, sine, square or triangular ).

Each time you change the frequency using its slider, a randomly selected scale is generated.
There are vector of semitones for the following tones:
- F# minor.
- A major.
- G minor.
- Bb major.
- A minor.
- C major.
- B minor.
- D major.

You can apply an ADSR or a Window envelope to the generated signal,
both of them or just nothing.

You also have the option of recording it and save the result to an audio file.

We expect that you enjoy this application example.

**** build and run ****

The example is already compiled. Following, there are some instructions:

To run the example type:

run

or

./run.sh

depending in what kind of shell you are.

Although the example is already compiled, you can rebuild it if you wish.

To build the example just type:

build

if you are using a MsWindows command line shell.
If you are under a Linux like shell, type:

./build.sh

