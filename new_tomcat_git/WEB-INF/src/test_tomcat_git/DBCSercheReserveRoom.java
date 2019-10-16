package test_tomcat_git;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBCSercheReserveRoom extends DataBaseConnectRead
{
	private String[] sqls;
	private int[] userinfo;
	PreparedStatement[] pstmts;
	DBCbeforeUpdate dbcb = new DBCbeforeUpdate();
	DataBaseConnectUpdate dbcu = new DataBaseConnectUpdate();
	DBCNotEmpty DBCNE = new DBCNotEmpty();
	SQLRepository sr = new SQLRepository();

	int[] entryroom(String user_name,int room_id)
	{
		userinfo = new int[3];
		pstmts = new PreparedStatement[2];

		for(int i = 0;i < userinfo.length;i++)
		{
			userinfo[i] = 0;
		}


		pstmts[0] = cc.createpStatement(cc.createconnection(),sr.SelectEmptyUser());
		pstmts[1] = cc.createpStatement(cc.createconnection(),sr.SelectReserveRoom());

		try
		{
			rs = pstmts[0].executeQuery();

			if(rs.next())
			{
				userinfo[0] = rs.getInt("user_id");
				rs.close();
			}
			else
			{
				System.out.println("空きなかったよ(´・ω・｀)");
				userinfo[0] = DBCNE.userIDNotempty();
			}

			pstmts[1].setInt(1,room_id);
			rs = pstmts[1].executeQuery();


			if(rs.next())
			{
				userinfo[1] = rs.getInt("room_id");
				userinfo[2] = rs.getInt("player_number");
				rs.close();
			}
			else
			{
				int[] keep = DBCNE.RoomNotempty(sqls[1]);
				userinfo[1] = keep[0];
				userinfo[2] = keep[1];
			}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			cc.close();
		}

		/*
		sqls[0] = "SELECT * FROM user WHERE user_name is null ORDER BY user_id LIMIT 1;" ;
		sqls[1] = "SELECT * FROM room WHERE user_id = -1 AND room_id = "+room_id+ " ORDER BY user_id LIMIT 1;" ;
		System.out.println("○○○○○○○○○○○○○○○○○○○○○○お部屋探し○○○○○○○○○○○○○○○○○○○○○○○○○○○○○○○○○");
		*/


		//sqls[0] = sr.SelectEmptyUser();
		//userinfo=dbcb.beforeupdate(sqls);
		dbcu.update(user_name,userinfo,0);
		return userinfo;
	}
}
