package com.mtecresults.videopresenter;

import java.util.*;

public class Scroller extends TimerTask {
	
	boolean running = false;
	RunnerListModel model;
	
	Scroller(RunnerListModel model){
		this.model = model;
	}

	public void run(){
		int currentLocation = model.getNumberToStartAt();
		int numToScroll = model.getNumberToScroll();
		if(model.getRunners()==null){
			return;
		}
		int total = model.getRunners().length;
		if((currentLocation+numToScroll)>=total){
			model.setNumberToStartAt(0);
		}
		else{
			model.setNumberToStartAt(currentLocation+numToScroll);
		}
		model.update();
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
}
