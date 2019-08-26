package test_tomcat_git;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateConnection //データベースに接続するための準備をするクラス
{
	Statement stmt;
	Connection conn;

	protected final String url = "jdbc:mysql://localhost:3306/u22?characterEncoding=UTF-8&serverTimezone=JST"; //データベースのURLまたはIPアドレス、ローカルの場合はパス
	protected final String user = "root";//データベースへアクセスするID
	protected final String password = "ncc_NCC2019";//データベースのパスワード

	Connection createconnection()//データベースと接続を作成しConnectionを返す
	{
		try//使用するJDBC
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (ClassNotFoundException e1)
		{

			e1.printStackTrace();
		}

		try//データベースに接続する
		{
			conn = DriverManager.getConnection(url,user,password);
		}
		catch(SQLException e)
		{
			System.out.println("con is null");
		}


		return conn;
	}

	Statement createstatement(Connection conn)//ステートメントを作成し返す
	{

		try
		{
			stmt = conn.createStatement();//ステートメントを作成する
		}

		catch (SQLException e)
		{
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return stmt;
	}

	void close()//接続をクローズする
	{
		try
		{
			conn.close();//接続切断
			stmt.close();//ステートメントをクローズ
		}
		catch (SQLException e)
		{
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}
