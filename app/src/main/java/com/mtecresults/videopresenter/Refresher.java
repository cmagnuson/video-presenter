package com.mtecresults.videopresenter;

import java.util.*;

public class Refresher extends TimerTask {


	boolean running = false;
	RunnerListModel model;

	Refresher(RunnerListModel model){
		this.model = model;
	}

	public void run(){

		model.update();
	}
}
