package test_tomcat_git;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CardnameText extends CardText
{
	int cardcount;
	PreparedStatement pstmt;
	ResultSet rs;

	CreateStatement CS = new CreateStatement();

	CardnameText()
	{
		pstmt = CS.CountCard();
		cardcount = 0;
	}

	void cardnamewrite(File file)
	{
		cardcount = CardCount();
	}

}
