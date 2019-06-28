package test_tomcat_git;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TextWrite extends TextRead
{
	BufferedWriter bw;
	PrintWriter pw;
	FileWriter fw;
	String text,writetext;

	void write(int room_id,int player_number,int line_number,int[] write)
	{

		line = settext(room_id,player_number);
		text = "" ; writetext = "";

		for(int i =0;i<write.length;i++)
		{
			writetext += write[i];
			if((i+1)<write.length)
			{
				writetext += ",";
			}
		}

		line[line_number] = writetext;

		for(int i = 0;i<line.length;i++)
		{
			text += line[i];

			if((i+1)<line.length)
			{
				text += "s";
			}
		}
		System.out.println("TextWriteでの書き込まれる文字列だお");
		System.out.println(text);

		try
		{
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			pw = new PrintWriter(bw);
			pw.println(text);
		}
		catch(Exception e)
		{

		}
		finally
		{
			bwclose();
		}
	}

	void bwclose()
	{
		if(bw != null)
		{
			try
			{
				bw.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
