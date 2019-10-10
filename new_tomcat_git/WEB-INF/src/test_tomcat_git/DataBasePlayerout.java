package test_tomcat_git;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBasePlayerout extends DataBaseConnectUpdate //プレイヤーが退出した際にデータベース上の情報を更新する
{
	Roomdelete rd;
	CreateStatement cs;
	ResultSet rs;
	int [] userid;
	final int useridnum = 2,logout = 3,no_user = 0;
	PreparedStatement[] pstmts;

	DataBasePlayerout()
	{
		super();
		rd = new Roomdelete();
		cs = new CreateStatement();
		userid = new int[useridnum];
	}

	void logout(int[] playerinfo)//ユーザID,ルームID,プレイヤー番号の順番で格納
	{
		try
		{
			/*
			Statement stmt = CC.createstatement(conn = CC.createconnection());
			stmt.executeUpdate("UPDATE user SET user_name = NULL WHERE user_id = "+playerinfo[0]+";");
			stmt.executeUpdate("UPDATE room SET user_id = 0 WHERE user_id = "+playerinfo[0]+";");
			*/

			pstmts = cs.Playerout();
			pstmts[0].setInt(1, playerinfo[0]);
			pstmts[0].executeUpdate();

			pstmts[1].setInt(1, playerinfo[0]);
			pstmts[1].executeUpdate();

			noplayer(playerinfo[1]);//テスト

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			cs.closepstmts(pstmts);
		}

		//noplayer(playerinfo[1]);//テスト
	}

	private void noplayer(int room_id)
	{
		for(int i = 0;i<userid.length;i++)
		{
			userid[i] = 0;
		}
		try
		{
			/*
			Statement stmt = CC.createstatement(conn = CC.createconnection());
			rs = stmt.executeQuery("SELECT * FROM room WHERE room_id = "+room_id+";");
			*/

			pstmts[2].setInt(1, room_id);
			rs = pstmts[2].executeQuery();

			for(int i = 0;rs.next();i++)
			{
				userid[i] = rs.getInt("user_id");
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
				rs.close();
			}
			catch(SQLException e)
			{
				System.out.println(e);
				//例外処理
			}
		}

		if(userid[0]==no_user&&userid[1]==no_user)
		{
			rd.delete(room_id);
		}
	}
}
