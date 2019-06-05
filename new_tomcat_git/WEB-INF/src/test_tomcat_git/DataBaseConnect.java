package test_tomcat_git;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnect
{
	protected Connection conn = null;
	protected String url = "jdbc:mysql://localhost/u22?characterEncoding=UTF-8&serverTimezone=JST"; //データベースのURLまたはIPアドレス、ローカルの場合はパス
	protected String user = "root";//データベースへアクセスするID
	protected String password = "yasutaka13";//データベースのパスワード

	protected int[] reference = new int[6];//受け渡す情報が入る
	protected int[] room = new int[3];//ルームIDとユーザIDが入る
	protected int timeoutseconds = 30;//タイムアウト時間

	int[] reference(int id,int type)//idはカードidなど、typeは攻防か、ルーム検索か
	{
		for(int i = 0;i < reference.length;i++)
		{
			reference[i] = 0;
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
				reference[0] = rs.getInt("id");
				reference[1] = rs.getInt("ダメージ");
				reference[2] = rs.getInt("攻防");
				reference[3] = rs.getInt("防カードid1(仮)");
				reference[4] = rs.getInt("防カードid2(仮)");
				reference[5] = rs.getInt("コスト");

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
		return reference;
	}

	String[] update(String username)//受け渡されたusernameをデータベースへインサートする
	{
		reference(0,-1);//空いているルームと攻守を検索

		String[] userinfo = new String[3];
		try
		{
			conn = DriverManager.getConnection(url,user,password);

			//SQL
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
		//データベースからのintデータをString配列にキャスト
		return userinfo;


	}
}
