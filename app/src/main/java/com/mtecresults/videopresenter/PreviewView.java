package com.mtecresults.videopresenter;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.*;

public class PreviewView {

	public JFrame window;
	private JComponent view;

	public PreviewView(String name, boolean right) {

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gs = ge.getDefaultScreenDevice();

		window = new JFrame(name, gs.getDefaultConfiguration());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(480, 360);
		if(right){
			window.setLocation(gs.getDefaultConfiguration().getBounds().width-480, 
				gs.getDefaultConfiguration().getBounds().height-360);
		}
		else{
			window.setLocation(0,gs.getDefaultConfiguration().getBounds().height-360);
		}
		window.setAlwaysOnTop(true);
		//window.setUndecorated(true);
		window.setResizable(false);
		window.setVisible(true);
	}

	public void setView(JComponent j){
		if(view!=null){
			window.getContentPane().remove(view);
		}
		view = j;
		window.getContentPane().add(view);
		window.validate();
		window.repaint();
	}
}
