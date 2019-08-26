package test_tomcat_git;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnectCard extends DataBaseConnectRead //データベースからカードの検索結果を返す
{
	ResultSet cardinfo()
	{
		Statement stmt = CC.createstatement(conn = CC.createconnection());//ステートメントを取得

		try
		{
			rs = stmt.executeQuery("SELECT * FROM card");//カードの情報を取得するSQL
		}
		catch(SQLException e)
		{
			System.out.println(e);
			System.out.println("データベースからカードの情報が取れなかったよ");
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
			}
		}

		return rs;
	}
}
