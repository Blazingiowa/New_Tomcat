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
		System.out.println("▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲書き込みXのY回目の後ならまっすーがくれた数字");
		for(int i =0;i<write.length;i++)
		{
			System.out.println(write[i]);
		}
		//System.out.println(room_id+","+player_number+","+line_number);
		line = settext(room_id,player_number);
		text = "" ; writetext = "";
		//System.out.println("writetext上の最初のlineだよ");
		/*for(int i =0;i<line.length;i++)
		{
			System.out.println(i+"行目:"+line[i]);
		}*/

		for(int i =0;i<write.length;i++)
		{
			writetext += write[i];
			if((i+1)<write.length)
			{
				writetext += ",";
			}
		}
		/*System.out.println("更新する文字列だお");
		System.out.println(writetext);*/


		line[line_number] = writetext;

		for(int i = 0;i<line.length;i++)
		{
			text += line[i];

			if((i+1)<line.length)
			{
				text += "s";
			}
		}

		/*System.out.println("TextWriteでの書き込まれる文字列だお");
		System.out.println(text);
		System.out.println("書き込むのは"+line_number+"行目だよ");*/

		System.out.println("writetext上の書き込まれたtxtだよ");
		for(int i =0;i<line.length;i++)
		{
			System.out.println(i+"行目:"+line[i]);
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
