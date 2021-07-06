package com.fatec.lab04;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;
import oracle.jdbc.driver.OracleDriver;

public class App 
{
    public static void main( String[] args ) {
        hardcoded();
        softcoded();
    }
    
    public static Connection getConnection() throws SQLException{
        try {
           Class.forName ("oracle.jdbc.OracleDriver");
       } catch (ClassNotFoundException e) {
           e.printStackTrace();
       }
        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:fatec","system","fatec");
        System.out.println("Connection Success");
        return con;  
    }

    
	public static void hardcoded() {
        System.out.println("---- Inicio execucao (Hardcoded) ----");

		Long tempoInicio = System.currentTimeMillis();
		
        try {
            Connection con = getConnection(); 
            Statement statement = con.createStatement();
            String query; 
            ResultSet result;
            Long cont;
            String data_hora;

            for (cont = (long) 1 ; cont <= (long) 100000 ; cont++) { 
                query = "select data_hora from lab04_parse where id=" + Long.toString(cont) ; 
                result = statement.executeQuery(query) ;
                result.close() ;
            }

            statement.close() ;

        } catch (SQLException e) {
            e.printStackTrace();
        }
		
		Long tempoFim = System.currentTimeMillis() ;
		Long tempoExec = tempoFim - tempoInicio;
		
		String tempoTotal = String.format("%d min, %d seg", 
			    TimeUnit.MILLISECONDS.toMinutes(tempoExec),
			    TimeUnit.MILLISECONDS.toSeconds(tempoExec) - 
			    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(tempoExec))
			);

		System.out.println("Tempo execucao (Hardcoded): " + tempoTotal);
		
	}
	
	public static void softcoded() {
        System.out.println("---- Inicio execucao (Softcoded) ----");

		Long tempoInicio = System.currentTimeMillis();

		try {
			Connection con = getConnection(); 
			PreparedStatement statement = con.prepareStatement("select data_hora from lab04_parse where id = ?") ;
			ResultSet result;
			
			Long cont; 
			for(cont = (long) 1 ; cont <= (long) 100000 ; cont++) { 
				statement.setLong(1, cont);
				result = statement.executeQuery();
				result.close();
			}
			statement.close() ;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Long tempoFim = System.currentTimeMillis() ;
		Long tempoExec = tempoFim - tempoInicio;
		
		String tempoTotal = String.format("%d min, %d seg", 
			    TimeUnit.MILLISECONDS.toMinutes(tempoExec),
			    TimeUnit.MILLISECONDS.toSeconds(tempoExec) - 
			    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(tempoExec))
			);

		System.out.println("Tempo execucao (Softcoded): " +tempoTotal);
	}
}
