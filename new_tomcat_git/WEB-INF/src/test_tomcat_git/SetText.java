package test_tomcat_git;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SetText
{
	BufferedReader br;
	File file;
	String text;
	String[][] alltext;
	String[] line;
	

	String[] settext(int room,int player)
	{
		if(player==3)
		{
			alltext = new String[2][3];
			line = new String[2];
			//data = new int[3];
			file = new File("/var/www/html/"+ room + "/room.txt");
			System.out.println("room.txt見ようとしてるよ");
		}
		else
		{
			alltext = new String[7][3];
			line = new String[7];
			//data = new int[3];
			file = new File("/var/www/html/"+ room + "/"+player+".txt");
		}



		try
		{
			br = new BufferedReader(new FileReader(file));

			text = br.readLine();
			System.out.println("Readでのテキストファイルの中身");
			System.out.println(text);

			String[] array = text.split(",");

			for(int i = 0,j = 0,k=0;k<array.length;k++)
			{
				if(!array[k].equals("s"))
				{
					alltext[i][j] = array[k];

					j++;
				}
				else
				{
					i++;
					j = 0;
				}
			}

			for(int i = 0;i<alltext.length;i++)
			{
				line[i] = alltext[i][0]+","+alltext[i][1]+","+alltext[i][2];
			}

		}
		catch(Exception e)
		{

		}
		finally
		{
			brclose();
		}

		for(int i = 0;i<line.length;i++)
		{
			System.out.println(line[i]);
		}
		return line;
	}

	private void brclose()
	{
		if(br!=null)
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
	}
}
