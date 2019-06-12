package test_tomcat_git;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Text
{
	//DataBaseConnect DBC = new DataBaseConnect();

	BufferedReader br;
	FileWriter filewriter;
	BufferedWriter bw;
	PrintWriter pw;

	int[] playerinfo;//配列数は仮設定


	//int roomid,playernumber;//ルーム番号をintにキャスト

	File file;

	int[] editer(int room,int number,int linenumber,int WriteorRead,int[] rewrite)//試験的に作るため呼び出し禁止 room 部屋番号　number プレイヤー番号 書き換える配列
	{
		playerinfo = new int[3];
		String[] line = new String[10];//配列数(行数)は仮設定、各行の情報が入力
		String[][] alltext = new String[10][3];

		if(number == 3)//numberが3であたったらルームファイルを確認するまた、この場合はlinenumberにプレイヤー番号が入る
		{
			file = new File("");
			number = linenumber;
			linenumber = 0;
		}
		else
		{
			file = new File("");//roomidとplayernumberを使用してファイルを特定
		}


		//以下テキストファイル読み込み
		try
		{
			br = new BufferedReader(new FileReader(file));
			String str = br.readLine();

			String[] array = str.split(",");

			for(int i = 0,j = 0,k = 0;k<array.length;k++)
			{
				System.out.println(i+","+j+","+k);
				if(!array[k].equals("s"))
				{
					alltext[i][j] = array[k];

					j++;
				}
				else
				{
					i++;
					j=0;
				}

			}

			for(int i =0;i<alltext.length;i++)
			{
				line[i] = alltext[i][0]+","+alltext[i][1]+","+alltext[i][2];
			}

		}

		catch(Exception e)
		{
			//何かしらレスポンスを記述
		}

		finally
		{
			close();
		}

		//以下読み書き処理
		if(WriteorRead == 0)
		{
			if(rewrite == null)
			{
				startup(line);//一番最初はlineの中身が空だから-1を入力しテキストファイルの更新で最初の行を変更
				/*rewrite = new int[3];
				rewrite[0] = 0;
				rewrite[1] = -1;
				rewrite[2] = -1;*/
			}

			else
			{
				filewriter(line,linenumber,rewrite);//テキストファイルの更新　行配列、行番号、書き込む配列
			}

			return null;
		}
		else
		{
			filereader(line,linenumber);//テキストファイルの読み込み　行配列、行番号

			return playerinfo;
		}
	}

	private String[] startup(String[] lineinfo)//ゲームの初期設定
	{
		String text = "",linestr ="";

		int[] defSet = new int[3];
		int[] hpSet = {-1,100,100};
		int[] manaSet = {-1,1,1};
		int[] defDamage = {0,0,0};


		for(int i = 0;i<defSet.length;i++)
		{
			defSet[i] = -1;
		}

		for(int i = 0;i<defSet.length;i++)//linestrに1行分の-1を挿入
		{
			linestr = linestr+defSet[i];

			if((i+1)<defSet.length)
			{
				linestr = linestr+",";
			}
		}

		/*以下textファイルへ出力*/
		lineinfo[0] = "0,-1,-1,s,";

		for(int i = 1;i<lineinfo.length;i++)
		{
			lineinfo[i] = linestr;

			text = text+linestr;

			if((i+1)<lineinfo.length)
			{
				text = text+",s,";
			}
		}

		try
		{
			filewriter = new FileWriter(file);
			bw = new BufferedWriter(filewriter);
			pw = new PrintWriter(bw);
			pw.println(text);
		}

		catch(Exception e)
		{

		}

		finally
		{
			close();
		}

		/*-1以外の初期値を入力*/
		filewriter(lineinfo,1,hpSet);
		filewriter(lineinfo,2,manaSet);
		filewriter(lineinfo,4,defDamage);
		filewriter(lineinfo,6,defDamage);

		return null;
	}

	private void filewriter(String[] lineinfo,int linenumber,int[] write) //ファイル書き込み
	{
		String text = "",linestr = "";

		for(int i = 0;i<write.length;i++)
		{
			linestr = linestr+write[i];
			if((i+1)<write.length)
			{
				linestr = linestr+",";
			}
		}

		lineinfo[linenumber] = linestr;

		for(int i = 0;i<lineinfo.length;i++)
		{
			text = text+lineinfo[i];

			if((i+1)<lineinfo.length)
			{
				text = text+",s,";
			}
		}

		try
		{
			filewriter = new FileWriter(file);

			bw = new BufferedWriter(filewriter);
			pw = new PrintWriter(bw);
			pw.println(text);
		}

		catch(Exception e)
		{

		}

		finally
		{
			close();
		}
	}

	private void filereader(String[] lineinfo,int linenumber) //ファイル読み込み
	{
		//カンマ区切り解除
		String[] temporary = lineinfo[linenumber].split(",");

		//以下int配列へキャスト
		for(int i = 0;i<temporary.length;i++)
		{
			playerinfo[i] = Integer.parseInt(temporary[i]);
		}
	}


	void close()
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
