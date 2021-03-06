package test_tomcat_git;

public class GameProject
{
	//ルーム状況を入れるための配列
	int[] player = new int[2];

	int[][] p1_card = new int[3][8];//統合処理の時の、カード判定時に使う
	int[][] p2_card = new int[3][8];//上に同じ

	int[] hp;//ｈｐの情報を格納するための配列
	int[] move_pt;//行動値の情報を格納するための配列

	int[] p1_cardinfo;//DBから持ってきたカード情報を退避させるための配列(p1
	int[] p2_cardinfo;//DBから持ってきたカード情報を退避させるための配列(p2

	int[] textW = new int[3];//テキストファイルの内容を一時的に避難させるための１次元配列
	int[] textF;//テキストファイルの内容を一時的に避難させるための可変可能な１次元配列
	int w;//みんな大好き一時退避変数だよ！＜＜０にｗを代入＞＞
	int[][] textmain = new int[7][3];//避難させた内容を格納するための配列

	//攻撃が通せるかどうか判定するための変数とフラグ
	int count = 0;
	boolean flag = false;

	DataBaseConnectRead DBC = new DataBaseConnectRead();//DBクラスのインスタンス
	Text tx = new Text();//テキストクラスのインスタンス

	/*infoの配列内容------------------------/useの配列の内容--------/
	/										/						/
	/	[ユーザID][ルームID][ユーザ番号]	/	[カードID][][]...	/
	/										/						/
	/---------------------------------------/----------------------*/

