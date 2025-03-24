/*
 * Copyright (c) 2023 Ismael Mosquera Rivera
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
* TestAudioRecorder.java
*
* Author: Ismael Mosquera Rivera
*/

import imr.util.InputReader;
import imr.sound.audio.AudioRecorder;
import javax.sound.sampled.UnsupportedAudioFileException;

public class TestAudioRecorder
{
public static void main(String[] args)
{
boolean hasRec = false;
short option;
String s;
AudioRecorder recorder = new AudioRecorder();

show_menu();
do
{
System.out.println();
System.out.print("option: ");
option = InputReader.readShort();
if(option == 0) option = 7;
switch(option)
{
	case 1:
	show_menu();
	break;
	case 2:
	recorder.rec();
	hasRec = true;
	break;
	case 3:
	if(hasRec)
	{
		recorder.play();
	}
	else
	{
		System.out.println("sorry, audio has not been recorded yet!");
	}
	break;
	case 4:
	recorder.stop();
	break;
	case 5:
	if(hasRec)
	{
		System.out.println();
		System.out.print("filename: ");
		s = InputReader.readString();
		try
		{
		recorder.save(s);
		System.out.println();
				System.out.println(s + " file saved successfully.");
	}
	catch(UnsupportedAudioFileException e)
	{
		System.out.println(e);
	}
	}
	else
	{
		System.out.println("sorry: there is not recorded audio to save yet!");
	}
	break;
	case 6:
	recorder.stop();
	option = 0;
	break;
	default:
	System.out.println("not available.");
}
}while(option != 0);

System.out.println();
System.out.println("bye.");
}

private static void show_menu()
{
System.out.println("1: show menu.");
System.out.println("2: rec.");
System.out.println("3: play.");
System.out.println("4: stop.");
System.out.println("5: save.");
System.out.println("6: exit.");
}
}

// END
