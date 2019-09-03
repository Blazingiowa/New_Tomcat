package test_tomcat_git;

import java.io.File;

public class Rematch extends Gamestart //リマッチ用のクラスunityからリマッチ要望が来た場合ここに飛ばしてね
{
	int [] line;
	//再選希望が来た場合ここへ
	void wantremacth(int[] info)//ユーザID　ルームID　プレイヤー番号
	{

		files = new File[4];
		files[0] = new File("/var/www/html/game/"+info[1]+"/1.txt");
		files[1] = new File("/var/www/html/game/"+info[1]+"/2.txt");
		files[2] = new File("/var/www/html/game/"+info[1]+"/cooltime.txt");
		files[3] = new File("/var/www/html/game/"+ info[1] +"/room.txt");
		int[] write = {0,0,0};

		//
		st.textfile(info[1], 1, files[0]);
		st.textfile(info[1], 2, files[1]);
		tw.write(info[1],3,1,write);

		coolt.createcooltime(files[2]);

		line = tr.read(info[1],3,0);
		System.out.println("誰かがリマッチを希望したときに読み込んだテキスト");
		for(int i = 0;i<line.length;i++)
		{
			System.out.println(i+"文字目"+line[i]);
		}
		if(line[1]==3&&line[2]==3)
		{
			for(int i = 1;i<line.length;i++)
			{
				line[i]=1;
			}
		}
		tw.write(info[1],3,0,line);


		line = tr.read(info[1],3,0);
		line[info[2]] = 2;
		//一応デバッグ
		System.out.println("再選を申し込んだプレイヤー:"+info[2]);
		System.out.println("書き込まれるテキスト");
		for(int i = 0;i<line.length;i++)
		{
			System.out.println(i+"文字目"+line[i]);
		}
		tw.write(info[1],3,0,line);

		line = tr.read(info[1],1,0);
		if(line[1]==2&&line[2]==2)
		{
			restart(info[1]);
		}
	}

	//再選が同意された場合の処理
	void restart(int room_id)//ルームID
	{
		line = tr.read(room_id,3,0);
		/*
		 もし1で予期せぬ挙動をした場合は1ではなく一度3に変更してそれをunityに認識させてください。
		そうした場合wantrematchメソッドにあるコメントアウト解除してみてください
		*/
		line[1] = 3;
		line[2] = 3;
		tw.write(room_id,3,0,line);

		//ゲームで使用したテキストファイルを初期化する
		files = new File[4];
		files[0] = new File("/var/www/html/game/"+room_id+"/1.txt");
		files[1] = new File("/var/www/html/game/"+room_id+"/2.txt");
		files[2] = new File("/var/www/html/game/"+room_id+"/cooltime.txt");
		files[3] = new File("/var/www/html/game/"+ room_id +"/room.txt");
		int[] write = {0,0,0};
		int[] write2 = {-1,1,1};

		//
		st.textfile(room_id, 1, files[0]);
		st.textfile(room_id, 2, files[1]);
		tw.write(room_id,3,1,write);
		tw.write(room_id,3,0, write2);

		coolt.createcooltime(files[2]);

	}

}
