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
	//SetText set = new SetText();

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

	String[] settext(int room,int player)
	{
		if(player==3)
		{
			alltext = new String[2][3];
			line = new String[2];
			data = new int[3];
			file = new File("/var/www/html/game/"+ room + "/room.txt");
			//System.out.println("room.txt見ようとしてるよ");
		}
		else if(player == 4)
		{
			alltext = new String[2][20];
			line = new String[2];
			data = new int[20];
			file = new File("/var/www/html/game/"+ room + "/cooltime.txt");
		}
		else
		{
			alltext = new String[7][3];
			line = new String[7];
			data = new int[3];
			file = new File("/var/www/html/game/"+ room + "/"+player+".txt");
		}



		try
		{
			br = new BufferedReader(new FileReader(file));

			text = br.readLine();
			/*System.out.println("Readでのテキストファイルの中身");
			System.out.println(text);*/

			String[] array = text.split("s");

			for(int i = 0;i<array.length;i++)
			{
				line[i] = array[i];
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

	void brclose()
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
