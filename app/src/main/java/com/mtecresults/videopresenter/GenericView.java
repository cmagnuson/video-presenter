package com.mtecresults.videopresenter;

import java.awt.*;
import javax.swing.*;

public abstract class GenericView extends JComponent {

	private static final long serialVersionUID = -4911648061167666468L;
	abstract void update();
	public abstract void paint(Graphics g);
	
}
