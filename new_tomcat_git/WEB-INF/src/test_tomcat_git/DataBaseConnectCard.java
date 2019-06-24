package test_tomcat_git;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnectCard extends DataBaseConnectRead
{
	ResultSet cardinfo()
	{
		connect();
		try
		{
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM card");
		}
		catch(SQLException e)
		{

		}
		finally
		{
			try
			{
				if (conn != null)
				{
					conn.close();
				}
			}
			catch(SQLException e)
			{
				System.out.println(e);
				//例外処理
			}
		}

		return rs;
	}
}
