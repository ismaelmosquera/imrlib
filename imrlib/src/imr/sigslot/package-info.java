/**
* package imr.sigslot
* <p>
* This package implements a Signal / Slot subsystem.
* Currently, there are the following classes already coded:
* <ul>
* <li>Signal0</li>
* <li>Signal1</li>
* <li>Signal2</li>
* <li>Signal3</li>
* <li>Slot0</li>
* <li>Slot1</li>
* <li>Slot2</li>
* <li>Slot3</li>
* </ul>
*
* Each slot is coded as a functional interface, so you can even use lambda expressions.
* <p>
* As you may already guess, signals can emit from 0 to 3 parameters which are generics. following an example for Signal0/Slot0:
* <code>
*
* Signal0 signal0 = new Signal0(new Slot0()
* {
* public void receive() 
* {
* System.out.println("Slot0: signal received!");
* }
* });
* signal0.emit();
* </code>
* <p>
* Output:
* Slot0: signal received!
*
* @author Ismael Mosquera Rivera
*
*/

package imr.sigslot;
