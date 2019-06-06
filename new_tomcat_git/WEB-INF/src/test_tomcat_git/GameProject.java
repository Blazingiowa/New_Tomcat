package test_tomcat_git;

public class GameProject
{
	//ルーム状況を入れるための配列
	int[] player = new int[2];

	//ルーム状況表にプレイヤー１の情報を書き込むための配列
	final int[] player1 =
	{ 1, 0 };

	//ルーム状況表にプレイヤー２の情報を書き込むための配列
	final int[] player2 =
	{ 0, 1 };

	int[][] atcard = new int[12][5];//攻撃のカード（カードID０～１１の計１２枚）
	int[][] defcard = new int[8][2];//防御のカード（カードID１２～１９の計８枚）

	int p1_movept;//ｐ１のリソース（行動値的なもの）
	int p2_movept;//ｐ２のリソース（行動値的なもの）

	int p1_hp;//ｐ１のHP（信頼度）
	int p2_hp;//ｐ２のHP（信頼度）

	int[] atcardinfo = new int[5];//DBから攻撃カードのデータを受け取るときの退避用配列
	int[] defcardinfo = new int[2];//DBから防御カードのデータを受け取るときの退避用配列

	int[] textW;//テキストファイルの内容を一時的に避難させるための１次元配列
	int w;//みんな大好き一時退避変数だよ！＜＜０にｗを代入＞＞
	int[][] textmain = new int[5][3];//避難させた内容を格納するための配列

	DataBaseConnect DBC = new DataBaseConnect();//DBクラスのインスタンス
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
		start();

		//テキストファイルを検索[ルームID][ユーザ番号][行数][書0、読1][書き込みたい配列、読みはnull]
		//プレイヤーの処理状況の情報が入っている０行目を持ってくる
		textW = tx.editer(info[1], info[2], 0, 1, null);

		//テキストを読み込み、書き換える
		txtReadWrite(info, use);

	}

	/*攻撃カードの手札の配列内容(5×5)--------------------------/
	/															/
	/	[カードID][ダメージ値][コスト][防カード１][防カード２]	/
	/															/
	/----------------------------------------------------------*/

	//攻撃カードの設定
	void atcard()
	{
		for (int i = 0; i < 12; i++)
		{
			atcardinfo = DBC.reference(i, 0);//DBのカード情報を取り出して退避用配列に入れる
			for (int j = 0; j < 5; j++)
			{
				//攻撃のカード情報をセット
				atcard[i][j] = atcardinfo[j];
			}
		}

	}

	/*防御カードの配列内容(8×2)----/
	/								/
	/	[カードID][コスト]			/
	/								/
	/------------------------------*/

	//防御カードの設定
	void defcard()
	{
		for (int i = 0; i < 8; i++)
		{
			defcardinfo = DBC.reference(i, 0);//DBのカード情報を取り出して退避用配列に入れる
			for (int j = 0; j < 2; j++)
			{
				//防御のカード情報をセット
				defcard[i][j] = defcardinfo[j];
			}
		}

	}

	//初期設定
	void start()
	{
		//（使わないデータの場所には-1）
		for (int i = 0; i < textmain.length; i++)
		{
			for (int j = 0; j < textmain[0].length; j++)
			{
				textmain[i][j] = -1;
			}
		}
		//処理情報のところを０にし、処理前の情報にする。
		textmain[0][0] = 0;
	}

	//textの読み込みと書き込み
	void txtReadWrite(int[] playerinfo, int[] usecard)
	{
		//プレイヤーの処理が終わっているのかどうか判定（０はまだ、１で処理済み）
		if (textW[0] == 0)
		{
			for (int i = 0; i < textmain.length; i++)
			{
				textW = tx.editer(playerinfo[1], playerinfo[2], i, 1, null);

				//テキストには初期で入ってるデータを配列に入れる
				for (int j = 0; j < textW.length; j++)
				{
					w = textW[j];
					textmain[i][j] = w;
				}

			}

			textmain[0][0] = 1;//とりあえず、処理済みにデータを変更
			//for文を使って２次元配列を１次元配列に退避し、テキストファイルに書き込む
			for (int i = 0; i < textmain.length; i++)
			{
				if (i == 1)//１行目の時が自分が使ったカードの情報
				{
					for (int j = 0; j < textmain[1].length; j++)
					{
						w = usecard[j];
						textW[j] = w;
					}
				}
				else//それ以外の時は退避用変数に入れて、そこから１次元配列にデータを入れてテキストに書き込む
				{
					for (int j = 0; j < textmain[1].length; j++)
					{
						w = textmain[i][j];
						textW[j] = w;
					}
				}
				tx.editer(playerinfo[1], playerinfo[2], i, 0, textW);
			}

			//プレイヤー１のときの処理
			if (playerinfo[2] == 1)
			{
				//ルーム状況表にプレイヤー１の処理が終わったことを書き込む
				tx.editer(playerinfo[1], 3, playerinfo[2], 0, player1);
			}

			//プレイヤー２の時の処理
			else if (playerinfo[2] == 2)
			{
				//ルーム状況表にプレイヤー２の処理が終わったことを書き込む
				tx.editer(playerinfo[1], 3, playerinfo[2], 0, player2);
			}
		}
	}

}