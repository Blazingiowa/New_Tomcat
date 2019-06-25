package test_tomcat_git;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CardText extends TextWrite //カードリストテキストを作るクラス
{
	DataBaseConnectCard DBCC = new DataBaseConnectCard();
	ResultSet rs;
	int[][] cardlist;

	void cardcreate(int room)
	{
		file = new File("var/www/html/"+room+"/card.txt");
		/*データベースにアクセスしカード情報を確保する*/
		rs = DBCC.cardinfo();
		/*取得したデータをもとにテキストファイルに出力する*/
		cardlist = new int[20][3];
		line = new String[20];

		try
		{
			rs.next();
			for(int i=0;i<cardlist.length;i++)
			{
				cardlist[i][0] = rs.getInt("card_id");
				cardlist[i][1] = rs.getInt("dmg");
				cardlist[i][2] = rs.getInt("cost");
				rs.next();
			}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		for(int i = 0;i<line.length;i++)
		{
			line[i] = cardlist[i][0]+","+cardlist[i][1]+","+cardlist[i][2];
		}

		for(int i = 0;i<line.length;i++)
		{
			writetext = line[i];
			if((i+1)<line.length)
			{
				writetext += "s";
			}
		}

		try
		{
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			pw = new PrintWriter(bw);
			pw.println(writetext);
		}
		catch(Exception e)
		{

		}
		finally
		{
			bwclose();
		}
	}
}
