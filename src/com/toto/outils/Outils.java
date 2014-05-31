package com.toto.outils;

public class Outils 
{
	public static float convertionSexagesimalVersDecimal(int degres, int minutes, int secondes)
	{
		return degres + minutes / 60 + secondes / 60;
	}
}
