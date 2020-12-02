package com.mtecresults.videopresenter;

import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class InitializeDB {
	
	public static Connection run(String dbFilename){
		Connection conn = connectToAccessDatabase(dbFilename);
		return conn;
	}
    
    static public Connection connectToAccessDatabase(String file){
		String driver="sun.jdbc.odbc.JdbcOdbcDriver";
		String lConnectStr = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=" + 
		file + 
		";DriverID=22;READONLY=false";
		try {
			Class.forName(driver);
			return DriverManager.getConnection(lConnectStr);
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("DB COULD NOT BE FOUND!");
		System.exit(0);
		return null;
	}

    public static Connection run(String dbLocation, String userName, String password){
        Connection conn = connectToMySqlDatabase(dbLocation, userName, password);
        return conn;
    }
    
    static public Connection connectToMySqlDatabase(String dbLocation, String userName, String password){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://"+dbLocation;
            Connection con = DriverManager.getConnection(url, userName, password);
            if(con.getWarnings()!=null){System.out.println(con.getWarnings());}
            return con;
        }
        catch( Exception e ) {
            e.printStackTrace();
            System.out.println("DB COULD NOT BE FOUND!");
            //System.exit(0);
            return null;
        }
    }
    
	public static Connection run(JFrame frame){
//		Object[] options = {"Access","MySQL"};
//		int n = JOptionPane.showOptionDialog(frame, "Select DB Type: ","DB-Selector",JOptionPane.YES_NO_OPTION,
//				JOptionPane.QUESTION_MESSAGE,null,options,options[1]);
//		if(n==0){
//	        JFileChooser fc = new JFileChooser("C:\\shared\\");
//	        fc.showOpenDialog(frame);
//	        return run(fc.getSelectedFile().getAbsolutePath());
//		}
//		if(n==1){
	        String dbAddress = JOptionPane.showInputDialog(frame,"Enter an ip address for the db: ","localhost");
	        String dbName = JOptionPane.showInputDialog(frame, "Enter the db name: ","");
	        String dbUser = JOptionPane.showInputDialog(frame,"Enter a User Name for the db: ","");
	        String dbPass = JOptionPane.showInputDialog(frame,"Enter a Password: ","");
	        return run(dbAddress+"/"+dbName, dbUser, dbPass);
	//	}
//		JOptionPane.showMessageDialog(frame, "Not A Valid Choice!");
//		System.out.println("INVALID DB CHOICE MADE");
//		System.exit(-1);
//		return null;
	}

}
