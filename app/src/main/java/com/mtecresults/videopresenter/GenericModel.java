package com.mtecresults.videopresenter;

public interface GenericModel {

	public void removeAllViews();
	public GenericView getNewView();
	public void backward();
	public void forward();
	public void toggleScrolling();
}
