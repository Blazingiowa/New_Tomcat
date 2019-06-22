package test_tomcat_git;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

public class StartupText extends TextWrite/* 月曜中に対応表作成テキストクラスとデータベースクラスの分割をやること*/
{
	void textfile(int room_id,int player_number,int line_number)
	{
		settext(room_id,player_number);
		text = ""; writetext = "";

		int[] defSet = new int[3];
		int[] hpSet = {-1,100,100};
		int[] manaSet = {-1,1,1};
		int[] defDamage = {0,0,0};

		for(int i = 0;i<defSet.length;i++)
		{
			defSet[i] = -1;
		}

		for(int i = 0;i<defSet.length;i++)//writetextに1行分の-1を挿入
		{
			writetext += defSet[i];

			if((i+1)<defSet.length)
			{
				writetext += ",";
			}
		}

		line[0] = "0,-1,-1,s,";

		for(int i = 1;i<line.length;i++)
		{
			line[i] =writetext;

			text +=writetext;

			if((i+1)<line.length)
			{
				text = text+",s,";
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

		write(room_id,player_number,1,hpSet);
		write(room_id,player_number,2,manaSet);
		write(room_id,player_number,4,defDamage);
		write(room_id,player_number,6,defDamage);

	}
}
