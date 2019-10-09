package test_tomcat_git;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateStatement
{
	String[] Roomsql;
	PreparedStatement pstmt;
	PreparedStatement[] pstmts;
	Connection conn;

	CreateConnection CC = new CreateConnection();

	CreateStatement()
	{
		conn = CC.createconnection();
	}

	PreparedStatement SerchEmptyUserTable()
	{
		try
		{
			pstmt = conn.prepareStatement( "SELECT * FROM user WHERE user_name is NULL ORDER BY user_id LIMIT 1;");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return pstmt;
	}

	PreparedStatement SerchEmptyRoomTable(int reserve)
	{
		Roomsql = new String[2];
		Roomsql[0] =  "SELECT * FROM room WHERE user_id = 0 ORDER BY room_id LIMIT 1;";
		Roomsql[1] =  "SELECT * FROM room WHERE user_id = 0 AND player_number = 1 ORDER BY room_id LIMIT 1;";
		try
		{
			pstmt = conn.prepareStatement(Roomsql[reserve]);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return pstmt;
	}

	PreparedStatement SerchRoom()
	{
		try
		{
			pstmt = conn.prepareStatement("SELECT * FROM room WHERE room_id = ?;");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return pstmt;
	}

	PreparedStatement SerchCardTabele()
	{
		try
		{
			pstmt = conn.prepareStatement("SELECT * FROM card WHERE card_id = ?;");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return pstmt;
	}

	PreparedStatement SerchCardTabeleText()
	{
		try
		{
			pstmt = conn.prepareStatement("SELECT * FROM card;");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return pstmt;
	}

	PreparedStatement UpdateUserTable()
	{
		try
		{
			pstmt= conn.prepareStatement("UPDATE user SET user_name = ? WHERE user_id = ?;");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return pstmt;
	}

	PreparedStatement UpdateRoomTable()
	{
		try
		{
			pstmt = conn.prepareStatement("UPDATE room SET user_id = ? WHERE room_id = ? AND player_number = ?;");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return pstmt;
	}

	PreparedStatement RoomReserve()
	{
		try
		{
			pstmt = conn.prepareStatement("UPDATE room SET user_id = -1 WHERE room_id = ? AND player_number = 2;");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return pstmt;
	}

	PreparedStatement[] RoomEntry()
	{
		pstmts = new PreparedStatement[2];
		try
		{
			pstmts[0] = SerchEmptyUserTable();
			pstmts[1] = conn.prepareStatement("SELECT * FROM room WHERE user_id = -1 AND room_id = ? ORDER BY user_id LIMIT 1;");
		}
		catch (SQLException e)
		{
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return pstmts;
	}

	PreparedStatement[] Playerout()
	{
		pstmts = new PreparedStatement[3];
		try
		{
			pstmts[0] = conn.prepareStatement("UPDATE user SET user_name = NULL WHERE user_id = ?;");
			pstmts[1] = conn.prepareStatement("UPDATE room SET user_id = 0 WHERE user_id = ?;");
			pstmts[2] = conn.prepareStatement("SELECT * FROM room WHERE room_id = ?;");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return pstmts;
	}

	PreparedStatement[] addUserId()
	{
		pstmts = new PreparedStatement[2];
		try
		{
			pstmts[0] = conn.prepareStatement("SELECT MAX(user_id) as maxno FROM user;");
			pstmts[1] = conn.prepareStatement("INSERT INTO user VALUES(?,NULL);");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return pstmts;
	}

	PreparedStatement[] addRoom()
	{
		pstmts = new PreparedStatement[3];
		try
		{
			pstmts[0] = conn.prepareStatement("SELECT MAX(room_id) as maxno FROM room;");
			pstmts[1] = conn.prepareStatement("INSERT INTO room VALUES(?,1,0);");
			pstmts[2] = conn.prepareStatement("INSERT INTO room VALUES(?,2,0);");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return pstmts;
	}

	PreparedStatement SerchReserveRoom()
	{
		try
		{
			pstmt = conn.prepareStatement("SELECT * FROM room WHERE user_id = -1 AND room_id = ? ORDER BY user_id LIMIT 1;");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return pstmt;
	}

	PreparedStatement[] ExistCheck()
	{
		pstmts = new PreparedStatement[2];

		try
		{
			pstmts[0] = conn.prepareStatement("SELECT DISTINCT room_id FROM room;");
			pstmts[1] = conn.prepareStatement("SELECT * FROM room WHERE room_id = ? AND player_number = 2 ;");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return pstmts;
	}

	PreparedStatement CountCard()
	{
		try
		{
			pstmt = conn.prepareStatement("SELECT COUNT(*) AS number FROM card;");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return pstmt;
	}

	PreparedStatement CardTextget()
	{
		try
		{
			pstmt = conn.prepareStatement("SELECT * FROM card_text;");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return pstmt;
	}

	void closepstmt(PreparedStatement pstmt)
	{
		try
		{
			pstmt.clearParameters();
		}
		catch (SQLException e1)
		{
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
		try
		{
			pstmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		CC.close();
	}

	void closepstmts(PreparedStatement[] pstmts)
	{
		for(int i =0;i<pstmts.length;i++)
		{
			try
			{
				pstmts[i].clearParameters();
			}
			catch (SQLException e1)
			{
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
			}
			try
			{
				pstmts[i].close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}

		CC.close();
	}

}
