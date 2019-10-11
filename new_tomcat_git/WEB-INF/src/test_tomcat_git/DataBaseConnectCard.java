package test_tomcat_git;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBaseConnectCard extends DataBaseConnectRead //データベースからカードの検索結果を返す
{
	ResultSet cardinfo()
	{
		//Statement stmt = cc.createstatement(conn = cc.createconnection());//ステートメントを取得
		pstmt = cc.createpStatement(cc.createconnection(),sr.SelectAllCard());
		try
		{
			//rs = stmt.executeQuery("SELECT * FROM card");//カードの情報を取得するSQL
			rs = pstmt.executeQuery();
		}
		catch(SQLException e)
		{
			System.out.println(e);
			System.out.println("データベースからカードの情報が取れなかったよ");
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
			}
		}

		return rs;
	}
}
