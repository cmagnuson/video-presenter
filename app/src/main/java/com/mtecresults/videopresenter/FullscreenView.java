package com.mtecresults.videopresenter;

import java.awt.*;
import javax.swing.*;

public class FullscreenView {

	public JFrame window;
	private JComponent view;

	public FullscreenView() {

		// Create a frame
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gs = ge.getDefaultScreenDevice();
		if(ge.getScreenDevices().length<2){
			System.err.println("Only One Screen Connected!");
			gs = ge.getScreenDevices()[0];
		}
		else{
			gs = ge.getScreenDevices()[1];
		}

		window = new JFrame(gs.getDefaultConfiguration());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(gs.getDefaultConfiguration().getBounds().width, gs.getDefaultConfiguration().getBounds().height);
		window.setAlwaysOnTop(true);
		window.setUndecorated(true);
		//gs.setFullScreenWindow(window);
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
