package com.mtecresults.videopresenter;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class RunnerListController implements ActionListener {

	GenericModel model = null;
	public JFrame window;
	private static final int WIDTH = 170;
	private static final int HEIGHT = 25;
	private JButton play = null;
	private JButton forward = null;
	private JButton backward = null;
	
	public RunnerListController(boolean right){
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gs = ge.getDefaultScreenDevice();

		window = new JFrame(gs.getDefaultConfiguration());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(WIDTH, HEIGHT);
		if(right){
			window.setLocation(gs.getDefaultConfiguration().getBounds().width-WIDTH, 
				gs.getDefaultConfiguration().getBounds().height-HEIGHT);
		}
		else{
			window.setLocation(0, gs.getDefaultConfiguration().getBounds().height-HEIGHT);
		}
		
		ImageIcon playIcon = new ImageIcon(this.getClass().getResource("/play.png"));
		ImageIcon ffIcon = new ImageIcon(this.getClass().getResource("/ff.png"));
		ImageIcon rewIcon = new ImageIcon(this.getClass().getResource("/rew.png"));
		play = new JButton(playIcon);
		forward = new JButton(ffIcon);
		backward = new JButton(rewIcon);
		play.setSize(30, 30);
		forward.setSize(30,30);
		backward.setSize(30,30);
		play.addActionListener(this);
		forward.addActionListener(this);
		backward.addActionListener(this);
		
		window.setLayout(new GridLayout(1,3));
		window.add(backward);
		window.add(play);
		window.add(forward);
		
		window.setAlwaysOnTop(true);
		window.setUndecorated(true);
		window.setResizable(false);
		window.setVisible(true);
	}
	
	public void setModel(GenericModel model){
		this.model = model;
	}
	
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==play){
			model.toggleScrolling();
		}
		if(ae.getSource()==forward){
			model.forward();
		}
		if(ae.getSource()==backward){
			model.backward();
		}
	}
	
	
}
