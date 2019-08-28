package test_tomcat_git;

import java.sql.SQLException;
import java.sql.Statement;

public class DataBasePlayerout extends DataBaseConnectUpdate //プレイヤーが退出した際にデータベース上の情報を更新する
{
	Roomdelete rd = new Roomdelete();
	void logout(int[] playerinfo)//ユーザID,ルームID,プレイヤー番号の順番で格納
	{
		try
		{
			Statement stmt = CC.createstatement(conn = CC.createconnection());
			stmt.executeUpdate("UPDATE user SET user_name = NULL WHERE user_id = "+playerinfo[0]+";");
			stmt.executeUpdate("UPDATE room SET user_id = 0 WHERE user_id = "+playerinfo[0]+";");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (conn != null)
				{
					conn.close();//データベースとの接続を解除
				}
			}
			catch(SQLException e)
			{
				System.out.println(e);
				//例外処理

			}
		}

		noplayer(playerinfo[1]);//テスト
	}

	void noplayer(int room_id)
	{
		Result = new int[2];
		for(int i = 0;i<Result.length;i++)
		{
			Result[i] = 0;
		}
		try
		{
			Statement stmt = CC.createstatement(conn = CC.createconnection());
			rs = stmt.executeQuery("SELECT * FROM room WHERE room_id = "+room_id+";");

			for(int i = 0;rs.next();i++)
			{
				Result[i] = rs.getInt("user_id");
			}
		}
		catch(SQLException e)
		{
			System.out.println(e);
			System.out.println("DatabasePlayeroutのnoplayerで実行されてるSQLがおかしいよ");
		}
		finally
		{
			try
			{
				if (conn != null)
				{
					conn.close();//データベースとの接続を解除
				}
				rs.close();
			}
			catch(SQLException e)
			{
				System.out.println(e);
				//例外処理
			}
		}

		if(Result[0]==0&&Result[1]==0)
		{
			rd.delete(room_id);
		}
	}
}
