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

		Statement stmt = CC.createstatement(conn = CC.createconnection());

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

			rs = stmt.executeQuery("SELECT * FROM user WHERE user_name is null ORDER BY user_id LIMIT 1;");
			//結果の挿入
			rs.next();
			Result[0] = rs.getInt("user_id");
			rs.close();

			rs = stmt.executeQuery("SELECT * FROM room WHERE user_id = 0 ORDER BY room_id LIMIT 1;");
			//結果の挿入
			rs.next();
			Result[1] = rs.getInt("room_id");
			Result[2] = rs.getInt("player_number");
			rs.close();

			System.out.println("DBCbeforeUpdate上での値だお");
			System.out.println("user_id:"+Result[0]);
			System.out.println("room_id:"+Result[1]);
			System.out.println("player_number:"+Result[2]);


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
