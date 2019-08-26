package test_tomcat_git;

import java.sql.SQLException;
import java.sql.Statement;

public class DBCNotEmpty extends DataBaseConnectRead //データベース上でユーザーIDと部屋がすべて空いていない場合に追加する
{

	int user_id;
	int[] roominfo = new int[2];

	int userIDNotempty()//ユーザーIDを追加する
	{
		Statement stmt = CC.createstatement(conn = CC.createconnection());//ステートメントを取得

		//System.out.println("//////////////////////////////////////////////////////////////////////DBCNEのユーザーIDのデバッグだお//////////////////////////////////////////////////////////////////////");
		try
		{
			rs = stmt.executeQuery("SELECT MAX(user_id) as maxno FROM user;");//現在のユーザーIDの最大値を取得する
			//結果の挿入
			rs.next();
			user_id = rs.getInt("maxno");
			//System.out.println("今のユーザーIDの最大値だよ"+user_id);
			rs.close();
			user_id++;
			//System.out.println("追加するユーザーIDだよ"+user_id);
			stmt.executeUpdate("INSERT INTO user VALUES("+user_id+",NULL); ");//ユーザーIDを追加する

		}
		catch(SQLException e)
		{
			System.out.println("ユーザーIDが追加できなかったよ");
		}
		finally
		{
			CC.close();
		}
		System.out.println("追加されたuser_idだよ"+user_id);

		return user_id;
	}

	int[] RoomNotempty(String sql)//部屋を追加する
	{
		Statement stmt = CC.createstatement(conn = CC.createconnection());//ステートメントを取得
		Result = new int[2];
		//System.out.println("//////////////////////////////////////////////////////////////////////DBCNEのルームIDのデバッグだお//////////////////////////////////////////////////////////////////////");

		try
		{
			rs = stmt.executeQuery("SELECT MAX(room_id) as maxno FROM room;");//現在の部屋の最大番号を取得する
			//結果の挿入
			rs.next();
			Result[0] = rs.getInt("maxno");
			//System.out.println("一番最後の部屋だお"+Result[0]);
			rs.close();
			Result[0]++;
			stmt.executeUpdate("INSERT INTO room VALUES("+Result[0]+",1,0);");//追加する部屋の番号とプレイヤー1を追加
			//System.out.println("実行したSQLは:INSERT INTO room VALUES("+Result[0]+",1,0);");

			stmt.executeUpdate("INSERT INTO room VALUES("+Result[0]+",2,0);");//追加する部屋の番号とプレイヤー2を追加
			//System.out.println("実行したSQLは:INSERT INTO room VALUES("+Result[0]+",2,0);");

			rs = stmt.executeQuery(sql);
			///System.out.println(sql);
			rs.next();
			Result[1] = rs.getInt("player_number");

			/*System.out.println("room_idは"+Result[0]);
			System.out.println("player_numberは"+Result[1]);*/

		}
		catch(SQLException e)
		{
			System.out.println(e);
			System.out.println("ルームが追加できなかったお");
		}
		finally
		{
			CC.close();//データベースとの接続を解除
		}
		//System.out.print("追加されたroomだお"+Result[0]);

		return Result;
	}
}
