package test_tomcat_git;

public class GameProject_Integrated_P2 extends GameProject_Main
{
	void IntegratedP2(int[] playerinfo)
	{
		System.out.println("ｐ２で入った時の統合処理");

		//自分のテキストの情報をもってくる
		for(int i = 0;i<textmain.length;i++)
		{
			//テキストの０行目から順番に持ってきて退避
			textW = txR.read(playerinfo[1], playerinfo[2], i);
			for(int j = 0;j<textmain[0].length;j++)
			{
				w = textW[j];//退避させた１次元配列をさらに１つずつ退避
				textmain[i][j] = w;//textmainに格納
			}
		}

		textF = txR.read(playerinfo[1], 1, 3);//ｐ１が使ったカードの情報を持ってきて退避

		System.out.println("ｐ２の統合でｐ１の使ったカードを持ってくる" + textF);

		//ｐ２のところにｐ１の情報を持ってくる
		for (int i = 0; i < p1_card.length; i++)
		{
			textmain[5][i] = textF[i];//２次元配列のｐ１のカード情報のところにセット

			p1_card[i][0] = textF[i];//ｐ１のカード情報の場所にもセット
			p2_card[i][0] = textmain[3][i];//２次元配列からｐ２の使ったカードの情報をセット
		}

		//ｐ１の使ったカードの情報をDBから持ってくる
		for (int i = 0; i < p1_card.length; i++)
		{
			//使ったカードがあったら（つまり、－１でなければ）
			if (p1_card[i][0] != -1)
			{
				p1_cardinfo = DBC.reference(p1_card[i][0]);//p1の使ったカード情報をDBからもってくる

				for (int j = 0; j < p1_cardinfo.length; j++)
				{
					//ｐ１の情報を入れていく
					w = p1_cardinfo[j];
					p1_card[i][j] = w;
				}
			}
		}

		//ｐ２の使ったカードの情報をDBから持ってくる
		for (int i = 0; i < p2_card.length; i++)
		{
			//使ったカードがあったら（つまり、－１でなければ）
			if (p2_card[i][0] != -1)
			{
				p2_cardinfo = DBC.reference(p2_card[i][0]);//p2の使ったカード情報をDBからもってくる

				for (int j = 0; j < p2_cardinfo.length; j++)
				{
					//ｐ２の情報を入れていく
					w = p2_cardinfo[j];
					p2_card[i][j] = w;
				}
			}
		}

		//p2_cardの行を回すためのfor
		for (int i = 0; i < p2_card.length; i++)
		{
			//攻撃が通せるか判定するためのフラグリセット
			flag = false;

			//攻撃が通せるか判定するための変数リセット
			count = 0;

			//使ったカードがあるかどうか判定
			if (p2_card[i][0] != -1)
			{
				//p2_cardの対応IDの列を回すためのfor
				for (int j = 3; j < p2_card[0].length; j++)
				{
					//対応IDがあるかどうかの判定
					if (p2_card[i][j] != -1)
					{
						//p1_cardのIDの行を回すためのfor
						for (int k = 0; k < p1_card.length; k++)
						{
							//p2_cardの使ったカードIDに対応しているIDがp1_cardにあるか判定
							if (p2_card[i][j] == p1_card[k][0])
							{
								//ｐ２のカードが攻撃で、ｐ１の防御に防がれたとき
								if (0 <= p2_card[i][0] && p2_card[i][0] < 12)
								{
									textmain[6][k] -= p2_card[i][2] / 2;//ｐ２が受けたダメージを計算して配列に入れる
									flag = true;
								}
								//ｐ２のカードが防御で、ｐ１の攻撃を防いだとき
								else
								{
									//修正場所
									textmain[4][i] += p1_card[k][2] / 2;//ｐ２がリフレクトしたダメージを計算して配列に入れる
								}
							}

							//対応しているIDでなかった場合
							else
							{
								//攻撃カードかどうかの判定
								if (0 <= p2_card[i][0] && p2_card[i][0] < 12)
								{
									//カウントをプラスする
									count++;

									//カウントが３回溜まっていて、ｐ１の使ったカードに対応IDがなかった場合
									if (count == 6 && flag == false)
									{
										textmain[4][i] += p2_card[i][2];//ｐ２が与えたダメージを配列に入れる
										count = 0;//カウントリセット

										System.out.println("ｐ２の攻撃が通ったよ！" + textmain[4][i] + "ダメージ");
									}
								}
							}

						}
					}
				}
			}
		}

		//ｐ１の使用したカードに攻撃カードがあった場合の処理
		for (int i = 0; i < p1_card.length; i++)
		{
			//攻撃が通せるか判定するためのフラグリセット
			flag = false;

			//攻撃が通せるか判定するための変数リセット
			count = 0;

			//使ったカードがあるかどうか判定
			if (p1_card[i][0] != -1)
			{
				//攻撃カードかどうかの判定
				if (0 <= p1_card[i][0] && p1_card[i][0] < 12)
				{
					//p1_cardの対応IDの列を回すためのfor
					for (int j = 3; j < p1_card[0].length; j++)
					{
						//対応IDがあるかどうかの判定
						if (p1_card[i][j] != -1)
						{
							//p2_cardのIDの行を回すためのfor
							for (int k = 0; k < p2_card.length; k++)
							{
								//p1_cardの使ったカードIDに対応しているIDがp2_cardにあるか判定
								if (p1_card[i][j] == p2_card[k][0])
								{
									//ｐ１のカードが攻撃で、ｐ２の防御に防がれたとき
									if (0 <= p1_card[i][0] && p1_card[i][0] < 12)
									{
										flag = true;
									}
								}

								//対応しているIDでなかった場合
								else
								{
									//カウントをプラスする
									count++;

									//カウントが６回溜まっていて、ｐ２の使ったカードに対応IDがなかった場合
									if (count == 6 && flag == false)
									{
										textmain[6][i] -= p1_card[i][2];//ｐ１が与えたダメージを配列に入れる
										count = 0;//カウントリセット

										System.out.println("ｐ１の攻撃が通ったよ！" + textmain[6][i] + "ダメージ");
									}
								}

							}
						}
					}
				}
			}
		}

		//統合処理の結果、発生したダメージをｈｐから減らす
		//ｐ１のｈｐを減らす
		for (int i = 0; i < textmain[0].length; i++)
		{
			textmain[1][1] -= textmain[4][i];
		}
		//ｈｐが-１以下になった場合０にする
		if(textmain[1][1] <= -1)
		{
			textmain[1][1] = 0;
		}
		//ｐ２のｈｐを減らす
		for (int i = 0; i < textmain[0].length; i++)
		{
			textmain[1][2] += textmain[6][i];
		}
		//ｈｐが-１以下になった場合０にする
		if(textmain[1][2] <= -1)
		{
			textmain[1][2] = 0;
		}

		textmain[2][1]++;//ｐ１の行動値を１増やす
		textmain[2][2]++;//ｐ２の行動値を２増やす

		System.out.println("ここからプレイヤー２のテキスト書き込み");

		//ｐ２の統合処理後の情報をテキストに書き込む
		for (int i = 0; i < textmain.length; i++)
		{
			for (int j = 0; j < textmain[1].length; j++)
			{
				w = textmain[i][j];//２次元配列の情報をセット
				textW[j] = w;
			}
			System.out.println("書き込み10の" + i + "回目");
			txW.write(playerinfo[1], playerinfo[2], i, textW);//テキストに書き込み
		}

		//統合処理後に各プレイヤーの処理判定を０に戻す
		/*ここでtextmainの処理判定部分を０に戻せばこの後に行われるｐ１の情報を
		 	書き込む時にも０を入れてくれる*/
		for (int i = 0; i < textW.length; i++)
		{
			if (i == 0)
			{
				textmain[0][0] = 0;
				w = textmain[0][0];
				textW[i] = w;
			}

			else
			{
				textW[i] = -1;
			}
		}
		System.out.println("書き込み11");
		txW.write(playerinfo[1], 2, 0, textW);//ｐ２の処理判定のところを書き換える

		System.out.println("ここからｐ２で入った時のプレイヤー１のテキスト書き込み");

		//ｐ１の統合処理後の情報をテキストに書き込む
		for (int i = 0; i < textmain.length; i++)
		{
			switch (i)
			{
				case 3:
					for (int j = 0; j < textmain[1].length; j++)
					{
						w = textmain[5][j];
						textW[j] = w;
					}
					break;
				case 4:
					for (int j = 0; j < textmain[1].length; j++)
					{
						w = textmain[6][j] * (-1);
						textW[j] = w;
					}
					break;
				case 5:
					for (int j = 0; j < textmain[1].length; j++)
					{
						w = textmain[3][j];
						textW[j] = w;
					}
					break;
				case 6:
					for (int j = 0; j < textmain[1].length; j++)
					{
						w = textmain[4][j] * (-1);
						textW[j] = w;
					}
					break;
				default:
					for (int j = 0; j < textmain[1].length; j++)
					{
						w = textmain[i][j];//２次元配列の情報をセット
						textW[j] = w;
					}
			}
			System.out.println("書き込み12の" + i + "回目");
			txW.write(playerinfo[1], 1, i, textW);//テキストに書き込み
		}

		System.out.print("ｐ２が１枚目のカードで与えるダメージ：" + textmain[4][0]);
		System.out.print("ｐ２が２枚目のカードで与えるダメージ：" + textmain[4][1]);
		System.out.println("ｐ２が３枚目のカードで与えるダメージ：" + textmain[4][2]);
		System.out.print("ｐ１が１枚目のカードで与えるダメージ：" + textmain[6][0]);
		System.out.print("ｐ１が２枚目のカードで与えるダメージ：" + textmain[6][1]);
		System.out.println("ｐ１が３枚目のカードで与えるダメージ：" + textmain[6][2]);

	}

