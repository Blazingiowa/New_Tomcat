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

public class TaiouText extends CardText
{
	DataBaseConnectRead DBCR = new DataBaseConnectRead();

	DataBaseConnectCard DBCC = new DataBaseConnectCard();
	ResultSet rs;
	Connection conn;
	protected String url = "jdbc:mysql://localhost:3306/u22?characterEncoding=UTF-8&serverTimezone=JST"; //データベースのURLまたはIPアドレス、ローカルの場合はパス
	protected String user = "root";//データベースへアクセスするID
	protected String password = "ncc_NCC2019";//データベースのパスワード


	void taioucreate(int room)
	{


		conn = connect();
		if(conn != null)
		{
			System.out.println("connは入ってるよ");
		}
		try
		{

			Statement stmt2 = conn.createStatement();
			stmt2.executeQuery("SELECT * FROM card;");
			rs = stmt2.getResultSet();
		}
		catch(SQLException e)
		{

		}
		finally
		{

		}

		file = new File("var/www/html/"+room+"/taiou.txt");//room_idを使用してファイルを作成
		/*データベースにアクセスし対応表を確保する*/
		//rs = DBCC.cardinfo();
		/*取得したデータをもとにテキストファイルに出力する*/
		cardlist = new int[20][6];
		line = new String[20];

		for(int i = 0;i<cardlist.length;i++)
		{
			for(int j = 0;j<cardlist[i].length;j++)
			{
				cardlist[i][j]=-1;
			}
		}

		try
		{

			int a = 0;
			/*for(int i =0;i<cardlist.length;i++)
>>>>>>> branch 'neo_yt' of https://github.com/Blazingiowa/New_Tomcat.git
			{
<<<<<<< HEAD
				System.out.println("rsは入ってるよ");
			}

			int a=0;
			//rs.next();
			while(rs.next())
			{
				cardlist[a][0] = rs.getInt("card_id");
=======
				rs.next();
				cardlist[i][0] = rs.getInt("card_id");
>>>>>>> branch 'neo_yt' of https://github.com/Blazingiowa/New_Tomcat.git
				String[] array = rs.getString("taio_id").split(",");

				for(int j = 0,k=1;j<array.length;j++,k++)
				{

					cardlist[a][k] = Integer.parseInt(array[j]);
				}
<<<<<<< HEAD
				a++;

=======
				//rs.next();
			}*/

			while(rs.next())
			{
				cardlist[a][0] = rs.getInt("card_id");
				String[] array = rs.getString("taio_id").split(",");

				for(int j = 0,k=1;j<array.length;j++,k++)
				{
					cardlist[a][k] = Integer.parseInt(array[j]);
				}
				a++;
			}
		}

		catch (SQLException e)
		{
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		for(int i =0;i<line.length;i++)
		{
			for(int j = 0;j<cardlist[i].length;j++)
			{
				line[i] += cardlist[i][j];
				if((i+1)<cardlist[i].length)
				{
					line[i] += ",";
				}
			}
		}

		for(int i =0;i<line.length;i++)
		{
			writetext += line[i];
			if((i+1)<line.length)
			{
				writetext += ",s,";
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
		Close();
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
