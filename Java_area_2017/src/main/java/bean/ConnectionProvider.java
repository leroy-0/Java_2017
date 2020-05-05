package bean;
import java.sql.*;
import static bean.Provider.*;

public class ConnectionProvider {
	static Connection con=null;
	static{
		try{
			Class.forName(DRIVER);
			con=DriverManager.getConnection(URL_DATABASE);
			}catch(Exception e){}
	}
	public static Connection getCon(){
		return con;
	}
}
