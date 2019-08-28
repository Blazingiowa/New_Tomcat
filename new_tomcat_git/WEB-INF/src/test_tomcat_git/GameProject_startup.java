package test_tomcat_git;

public class GameProject_startup extends GameProject_Main
{
	void start(int[] playerinfo)
	{
		//ルーム状況表から情報を持ってくる
		player = txR.read(playerinfo[1], 3, 1);

		//それぞれの状況が１だったらターンが進んでいるのでHPと行動値の情報を更新して、ほかの部分を初期化
		if (player[1] == 1 && player[2] == 1)
		{
			//ユニティが見続ける場所を統合処理が終わったので初期値（０）に戻す
			player[0] = 0;
			//ルーム状況表を０，０にして次の処理ができるように初期化
			player[1] = 0;
			player[2] = 0;

			System.out.println("書き込み１");
			txW.write(playerinfo[1], 3, 1, player);

			//前のターンの時に変化した、HPと行動値の情報を持ってくる
			hp = txR.read(playerinfo[1], playerinfo[2], 1);//hp
			move_pt = txR.read(playerinfo[1], playerinfo[2], 2);//行動値

			//２次元配列の初期化
			for (int i = 0; i < textmain.length; i++)
			{
				for (int j = 0; j < textmain[0].length; j++)
				{
					textmain[i][j] = -1;
				}
			}
			textmain[0][0] = 0;//処理判定を０に戻す
			textmain[1][1] = hp[1];//ｐ１のｈｐを更新
			textmain[1][2] = hp[2];//ｐ２のｈｐを更新
			textmain[2][1] = move_pt[1];//ｐ１の行動値を更新
			textmain[2][2] = move_pt[2];//ｐ２の行動値を更新

			//自分が与えるダメージと相手から受けるダメージの初期値は０にする
			for (int j = 0; j < textmain[0].length; j++)
			{
				textmain[4][j] = 0;
				textmain[6][j] = 0;
			}
			for (int i = 0; i < textmain.length; i++)
			{
				for (int j = 0; j < textmain[0].length; j++)
				{
					w = textmain[i][j];
					textW[j] = w;
				}
				System.out.println("書き込み２の" + i + "回目");
				txW.write(playerinfo[1], 1, i, textW);//ｐ１のテキストを更新

				System.out.println("書き込み３の" + i + "回目");
				txW.write(playerinfo[1], 2, i, textW);//ｐ２のテキストを更新
			}

			//プレイヤーの使ったカード情報の配列の初期化
			for (int i = 0; i < p1_card.length; i++)
			{
				for (int j = 0; j < p1_card[0].length; j++)
				{
					p1_card[i][j] = -1;//ｐ１の情報を初期化
					p2_card[i][j] = -1;//ｐ２の情報を初期化
				}
			}
		}

		//そうでなければ、最初のターンなので普通に初期化
		else
		{
			//（使わないデータの場所には-1）
			//２次元配列の初期化
			for (int i = 0; i < textmain.length; i++)
			{
				for (int j = 0; j < textmain[0].length; j++)
				{
					textmain[i][j] = -1;
				}
			}

			textmain[0][0] = 0;//処理判定を０に戻す
			textmain[1][1] = 100;//ｐ１のｈｐを初期化
			textmain[1][2] = 100;//ｐ２のｈｐを初期化
			textmain[2][1] = 1;//ｐ１の行動値を初期化
			textmain[2][2] = 1;//ｐ２の行動値を初期化

			//自分が与えるダメージと相手から受けるダメージの初期値は０にする
			for (int j = 0; j < textmain[0].length; j++)
			{
				textmain[4][j] = 0;
				textmain[6][j] = 0;
			}

			//プレイヤーの使ったカード情報の配列の初期化
			for (int i = 0; i < p1_card.length; i++)
			{
				for (int j = 0; j < p1_card[0].length; j++)
				{
					p1_card[i][j] = -1;//ｐ１の情報を初期化
					p2_card[i][j] = -1;//ｐ２の情報を初期化
				}
			}
		}

	}
}
