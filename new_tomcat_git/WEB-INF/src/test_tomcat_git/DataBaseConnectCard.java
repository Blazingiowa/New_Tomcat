package test_tomcat_git;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnectCard extends DataBaseConnectRead
{
	ResultSet cardinfo()
	{
		Statement stmt = CC.createstatement(conn = CC.createconnection());

		try
		{

			rs = stmt.executeQuery("SELECT * FROM card");
		}
		catch(SQLException e)
		{

		}
		finally
		{
			CC.close();
			try
			{
				rs.close();
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
