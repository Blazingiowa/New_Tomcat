package test_tomcat_git;

import java.sql.SQLException;
import java.sql.Statement;

public class DBCbeforeUpdate extends DataBaseConnectRead//空いている部屋と空いているユーザーIDを検索する
{
	DBCNotEmpty DBCNE = new DBCNotEmpty();

	int[] beforeupdate(String sql[])
	{
		Result = new int[3];
		/*
		String[] sql = new String[2];//実行するsqlを配列に格納する
		sql[0] = "SELECT * FROM user WHERE user_name is null ORDER BY user_id LIMIT 1;" ;
		sql[1] = "SELECT * FROM room WHERE user_id = 0 ORDER BY room_id LIMIT 1;";


		 if(reserve ==1 )//ロビーを使用した際にsql文を置き換える
		 {
		 	sql[1] = "SELECT * FROM room WHERE user_id = 0 AND player_number = 1 ORDER BY room_id LIMIT 1;";
		 }
		 */

		 //配列を初期化
		for(int i = 0;i < Result.length;i++)
		{
			Result[i] = 0;
		}

		Statement stmt = CC.createstatement(conn = CC.createconnection());

		try
		{
			rs = stmt.executeQuery(sql[0]);//空いているユーザーIDの検索

			if(rs.next())
			{
				Result[0] = rs.getInt("user_id");
				rs.close();
				//System.out.println("空きあったよ");
			}
			else
			{
				System.out.println("空きなかったよ(´・ω・｀)");
				Result[0] = DBCNE.userIDNotempty();
			}

			rs = stmt.executeQuery(sql[1]);//空いているルームの検索
			//結果の挿入

			if(rs.next())
			{
				Result[1] = rs.getInt("room_id");
				Result[2] = rs.getInt("player_number");
				rs.close();
			}
			else
			{
				int[] keep = DBCNE.RoomNotempty(sql[1]);
				Result[1] = keep[0];
				Result[2] = keep[1];
			}


			/*System.out.println("DBCbeforeUpdate上での値だお");
			System.out.println("user_id:"+Result[0]);
			System.out.println("room_id:"+Result[1]);
			System.out.println("player_number:"+Result[2]);*/

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
