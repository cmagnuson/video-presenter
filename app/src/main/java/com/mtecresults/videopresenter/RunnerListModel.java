package com.mtecresults.videopresenter;

import java.sql.*;
import java.util.*;

public class RunnerListModel implements GenericModel {

	private LinkedList<GenericView> views = new LinkedList<GenericView>();
	
	protected String sql = null;
	protected Statement stmt = null;
	protected Runner[] runners = new Runner[]{};
	private Scroller s = new Scroller(this);	
	private Refresher r = new Refresher(this);
	private Timer timer = new Timer();

	private int fontSize = 34;
	private String title = "";
	private int numberToStartAt = 0;
	private int numberToDisplay = 10;
	private int numberToScroll = 1;
	private int scrollTime = 2000; //2 seconds
	
	public RunnerListModel(String sql, String title, int refreshRate, Statement stmt){
		this.sql = sql;
		this.stmt = stmt;
		this.title = title;
		Timer t = new Timer();
		t.schedule(r, refreshRate, refreshRate);
		update();
	}
	
	public RunnerListView getNewView(){
		return new RunnerListView(this);
	}

	public void addView(GenericView v){
		views.add(v);
	}
	public void removeView(GenericView v){
		views.remove(v);
	}
	public void removeAllViews(){
		views =  new LinkedList<GenericView>();
	}
	
	public void backward() {
		int startNum = Math.max(getNumberToStartAt()-numberToScroll, 0);
		setNumberToStartAt(startNum);
		update();
	}
	public void forward() {
		if(getRunners()!=null){
			int startNum = Math.min(getNumberToStartAt()+numberToScroll, getRunners().length);
			setNumberToStartAt(startNum);
			update();
		}
	}

	
	void playPause() {
		toggleScrolling();
	}
	
	public void toggleScrolling(){
		if(s.isRunning()==true){
			s.setRunning(false);
			timer.cancel();
		}
		else{
			timer = new Timer();
			s = new Scroller(this);
			timer.schedule(s, scrollTime, scrollTime);
			s.setRunning(true);
		}
	}

	public int getFontSize() {
		return fontSize;
	}

	public String getTitle() {
		return title;
	}

	public int getNumberToStartAt() {
		return numberToStartAt;
	}

	public int getNumberToDisplay() {
		return numberToDisplay;
	}
	
	public int getNumberToScroll(){
		return numberToScroll;
	}

	public Runner[] getRunners(){
		return runners;
	}

	public void setUpdated(){
		for(GenericView v: views){
			v.repaint();
		}
	}
	
	//sql string must do <split>-GunTime and select AS DisplayTime
	public synchronized void update(){
		try{
			ResultSet rs = stmt.executeQuery(sql);
			LinkedList<Runner> ll = new LinkedList<Runner>();
			boolean firstRunner = true;
			long firstRunnerTime = 0;
			int place = 0;
			while(rs.next()){
				place++;
				if(firstRunner){
					firstRunnerTime = rs.getLong("DisplayTime");
					firstRunner = false;
				}
				Runner r = new Runner();
				r.setAge(rs.getString("age"));
				r.setBib(rs.getString("bib"));
				r.setFinishTime(rs.getLong("DisplayTime"));
				r.setBehindFirstFinisher(rs.getLong("DisplayTime")-firstRunnerTime);
				r.setFirstName(rs.getString("First Name"));
				r.setLastName(rs.getString("Last Name"));
				r.setPlace(""+place);
				r.setSex(rs.getString("sex"));
				ll.add(r);
			}
			rs.close();
			runners = ll.toArray(new Runner[]{});
			setUpdated();
		}
		catch(SQLException sql){
			System.err.println("Unexpected SQL Error in RunnerListModel update()");
			sql.printStackTrace();
		}
	}

	public void setNumberToStartAt(int numberToStartAt) {
		this.numberToStartAt = numberToStartAt;
	}

}
