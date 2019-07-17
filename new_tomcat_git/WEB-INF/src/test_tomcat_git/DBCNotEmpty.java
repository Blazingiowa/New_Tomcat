package test_tomcat_git;

import java.sql.SQLException;
import java.sql.Statement;

public class DBCNotEmpty extends DataBaseConnectRead
{

	int Result;
	int userIDNotempty(String sql)
	{
		Statement stmt = CC.createstatement(conn = CC.createconnection());

		try
		{
			rs = stmt.executeQuery("SELECT MAX(user_id) FROM user");
			//結果の挿入
			rs.next();
			Result = rs.getInt("user_id");
			rs.close();

			Result++;
			stmt.executeUpdate("INSERT INTO user VALUES("+Result+",NULL) ");

		}
		catch(SQLException e)
		{

		}

		return Result;
	}

	int RoomNotempty(String sql)
	{


		return 1;
	}
}
