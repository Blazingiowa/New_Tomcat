package test_tomcat_git;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CooltimeText extends TextWrite //カードのクールタイムを初期化し出力するクラス
{
	CardText ct;
	CreateStatement cs;

	private int[] cooltimelist;
	private String line;
	private PreparedStatement pstmt;
	private ResultSet rs;

	CooltimeText()
	{
		super();
		cs = new CreateStatement();
		ct = new CardText();
		writetext =null;
		line = null;
	}

	void createcooltime(File file)
	{
		cooltimelist = new int[ct.CardCount()];

		pstmt = cs.SerchCardTabeleText();
		//Statement stmt = CC.createstatement(conn = CC.createconnection());//ステートメントを取得
		try
		{
			rs = pstmt.executeQuery();

			//検索結果がなくなるまでcooltimelistにcostを格納
			for(int i = 0;rs.next();i++)
			{
				cooltimelist[i] = rs.getInt("cost");

				//初期はクールタイムは-2される
				if(cooltimelist[i]-2<0)
				{
					cooltimelist[i]=0;
				}
				else
				{
					cooltimelist[i] -= 2;
				}
			}
		}
		catch(SQLException e)
		{
			System.out.println("cooltaimeのSQL周辺が動かなかったよ(´・ω・｀)切り分けしてね");
		}

		//cooltimeをテキストに出力する形にする
		for(int i = 0;i<cooltimelist.length;i++)
		{
			line += cooltimelist[i];
			if((i+1)<cooltimelist.length)
			{
				line += ",";
			}
		}

		writetext = line+"s"+line;//sを改行文字として二人分のcooltimeを用意する

		//System.out.println("txtに出力される文字だよ");
		//System.out.println(writetext);
		try//cooltime.txtに出力する
		{
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			pw = new PrintWriter(bw);
			pw.println(writetext);
		}
		catch(Exception e)
		{
			System.out.println(e);
			System.out.println("cooltime.txtに出力できたかったよ");
		}
		finally
		{
			bwclose();
		}

	}
}
