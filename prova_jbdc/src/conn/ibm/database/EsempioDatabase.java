package conn.ibm.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.jdbc.MysqlDataSource;

public class EsempioDatabase {
	
	//una connessione con un database specifico
	
	private Connection con;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EsempioDatabase esempio = new EsempioDatabase();
		try {
			//System.out.println(esempio.startConnection().isValid(100));
			//System.out.println(esempio.startConnection().isClosed());
			//esempio.getInfo();
			//esempio.insertInfo("Lorenzo", "Taverna", "lorenzo@icloud.com", "3232324545");
			//esempio.deleteInfo();
			esempio.updateInfo();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Connection startConnection() throws SQLException {
		
		if(con==null) {
			
			MysqlDataSource dataSource= new MysqlDataSource();
			dataSource.setServerName("127.0.0.1");
			dataSource.setPortNumber(3306);
			dataSource.setUser("root");
			dataSource.setPassword("admin");
			dataSource.setDatabaseName("corso_java");
			
			con = dataSource.getConnection();
		}
		
		return con;
	}

	private void getInfo() throws SQLException {
		
		String sql= "SELECT id,nome,cognome,email,telefono FROM clienti";
		
		PreparedStatement ps = startConnection().prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			
			System.out.println("id: " + rs.getInt(1));
			System.out.println("nome: " + rs.getString(2));
			System.out.println("cognome: " + rs.getString(3));
			System.out.println("email: " + rs.getString(4));
			System.out.println("telefono: " + rs.getString(5));
			System.out.println();
			System.out.println("-------------------------------");
			System.out.println();

		}
		
		rs.close();
		ps.close();
	}
	
	private void insertInfo(String nome, String cognome, String email, String telefono) throws SQLException {
		
		String sql = "INSERT INTO clienti (nome, cognome, email, telefono) VALUES ('"+nome+"','"+cognome+"','"+email+"','"+telefono+"')";
		
		PreparedStatement ps = startConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		
		ps.executeUpdate();
		
		ResultSet rs = ps.getGeneratedKeys();
		
		rs.next();
		System.out.println("id: " + rs.getInt(1));
		
		rs.close();
		ps.close();

	}
	
	private void deleteInfo() throws SQLException {
		
		String sql = "DELETE FROM clienti WHERE id=1";
		
		PreparedStatement ps = startConnection().prepareStatement(sql);
		
		ps.executeUpdate();
		
		ps.close();

	}
	
	//si puo fare update
	
	private void updateInfo() throws SQLException {

		String sql = "UPDATE clienti SET telefono=? WHERE id=?";
		
		PreparedStatement ps = startConnection().prepareStatement(sql);
		
		ps.setString(1, "3245454789");
		ps.setInt(2, 2);
		
		ps.executeUpdate();
		ps.close();

	}
	
}
