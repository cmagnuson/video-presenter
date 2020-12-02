package com.mtecresults.videopresenter;

public class Runner {
	private String firstName;
	private String lastName;
	private String finishTime;
	private String behindFirstFinisher;
	private String sex;
	private String age;
	private String place;
	private String bib;
	
	Runner(){
		firstName="";
		lastName="";
		finishTime="";
		sex="";
		age="";
		bib="";
	}

	public String toString(){
		if(bib != null && !bib.equals(""))
			return ((place+"   ").substring(0,3)+(firstName +" "+(lastName+"                      ")).substring(0,20)+" "+(finishTime +"                 ").substring(0, 8)+ behindFirstFinisher);
		return "";
	}
	
	public String toStringNoBehind(){
		if(bib != null && !bib.equals(""))
			return ((firstName +" "+(lastName+"                           ")).substring(0,25)+" "+(finishTime +"                 ").substring(0, 8));
		return "";
	}
	
	public boolean equals(Runner r){
		return this.bib.equals(r.getBib());
	}

	public String getBib() {
		return bib;
	}

	public void setBib(String bib) {
		this.bib = bib;
	}
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		if(finishTime!=null){
			this.finishTime = finishTime;
		}
	}

	public void setFinishTime(long finishTime){
		this.finishTime = FormatTime.millisecondsToFormatted(finishTime);
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBehindFirstFinisher() {
		return behindFirstFinisher;
	}

	public void setBehindFirstFinisher(String behindFirstFinisher) {
		this.behindFirstFinisher = behindFirstFinisher;
	}
	
	public void setBehindFirstFinisher(long timeBeforeFirst){
		this.behindFirstFinisher = FormatTime.millisecondsToFormatted(timeBeforeFirst);
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	
}
