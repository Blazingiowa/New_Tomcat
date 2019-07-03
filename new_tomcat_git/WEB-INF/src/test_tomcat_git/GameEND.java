package test_tomcat_git;

public class GameEND
{
	//プレイヤーが退出したときのDBを更新するためのクラスをインスタンス
	DataBasePlayerout DBP = new DataBasePlayerout();

	void logout(int info[])
	{

		DBP.logout(info);
	}
}
