package test_tomcat_git;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class RoomText extends TextWrite
{
	void createroomtxt(int room_id,File file)
	{
		text = ""; writetext="";
		int[] online = {-1,0,0};
		int[] turn = {0,0,0};

		for(int i =0;i<online.length;i++)
		{
			writetext += online[i];
			if((i+1)<online.length)
			{
				writetext += ",";
			}
		}

		writetext += ",s,";

		for(int i =0;i<turn.length;i++)
		{
			writetext += turn[i];
			if((i+1)<turn.length)
			{
				writetext += ",";
			}
		}

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
}
