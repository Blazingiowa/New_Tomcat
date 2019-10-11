package test_tomcat_git;

import java.sql.PreparedStatement;

public class DBCSercheReserveRoom //extends DataBaseConnectUpdate
{
	final int sqlnum = 2,user_num = 3;
	protected int[] userinfo;
	protected CreateStatement cs;
	protected PreparedStatement[] pstmts;
	protected DataBaseConnectUpdate DBCU;
	protected DBCbeforeUpdate DBCB;
	CreateConnection cc;

	DBCSercheReserveRoom()
	{
		cc = new CreateConnection();
		cs = new CreateStatement();
		pstmts = new PreparedStatement[sqlnum];
		userinfo = new int[user_num];
	}

	int[] entryroom(String user_name,int room_id)
	{
		/*
		sql[0] = "SELECT * FROM user WHERE user_name is null ORDER BY user_id LIMIT 1;" ;
		sql[1] = "SELECT * FROM room WHERE user_id = -1 AND room_id = "+room_id+ " ORDER BY user_id LIMIT 1;" ;
		System.out.println("○○○○○○○○○○○○○○○○○○○○○○お部屋探し○○○○○○○○○○○○○○○○○○○○○○○○○○○○○○○○○");
		*/
		pstmts = cs.RoomEntry(cc.createconnection());
		userinfo=DBCB.beforeupdate(pstmts);
		DBCU.update(user_name,userinfo,0);
		return userinfo;
	}
}
