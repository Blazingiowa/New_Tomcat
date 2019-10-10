package test_tomcat_git;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CardnameText
{
	protected TextRead tr;
	protected BufferedWriter bw;
	protected PrintWriter pw;
	protected FileWriter fw;

	int cardcount;
	PreparedStatement pstmt;
	ResultSet rs;
	String[] line;
	String writetext;

	CreateStatement cs;
	CardText ct;
	TextWrite tw;

	CardnameText()
	{
		cs = new CreateStatement();
		ct = new CardText();
		tw = new TextWrite();

		//pstmt = cs.CountCardSQL();
		cardcount = 0;
		writetext = "";
	}

	void cardname(File file)
	{
		cardcount = ct.CardCount();
		line = new String[cardcount];
		pstmt = cs.CardTextget();
		for(int i =0;i<line.length;i++)
		{
			line[i]=null;
		}

		try
		{
			int count = 0;
			rs = pstmt.executeQuery();
			//rs = pstmt.getResultSet();
			while(rs.next())
			{
				line[count] += rs.getString("card_name")+",";
				line[count] += rs.getString("card_text_first")+",";
				line[count] += rs.getString("card_text_second")+",";
				line[count] += rs.getString("hukidashi_first")+",";
				line[count] += rs.getString("hukidashi_second")+",";
				count++;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		for(int i = 0;i<line.length;i++)
		{
			writetext +=line[i];
			if(i < line.length-1)
			{
				writetext +="s";
			}
		}

		try//テキストファイルに出力する
		{
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			pw = new PrintWriter(bw);
			pw.println(writetext);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			tw.bwclose();
		}
	}

}
