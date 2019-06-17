package test_tomcat_git;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnectIUpdate extends DataBaseConnectRead
{
	int[] playerdata;//ユーザーid,ルームid,プレイヤー番号

	int[] update(String username)//受け渡されたusernameをデータベースへインサートする
	{
		url = "jdbc:mysql://localhost/u22?characterEncoding=UTF-8&serverTimezone=JST";
		user = "root";
		password = "yasutaka13";

		playerdata=reference(0,-1);//空いているルームと攻守を検索

		int[] userinfo = new int[3];
		try
		{
			conn = DriverManager.getConnection(url,user,password);

			//SQL
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("");
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