	//クールタイム処理
	void setCT(int[] playerinfo)
	{

		//クールタイムテキストから情報を持ってくる
		for (int i = 0; i < CT.length; i++)
		{
			//各プレイヤーのクールタイムテキストから情報を持ってきて退避
			textF = txR.read(playerinfo[1], 4, i);
			for (int j = 0; j < CT[0].length; j++)
			{
				w = textF[j];//wに退避
				CT[i][j] = w;//対応した場所に格納
			}
		}

		//クールタイムの短縮処理
		for(int i = 0;i<CT.length;i++)
		{
			for(int j = 0;j<CT[0].length;j++)
			{
				if(CT[i][j] > 0)
				{
					CT[i][j]--;
				}
			}
		}

		//使ったカードのクールタイムをセット
		for (int i = 0; i < 2; i++)
		{
			for (int j = 0; j < textmain[3].length; j++)
			{
				//ｐ１のCTの設定
				if (i == 0)
				{
					if (textmain[3][j] != -1)
					{
						//使用したカードIDのところにクールタイムをセット
						CT[i][textmain[3][j]] = p2_card[j][1];
					}
				}
				//ｐ２のCTの設定
				else if (i == 1)
				{
					if (textmain[5][j] != -1)
					{
						//使用したカードIDのところにクールタイムをセット
						CT[i][textmain[5][j]] = p1_card[j][1];
					}
				}
			}
		}

		//更新したクールタイムの情報をテキストに書き込む
		for(int i = 0;i<CT.length;i++)
		{
			//各プレイヤーのクールタイムの情報を１次元配列に退避させる
			for(int j = 0;j<CT[0].length;j++)
			{
				w = CT[i][j];
				CTwrite[j] = w;
			}
			txW.write(playerinfo[1], 4, i, CTwrite);//テキストに書き込み
		}
	}
}
