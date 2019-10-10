package test_tomcat_git;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCNotEmpty //extends DataBaseConnectRead //データベース上でユーザーIDと部屋がすべて空いていない場合に追加する
{

	private int user_id;
	private int[] roominfo;
	private final int roominfonum =2,userid_emp =2,roomid_emp=3;
	PreparedStatement[] pstmts;
	ResultSet rs;
	CreateStatement cs;

	DBCNotEmpty()
	{
		cs = new CreateStatement();
	}

	int userIDNotempty()//ユーザーIDを追加する
	{
		pstmts = new PreparedStatement[userid_emp];
		pstmts = cs.addUserId();

		//Statement stmt = CC.createstatement(conn = CC.createconnection());//ステートメントを取得

		//System.out.println("//////////////////////////////////////////////////////////////////////DBCNEのユーザーIDのデバッグだお//////////////////////////////////////////////////////////////////////");
		try
		{
			//rs = stmt.executeQuery("SELECT MAX(user_id) as maxno FROM user;");//現在のユーザーIDの最大値を取得する
			rs = pstmts[0].executeQuery();
			//結果の挿入
			rs.next();
			user_id = rs.getInt("maxno");
			//System.out.println("今のユーザーIDの最大値だよ"+user_id);
			user_id++;
			//System.out.println("追加するユーザーIDだよ"+user_id);
			//stmt.executeUpdate("INSERT INTO user VALUES("+user_id+",NULL); ");//ユーザーIDを追加する
			pstmts[1].setInt(1, user_id);
			pstmts[1].executeUpdate();

		}
		catch(SQLException e)
		{
			System.out.println("ユーザーIDが追加できなかったよ");
		}
		finally
		{
			try
			{
				rs.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			cs.closepstmts(pstmts);
		}
		System.out.println("追加されたuser_idだよ"+user_id);

		return user_id;
	}

	int[] RoomNotempty(PreparedStatement pstmt)//部屋を追加する
	{
		pstmts = new PreparedStatement[roomid_emp];
		roominfo = new int[roominfonum];

		//Statement stmt = CC.createstatement(conn = CC.createconnection());//ステートメントを取得
		//Result = new int[2];

		try
		{
			//rs = stmt.executeQuery("SELECT MAX(room_id) as maxno FROM room;");//現在の部屋の最大番号を取得する
			rs = pstmts[0].executeQuery();
			//結果の挿入
			rs.next();
			roominfo[0] = rs.getInt("maxno");
			//System.out.println("一番最後の部屋だお"+Result[0]);
			rs.close();
			roominfo[0]++;
			//stmt.executeUpdate("INSERT INTO room VALUES("+Result[0]+",1,0);");//追加する部屋の番号とプレイヤー1を追加
			pstmts[1].setInt(1, roominfo[0]);
			//System.out.println("実行したSQLは:INSERT INTO room VALUES("+Result[0]+",1,0);");

			//stmt.executeUpdate("INSERT INTO room VALUES("+Result[0]+",2,0);");//追加する部屋の番号とプレイヤー2を追加
			//System.out.println("実行したSQLは:INSERT INTO room VALUES("+Result[0]+",2,0);");
			pstmts[2].setInt(1, roominfo[0]);

			//rs = stmt.executeQuery(sql);
			rs = pstmt.executeQuery();
			///System.out.println(sql);
			rs.next();
			roominfo[1] = rs.getInt("player_number");

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
			try
			{
				rs.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			cs.closepstmts(pstmts);
			//cs.closepstmt(pstmt);
			//CC.close();//データベースとの接続を解除
		}
		//System.out.print("追加されたroomだお"+Result[0]);

		return roominfo;
	}
}
