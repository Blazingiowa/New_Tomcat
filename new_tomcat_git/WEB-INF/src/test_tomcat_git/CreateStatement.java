package test_tomcat_git;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CreateStatement
{
	PreparedStatement pstmt;
	Connection conn;

	CreateStatement()
	{
		CreateConnection CC = new CreateConnection();
		conn = CC.createconnection();
	}

	PreparedStatement SerchUserTable()
	{
		return pstmt;
	}

	PreparedStatement SerchCardTabele()
	{
		return pstmt;
	}

	PreparedStatement UpdateUserTable()
	{
		return pstmt;
	}

}
