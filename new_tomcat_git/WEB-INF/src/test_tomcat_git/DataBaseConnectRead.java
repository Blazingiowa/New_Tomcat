package test_tomcat_git;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnectRead //データベースから取得した情報を配列に格納し返す
{
	CreateConnection cc = new CreateConnection();
	SQLRepository sr = new SQLRepository();

	protected Connection conn;
	protected int[] Result;//受け渡す情報が入る
	protected int[] room = new int[3];//ルームIDとユーザIDが入る
	protected Statement stmt;
	protected PreparedStatement pstmt;
	protected ResultSet rs;

	int[] reference(int id)
	{
		Result = new int[9];
		String card;

		for(int i = 0;i < Result.length;i++)
		{
			Result[i] = -1;
		}

		//Statement stmt = cc.createstatement(conn = cc.createconnection());//ステートメントを取得
		pstmt = cc.createpStatement(cc.createconnection(),sr.SelectCard());

		try
		{
			//SQL
			//stmt = conn.createStatement();
			//結果の挿入
			//rs = stmt.executeQuery("SELECT * FROM card WHERE card_id = "+id+";");//指定されたカードの情報を取得するためのSQL
			pstmt.setInt(1,id);
			rs = pstmt.executeQuery();
			rs.next();
			Result[0] = rs.getInt("card_id");
			Result[1] = rs.getInt("cost");
			Result[2] = rs.getInt("dmg");
			Result[8] = rs.getInt("type");
			card= rs.getString("taio_id");


			String[] array = card.split(",");//カンマ区切りされているカード情報をsplitし配列に格納
			for(int i = 0,j = 3;i<array.length;i++,j++)
			{
				Result[j] = Integer.parseInt(array[i]);
			}
		}
		catch(SQLException e)
		{
			System.out.println(e);
			System.out.println("id指定検索を失敗したよ");
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

		return Result;
	}

}
