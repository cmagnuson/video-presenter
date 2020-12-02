package com.mtecresults.videopresenter;

import java.sql.*;

public class LastRunnerListModel extends RunnerListModel {

	public LastRunnerListModel(String sql, String title, int refreshRate, Statement stmt){
		super(sql, title, refreshRate, stmt);
		this.sql = this.sql.replaceAll(" ASC ", " DESC ");
		
		int start = this.sql.indexOf("ORDER BY");
		int end = this.sql.indexOf("LIMIT") - 1;
		
		sql = sql.substring(0, start) + " ORDER BY results.time DESC " + sql.substring(end);
		
		System.err.println(sql);		
	}
	public LastRunnerListView getNewView(){
		return new LastRunnerListView(this);
	}
	
	public void backward() {
	}
	public void forward() {
	}	
	void playPause() {
	}
	public void toggleScrolling(){
	}

	public void setNumberToStartAt(int numberToStartAt) {
	}
}
