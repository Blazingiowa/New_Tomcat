package test_tomcat_git;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnectIUpdate extends DataBaseConnectRead
{
	
	
	int[] update(String username)//受け渡されたusernameをデータベースへインサートする
	{
		url = "jdbc:mysql://localhost/u22?characterEncoding=UTF-8&serverTimezone=JST";
		user = "root";
		password = "yasutaka13";
		
		reference(0,-1);//空いているルームと攻守を検索

		int[] userinfo = new int[3];
		try
		{
			conn = DriverManager.getConnection(url,user,password);

			//SQL
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
