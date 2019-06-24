package test_tomcat_git;

import java.io.File;

public class TaiouText extends TextWrite
{
	DataBaseConnectRead DBCR = new DataBaseConnectRead();

	void taioucreate(int room)
	{
		file = new File("");//room_idを使用してファイルを作成
		/*データベースにアクセスし対応表を確保する*/

		/*取得したデータをもとにテキストファイルに出力する*/
	}
}
