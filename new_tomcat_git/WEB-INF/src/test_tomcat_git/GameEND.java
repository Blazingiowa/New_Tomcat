package test_tomcat_git;

import java.io.File;

public class GameEND
{
	//プレイヤーが退出したときのDBを更新するためのクラスをインスタンス
	DataBasePlayerout DBP = new DataBasePlayerout();
	TextWrite tw = new TextWrite();
	TextRead tr = new TextRead();

	String[] path = new String[2];
	File[] file = new File[2];
	int[] write;

	void logout(int info[])//ユーザID　ルームID　プレイヤー番号
	{


		path[0] = "/var/www/html/game/"+ info[1] +"/room.txt";//ルームテキストのパス
		path[1] = "";

		//ファイルを削除する場合
		/*
		file[0] = new File("");
		file[1] = new File("");
		for(int i = 0;i<file.length;i++)
		{
			if(file[i].exists())
			{
				if(file[i].delete())
				{
					System.out.println(path[i]+"消せたよ＼(^_^)／");
				}
				else
				{
					System.out.println(path[i]+"消せなかったよ(´・ω・｀)");
				}
			}
		}
		*/

		//ファイルを消さずにログアウト判定を行う場合

		write = tr.read(info[1],3,0);
		write[0] = 0;

		tw.write(info[1],3,0,write);

		write = tr.read(info[1],3,0);
		write[info[2]] = 0;

		DBP.logout(info);
	}
}
