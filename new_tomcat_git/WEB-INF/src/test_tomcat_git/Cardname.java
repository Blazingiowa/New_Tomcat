package test_tomcat_git;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Cardname extends TextWrite
{
	String writetext;
	String[] line;
	CreateConnection cc = new CreateConnection();
	SQLRepository sr = new SQLRepository();
	CardText ct = new CardText();
	ResultSet rs;
	PreparedStatement pstmt;

	void createcardname(File file)
	{
		line = new String[ct.CardCount()];

		pstmt = cc.createpStatement(cc.createconnection(),sr.SelectCardtext());

		try
		{
			rs = pstmt.executeQuery();
			int count=0;
			while(rs.next())
			{
				line[count] += rs.getString("card_name")+",";
				line[count] += rs.getString("card_text_first")+",";
				line[count] += rs.getString("card_text_second")+",";
				line[count] += rs.getString("hukidashi_first")+",";
				line[count] += rs.getString("hukidashi_fsecond");
			}

		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}

		for(int i=0;i<line.length;i++)
		{
			writetext += line[i];
			if(i<line.length-1)
			{
				writetext +="s";
			}
		}

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
