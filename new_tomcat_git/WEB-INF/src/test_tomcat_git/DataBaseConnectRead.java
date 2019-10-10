package test_tomcat_git;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnectRead //データベースから取得した情報を配列に格納し返す
{
	//CreateConnection CC = new CreateConnection();
	CreateStatement cs;

	final int infonum = 3,Resultnum=9;
	protected int[] Result;//受け渡す情報が入る
	protected int[] room;//ルームIDとユーザIDが入る
	protected Statement stmt;
	protected PreparedStatement pstmt;
	protected ResultSet rs;

	DataBaseConnectRead()
	{
		cs = new CreateStatement();
		Result = new int[Resultnum];
		room = new int[infonum];
	}

	int[] reference(int id)
	{
		String card;

		for(int i = 0;i < Result.length;i++)
		{
			Result[i] = -1;
		}

		//Statement stmt = CC.createstatement(conn = CC.createconnection());//ステートメントを取得
		pstmt = cs.SerchAllCard();
		try
		{
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			rs.next();
			Result[0] = rs.getInt("card_id");
			Result[1] = rs.getInt("cost");
			Result[2] = rs.getInt("dmg");
			card= rs.getString("taio_id");
			Result[8] = rs.getInt("type");

			String[] array = card.split(",");//カンマ区切りされているカード情報をsplitし配列に格納
			for(int i = 0,j = 3;i<array.length;i++,j++)
			{
				Result[j] = Integer.parseInt(array[i]);
			}
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		finally
		{
			try
			{
				rs.close();
			}
			catch (SQLException e)
			{
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			cs.closepstmt(pstmt);
		}

		/*try
		{
			//SQL
			stmt = conn.createStatement();
			//結果の挿入
			rs = stmt.executeQuery("SELECT * FROM card WHERE card_id = "+id+";");//指定されたカードの情報を取得するためのSQL
			rs.next();
			Result[0] = rs.getInt("card_id");
			Result[1] = rs.getInt("cost");
			Result[2] = rs.getInt("dmg");
			card= rs.getString("taio_id");
			Result[8] = rs.getInt("type");

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
			CC.close();//データベースとの接続を解除

			try
			{
				rs.close();//ResultSetをクローズ
			}
			catch(SQLException e)
			{
				System.out.println(e);
				//例外処理

			}
		}*/

		return Result;
	}

}
