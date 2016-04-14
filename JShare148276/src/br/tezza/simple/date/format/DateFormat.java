package br.tezza.simple.date.format;

import java.text.SimpleDateFormat;

public class DateFormat {
	
	private SimpleDateFormat sdf;
	
	public SimpleDateFormat formatoData(){
		
		sdf = new SimpleDateFormat("dd/MM/yyyy H:mm:ss:SSS");
		
		return sdf;
	}

}
