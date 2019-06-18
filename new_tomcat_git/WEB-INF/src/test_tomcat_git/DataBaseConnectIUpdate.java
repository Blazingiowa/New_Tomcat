package test_tomcat_git;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnectIUpdate extends DataBaseConnectRead
{
	int[] userinfo;//ルームid,プレイヤー番号

	int[] update(String username)//受け渡されたusernameをデータベースへインサートする
	{
		url = "jdbc:mysql://localhost/u22?characterEncoding=UTF-8&serverTimezone=JST";
		user = "root";
		password = "yasutaka13";

		userinfo=beforeupdate();//空いているルームと攻守を検索

		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (ClassNotFoundException e1)
		{

			e1.printStackTrace();
		}

		try
		{
			conn = DriverManager.getConnection(url,user,password);

			//SQL
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("UPADATE user SET user_name = "+username+" WHERE user_id = "+userinfo[0]+";");
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

		return userinfo;


	}

}
