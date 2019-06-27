package test_tomcat_git;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TaiouText extends CardText
{
	DataBaseConnectRead DBCR = new DataBaseConnectRead();
	DataBaseConnectCard DBCC = new DataBaseConnectCard();
	CreateConnection CC = new CreateConnection();

	ResultSet rs;
	Connection conn;
	protected String url = "jdbc:mysql://localhost:3306/u22?characterEncoding=UTF-8&serverTimezone=JST"; //データベースのURLまたはIPアドレス、ローカルの場合はパス
	protected String user = "root";//データベースへアクセスするID
	protected String password = "ncc_NCC2019";//データベースのパスワード


	void taioucreate(int room)
	{


		file = new File("var/www/html/"+room+"/taiou.txt");//room_idを使用してファイルを作成
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

		Statement stmt = CC.createstatement(conn = CC.createconnection());
		try
		{
			stmt.executeQuery("SELECT * FROM card;");
			rs = stmt.getResultSet();
		}
		catch(SQLException e)
		{

		}

		try
		{
			int count = 0;
			if(rs != null)
			{
				System.out.println("rsはnullじゃないお");
			}
			while(rs.next())
			{
				cardlist[count][0] = rs.getInt("card_id");
				String[] array = rs.getString("taio_id").split(",");

				for(int j = 0,k=1;j<array.length;j++,k++)
				{
					cardlist[count][k] = Integer.parseInt(array[j]);
				}
				count++;
			}
		}

		catch (SQLException e)
		{
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		finally
		{
			CC.close();
			try
			{
				rs.close();
			}
			catch (SQLException e)
			{
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
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
	}
}
