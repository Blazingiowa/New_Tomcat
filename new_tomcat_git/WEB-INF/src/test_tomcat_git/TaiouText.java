package test_tomcat_git;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Statement;

public class TaiouText extends CardText
{

	void taioucreate(File file)
	{

		//file = new File("var/www/html/"+room+"/taiou.txt");//room_idを使用してファイルを作成
		/*取得したデータをもとにテキストファイルに出力する*/
		cardlist = new int[20][6];
		line = new String[20];

		for(int i =0;i<line.length;i++)
		{
			line[i] = "";
		}
		writetext = "";

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
			System.out.println("以下はTaiouTextのデバッグだお");
			int count = 0;
			/*if(rs != null)
			{
				System.out.println("TaiouText.javaのrsはnullじゃないお");
			}*/
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
			System.out.println("taiou配列の中身だお 出力される-1は正常な値だお");
			for(int i = 0;i<cardlist.length;i++)
			{
				System.out.print("card_id:"+cardlist[i][0]);
				System.out.print("対応1:"+cardlist[i][1]);
				System.out.print("対応2:"+cardlist[i][2]);
				System.out.print("対応3:"+cardlist[i][3]);
				System.out.print("対応4:"+cardlist[i][4]);
				System.out.print("対応5:"+cardlist[i][5]);
				System.out.println("");
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

		System.out.println("cardlist[0].length="+cardlist[0].length);
		for(int i =0;i<line.length;i++)
		{

			for(int j = 0;j<cardlist[i].length;j++)
			{
				line[i] += cardlist[i][j];
				if((j+1)<cardlist[i].length)
				{
					line[i] += ",";
				}
			}


		}
		/*System.out.println("一行ごとの情報だお");
		for(int i =0;i<line.length;i++)
		{
			System.out.println(line[i]);
		}*/



		/*
		for(int i =0;i<line.length;i++)
		{
			writetext += line[i];
			if((i+1)<line.length)
			{
				writetext += ",s,";
			}
		}
		*/

		for(int i =0;i<line.length;i++)
		{
			writetext += line[i];
			if((i+1)<line.length)
			{
				writetext += "s";
			}
		}


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
