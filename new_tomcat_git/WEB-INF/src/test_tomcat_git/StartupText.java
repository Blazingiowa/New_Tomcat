package test_tomcat_git;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

public class StartupText extends TextWrite
{
	void textfile(int room_id,int player_number)
	{
		text = ""; writetext = "";

		int[] defSet = new int[3];
		int[] hpSet = {-1,100,100};
		int[] manaSet = {-1,1,1};
		int[] defDamage = {0,0,0};
		line = new String[7];

		for(int i = 0;i<line.length;i++)
		{
			line[i] = "";
		}

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

		line[0] = "0,-1,-1";

		for(int i = 1;i<line.length;i++)
		{
			line[i] =writetext;
		}

		for(int i = 0;i<line.length;i++)
		{
			text += line[i];
			if((i+1)<line.length)
			{
				text = text+",s,";
			}
		}
		System.out.println("以下はStartuptTextのデバッグだお");
		System.out.println("一行ごとの情報だお");
		System.out.println("ここでは完全に中の数字は入れきれてないよ書き込みでができてるかは実際に中身を見てね");
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

		write(room_id,player_number,1,hpSet);/*
		write(room_id,player_number,2,manaSet);
		write(room_id,player_number,4,defDamage);
		write(room_id,player_number,6,defDamage);*/

	}
}
