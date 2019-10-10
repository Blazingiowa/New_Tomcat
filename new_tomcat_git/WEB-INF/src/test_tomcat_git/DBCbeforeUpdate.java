package test_tomcat_git;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBCbeforeUpdate extends DataBaseConnectRead//空いている部屋と空いているユーザーIDを検索する
{
	DBCNotEmpty dbcne;
	PreparedStatement[] pstmts_select;
	final int Resultnum = 3,select_num = 2;

	DBCbeforeUpdate()
	{
		super();
		Result = new int[Resultnum];
		dbcne = new DBCNotEmpty();
		pstmts_select = new PreparedStatement[select_num];
	}

	int[] beforeupdate(/*PreparedStatement[] pstmts*/int reserve)
	{
		pstmts_select[0] = cs.SerchEmptyUserTable();//空いているユーザーID
		pstmts_select[1] = cs.SerchEmptyRoomTable(reserve);//空いている部屋の検索

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

		//Statement stmt = CC.createstatement(conn = CC.createconnection());

		try
		{
			//rs = stmt.executeQuery(sql[0]);//空いているユーザーIDの検索
			rs = pstmts_select[0].executeQuery();

			if(rs.next())
			{
				Result[0] = rs.getInt("user_id");
				rs.close();
				//System.out.println("空きあったよ");
			}
			else
			{
				System.out.println("空きなかったよ(´・ω・｀)");
				Result[0] = dbcne.userIDNotempty();
			}


			//rs = stmt.executeQuery(sql[1]);//空いているルームの検索
			rs = pstmts_select[1].executeQuery();
			//結果の挿入

			if(rs.next())
			{
				Result[1] = rs.getInt("room_id");
				Result[2] = rs.getInt("player_number");
				rs.close();
			}
			else
			{
				int[] keep = dbcne.RoomNotempty(pstmts_select[1]);
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
			//System.out.println(sql[0]);
			//System.out.println(sql[1]);
			System.out.println(e);
		}
		finally
		{
			cs.closepstmts(pstmts_select);
		}

		return Result;

	}

}
