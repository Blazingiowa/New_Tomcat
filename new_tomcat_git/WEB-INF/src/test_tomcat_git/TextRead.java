package test_tomcat_git;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TextRead
{
	BufferedReader br;
	File file;
	String text;
	String[][] alltext;
	String[] line;

	int[] data;

	int[] read(int room_id,int player_number,int line_number)
	{
		line = settext(room_id,player_number);

		String[] array = line[line_number].split(",");

		for(int i = 0;i<array.length;i++)
		{
			data[i] = Integer.parseInt(array[i]);
		}

		return data;
	}

	protected String[] settext(int room,int player)
	{
		if(player==3)
		{
			alltext = new String[2][3];
			line = new String[2];
			data = new int[3];
			file = new File("/var/www/html/"+ room + "/room.txt");
		}
		else
		{
			alltext = new String[10][3];
			line = new String[10];
			data = new int[3];
			file = new File("/var/www/html/"+ room + "/"+player+".txt");
		}



		try
		{
			br = new BufferedReader(new FileReader(file));

			text = br.readLine();
			System.out.println("textread上での読み込んだテキストデータだお");
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
