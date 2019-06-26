package test_tomcat_git;

import java.sql.SQLException;
import java.sql.Statement;

public class DataBasePlayerout extends DataBaseConnectUpdate
{
	void logout(int[] playerinfo)//ユーザID,ルームID,プレイヤー番号の順番で格納
	{
		try
		{
			Statement stmt = CC.createstatement(conn = CC.createconnection());
			stmt.executeUpdate("UPDATE user SET user_name = NULL WHERE user_id = "+playerinfo[1]+";");
			stmt.executeUpdate("UPDATE room SET user_id = 0 WHERE user_id = "+playerinfo[1]+";");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (conn != null)
				{
					conn.close();
				}
			}
			catch(SQLException e)
			{
				System.out.println(e);
				//例外処理

			}
		}
	}
}
