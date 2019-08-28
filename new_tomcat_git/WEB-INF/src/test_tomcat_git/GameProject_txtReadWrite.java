package test_tomcat_git;

public class GameProject_txtReadWrite extends GameProject_Main
{
	void txtReadWrite(int[] playerinfo, int[] usecard)
	{
		System.out.println("テキストの書き読みクラスに入った");
		System.out.println(playerinfo[0]+" "+playerinfo[1]+" "+playerinfo[2]);
		System.out.println(usecard[0]+" "+usecard[1]+" "+usecard[2]);

		//テキストファイルを検索[ルームID][ユーザ番号][行数][書き込みの場合のみ配列をセット]
		//プレイヤーの処理状況の情報が入っている０行目を持ってくる
		textF = txR.read(playerinfo[1], playerinfo[2], 0);
		System.out.println(textF[0]+" "+textF[1]+" "+textF[2]+" ");

		//プレイヤーの処理が終わっているのかどうか判定（０はまだ、１で処理済み）
		if (textF[0] == 0)
		{
			for (int i = 0; i < textmain.length; i++)
			{
				textF = txR.read(playerinfo[1], playerinfo[2], i);

				//テキストに初期で入ってるデータを配列に入れる
				for (int j = 0; j < textW.length; j++)
				{
					w = textF[j];
					textmain[i][j] = w;
				}

			}

			textmain[0][0] = 1;//とりあえず、処理済みにデータを変更

			//for文を使って２次元配列を１次元配列に退避し、テキストファイルに書き込む
			for (int i = 0; i < textmain.length; i++)
			{
				if (i == 3)//３行目の時が自分が使ったカードの情報（０行目から）
				{
					for (int j = 0; j < textmain[1].length; j++)
					{
						w = usecard[j];//退避用変数に使ったカード情報をセット
						textmain[3][j] = w;//２次元配列の方にも入れておく
						textW[j] = w;//テキストに書き込む際に使用する退避用１次元配列にセット
					}
				}
				else//それ以外の時は退避用変数に入れて、そこから１次元配列にデータを入れてテキストに書き込む
				{
					for (int j = 0; j < textmain[1].length; j++)
					{
						w = textmain[i][j];//２次元配列の情報をセット
						textW[j] = w;
					}
				}
				System.out.println("書き込み４の" + i + "回目");
				txW.write(playerinfo[1], playerinfo[2], i, textW);//テキストに書き込み
			}

			//プレイヤー１のときの処理
			if (playerinfo[2] == 1)
			{
				//ルーム状況表を読み込む
				player = txR.read(playerinfo[1], 3, 1);
				if (player[1] == 0 && player[2] == 0)
				{
					player[1] = 1;
					player[2] = 0;
				}

				else if (player[1] == 0 && player[2] == 1)
				{
					player[1] = 1;
					player[2] = 1;
				}
				//ユニティが見続ける場所を++して更新
				player[0]++;

				System.out.println("書き込み５");
				//ルーム状況 表にプレイヤー１の処理が終わったことを書き込む
				txW.write(playerinfo[1], 3, 1, player);
			}

			//プレイヤー２の時の処理
			else if (playerinfo[2] == 2)
			{
				//ルーム状況表を読み込む
				player = txR.read(playerinfo[1], 3, 1);
				if (player[1] == 0 && player[2] == 0)
				{
					player[1] = 0;
					player[2] = 1;
				}

				else if (player[1] == 1 && player[2] == 0)
				{
					player[1] = 1;
					player[2] = 1;
				}
				//ユニティが見続ける場所を++して更新
				player[0]++;

				System.out.println("書き込み６");
				//ルーム状況表にプレイヤー２の処理が終わったことを書き込む
				txW.write(playerinfo[1], 3, 1, player);
			}
		}
	}
}
