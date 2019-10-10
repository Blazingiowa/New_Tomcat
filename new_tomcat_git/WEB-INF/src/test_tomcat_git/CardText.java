package test_tomcat_git;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CardText extends TextWrite //カードリストテキストを作るクラス
{
	CreateStatement cs;

	PreparedStatement select_card_pstmt,count_card_pstmt;
	ResultSet card_rs,count_rs;
	Connection conn;
	private final int item = 4;
	private int[][] cardlist;

	CardText()
	{
		super();
		cs = new CreateStatement();
	}

	void cardcreate(File file)
	{
		//配列の宣言
		cardlist = new int[CardCount()][item];
		line = new String[CardCount()];

		for(int i =0;i<line.length;i++)//配列lineを初期化
		{
			line[i] = "";
		}
		writetext = "";

		//Statement stmt = CC.createstatement(conn = CC.createconnection());//ステートメントを取得
		select_card_pstmt = cs.SerchAllCard();

		/*
		try
		{
			//stmt.executeQuery("SELECT * FROM card;");//カードの情報を取得するためのSQL

		}
		catch(SQLException e)
		{
			System.out.println("card.txt作成時にデータベースから情報が取れなかったよ");
		}
		*/

		try
		{
			card_rs = select_card_pstmt.executeQuery();

			int count = 0;
			while(card_rs.next())//データベースからの検索結果を最後まで取得
			{
				cardlist[count][0] = card_rs.getInt("card_id");
				cardlist[count][1] = card_rs.getInt("dmg");
				cardlist[count][2] = card_rs.getInt("cost");
				cardlist[count][3] = card_rs.getInt("type");
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

			try
			{
				card_rs.close();//ResultSetをクローズ
			}

			catch (SQLException e)
			{
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			cs.closepstmt(select_card_pstmt);
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

	//DBに登録されているカード枚数をカウントし返すメソッド
	int CardCount()
	{
		int number = 0;
		count_card_pstmt = cs.CountCardSQL();
		try
		{
			count_rs = count_card_pstmt.executeQuery();
			count_rs.next();
			number = count_rs.getInt("number");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		cs.closepstmt(count_card_pstmt);

		try
		{
			count_rs.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return number;
	}


}
