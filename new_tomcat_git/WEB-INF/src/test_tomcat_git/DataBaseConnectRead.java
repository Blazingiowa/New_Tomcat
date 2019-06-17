package test_tomcat_git;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnectRead
{
	protected Connection conn = null;
	protected String url = "jdbc:mysql://localhost/u22?characterEncoding=UTF-8&serverTimezone=JST"; //データベースのURLまたはIPアドレス、ローカルの場合はパス
	protected String user = "root";//データベースへアクセスするID
	protected String password = "yasutaka13";//データベースのパスワード

	protected int[] Result;//受け渡す情報が入る
	protected int[] room = new int[3];//ルームIDとユーザIDが入る
	protected int timeoutseconds = 30;//タイムアウト時間



	int[] reference(int id,int type)//idはカードidなど、typeは攻防か、ルーム検索か
	{
			Result = new int[8];
			String card;

			for(int i = 0;i < Result.length;i++)
			{
				Result[i] = -1;
			}

			try
			{
				conn = DriverManager.getConnection(url,user,password);
				DriverManager.setLoginTimeout(timeoutseconds);
				//SQL
				Statement stmt = conn.createStatement();
				//結果の挿入
				ResultSet rs = stmt.executeQuery("");
				Result[0] = rs.getInt("id");
				Result[1] = rs.getInt("コスト");
				Result[2] = rs.getInt("ダメージ");
				card= rs.getString("対応ID");

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
			conn = DriverManager.getConnection(url,user,password);
			DriverManager.setLoginTimeout(timeoutseconds);
			//SQL
			Statement stmt = conn.createStatement();
			//結果の挿入
			ResultSet rs = stmt.executeQuery("");
			Result[0] = rs.getInt("ユーザid");
			Result[1] = rs.getInt("ルームid");
			Result[2] = rs.getInt("プレイヤー番号");
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
