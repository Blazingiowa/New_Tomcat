package test_tomcat_git;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class RoomCheck extends DataBaseConnectUpdate
{
	protected int serch;
	protected boolean empty;
	Statement stmt;
	PreparedStatement room_exist,room_full;

	boolean existroom(int room_id)
	{
		boolean exist = false;
		stmt =cc.createstatement(conn = cc.createconnection());
		room_exist=cc.createpStatement(cc.createconnection(),sr.SelectRoomExist());

		try
		{
			serch= 0;
			//rs = stmt.executeQuery("SELECT DISTINCT room_id FROM room;");
			rs = room_exist.executeQuery();

			while(rs.next())
			{
				serch = rs.getInt("room_id");
				if(room_id == serch)
				{
					exist=true;
					break;
				}
			}
		}
		catch (SQLException e)
		{
			// TODO 自動生成された catch ブロック
			System.out.println(e);
		}
		finally
		{
			cc.close();//データベースとの接続を解除

			try
			{
				rs.close();//ResultSetをクローズ
			}
			catch(SQLException e)
			{
				System.out.println(e);
				//例外処理

			}
		}

		return exist;
	}

	boolean roomfull(int room_id)
	{
		stmt =cc.createstatement(conn = cc.createconnection());
		empty = true;
		room_full=cc.createpStatement(cc.createconnection(),sr.SelectRoomFull());

		try
		{
			//rs = stmt.executeQuery("SELECT * FROM room WHERE room_id = "+ room_id +" AND player_number = 2 ;");
			room_full.setInt(1,room_id);
			rs = room_full.executeQuery();

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
		}

		if(user_id != -1)
		{
			empty = false;
		}

		return empty;
	}

}

