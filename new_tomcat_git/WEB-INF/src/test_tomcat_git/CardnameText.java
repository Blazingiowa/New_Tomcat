package test_tomcat_git;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CardnameText
{
	int cardcount;
	PreparedStatement pstmt;
	ResultSet rs;
	String[] line;
	String writetext;

	CreateStatement CS = new CreateStatement();
	CardText ct = new CardText();
	TextWrite tw = new TextWrite();

	CardnameText()
	{
		pstmt = CS.CountCard();
		cardcount = 0;
		writetext = null;
	}

	String cardname()
	{
		cardcount = ct.CardCount();
		line = new String[cardcount];
		for(int i =0;i<line.length;i++)
		{
			line[i]=null;
		}

		try
		{
			int count = 0;
			pstmt.executeQuery();
			rs = pstmt.getResultSet();
			while(rs.next())
			{
				line[count] += rs.getString("card_name")+",";
				line[count] += rs.getString("card_text_first")+",";
				line[count] += rs.getString("card_text_first");
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

		return writetext;
	}

}