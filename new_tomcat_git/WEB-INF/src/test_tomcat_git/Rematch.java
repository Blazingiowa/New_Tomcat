package test_tomcat_git;

import java.io.File;

public class Rematch extends Gamestart //リマッチ用のクラスunityからリマッチ要望が来た場合ここに飛ばしてね
{
	void resturt(int room_id)
	{
		//ゲームで使用したテキストファイルを初期化する
		files = new File[4];
		files[0] = new File("/var/www/html/game/"+room_id+"/1.txt");
		files[1] = new File("/var/www/html/game/"+room_id+"/2.txt");
		files[2] = new File("/var/www/html/game/"+room_id+"/cooltime.txt");
		files[3] = new File("/var/www/html/game/"+ room_id +"/room.txt");
		int[] write = {0,0,0};

		//
		st.textfile(room_id, 1, files[0]);
		st.textfile(room_id, 2, files[1]);
		tw.write(room_id,3,1,write);

		coolt.createcooltime(files[2]);

	}
}
