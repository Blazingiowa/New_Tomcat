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

	CreateStatement()
	{
		CreateConnection CC = new CreateConnection();
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

	PreparedStatement SerchUser()
	{
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

	PreparedStatement[] Playerout()
	{
		pstmts = new PreparedStatement[2];
		try
		{
			pstmt = conn.prepareStatement("UPDATE user SET user_name = NULL WHERE user_id = ?;");
			pstmt = conn.prepareStatement("UPDATE room SET user_id = 0 WHERE user_id = ?;");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return pstmts;
	}

}
