package test_tomcat_git;

import java.io.File;

public class GameEND
{
	//メインメソッド
	void main(int info[])
	{
		del(info);
	}

	//途中退出するプレイヤーのテキストを消すメソッド
	void del(int playerinfo[])
	{
		//ルーム番号とプレイヤー番号でテキストを探す、そしてパスを格納
		 File file = new File("/var/www/html/"+ playerinfo[1] + "/" + playerinfo[2] + ".txt");

		 //テキストファイルが存在した場合
		if (file.exists())
		{
			file.delete();
		}
		//なかった場合
		else
		{
			return;
		}
	}
}
