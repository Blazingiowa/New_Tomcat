package test_tomcat_git;

import java.io.File;
import java.io.IOException;

public class Gamestart
{
	Gamemain Gm = new Gamemain();
	Text tx = new Text();
	File file;

	 DataBaseConnectUpdate DBCU = new  DataBaseConnectUpdate();
	 TaiouText tt = new TaiouText();
	 CardText ct = new CardText();
	 RoomText rt = new RoomText();
	 StartupText st = new StartupText();

	String[] userinfo = new String[3];//ユーザID,ルームID,プレイヤー番号の順番で格納
	int[] player = new int[3];
	String url;
	String[] path = new String[4];

	String[] createdirectry(String user_name) //
	{
		player = DBCU.update(user_name);

		path[0] = "/var/www/html/"+player[1]+"/"+player[2]+".txt";
		path[1] = "/var/www/html/"+player[1]+"/taiou.txt";
		path[2] = "/var/www/html/"+player[1]+"/card.txt";
		path[3] = "/var/www/html/"+player[1]+"/room.txt";


		for(int i = 0;i<userinfo.length;i++)
		{
			userinfo[i] = String.valueOf(player[i]);
		}

		file = new File(path[0]);
		if(file.exists() == false)//player.txt
		{
			System.out.println(path[0]);
			createfile(file);
		}
		tx.editer(player[1], player[2],0,0,null);
		st.textfile(player[1], player[2]);

		file = new File(path[1]);
		if(file.exists() == false)//対応表の有無
		{
			createfile(file);
			tt.taioucreate(player[1]);
		}

		file = new File(path[2]);
		if(file.exists() == false)//カード表の有無
		{
			createfile(file);
			ct.cardcreate(player[1]);
		}

		file = new File(path[3]);
		if(file.exists() == false)//room.txtの有無
		{
			createfile(file);
			rt.createroomtxt(player[1],file);
		}

		System.out.println("GameStartクラス上での情報だお");
		System.out.println("user_id:"+userinfo[0]);
		System.out.println("room_id:"+userinfo[1]);
		System.out.println("player_number:"+userinfo[2]);

		return userinfo;


	}

	void createfile(File newfile)
	{
		try
		{
			if(newfile.createNewFile())
			{
				System.out.println("ファイル作れたお");
				newfile.setWritable(true,false);
				newfile.setReadable(true,false);
				newfile.setExecutable(true,false);
			}
		}

		catch (IOException e)
		{
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}
