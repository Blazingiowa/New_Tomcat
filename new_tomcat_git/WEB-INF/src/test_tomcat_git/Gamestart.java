package test_tomcat_git;

import java.io.File;
import java.io.IOException;

public class Gamestart //ゲームが始まるときに一度だけ実行されるクラス
{

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
	RoomCheck rc = new RoomCheck();
	DBCSercheReserveRoom dr = new DBCSercheReserveRoom();

	String[] userinfo = new String[3];//ユーザーID,ルームID,プレイヤー番号の順番で格納
	String[] error = {"-1","-1","-1"};
	int[] player = new int[3];
	int[] online;
	String url;
	String[] path = new String[6];
	boolean exist,empty;

	File[] files = new File[6];
	/*
	 大野へロビー用の変数を追加しました。ロビーを使用した場合reserveに1をint型で引数として渡してください。使用しない場合は0で大丈夫です
	 ロビーを使用しない、ロビーで部屋を作成した場合はroom_idは0でお願いします。ロビーで部屋を検索した人はroom_idを保持しているのでそれを引数として渡してください。
	 Servletクラスとunityの調整が終わったらメソッドの引数部分、引数を使用している場所のコメントアウトを解除してください。
	 またDataBaseConnectUpdateにもこれ用のコメントアウトがあるため上記同様にコメントアウトを解除してください。
	 */
	String[] createdirectry(String user_name,int reserve,int room_id) //ルームディレクトリやテキストファイルを作成する
	{
		//ルームIDを探索する場合データベース上にその部屋が本当に存在しているか確認する
		if(room_id > 0)
		{
			exist = rc.existroom(room_id);
			empty = rc.roomfull(room_id);
			if(exist == false)
			{
				return error;
			}

			if(empty == false)
			{
				return error;
			}
		}

		if(reserve != 2)
		{
			player = DBCU.updateSQL(user_name,reserve,room_id);//データベースの情報を更新する
		}
		else
		{
			player = dr.entryroom(user_name, room_id);
		}

		player = DBCU.updateSQL(user_name,reserve,room_id);//データベースの情報を更新する

		files[0] = new File("/var/www/html/game/"+player[1]+"/"+player[2]+".txt");
		files[1] = new File("/var/www/html/game/"+player[1]+"/taiou.txt");
		files[2] = new File("/var/www/html/game/"+player[1]+"/card.txt");
		files[3] = new File("/var/www/html/game/"+player[1]+"/room.txt");
		files[4] = new File("/var/www/html/game/"+player[1]+"/cooltime.txt");
		files[5] = new File("/var/www/html/game/"+player[1]+"/player_name.txt");

		for(int i = 0;i<userinfo.length;i++)
		{
			userinfo[i] = String.valueOf(player[i]);
		}

		dir = new File("/var/www/html/game/"+player[1]);//ルームディレクトリの作成
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

		pn.set_playername(files[5], user_name, player[2]);//プレイヤー名をテキストファイル上に保持する

		online = tr.read(player[1],3,0);//プレイヤーのオンライン処理
		/*for(int i =0;i<online.length;i++)
		{
			System.out.println("room.textの中身:"+online[i]);
		}*/

		/*もしゲームエンド後に片方のプレイヤーがルームに残り続けて誰かが入ってくるのを待つ場合に以下のif処理が必要
		*/
		if(online[0]!=-1)
		{
			online[0]=-1;
		}

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

	void permission(File giveperm)//すべてのディレクトリとテキストファイルに権限を付与する
	{
		giveperm.setWritable(true);
		giveperm.setReadable(true,false);
		giveperm.setExecutable(true,false);
	}
}
