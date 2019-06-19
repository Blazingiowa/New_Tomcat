package test_tomcat_git;

public class Gamestart
{
	Gamemain Gm = new Gamemain();
	Text tx = new Text();

	 DataBaseConnectUpdate DBCU = new  DataBaseConnectUpdate();

	String[] userinfo = new String[3];//ユーザID,ルームID,プレイヤー番号の順番で格納
	int[] player = new int[3];

	String[] createdirectry(String user_name) //
	{
		player = DBCU.update(user_name);

		for(int i = 0;i<userinfo.length;i++)
		{
			userinfo[i] = String.valueOf(player[i]);
		}

		tx.editer(player[1], player[2],0,0,null);

		return userinfo;


	}
}
