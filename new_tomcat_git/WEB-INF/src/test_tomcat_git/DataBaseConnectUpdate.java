package test_tomcat_git;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBaseConnectUpdate extends DataBaseConnectRead //ログインしたプレイヤーの情報をデータベースに格納する
{
	int user_id;
	int[] userinfo;//ユーザID,ルームID,プレイヤー番号
	DBCbeforeUpdate DBCB = new DBCbeforeUpdate();
	String[] sqls = new String[2];
	PreparedStatement update_user_pstmt,update_room_pstmt,reserve_pstmt;

	int[] updateSQL(String user_name,int reserve,int room_id)//受け渡されたusernameをデータベースへインサートする
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

		/*
		sqls[0] = "SELECT * FROM user WHERE user_name is null ORDER BY user_id LIMIT 1;" ;
		if(reserve ==0)//通常のマッチ
		{
			sqls[1] = "SELECT * FROM room WHERE user_id = 0 ORDER BY room_id LIMIT 1;";
			System.out.println("クイックマッチ");
		}
		else if(reserve == 1)//部屋作成
		{
			sqls[1] = "SELECT * FROM room WHERE user_id = 0 AND player_number = 1 ORDER BY room_id LIMIT 1;";
		}
		*/

		sqls[0]=sr.SelectEmptyUser();
		sqls[1]=sr.SelectEmptyRoom(reserve);

		userinfo=DBCB.beforeupdate(sqls);

		update(user_name,userinfo,reserve);

		/*
		else//部屋の検索
		{
			userinfo=DBCB.beforeupdate(0);
			roomfull(room_id);
		}
		*/


/*
		try
		{
			Statement stmt1 = cc.createstatement(conn = cc.createconnection());
			Statement stmt2 = cc.createstatement(conn = cc.createconnection());
			Statement stmt3 = cc.createstatement(conn = cc.createconnection());


			stmt1.executeUpdate("UPDATE user SET user_name = '"+user_name+"' WHERE user_id = "+userinfo[0]+";");//空いているユーザーIDにユーザー名を格納する
			stmt2.executeUpdate("UPDATE room SET user_id = "+userinfo[0]+" WHERE room_id = "+userinfo[1]+" AND player_number = "+userinfo[2]+";");//空いているルームにユーザーIDを格納する
			//部屋を作った際に相手の場所を予約する
			if(reserve == 1)
			{
				stmt3.executeUpdate("UPDATE room SET user_id = -1 WHERE room_id = "+userinfo[1]+" AND player_number = 2;");
			}

		}
		catch(SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			cc.close();
		}
*/
		return userinfo;

	}

	void update(String user_name,int[] userinfo,int reserve)
	{
		try
		{
			/*
			Statement stmt1 = cc.createstatement(conn = cc.createconnection());
			Statement stmt2 = cc.createstatement(conn = cc.createconnection());
			Statement stmt3 = cc.createstatement(conn = cc.createconnection());
			*/

			update_user_pstmt = cc.createpStatement(cc.createconnection(),sr.UpdateLoginUser());
			update_room_pstmt = cc.createpStatement(cc.createconnection(),sr.UpdateLoginRoom());

			update_user_pstmt.setString(1,user_name);
			update_user_pstmt.setInt(2,userinfo[0]);

			update_room_pstmt.setInt(1,userinfo[0]);
			update_room_pstmt.setInt(2,userinfo[1]);
			update_room_pstmt.setInt(3,userinfo[2]);

			//stmt1.executeUpdate("UPDATE user SET user_name = '"+user_name+"' WHERE user_id = "+userinfo[0]+";");//空いているユーザーIDにユーザー名を格納する
			//stmt2.executeUpdate("UPDATE room SET user_id = "+userinfo[0]+" WHERE room_id = "+userinfo[1]+" AND player_number = "+userinfo[2]+";");//空いているルームにユーザーIDを格納する

			update_user_pstmt.executeUpdate();
			update_room_pstmt.executeUpdate();

			//部屋を作った際に相手の場所を予約する
			if(reserve == 1)
			{
				//stmt3.executeUpdate("UPDATE room SET user_id = -1 WHERE room_id = "+userinfo[1]+" AND player_number = 2;");

				reserve_pstmt =cc.createpStatement(cc.createconnection(),sr.UpdateReserveRoom());
				reserve_pstmt.setInt(1,userinfo[1]);
				reserve_pstmt.executeUpdate();
			}

		}
		catch(SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			cc.close();
		}
	}

/*	private void roomfull(int room_id)
	{
		user_id=0;
		Statement stmt4 = cc.createstatement(conn = cc.createconnection());

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
			cc.close();
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
