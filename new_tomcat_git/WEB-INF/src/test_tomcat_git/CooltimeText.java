package test_tomcat_git;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Statement;

public class CooltimeText extends CardText
{
	void createcooltime(File file)
	{
		int[] cooltimelist = new int[20];

		writetext = "";
		String line = "";

		Statement stmt = CC.createstatement(conn = CC.createconnection());
		try
		{
			stmt.executeQuery("SELECT cost FROM card;");
			rs = stmt.getResultSet();

			for(int i = 0;rs.next();i++)
			{
				cooltimelist[i] = rs.getInt("cost");
				cooltimelist[i] -= 2;
			}
		}
		catch(SQLException e)
		{
			System.out.println("cooltaimeのSQL周辺が動かなかったお(´・ω・｀)切り分けしてね");
		}

		for(int i = 0;i<cooltimelist.length;i++)
		{
			line += cooltimelist[i];
			if((i+1)<cooltimelist.length)
			{
				line += ",";
			}
		}

		/*
		writetext = line+",s,"+line;
		*/
		writetext = line+"s"+line;

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
