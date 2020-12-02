package com.mtecresults.videopresenter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class LastRunnerListView extends RunnerListView {

	private static final long serialVersionUID = 1L;

	LastRunnerListView(RunnerListModel model){
		super(model);
	}
	
	public void paint(Graphics g) {	
		Runner[] runnerList = model.getRunners();
		Graphics2D g2d = (Graphics2D)g;
		int maxWidth = this.getWidth();
		int maxHeight = this.getHeight();
		if(g.getClipBounds()!=null){
			maxWidth = g.getClipBounds().width;
			maxHeight = g.getClipBounds().height;
			System.out.println("Height: "+maxHeight+" Width: "+maxWidth);
		}
		int fontSize = model.getFontSize();
		double scaleFactor = 1;
		if(maxWidth<6410 && maxHeight<481){
			scaleFactor = .6;
		}
		
		g.setColor(Color.BLACK);
		g.fillRect(0,0,maxWidth,maxHeight+30);
		g.setColor(Color.WHITE);
		g2d.setFont(new Font("Monospaced", Font.BOLD, (int)(fontSize*3/2*scaleFactor)));
		g2d.drawString(model.getTitle(), 20, (int)(50*scaleFactor));
		g2d.setFont(new Font( "Monospaced", Font.BOLD, (int)(fontSize*scaleFactor)));
		g2d.drawString("   Name                 Time", 10, (int)(120*scaleFactor));
		int i=model.getNumberToStartAt();
		while(runnerList != null && i<runnerList.length && i<(model.getNumberToDisplay()+model.getNumberToStartAt()) && runnerList[i] != null){			
			g2d.drawString(((runnerList[i].toStringNoBehind())+"                                                           ").substring(0,40), 10, (int)(scaleFactor*(maxHeight/(model.getNumberToDisplay()*3/2)*(i-model.getNumberToStartAt()) + 200)));
			System.out.println("now displaying: "+i+" "+runnerList[i]);
			i++;
		}
	}
	
}
