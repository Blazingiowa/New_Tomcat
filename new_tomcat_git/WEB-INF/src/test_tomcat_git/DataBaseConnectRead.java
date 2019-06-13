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

	protected int[] Result = new int[6];//受け渡す情報が入る
	protected int[] room = new int[3];//ルームIDとユーザIDが入る
	protected int timeoutseconds = 30;//タイムアウト時間



	int[] reference(int id,int type)//idはカードidなど、typeは攻防か、ルーム検索か
	{
		for(int i = 0;i < Result.length;i++)
		{
			Result[i] = 0;
		}


		if(type == -1)//ルームの空き情報を検索
		{
			try
			{
				conn = DriverManager.getConnection(url,user,password);
				DriverManager.setLoginTimeout(timeoutseconds);
				//SQL
				Statement stmt = conn.createStatement();
				//結果の挿入
				ResultSet rs = stmt.executeQuery("");
				room[0] = rs.getInt("ユーザid");
				room[1] = rs.getInt("ルームid");
				room[3] = rs.getInt("");//ルームでのユーザ番号(プレイヤー１など)
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

		}

		else//カード情報を検索
		{
			try
			{
				conn = DriverManager.getConnection(url,user,password);
				DriverManager.setLoginTimeout(timeoutseconds);
				//SQL
				Statement stmt = conn.createStatement();
				//結果の挿入
				ResultSet rs = stmt.executeQuery("");
				Result[0] = rs.getInt("id");
				Result[1] = rs.getInt("ダメージ");
				Result[2] = rs.getInt("攻防");
				Result[3] = rs.getInt("防カードid1(仮)");
				Result[4] = rs.getInt("防カードid2(仮)");
				Result[5] = rs.getInt("コスト");

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


		}
		return Result;
	}


}
