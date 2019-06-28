package test_tomcat_git;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CardText extends TextWrite //カードリストテキストを作るクラス
{
	DataBaseConnectCard DBCC = new DataBaseConnectCard();
	CreateConnection CC = new CreateConnection();
	ResultSet rs;
	int[][] cardlist;
	Connection conn;


	void cardcreate(int room)
	{
		file = new File("var/www/html/"+room+"/card.txt");
		/*データベースにアクセスしカード情報を確保する*/
		//rs = DBCC.cardinfo();
		/*取得したデータをもとにテキストファイルに出力する*/
		cardlist = new int[20][3];
		line = new String[20];

		Statement stmt = CC.createstatement(conn = CC.createconnection());

		try
		{
			stmt.executeQuery("SELECT * FROM card;");
			rs = stmt.getResultSet();
		}
		catch(SQLException e)
		{

		}

		try
		{
			int count = 0;
			while(rs.next())
			{
				cardlist[count][0] = rs.getInt("card_id");
				cardlist[count][1] = rs.getInt("dmg");
				cardlist[count][2] = rs.getInt("cost");
				count++;
			}
			System.out.println("以下はcardtextのデバッグだお");
			System.out.println("cardlist配列の中身だお");
			for(int i =0;i<cardlist.length;i++)
			{
				System.out.println("card_id:"+cardlist[i][0]);
				System.out.print("dmg:"+cardlist[i][1]);
				System.out.print("cost:"+cardlist[i][2]);
			}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			CC.close();
			try
			{
				rs.close();
			}

			catch (SQLException e)
			{
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}

		for(int i = 0;i<line.length;i++)
		{
			line[i] = cardlist[i][0]+","+cardlist[i][1]+","+cardlist[i][2];
		}
		System.out.println("一行ごとの情報だお");
		for(int i =0;i<line.length;i++)
		{
			System.out.println(i+"行目:"+line[i]);
		}

		for(int i = 0;i<line.length;i++)
		{
			writetext += line[i];
			if((i+1)<line.length)
			{
				writetext += ",s,";
			}
		}
		System.out.println("txtに出力される文字だお");
		System.out.println(writetext);

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
