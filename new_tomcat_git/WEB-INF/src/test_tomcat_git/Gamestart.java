package test_tomcat_git;

import java.io.File;
import java.io.IOException;

public class Gamestart
{
	Gamemain Gm = new Gamemain();
	Text tx = new Text();
	File file,dir;

	DataBaseConnectUpdate DBCU = new  DataBaseConnectUpdate();
	TaiouText tt = new TaiouText();
	CardText ct = new CardText();
	RoomText rt = new RoomText();
	StartupText st = new StartupText();
	TextWrite tw = new TextWrite();
	TextRead tr = new TextRead();
	CooltimeText coolt = new CooltimeText();
	Player_name pn = new Player_name();

	String[] userinfo = new String[3];//ユーザーID,ルームID,プレイヤー番号の順番で格納
	int[] player = new int[3];
	int[] online;
	String url;
	String[] path = new String[6];

	File[] files = new File[6];
	String[] createdirectry(String user_name) //
	{
		player = DBCU.update(user_name);

		files[0] = new File("/var/www/html/"+player[1]+"/"+player[2]+".txt");
		files[1] = new File("/var/www/html/"+player[1]+"/taiou.txt");
		files[2] = new File("/var/www/html/"+player[1]+"/card.txt");
		files[3] = new File("/var/www/html/"+player[1]+"/room.txt");
		files[4] = new File("/var/www/html/"+player[1]+"/cooltime.txt");
		files[5] = new File("/var/www/html/"+player[1]+"/player_name.txt");

		for(int i = 0;i<userinfo.length;i++)
		{
			userinfo[i] = String.valueOf(player[i]);
		}

		dir = new File("/var/www/html/"+player[1]);//ルームディレクトリの作成
		dir.mkdir();
		permission(dir);

		if(files[0].exists() == false)//player.txt
		{
			System.out.println(path[1]);
			createfile(files[0]);
			permission(files[0]);
		}
		st.textfile(player[1], player[2],files[0]);

		if(files[1].exists() == false)//対応表の有無
		{
			createfile(files[1]);
			permission(files[1]);
			tt.taioucreate(files[1]);
		}

		if(files[2].exists() == false)//カード表の有無
		{
			createfile(files[2]);
			permission(files[2]);
			ct.cardcreate(files[2]);
		}

		if(files[3].exists() == false)//room.txtの有無
		{
			createfile(files[3]);
			permission(files[3]);
			rt.createroomtxt(player[1],files[3]);
		}

		if(files[4].exists() == false)//cooltime.txtの有無
		{
			createfile(files[4]);
			permission(files[4]);
			coolt.createcooltime(files[4]);
		}

		if(files[5].exists() == false)//player_name保持クラス
		{
			createfile(files[5]);
			permission(files[5]);
			pn.create_nametext(files[5]);
		}

		pn.set_playername(files[5], user_name, player[2]);

		online = tr.read(player[1],3,0);//プレイヤーのオンライン処理
		/*for(int i =0;i<online.length;i++)
		{
			System.out.println("room.textの中身:"+online[i]);
		}*/
		online[player[2]] = 1;

		tw.write(player[1],3,0, online);

		/*System.out.println("GameStartクラス上での情報だお");
		System.out.println("user_id:"+userinfo[0]);
		System.out.println("room_id:"+userinfo[1]);
		System.out.println("player_number:"+userinfo[2]);*/

		return userinfo;
	}

	void createfile(File newfile)
	{
		try
		{
			if(newfile.createNewFile())
			{
				//System.out.println("ファイル作れたお＼(^_^)／");
			}
		}
		catch (IOException e)
		{
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	void permission(File giveperm)
	{
		giveperm.setWritable(true);
		giveperm.setReadable(true,false);
		giveperm.setExecutable(true,false);
	}
}
