This example demonstrates the class BandPassFilter.

Input signal => 100hz, 200Hz, 300Hz, 400Hz, 500Hz, 600Hz, 700Hz, 800Hz, 900Hz, 1000Hz
Band = [400Hz .. 700Hz]
Output signal => -- , -- , -- , 400Hz, 500Hz, 600Hz, 700Hz, -- , -- , --

Every signal chunk has a duration of 500ms.

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

