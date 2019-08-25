package test_tomcat_git;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class StartupText extends TextWrite //player.txtを出力するクラス
{
	void textfile(int room_id,int player_number,File file)
	{
		text = ""; writetext = "";

		int[] defSet = new int[3];
		int[] hpSet = {-1,100,100};//hp
		int[] manaSet = {-1,1,1};//mana
		int[] defDamage = {0,0,0};//発生したダメージ
		line = new String[7];

		for(int i = 0;i<line.length;i++)
		{
			line[i] = "";
		}

		for(int i = 0;i<defSet.length;i++)
		{
			defSet[i] = -1;//初期値-1を格納
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

		for(int i = 1;i<line.length;i++)//テキストに出力形にする
		{
			line[i] =writetext;
		}

		for(int i = 0;i<line.length;i++)
		{
			text += line[i];
			if((i+1)<line.length)
			{
				text = text+"s";
			}
		}

		/*System.out.println("以下はStartuptTextのデバッグだお");
		System.out.println("一行ごとの情報だお");
		System.out.println("ここでは完全に中の数字は入れきれてないよ書き込みでができてるかは実際に中身を見てね");*/
		for(int i =0;i<line.length;i++)
		{
			System.out.println(i+"行目:"+line[i]);
		}

		try//playerテキストに出力
		{
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			pw = new PrintWriter(bw);
			pw.println(text);
			//System.out.println("playerに出力される文字列"+text);
		}

		catch(Exception e)
		{
			System.out.println("playerni出力できなかった(´・ω・｀)");
		}

		finally
		{
			bwclose();
		}

		//-1を出力した部分で上書きが必要な部分を上書きする
		write(room_id,player_number,1,hpSet);
		write(room_id,player_number,2,manaSet);
		write(room_id,player_number,4,defDamage);
		write(room_id,player_number,6,defDamage);

	}
}
