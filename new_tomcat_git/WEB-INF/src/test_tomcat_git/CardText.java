package test_tomcat_git;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CardText extends TextWrite //カードリストテキストを作るクラス
{
	DataBaseConnectCard DBCC = new DataBaseConnectCard();
	CreateConnection CC = new CreateConnection();
	ResultSet rs;
	int[][] cardlist;
	Connection conn;
	protected String url = "jdbc:mysql://localhost:3306/u22?characterEncoding=UTF-8&serverTimezone=JST"; //データベースのURLまたはIPアドレス、ローカルの場合はパス
	protected String user = "root";//データベースへアクセスするID
	protected String password = "ncc_NCC2019";//データベースのパスワード

	void cardcreate(int room)
	{
		file = new File("var/www/html/"+room+"/card.txt");
		/*データベースにアクセスしカード情報を確保する*/
		//rs = DBCC.cardinfo();
		/*取得したデータをもとにテキストファイルに出力する*/
		cardlist = new int[20][3];
		line = new String[20];

		conn = connect();
		Statement stmt = CC.createstatement(conn = CC.createconnection());

		try
		{
			stmt.executeQuery("SELECT * FROM card;");
			rs = stmt.getResultSet();
		}
		catch(SQLException e)
		{

		}
		finally
		{

		}

		try
		{
			int count = 0;
			while(rs.next())
			{
				cardlist[count][0] = rs.getInt("card_id");
				cardlist[count][1] = rs.getInt("dmg");
				cardlist[count][2] = rs.getInt("cost");
				count++;
			}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		for(int i = 0;i<line.length;i++)
		{
			line[i] = cardlist[i][0]+","+cardlist[i][1]+","+cardlist[i][2];
		}

		for(int i = 0;i<line.length;i++)
		{
			writetext = line[i];
			if((i+1)<line.length)
			{
				writetext += "s";
			}
		}

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

	Connection connect()
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
			//DriverManager.setLoginTimeout(timeoutseconds);
		}
		catch(SQLException e)
		{

		}
		Close();

		return conn;
	}

	void Close()
	{
		try
		{
			if (conn != null)
			{
				conn.close();
			}
		}
		catch(SQLException e)
		{
			System.out.println(e);
			//例外処理
		}
	}
}
