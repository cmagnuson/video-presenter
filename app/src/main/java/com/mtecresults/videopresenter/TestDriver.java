package com.mtecresults.videopresenter;

import java.sql.*;

public class TestDriver {

	public static void main(String[] args) throws Exception {
		Connection conn = InitializeDB.connectToMySqlDatabase("localhost/tcm08", "root", "");
	
		RunnerListModel rlm = new RunnerListModel("SELECT (results.30K-results.GunTime) AS DisplayTime, age,bib,sex,`First Name`,`Last Name` FROM entries, results WHERE entries.pid = results.pid AND (results.30K-results.GunTime)>0 ORDER BY (results.30K-results.GunTime) ASC LIMIT 23", "TEST", 10000, conn.createStatement());
		RunnerListView rlv = new RunnerListView(rlm);
		RunnerListView rlv2 = new RunnerListView(rlm);
		FullscreenView fv2 = new FullscreenView();
		fv2.setView(rlv);
		PreviewView fv = new PreviewView("LIVE", true);
		fv.setView(rlv2);
		rlm.toggleScrolling();
		RunnerListModel rlm2 = new RunnerListModel("SELECT (results.30K-results.GunTime) AS DisplayTime, age,bib,sex,`First Name`,`Last Name` FROM entries, results WHERE sex='F' AND entries.pid = results.pid AND (results.30K-results.GunTime)>0 ORDER BY (results.30K-results.GunTime) ASC LIMIT 23", "TEST", 10000, conn.createStatement());
		RunnerListView rlv3 = new RunnerListView(rlm2);
		PreviewView pv = new PreviewView("PREVIEW", false);
		pv.setView(rlv3);
		RunnerListController cont = new RunnerListController(false);
		cont.setModel(rlm2);
		RunnerListController cont2 = new RunnerListController(true);
		cont2.setModel(rlm);
	}

}
