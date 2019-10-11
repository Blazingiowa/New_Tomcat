package test_tomcat_git;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomCheck //extends DataBaseConnectUpdate
{
	protected int serch,user_id;
	protected final int rsnum = 2;
	protected boolean empty,exist;
	protected boolean[] roomchecker;
	protected ResultSet[] rss;
	protected PreparedStatement[] pstmts;
	CreateConnection cc;
	CreateStatement cs;

	RoomCheck()
	{
		cc = new CreateConnection();
		serch = 0;
		exist = false;
		empty = true;
		roomchecker = new boolean[rsnum];
		roomchecker[0] = false;
		roomchecker[1] = true;
		rss = new ResultSet[rsnum];
		cs = new CreateStatement();

	}


	boolean[] roomchcek(int room_id)
	{
		pstmts = cs.ExistCheck(cc.createconnection());
		try
		{
			rss[0] = pstmts[0].executeQuery();
			while(rss[0].next())
			{
				serch = rss[0].getInt("room_id");
				if(room_id == serch)
				{
					roomchecker[0]=true;
					break;
				}
			}

			pstmts[1].setInt(1, room_id);
			rss[1] = pstmts[1].executeQuery();
			rss[1].next();
			user_id = rss[1].getInt("user_id");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			for(int i = 0;i<rss.length;i++)
			{
				try
				{
					rss[i].close();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
			cs.closepstmts(pstmts);
		}

		if(user_id != -1)
		{
			roomchecker[1] = false;
		}

		return roomchecker;
	}

	/*
	boolean existroom(int room_id)
	{
		//boolean exist = false;
		//stmt =CC.createstatement(conn = CC.createconnection());

		try
		{
			//serch= 0;
			//rs = stmt.executeQuery("SELECT DISTINCT room_id FROM room;");
			rs = pstmts[0].executeQuery();

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
		stmt =CC.createstatement(conn = CC.createconnection());
		empty = true;

		try
		{
			rs = stmt.executeQuery("SELECT * FROM room WHERE room_id = "+ room_id +" AND player_number = 2 ;");

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
		}

		if(user_id != -1)
		{
			empty = false;
		}

		return empty;
	}
	*/

}

