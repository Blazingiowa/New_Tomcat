package test_tomcat_git;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class RoomText extends TextWrite
{
	void createroomtxt(int room_id,File file)//ルームテキストを作成
	{
		text = ""; writetext="";
		int[] online = {-1,0,0};//左から接続が遮断されたか、一人目が入室、二人目が入室
		int[] turn = {0,0,0};//help me まっすー

		//配列をテキストファイルへ出力する形にする
		for(int i =0;i<online.length;i++)
		{
			writetext += online[i];
			if((i+1)<online.length)
			{
				writetext += ",";
			}
		}

		 writetext += "s";

		for(int i =0;i<turn.length;i++)
		{
			writetext += turn[i];
			if((i+1)<turn.length)
			{
				writetext += ",";
			}
		}

		/*System.out.println("roomtextのデバッグだお");
		System.out.println("出力される文字だお:"+writetext);*/

		try//テキストファイルに出力する
		{
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			pw = new PrintWriter(bw);
			pw.println(writetext);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			bwclose();
		}
	}
}
