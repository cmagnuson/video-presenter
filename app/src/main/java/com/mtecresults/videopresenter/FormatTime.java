package com.mtecresults.videopresenter;

public class FormatTime {

	static public long timeToMilliseconds(int hours, int minutes, double seconds){
		return (long)(hours*60*60*1000+minutes*60*1000+seconds*1000);
	}
	
//	found at Java time reference page
	static public String millisecondsToFormatted(long timeInMillis){
		if(timeInMillis <= 0){
			return "";
		}
		
		//increment by 1 sec so rounding is correct
		timeInMillis+=1000;
		
		String hoursStr = "";
		String minutesStr ="";
		String secondsStr = "";
		
		int remdr = (int) ( timeInMillis % ( 24L * 60 * 60 * 1000 ));
		
		int hours = remdr / ( 60 * 60 * 1000 );
		
		remdr = remdr % ( 60 * 60 * 1000 );
		
		int minutes = remdr / ( 60 * 1000 );
		
		remdr = remdr % ( 60 * 1000 );
		
		int seconds = remdr / 1000;
		
		//int pt =(int)(timeInMillis - (60*60*1000*hours + 60*1000*minutes + 1000*seconds));//(remdr % 1000)/10;
		
		if (hours != 0){
			hoursStr=(new Integer(hours)).toString()+":";
		}
		if (minutes < 10){
			if(hours==0 && minutes == 0){
				minutesStr="0:";
				hoursStr="";
			}
			else if(hours==0){
				minutesStr = (new Integer(minutes)).toString()+":";
			}
			else{
				minutesStr="0"+(new Integer(minutes)).toString()+":";
			}
		}
		else{
			minutesStr=(new Integer(minutes)).toString()+":";
		}
		if (seconds < 10){									//the two commented lines below add decimal pt
			secondsStr="0"+(new Integer(seconds)).toString();//+"."+((new Integer(pt)).toString()+"  ").substring(0,2);
		}
		else{
			secondsStr=(new Integer(seconds)).toString();//+"."+((new Integer(pt)).toString()+"  ").substring(0,2);
		}  
		/*
		 * to return times without 00:03 for example, for splits - differences from leader
		 * 
		if(minutesStr.equals("00:") && hoursStr.equals("")){
			return secondsStr;
		}
		*/
		return (hoursStr+minutesStr+secondsStr);
		/*
		if(hours==0){
			String ret = (new Integer(minutes)).toString()+":"+(new Integer(seconds)).toString()+"."+(new Integer(pt)).toString();
			return ret;
		}
		String ret = (new Integer(hours)).toString()+":"+(new Integer(minutes)).toString()+":"+(new Integer(seconds)).toString()+"."+(new Integer(pt)).toString();
		return ret;  */
	}
}

