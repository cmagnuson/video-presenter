package com.mtecresults.videopresenter;

import java.sql.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class GuiDriver implements ActionListener, Runnable {

	private static JFrame frame;
	private static JComboBox screenList;
	private static JComboBox eventList;
	private static JComboBox sexList;
	private static JComboBox catList;
	private static JTextField minAge, maxAge;
	private static JTextField searchLimit;
	private static JTextField title;
	private static JTextField refreshRate;
	private static JButton previewButton, liveButton;
	private static JLabel eventListLabel, sexListLabel, catListLabel, searchLimitLabel, minAgeLabel, maxAgeLabel, titleLabel,
	refreshRateLabel, screenListLabel;
	private static Connection conn;
	private static Long resultsSiteRace;
	private static GenericModel liveModel, previewModel;
	private static GenericView liveViewBig, liveViewSmall, previewView;
	private static FullscreenView fullscreenView;
	private static PreviewView previewPreview, previewLive;
	private static RunnerListController previewController, liveController;
	private static LinkedList<String> events = new LinkedList<String>();

	public static void main(String[] args) throws Exception {
		GuiDriver g = new GuiDriver();
		g.run();

		liveModel = new RunnerListModel("SELECT (results.Time-results.GunTime) AS DisplayTime, age,bib,sex,`First Name`,`Last Name` FROM entries, results WHERE entries.pid = results.pid AND (results.Time-results.GunTime)>0 ORDER BY (results.Time-results.GunTime) ASC LIMIT 100", "DEFAULT", 10000, conn.createStatement());
		fullscreenView = new FullscreenView();
		previewLive = new PreviewView("LIVE", true);
		liveController = new RunnerListController(true);
		newLiveModel(liveModel);

		previewModel = new RunnerListModel("SELECT (results.Time-results.GunTime) AS DisplayTime, age,bib,sex,`First Name`,`Last Name` FROM entries, results WHERE entries.pid = results.pid AND (results.Time-results.GunTime)>0 ORDER BY (results.Time-results.GunTime) ASC LIMIT 100", "DEFAULT", 10000, conn.createStatement());
		previewPreview = new PreviewView("PREVIEW", false);
		previewController = new RunnerListController(false);
		newPreviewModel(previewModel);
		frame.toFront();
	}

	private static void newLiveModel(GenericModel rlm){
		if(liveModel!=null){
			liveModel.removeAllViews();
		}
		liveModel = rlm;
		liveViewBig = rlm.getNewView();
		liveViewSmall = rlm.getNewView();
		fullscreenView.setView(liveViewBig);
		previewLive.setView(liveViewSmall);
		liveController.setModel(liveModel);
	}
	private static void newPreviewModel(GenericModel rlm){
		if(previewModel!=null){
			previewModel.removeAllViews();
		}
		previewModel = rlm;
		previewView = rlm.getNewView();
		previewPreview.setView(previewView);
		previewController.setModel(previewModel);
	}

	private GuiDriver() throws Exception {
		frame = new JFrame("Video Presenter");
		frame.setLayout(new GridLayout(14,2));
		conn = InitializeDB.run(frame);
		String str = (String)JOptionPane.showInputDialog(
				frame,
				"race id (blank if not using results site):",
				"MtecResults Race ID",
				JOptionPane.PLAIN_MESSAGE,
				null,
				null,
				null);

		if(str!=null && str.length()>0){
			resultsSiteRace = Long.valueOf(str);
		}

		screenList = new JComboBox();
		screenList.addItem("Video");
		screenList.addItem("Announce");
		screenListLabel = new JLabel("Screen Type: ");
		frame.add(screenListLabel);
		frame.add(screenList);

		if(resultsSiteRace==null){
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM results LIMIT 0");
			ResultSetMetaData rsmd = rs.getMetaData();
			for(int i=0; i<rsmd.getColumnCount(); i++){
				String column = rsmd.getColumnName(i+1);
				if(!column.equalsIgnoreCase("GunTime") && !column.equalsIgnoreCase("Pid")){
					events.add(column);
				}
			}
		}
		else{
			PreparedStatement pst = conn.prepareStatement("SELECT * FROM split WHERE race_id=?");
			pst.setLong(1, resultsSiteRace);
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				if(!rs.getString("title").equalsIgnoreCase("GunStart")){
					events.add(rs.getString("title"));
				}
			}
		}
		eventList = new JComboBox(events.toArray());
		eventListLabel = new JLabel("Location: ");
		frame.add(eventListLabel);
		frame.add(eventList);

		sexList = new JComboBox();
		sexList.addItem("%");
		sexList.addItem("M");
		sexList.addItem("F");
		sexListLabel = new JLabel("Sex: ");
		frame.add(sexListLabel);
		frame.add(sexList);

		catList = new JComboBox();
		catList.addItem("%");

		if(resultsSiteRace==null){
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT DISTINCT cat FROM entries");
			while(rs.next()){
				catList.addItem(rs.getString("cat"));
			}
		}
		catListLabel = new JLabel("Event: ");
		frame.add(catListLabel);
		frame.add(catList);

		minAge = new JTextField("1",2);
		minAgeLabel = new JLabel("Age >= ");
		frame.add(minAgeLabel);
		frame.add(minAge);

		maxAge = new JTextField("99",2);
		maxAgeLabel = new JLabel("Age <= ");
		frame.add(maxAgeLabel);
		frame.add(maxAge);

		searchLimit = new JTextField("100",4);
		searchLimitLabel = new JLabel("Max Results: ");
		frame.add(searchLimitLabel);
		frame.add(searchLimit);

		title = new JTextField(12);
		titleLabel = new JLabel("Title: ");
		frame.add(titleLabel);
		frame.add(title);

		refreshRate = new JTextField("1000", 4);
		refreshRateLabel = new JLabel("Refresh Rate (ms): ");
		frame.add(refreshRateLabel);
		frame.add(refreshRate);

		previewButton = new JButton("Preview");
		liveButton = new JButton("LIVE");
		previewButton.addActionListener(this);
		previewButton.setForeground(Color.GREEN);
		liveButton.addActionListener(this);
		liveButton.setForeground(Color.RED);
		frame.add(previewButton);
		frame.add(liveButton);

		frame.pack();
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
	}

	private String generateSql(){
		if(resultsSiteRace==null){
			return "SELECT (results."+eventList.getSelectedItem()+"-results.GunTime) AS DisplayTime, age,bib,sex,`First Name`,`Last Name` " +
			"FROM entries, results " +
			"WHERE entries.pid = results.pid " +
			"AND (results."+eventList.getSelectedItem()+"-results.GunTime)>0 " +
			"AND sex LIKE '"+sexList.getSelectedItem()+"' " +
			"AND cat LIKE '"+catList.getSelectedItem()+"' "+
			"AND age >="+minAge.getText()+" AND age <="+maxAge.getText()+" "+
			"ORDER BY (results."+eventList.getSelectedItem()+"-results.GunTime) ASC " +
			"LIMIT "+searchLimit.getText();
		}
		else{
			return "SELECT (st2.time_milli-st1.time_milli) AS DisplayTime, age,bib,sex,first_name AS 'First Name',last_name AS 'Last Name' "+
			"FROM runner, split_time st1, split_time st2 " +
			"WHERE runner.race_id="+resultsSiteRace+" AND st2.runner_id=runner.id AND st1.id=runner.gun_start_id " +
			"AND st2.id IN (SELECT split_time.id " +
			"FROM split_time, split " +
			"WHERE split_time.split_id=split.id AND split.race_id="+resultsSiteRace+" AND split.title='"+eventList.getSelectedItem()+"')"+
			" AND st2.time_milli-st1.time_milli>0 "+
			"AND sex LIKE '"+sexList.getSelectedItem()+"' " +
			"AND age >="+minAge.getText()+" AND age <="+maxAge.getText()+" "+
			"ORDER BY (st2.time_milli-st1.time_milli) ASC " +
			"LIMIT "+searchLimit.getText();
		}
	}

	public void actionPerformed(ActionEvent ae) {
		try{
			GenericModel m;
			if(screenList.getSelectedItem().equals("Announce")){
				m = new LastRunnerListModel(generateSql(), title.getText(), Integer.valueOf(refreshRate.getText()), conn.createStatement());
			}
			else{
				m = new RunnerListModel(generateSql(), title.getText(), Integer.valueOf(refreshRate.getText()), conn.createStatement());
			}
			if(ae.getSource()==previewButton){
				newPreviewModel(m);
			}
			else if(ae.getSource()==liveButton){
				newLiveModel(m);
			}
		}
		catch(SQLException sql){
			sql.printStackTrace();
		}
	}

	public void run(){}

}
