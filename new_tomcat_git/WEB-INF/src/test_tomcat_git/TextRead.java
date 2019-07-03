package test_tomcat_git;

import java.io.BufferedReader;
import java.io.File;

public class TextRead
{
	BufferedReader br;
	File file;
	String text;
	String[][] alltext;
	String[] line;
	SetText set = new SetText();

	int[] data;

	int[] read(int room_id,int player_number,int line_number)
	{
		line = set.settext(room_id,player_number);


		String[] array = line[line_number].split(",");

		for(int i = 0;i<array.length;i++)
		{
			data[i] = Integer.parseInt(array[i]);
		}

		return data;
	}

}
