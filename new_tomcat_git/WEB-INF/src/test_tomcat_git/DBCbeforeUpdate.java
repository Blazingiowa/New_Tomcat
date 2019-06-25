package test_tomcat_git;

import java.sql.SQLException;
import java.sql.Statement;

public class DBCbeforeUpdate extends DataBaseConnectRead
{
	int[] beforeupdate()
	{
		Result = new int[3];
		for(int i = 0;i < Result.length;i++)
		{
			Result[i] = 0;
		}

		conn = connect();

		/*try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (ClassNotFoundException e1)
		{

			e1.printStackTrace();
		}*/

		try
		{
			/*conn = DriverManager.getConnection(url,user,password);
			DriverManager.setLoginTimeout(timeoutseconds);*/

			//SQL
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM user WHERE user_name = null ORDER BY user_id LIMIT 1;");
			//結果の挿入
			Result[0] = rs.getInt("user_id");

			rs = stmt.executeQuery("SELECT * FROM room WHERE user_id = 0 ORDER BY room_id LIMIT 1;");
			//結果の挿入
			Result[1] = rs.getInt("room_id");
			Result[2] = rs.getInt("player_number");


		}
		catch(SQLException e)
		{
			System.out.println(e);
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

		return Result;

	}
}
