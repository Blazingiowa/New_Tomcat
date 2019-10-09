package test_tomcat_git;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Player_name  extends TextWrite//プレイヤー名を保持する
{
	BufferedReader br;

	Player_name()
	{
		super();
	}

	void create_nametext(File file)//テキストファイルを作成する
	{
		text = "-1,-1,-1";//初期化

		writing(file,text);

		/*
		try
		{
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			pw = new PrintWriter(bw);
			pw.print(text);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			bwclose();
		}
		*/

	}

	void set_playername(File file,String name,int number)//入ってきたプレイヤー名をテキストに出力する
	{
		try
		{
			br = new BufferedReader(new FileReader(file));//テキストファイルを読み込む
			text = br.readLine();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				br.close();
			}

			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		String[] array= text.split(",");
		array[number] = name;
		text="";

		for(int i = 0; i<array.length;i++)
		{
			text += array[i];
			if((i+1)<array.length)
			{
				text += ",";
			}
		}

		writing(file,text);

		//書き換えた情報をテキストファイルを出力する
		/*
		 try
		{
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			pw = new PrintWriter(bw);
			pw.print(text);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			bwclose();
		}
		*/

	}
}
