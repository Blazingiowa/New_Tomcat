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
	CreateConnection CC = new CreateConnection();
	ResultSet rs;
	Connection conn;
	int[][] cardlist;



	void cardcreate(File file)
	{
		//配列の宣言
		cardlist = new int[20][3];
		line = new String[20];

		for(int i =0;i<line.length;i++)//配列lineを初期化
		{
			line[i] = "";
		}
		writetext = "";

		Statement stmt = CC.createstatement(conn = CC.createconnection());//ステートメントを取得

		try
		{
			stmt.executeQuery("SELECT * FROM card;");//カードの情報を取得するためのSQL
			rs = stmt.getResultSet();
		}
		catch(SQLException e)
		{
			System.out.println("card.txt作成時にデータベースから情報が取れなかったよ");
		}

		try
		{
			int count = 0;
			while(rs.next())//データベースからの検索結果を最後まで取得
			{
				cardlist[count][0] = rs.getInt("card_id");
				cardlist[count][1] = rs.getInt("dmg");
				cardlist[count][2] = rs.getInt("cost");
				count++;
			}
			/*System.out.println("以下はcardtextのデバッグだよ");
			System.out.println("cardlist配列の中身だよ");
			for(int i =0;i<cardlist.length;i++)
			{
				System.out.print("card_id:"+cardlist[i][0]);
				System.out.print("dmg:"+cardlist[i][1]);
				System.out.print("cost:"+cardlist[i][2]);
				System.out.println("");
			}*/

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			CC.close();//データベースとの接続を解除
			try
			{
				rs.close();//ResultSetをクローズ
			}

			catch (SQLException e)
			{
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}

		for(int i = 0;i<line.length;i++)//テキストファイルに書き込む情報を行ごとにまとめる
		{
			line[i] = cardlist[i][0]+","+cardlist[i][1]+","+cardlist[i][2];
		}
		/*System.out.println("一行ごとの情報だよ");
		for(int i =0;i<line.length;i++)
		{
			System.out.println(i+"行目:"+line[i]);
		}*/

		for(int i = 0;i<line.length;i++)//sを改行文字として1行にまとめる
		{
			writetext += line[i];
			if((i+1)<line.length)
			{
				writetext += "s";
			}
		}
		/*System.out.println("txtに出力される文字だよ");
		System.out.println(writetext);*/

		try//テキストファイルを出力する
		{
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			pw = new PrintWriter(bw);
			pw.println(writetext);
		}
		catch(Exception e)
		{
			System.out.print(e);
			System.out.println("card.txtに書き込めなかったよ");
		}
		finally
		{
			bwclose();//テキストファイルへの出力関係をクローズ
		}
	}


}
