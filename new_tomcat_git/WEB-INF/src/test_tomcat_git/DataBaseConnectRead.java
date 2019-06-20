package test_tomcat_git;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnectRead
{
	protected Connection conn = null;
	protected String url = "jdbc:mysql://localhost:3306/u22?characterEncoding=UTF-8&serverTimezone=JST"; //データベースのURLまたはIPアドレス、ローカルの場合はパス
	protected String user = "root";//データベースへアクセスするID
	protected String password = "ncc_NCC2019";//データベースのパスワード

	protected int[] Result;//受け渡す情報が入る
	protected int[] room = new int[3];//ルームIDとユーザIDが入る
	protected int timeoutseconds = 30;//タイムアウト時間



	int[] reference(int id)//idはカードidなど、typeは攻防か、ルーム検索か
	{
		Result = new int[8];
		String card;

		for(int i = 0;i < Result.length;i++)
		{
			Result[i] = -1;
		}

		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (ClassNotFoundException e1)
		{

			e1.printStackTrace();
		}

		try
		{

			conn = DriverManager.getConnection(url,user,password);
			DriverManager.setLoginTimeout(timeoutseconds);
			//SQL
			Statement stmt = conn.createStatement();
			//結果の挿入
			ResultSet rs = stmt.executeQuery("SELECT * FROM card WHERE card_id = "+id+";");
			rs.next();
			Result[0] = rs.getInt("card_id");
			Result[1] = rs.getInt("cost");
			Result[2] = rs.getInt("dmg");
			card= rs.getString("taio_id");

			String[] array = card.split(",");
			for(int i = 0,j = 3;i<array.length;i++,j++)
			{
				Result[j] = Integer.parseInt(array[i]);
			}

		}
		catch(SQLException e)
		{
			System.out.println(e);
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
		return Result;
	}

	int[] beforeupdate()
	{
		Result = new int[3];
		for(int i = 0;i < Result.length;i++)
		{
			Result[i] = 0;
		}

		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (ClassNotFoundException e1)
		{

			e1.printStackTrace();
		}

		try
		{
			conn = DriverManager.getConnection(url,user,password);
			DriverManager.setLoginTimeout(timeoutseconds);
			//SQL
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM user WHERE user_name IS null ORDER BY user_id LIMIT 1;");
			//結果の挿入
			rs.next();
			Result[0] = rs.getInt("user_id");

			rs = stmt.executeQuery("SELECT * FROM room WHERE user_id = 1 ORDER BY room_id LIMIT 1;");
			//結果の挿入
			rs.next();
			Result[1] = rs.getInt("room_id");
			Result[2] = rs.getInt("player_number");

			for(int i=0;i<Result.length;i++)
			{
				System.out.println("あいてる部屋とかだお"+Result[i]);
			}


		}
		catch(SQLException e)
		{
			System.out.println(e);
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

		return Result;

	}


}
