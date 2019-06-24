package test_tomcat_git;

import java.io.File;

public class TaiouText extends TextWrite
{
	DataBaseConnectRead DBCR = new DataBaseConnectRead();
	
	void create(int room_id)
	{
		file = new File("");//room_idを使用してファイルを作成
		
	}
}
