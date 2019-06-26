package test_tomcat_git;

import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnectUpdate extends DataBaseConnectRead
{
	int[] userinfo;//ルームid,プレイヤー番号
	DBCbeforeUpdate DBCB = new DBCbeforeUpdate();

	int[] update(String username)//受け渡されたusernameをデータベースへインサートする
	{
		/*url = "jdbc:mysql://localhost:3306/u22?characterEncoding=UTF-8&serverTimezone=JST";
		user = "root";
		password = "ncc_NCC2019";*/

		userinfo=DBCB.beforeupdate();//空いているルームと攻守を検索

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
			conn = connect();

			//SQL
			Statement stmt3 = conn.createStatement();
			Statement stmt4 = conn.createStatement();
			stmt3.executeUpdate("UPDATE user SET user_name = '"+username+"' WHERE user_id = "+userinfo[0]+";");
			stmt4.executeUpdate("UPDATE room SET user_id = "+userinfo[0]+" WHERE room_id = "+userinfo[1]+" AND player_number = "+userinfo[2]+";");
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

	void reset()
	{

	}

}
