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

	int p1_hp = 100;//ｐ１のｈｐの変数
	int p2_hp = 100;//ｐ２のｈｐの変数

	int p1_movept = 1;//ｐ１の行動値
	int p2_movept = 1;//ｐ２の行動値

	int[][] p1_card = new int[3][8];//統合処理の時の、カード判定時に使う
	int[][] p2_card = new int[3][8];//上に同じ

	int[][] atcard = new int[12][5];//攻撃のカード（カードID０～１１の計１２枚）
	int[][] defcard = new int[8][2];//防御のカード（カードID１２～１９の計８枚）

	int[] atcardinfo = new int[5];//DBから攻撃カードのデータを受け取るときの退避用配列
	int[] defcardinfo = new int[2];//DBから防御カードのデータを受け取るときの退避用配列

	int[] textW;//テキストファイルの内容を一時的に避難させるための１次元配列
	int w;//みんな大好き一時退避変数だよ！＜＜０にｗを代入＞＞
	int[][] textmain = new int[7][3];//避難させた内容を格納するための配列

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

		//テキストを読み込み、書き換え
		txtReadWrite(info, use);

		//ルーム状況表から情報をもってくる
		player = tx.editer(info[1], 3, info[2], 1, null);

		//それぞれのプレイヤーが処理が終わっているかどうかの判定
		if (player[0] == 1 && player[1] == 1)
		{
			//ｐ１のとき
			if (info[2] == 1)
			{
				textW = tx.editer(info[1], 2, 5, 1, null);//相手が使ったカードの情報を持ってきて退避
				for (int i = 0; i < p2_card.length; i++)
				{
					textmain[5][i] = textW[i];//２次元配列の相手のカード情報のところにセット
					p2_card[i][0] = textW[i];//相手のカード情報の場所にもセット
					p1_card[i][0] = textmain[3][i];//２次元配列から自分の使ったカードの情報をセット
				}
			}
			//ｐ２のとき
			else if (info[2] == 2)
			{

			}
		}

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

	//配列の初期化
	void start()
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

	//textの読み込みと書き込み
	void txtReadWrite(int[] playerinfo, int[] usecard)
	{
		//プレイヤーの処理が終わっているのかどうか判定（０はまだ、１で処理済み）
		if (textW[0] == 0)
		{
			for (int i = 0; i < textmain.length; i++)
			{
				textW = tx.editer(playerinfo[1], playerinfo[2], i, 1, null);

				//テキストに初期で入ってるデータを配列に入れる
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