package com.mtecresults.videopresenter;

import java.awt.*;

public class RunnerListView extends GenericView {
	
	private static final long serialVersionUID = 1L;

	protected RunnerListModel model;

	RunnerListView(RunnerListModel model){
		this.model = model;
		model.addView(this);
	}
	
	void update() {
		model.update();
		this.repaint();
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
		g2d.drawString("   Name                 Time   Behind", 10, (int)(120*scaleFactor));
		int i=model.getNumberToStartAt();
		while(runnerList != null && i<runnerList.length && i<(model.getNumberToDisplay()+model.getNumberToStartAt()) && runnerList[i] != null){			
			g2d.drawString(((runnerList[i].toString())+"                                                           ").substring(0,40), 10, (int)(scaleFactor*(maxHeight/(model.getNumberToDisplay()*3/2)*(i-model.getNumberToStartAt()) + 200)));
			System.out.println("now displaying: "+i+" "+runnerList[i]);
			i++;
		}
	}
}

