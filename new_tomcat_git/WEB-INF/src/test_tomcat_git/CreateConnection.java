package test_tomcat_git;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateConnection
{
	Statement stmt;
	Connection conn;

	protected final String url = "jdbc:mysql://localhost:3306/u22?characterEncoding=UTF-8&serverTimezone=JST"; //データベースのURLまたはIPアドレス、ローカルの場合はパス
	protected final String user = "root";//データベースへアクセスするID
	protected final String password = "ncc_NCC2019";//データベースのパスワード

	Connection createconnection()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (ClassNotFoundException e1)
		{

			e1.printStackTrace();
		}

		try
		{
			conn = DriverManager.getConnection(url,user,password);
		}
		catch(SQLException e)
		{
			System.out.println("con is null");
		}


		return conn;
	}

	Statement createstatement(Connection conn)
	{

		try
		{
			stmt = conn.createStatement();
		}

		catch (SQLException e)
		{
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return stmt;
	}

	void close()
	{
		try
		{
			conn.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}
