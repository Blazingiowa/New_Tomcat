package test_tomcat_git;

public class Gamestart
{
	Gamemain Gm = new Gamemain();
	Text tx = new Text();
	DataBaseConnectRead DBCR = new DataBaseConnectRead();
	String[] userinfo = new String[3];//ユーザID,ルームID,プレイヤー番号の順番で格納
	int[] player = new int[3];

	String[] createdirectry(String user_name) //
	{
		player = DBCR.update(user_name);

		for(int i = 0;i<userinfo.length;i++)
		{
			userinfo[i] = String.valueOf(player[i]);
		}

		tx.editer(player[1], player[2],0,0,null);

		//ローカル上のフォルダを検索

		/*------------------------以下再考中-----------------------------------*/
		/*
		 try{
		    	Path path1 = Paths.get("");//相対指定でルームidフォルダ作成
		    	System.out.println(path1);
				Files.createDirectory(path1);
			}
		    catch (IOException e)
		    {
				e.printStackTrace();
			}
		*/
		return userinfo;


	}
}
