package test_tomcat_git;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBasePlayerout extends DataBaseConnectUpdate //プレイヤーが退出した際にデータベース上の情報を更新する
{
	Roomdelete rd = new Roomdelete();
	CreateConnection cc = new CreateConnection();
	SQLRepository sr = new SQLRepository();

	PreparedStatement logout_user,logout_room,room_check,reserve_cancel;

	void logout(int[] playerinfo)//ユーザID,ルームID,プレイヤー番号の順番で格納
	{
		System.out.println("logout通ったよ");

		logout_user =cc.createpStatement(cc.createconnection(),sr.UpdateLogoutUser());
		logout_room =cc.createpStatement(cc.createconnection(),sr.UpdateLogoutRoom());

		try
		{
			/*
			Statement stmt = cc.createstatement(conn = cc.createconnection());
			stmt.executeUpdate("UPDATE user SET user_name = NULL WHERE user_id = "+playerinfo[0]+";");
			stmt.executeUpdate("UPDATE room SET user_id = 0 WHERE user_id = "+playerinfo[0]+";");
			*/

			logout_user.setInt(1,playerinfo[0]);
			logout_user.executeUpdate();

			logout_room.setInt(1,playerinfo[0]);
			logout_room.executeUpdate();

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			cc.close();
			/*
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
			*/
		}

		noplayer(playerinfo[1]);//テスト
	}

	void noplayer(int room_id)
	{
		System.out.println("noplayer入ったお");
		Result = new int[2];
		for(int i = 0;i<Result.length;i++)
		{
			Result[i] = 0;
		}

		room_check = cc.createpStatement(cc.createconnection(),sr.SelectRoomUser());
		reserve_cancel = cc.createpStatement(cc.createconnection(),sr.UpdateLogoutReserve());

		try
		{
			/*
			Statement stmt = cc.createstatement(conn = cc.createconnection());
			rs = stmt.executeQuery("SELECT * FROM room WHERE room_id = "+room_id+";");
			*/

			room_check.setInt(1,room_id);
			rs = room_check.executeQuery();

			for(int i = 0;rs.next();i++)
			{
				Result[i] = rs.getInt("user_id");
			}

			for(int i=0;i<Result.length;i++)
			{
				int w=i;
				if(Result[i] <0)
				{
					reserve_cancel.setInt(1,room_id);
					reserve_cancel.setInt(2,++w);
					reserve_cancel.executeUpdate();
				}
			}
		}
		catch(SQLException e)
		{
			System.out.println(e);
			System.out.println("DatabasePlayeroutのnoplayerで実行されてるSQLがおかしいよ");
		}
		finally
		{
			cc.close();
			/*
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
			*/
		}

		if(Result[0]==0&&Result[1]==0)
		{
			rd.delete(room_id);
		}
	}
}