	//メインメソッド
	void main(int[] info, int[] use)
	{
		//textmainの内容を初期化
		start(info);

		//テキストファイルを検索[ルームID][ユーザ番号][行数][書0、読1][書き込みたい配列、読みはnull]
		//プレイヤーの処理状況の情報が入っている０行目を持ってくる
		textF = tx.editer(info[1], info[2], 0, 1, null);

		//テキストを読み込み、書き換え
		txtReadWrite(info, use);

		//ルーム状況表から情報をもってくる[ルームID][共有ファイルについてなので、３][ユーザ番号で行数指定][書０読１][書き込みたい配列またはnull]
		player = tx.editer(info[1], 3, info[2], 1, null);

		//それぞれのプレイヤーが処理が終わっているかどうかの判定
		if (player[0] == 1 && player[1] == 1)
		{
			//統合処理
			Integrated(info);
		}

	}




/*---配列の初期化メソッド----------------------------------------------------------------------------------------------*/
	void start(int[] playerinfo)
	{
		//ルーム状況表から情報を持ってくる
		player = tx.editer(playerinfo[1], 3, playerinfo[2], 1, null);

		//それぞれの状況が１だったらターンが進んでいるのでHPと行動値の情報を更新して、ほかの部分を初期化
		if (player[0] == 1 && player[1] == 1)
		{
			//ルーム状況表を０，０にして次の処理ができるように初期化
			player[0] = 0;
			player[1] = 0;
			tx.editer(playerinfo[1], 3, playerinfo[2], 0, player);

			//前のターンの時に変化した、HPと行動値の情報を持ってくる
			hp = tx.editer(playerinfo[1], playerinfo[2], 1, 1, null);//hp
			move_pt = tx.editer(playerinfo[1], playerinfo[2], 2, 1, null);//行動値

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
				tx.editer(playerinfo[1], 1, i, 0, textW);//ｐ１のテキストを更新
				tx.editer(playerinfo[1], 2, i, 0, textW);//ｐ２のテキストを更新
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




/*----textの読み込みと書き込みメソッド---------------------------------------------------------------------------------*/
	void txtReadWrite(int[] playerinfo, int[] usecard)
	{
		//プレイヤーの処理が終わっているのかどうか判定（０はまだ、１で処理済み）
		if (textF[0] == 0)
		{
			for (int i = 0; i < textmain.length; i++)
			{
				textF = tx.editer(playerinfo[1], playerinfo[2], i, 1, null);

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
				tx.editer(playerinfo[1], playerinfo[2], i, 0, textW);//テキストに書き込み
			}

			//プレイヤー１のときの処理
			if (playerinfo[2] == 1)
			{
				//ルーム状況表を読み込む
				player = tx.editer(playerinfo[1], 3, playerinfo[2], 1, null);
				if (player[0] == 0 && player[1] == 0)
				{
					player[0] = 1;
					player[1] = 0;
				}

				else if (player[0] == 0 && player[1] == 1)
				{
					player[0] = 1;
					player[1] = 1;
				}
				//ルーム状況 表にプレイヤー１の処理が終わったことを書き込む
				tx.editer(playerinfo[1], 3, playerinfo[2], 0, player);
			}

			//プレイヤー２の時の処理
			else if (playerinfo[2] == 2)
			{
				//ルーム状況表を読み込む
				player = tx.editer(playerinfo[1], 3, playerinfo[2], 1, null);
				if (player[0] == 0 && player[1] == 0)
				{
					player[0] = 0;
					player[1] = 1;
				}

				else if (player[0] == 1 && player[1] == 0)
				{
					player[0] = 1;
					player[1] = 1;
				}

				//ルーム状況表にプレイヤー２の処理が終わったことを書き込む
				tx.editer(playerinfo[1], 3, playerinfo[2], 0, player);
			}
		}
	}




/*----統合処理メソッド-------------------------------------------------------------------------------------------------*/
	void Integrated(int[] playerinfo)
	{
		//ｐ１のとき
		if (playerinfo[2] == 1)
		{
			textF = tx.editer(playerinfo[1], 2, 5, 1, null);//ｐ２が使ったカードの情報を持ってきて退避

			//ｐ１のところにｐ２の情報を持ってくる
			for (int i = 0; i < p2_card.length; i++)
			{
				textmain[5][i] = textF[i];//２次元配列のｐ２のカード情報のところにセット

				p2_card[i][0] = textF[i];//ｐ２のカード情報の場所にもセット
				p1_card[i][0] = textmain[3][i];//２次元配列からｐ１の使ったカードの情報をセット
			}

			//使ったカードの情報をDBから持ってくる
			for (int i = 0; i < p1_card.length; i++)
			{
				p1_cardinfo = DBC.reference(p1_card[i][0], 0);//p1の使ったカード情報
				p2_cardinfo = DBC.reference(p2_card[i][0], 0);//p2の使ったカード情報

				for (int j = 0; j < p1_cardinfo.length; j++)
				{
					//先にｐ１の情報を入れていく
					w = p1_cardinfo[j];
					p1_card[i][j] = w;

					//次にｐ２
					w = p2_cardinfo[j];
					p2_card[i][j] = w;
				}
			}

			//p1_cardの行を回すためのfor
			for (int i = 0; i < p1_card.length; i++)
			{
				//攻撃が通せるか判定するためのフラグリセット
				flag = false;

				//使ったカードがあるかどうか判定
				if (p1_card[i][0] != -1)
				{
					//p1_cardの対応IDの列を回すためのfor
					for (int j = 3; j < p1_card[0].length; j++)
					{
						//攻撃が通せるか判定するための変数リセット
						count = 0;

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
										textmain[6][k] -= p1_card[i][2] / 2;//ｐ１が受けたダメージを計算して配列に入れる
										flag = true;
									}
									//ｐ１のカードが防御で、ｐ２の攻撃を防いだとき
									else
									{
										textmain[4][i] += p2_card[k][2] / 2;//ｐ１がリフレクトしたダメージを計算して配列に入れる
									}
								}

								//対応しているIDでなかった場合
								else
								{
									//攻撃カードかどうかの判定
									if (0 <= p1_card[i][0] && p1_card[i][0] < 12)
									{
										//カウントをプラスする
										count++;

										//カウントが３回溜まっていて、ｐ２の使ったカードに対応IDがなかった場合
										if (count == 3 && flag == false)
										{
											textmain[4][i] = p1_card[i][2];//ｐ１が与えたダメージを配列に入れる
											count = 0;//カウントリセット
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
				textmain[1][1] += textmain[6][i];
			}
			//ｐ２のｈｐを減らす
			for (int i = 0; i < textmain[0].length; i++)
			{
				textmain[1][2] -= textmain[4][i];
			}

			textmain[2][1]++;//ｐ１の行動値を１増やす
			textmain[2][2]++;//ｐ２の行動値を２増やす

			//ｐ１の統合処理後の情報をテキストに書き込む
			for (int i = 0; i < textmain.length; i++)
			{
				for (int j = 0; j < textmain[1].length; j++)
				{
					w = textmain[i][j];//２次元配列の情報をセット
					textW[j] = w;
				}

				tx.editer(playerinfo[1], playerinfo[2], i, 0, textW);//テキストに書き込み
			}
			//ｐ２の統合処理後の情報をテキストに書き込む
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

				tx.editer(playerinfo[1], 2, i, 0, textW);//テキストに書き込み
			}
		}

		//ｐ２のとき
		else if (playerinfo[2] == 2)
		{
			textF = tx.editer(playerinfo[1], 1, 5, 1, null);//ｐ１が使ったカードの情報を持ってきて退避

			//ｐ２のところにｐ１の情報を持ってくる
			for (int i = 0; i < p1_card.length; i++)
			{
				textmain[5][i] = textF[i];//２次元配列のｐ１のカード情報のところにセット

				p1_card[i][0] = textF[i];//ｐ１のカード情報の場所にもセット
				p2_card[i][0] = textmain[3][i];//２次元配列からｐ２の使ったカードの情報をセット
			}

			//使ったカードの情報をDBから持ってくる
			for (int i = 0; i < p2_card.length; i++)
			{
				p1_cardinfo = DBC.reference(p1_card[i][0], 0);//p1の使ったカード情報
				p2_cardinfo = DBC.reference(p2_card[i][0], 0);//p2の使ったカード情報

				for (int j = 0; j < p1_cardinfo.length; j++)
				{
					//先にｐ１の情報を入れていく
					w = p1_cardinfo[j];
					p1_card[i][j] = w;

					//次にｐ２
					w = p2_cardinfo[j];
					p2_card[i][j] = w;
				}
			}

			//p2_cardの行を回すためのfor
			for (int i = 0; i < p2_card.length; i++)
			{
				//攻撃が通せるか判定するためのフラグリセット
				flag = false;

				//使ったカードがあるかどうか判定
				if (p2_card[i][0] != -1)
				{
					//p2_cardの対応IDの列を回すためのfor
					for (int j = 3; j < p2_card[0].length; j++)
					{
						//攻撃が通せるか判定するための変数リセット
						count = 0;

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
										textmain[4][i] += p2_card[k][2] / 2;//ｐ２がリフレクトしたダメージを計算して配列に入れる
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
										if (count == 3 && flag == false)
										{
											textmain[4][i] = p2_card[i][2];//ｐ１が与えたダメージを配列に入れる
											count = 0;//カウントリセット
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
			//ｐ２のｈｐを減らす
			for (int i = 0; i < textmain[0].length; i++)
			{
				textmain[1][2] += textmain[6][i];
			}

			textmain[2][1]++;//ｐ１の行動値を１増やす
			textmain[2][2]++;//ｐ２の行動値を２増やす

			//ｐ２の統合処理後の情報をテキストに書き込む
			for (int i = 0; i < textmain.length; i++)
			{
				for (int j = 0; j < textmain[1].length; j++)
				{
					w = textmain[i][j];//２次元配列の情報をセット
					textW[j] = w;
				}

				tx.editer(playerinfo[1], playerinfo[2], i, 0, textW);//テキストに書き込み
			}
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

				tx.editer(playerinfo[1], 1, i, 0, textW);//テキストに書き込み
			}
		}
	}

}