package test_tomcat_git;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBaseConnectUpdate //extends DataBaseConnectRead //ログインしたプレイヤーの情報をデータベースに格納する
{
	protected int user_id;
	protected int[] userinfo;//ユーザID,ルームID,プレイヤー番号
	final int select_num = 2,update_num = 2;
	PreparedStatement[] pstmts_select,pstmts_update;
	PreparedStatement reserve_pstmt;
	CreateStatement cs;
	DBCbeforeUpdate DBCB;

	DataBaseConnectUpdate()
	{
		cs = new CreateStatement();
		DBCB = new DBCbeforeUpdate();
		pstmts_select = new PreparedStatement[select_num];
		pstmts_update =new PreparedStatement[update_num];
	}

	int[] updateSQL(String user_name,int reserve,int room_id)//受け渡されたusernameをデータベースへ挿入する
	{
		//userinfo=DBCB.beforeupdate();//空いているルームと攻守を検索
		/*System.out.println("DataBaseUpdateクラス上での情報だお");
		System.out.println("user_id:"+userinfo[0]);
		System.out.println("room_id:"+userinfo[1]);
		System.out.println("player_number:"+userinfo[2]);*/


		//以下ロビー用のプログラム調整が終了したらコメントアウトを解除してくださいその後上の
		 //検索は削除してください

		/*
		 if(reserve ==0)//通常のマッチ
		{
			userinfo=DBCB.beforeupdate(reserve);
		}
		else if(room_id == 0)//部屋作成
		{
			userinfo=DBCB.beforeupdate(reserve);
		}
		else//部屋の検索
		{
			userinfo=DBCB.beforeupdate(0);
			roomfull(room_id);
		}
		*/

		pstmts_select[0] = cs.SerchEmptyUserTable();//空いているユーザーID
		pstmts_select[1] = cs.SerchEmptyRoomTable(reserve);//空いている部屋の検索
		System.out.println(pstmts_select);

		/*
		sql[0] = "SELECT * FROM user WHERE user_name is null ORDER BY user_id LIMIT 1;";
		if(reserve ==0)//通常のマッチ
		{
			sql[1] = "SELECT * FROM room WHERE user_id = 0 ORDER BY room_id LIMIT 1;";
			System.out.println("クイックマッチ");
		}
		else if(reserve == 1)//部屋作成
		{
			sql[1] = "SELECT * FROM room WHERE user_id = 0 AND player_number = 1 ORDER BY room_id LIMIT 1;";
		}*/
		//userinfo=DBCB.beforeupdate(sql);
		userinfo=DBCB.beforeupdate(pstmts_select);

		update(user_name,userinfo,reserve);

		return userinfo;

	}

	void update(String user_name,int[] userinfo,int reserve)
	{
		try
		{
			/*Statement stmt1 = CC.createstatement(conn = CC.createconnection());
			Statement stmt2 = CC.createstatement(conn = CC.createconnection());
			Statement stmt3 = CC.createstatement(conn = CC.createconnection());

			stmt1.executeUpdate("UPDATE user SET user_name = '"+user_name+"' WHERE user_id = "+userinfo[0]+";");//空いているユーザーIDにユーザー名を格納する
			stmt2.executeUpdate("UPDATE room SET user_id = "+userinfo[0]+" WHERE room_id = "+userinfo[1]+" AND player_number = "+userinfo[2]+";");//空いているルームにユーザーIDを格納する
			//部屋を作った際に相手の場所を予約する
			if(reserve == 1)
			{
				stmt3.executeUpdate("UPDATE room SET user_id = -1 WHERE room_id = "+userinfo[1]+" AND player_number = 2;");
				reserve_pstmt = cs.RoomReserve();
			}*/

			pstmts_update[0] = cs.UpdateUserTable();
			pstmts_update[0].setString(1, user_name);
			pstmts_update[0].setInt(2, userinfo[0]);

			pstmts_update[1] = cs.UpdateRoomTable();
			pstmts_update[1].setInt(1, userinfo[0]);
			pstmts_update[1].setInt(2, userinfo[1]);
			pstmts_update[1].setInt(3, userinfo[2]);

			pstmts_update[0].executeUpdate();
			pstmts_update[1].executeUpdate();

			if(reserve == 1)
			{
				reserve_pstmt = cs.RoomReserve();
				reserve_pstmt.setInt(1, userinfo[1]);
				reserve_pstmt.executeUpdate();
				cs.closepstmt(reserve_pstmt);
			}

		}
		catch(SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			//CC.close();
			//cs.closepstmt(reserve_pstmt);
			cs.closepstmts(pstmts_update);
		}
	}

/*	private void roomfull(int room_id)
	{
		user_id=0;
		Statement stmt4 = CC.createstatement(conn = CC.createconnection());

		try
		{
			rs = stmt4.executeQuery("SELECT * FROM room WHERE room_id = "+ room_id +" AND player_number = 2 ;");

			while(rs.next())
			{
				user_id = rs.getInt("user_id");
			}
		}
		catch(SQLException e)
		{
			System.out.println(e);
			System.out.println("ルームの満員チェックSQLでエラーです");
		}
		finally
		{
			CC.close();
			try
			{
				rs.close();
			}
			catch(SQLException e)
			{
				System.out.println(e);
				System.out.println("DataBasebeforeUpdateのrsが閉じれなかった");
			}

			if(user_id == -1)
			{
				userinfo[1] = room_id;
				userinfo[2] = 2;
			}

		}

	}
	*/

}
