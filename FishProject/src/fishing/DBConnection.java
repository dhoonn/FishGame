package fishing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	public static Connection makeConnection() {
		// Connection 타입의 변수 con 선언
		Connection con = null;
		String user = "JDBC0731"; // 접속할 DB 계정
		String password = "1234"; // 접속할 계정의 비번
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(url, user, password);
			System.out.println("DB 연결 성공");
			
		} catch (ClassNotFoundException e) {
			System.out.println("DB 드라이버 로딩 실패 "+e.toString());
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("DB 접속 실패 "+e.toString());
			e.printStackTrace();
		}
		
  	    return con;
}

}
