package davide.cruciata.test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.jdbc.MysqlDataSource;

public class DatabaseTest {
	
	private Connection conn;

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		String url = "jdbc:mysql://localhost:3306";
		String user = "root";
		String password = "admin";
		
		DatabaseTest db = new DatabaseTest();
		
        db.startConnection(url, user, password,null);
        db.createDatabase(db.startConnection(url, user, password,null),"proviamo");
        
        //db.useDbTest("dbTest");
        
        String nomeTabella = "Utente";
        List<String> nomeColonne = new ArrayList<>(); 
        List<String> nomeTipiOpzioni = new ArrayList<>(); 
        
        nomeColonne.add("id");
        nomeColonne.add("cognome");
        nomeColonne.add("nome");
        
        nomeTipiOpzioni.add(" int PRIMARY KEY NOT NULL, ");
        nomeTipiOpzioni.add(" VARCHAR(255) NOT NULL, ");
        nomeTipiOpzioni.add(" VARCHAR(255) NOT NULL ");
        
        //db.createTableUtente(nomeTabella,nomeColonne,nomeTipiOpzioni);
        
        //db.useDbTest("dbTest");
        
        //nota bene nel mio db ho scambiato i nomi per i cognomi e ho fatto gli inserimenti male pero le query funzionano facendo attenzione a questo particolare
        
        //db.query1("Marco");
        //db.query2();
        //db.query3();
        //db.query4("Marzia");
        //db.query5();
        //db.query6();

	}	

	private void createDatabase(Connection conn, String dbTest) throws SQLException {
		// TODO Auto-generated method stub
		
		String url = "jdbc:mysql://localhost:3306";
		String user = "root";
		String password = "admin";
		
		String sql = "CREATE DATABASE IF NOT EXISTS " + dbTest + ";";
		
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.executeUpdate();
        System.out.println("Database " + dbTest + " creato con successo o esiste già.");
	
	}

	private Connection startConnection(String url, String user, String password, String nameDB) throws SQLException {
		
		if(conn==null) {
			
			MysqlDataSource dataSource= new MysqlDataSource();
			
			dataSource.setURL(url);
			dataSource.setUser(user);
			dataSource.setPassword(password);
			dataSource.setDatabaseName(nameDB);
			
			conn = dataSource.getConnection();
		}
		
		return conn;
	}
	
	private void useDbTest(String dbTest) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "USE " + dbTest + ";";
		
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
            System.out.println("Stai usando " + dbTest );
        }
				
	}

	private void createTableUtente(String nomeTabella, List<String> nomeColonne, List<String> nomeTipiOpzioni) throws SQLException {

		String sql =  "CREATE TABLE IF NOT EXISTS "+ nomeTabella + "(";
		
		
		for (int i=0;i<nomeColonne.size();i++) {
			sql = sql.concat(" ").concat(nomeColonne.get(i)).concat(nomeTipiOpzioni.get(i));
		}
		
		sql = sql.concat(");");
		
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
            System.out.println("Tabella creata correttamente" );
        }
	}
	
	private void query6() throws SQLException {
		// TODO Auto-generated method stub
		String sql = "SELECT utente.nome, utente.cognome, libro.titolo, prestito.data_inizio, prestito.data_fine\r\n"
				+ "FROM prestito\r\n"
				+ "JOIN utente ON prestito.id_u = utente.id\r\n"
				+ "JOIN libro ON prestito.id_l = libro.id\r\n"
				+ "WHERE DATEDIFF(prestito.data_fine, prestito.data_inizio) > 15;\r\n";
		
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				
				System.out.println("utente: " + rs.getString(1));
				System.out.println("libro: " + rs.getString(3));
				System.out.println("data inizio: " + rs.getDate(4));
				System.out.println("data fine: " + rs.getDate(5));
			}
        }
	}

	private void query5() throws SQLException {
		// TODO Auto-generated method stub
		String sql = "SELECT libro.titolo, libro.autore, COUNT(prestito.id) AS numero_prestiti\r\n"
				+ "FROM prestito\r\n"
				+ "JOIN libro ON prestito.id_l = libro.id\r\n"
				+ "GROUP BY libro.id\r\n"
				+ "ORDER BY numero_prestiti DESC;\r\n";
		
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				
				System.out.println("titolo: " + rs.getString(1));
				System.out.println("numero prestiti: " + rs.getInt(3));
				
				
			}
        }
	}

	private void query4(String utente) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "SELECT libro.titolo, libro.autore, prestito.data_inizio, prestito.data_fine\r\n"
				+ "FROM prestito\r\n"
				+ "JOIN utente ON prestito.id_u = utente.id\r\n"
				+ "JOIN libro ON prestito.id_l = libro.id\r\n"
				+ "WHERE utente.cognome = ?\r\n"
				+ "ORDER BY prestito.data_inizio;";
		
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			
			ps.setString(1, utente);
            
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				
				System.out.println("titolo: " + rs.getString(1));
				System.out.println("data inizio: " + rs.getDate(3));
				System.out.println("data fine: " + rs.getDate(4));
				
			}
        }
		
	}

	private void query3() throws SQLException {
		// TODO Auto-generated method stub
		
		String sql = "SELECT utente.nome, utente.cognome, libro.titolo\r\n"
				+ "FROM prestito\r\n"
				+ "JOIN utente ON prestito.id_u = utente.id\r\n"
				+ "JOIN libro ON prestito.id_l = libro.id\r\n"
				+ "WHERE prestito.data_fine IS NULL OR prestito.data_fine > CURRENT_DATE;\r\n";
		
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				
				System.out.println("utente: " + rs.getString(1));
				System.out.println("libro: " + rs.getString(3));
				
			}
        }
		
	}

	private void query2() throws SQLException {
		// TODO Auto-generated method stub
		String sql = "SELECT utente.nome, utente.cognome, COUNT(prestito.id) AS numero_libri_letti\r\n"
				+ "FROM prestito\r\n"
				+ "JOIN utente ON prestito.id_u = utente.id\r\n"
				+ "GROUP BY utente.id, utente.nome, utente.cognome\r\n"
				+ "ORDER BY numero_libri_letti DESC\r\n"
				+ "LIMIT 3;";
		
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				
				System.out.println("utente: " + rs.getString(1));
				
			}
        }
		
	}

	private void query1(String string) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "SELECT libro.titolo, libro.autore, prestito.data_inizio, prestito.data_fine "
				+ "FROM prestito "
				+ "JOIN utente ON prestito.id_u = utente.id "
				+ "JOIN libro ON prestito.id_l = libro.id "
				+ "WHERE utente.cognome = ? "
				+ "ORDER BY prestito.data_inizio;";
		
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			
			ps.setString(1, string);
            
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				
				System.out.println("titolo libro: " + rs.getString(1));
				System.out.println("autore libro: " + rs.getString(2));
				System.out.println("data inizio: " + rs.getDate(3));
				System.out.println("data fine: " + rs.getDate(4));
				
			}
        }
	}
}
