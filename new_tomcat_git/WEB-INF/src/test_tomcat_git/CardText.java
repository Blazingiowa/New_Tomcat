package test_tomcat_git;

import java.io.File;
import java.sql.ResultSet;

public class CardText extends TextWrite
{
	DataBaseConnectCard DBCC = new DataBaseConnectCard();
	ResultSet rs;

	void cardcreate(int room)
	{
		file = new File("");
		/*データベースにアクセスしカード情報を確保する*/
		rs = DBCC.cardinfo();
		/*取得したデータをもとにテキストファイルに出力する*/


	}
}
