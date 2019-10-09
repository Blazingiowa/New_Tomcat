package test_tomcat_git;

public class GameProject_Main
{
	final int Users = 2;//プレイヤー人数

	final int UserInfo = 4;//プレイヤー情報

	final int RoomInfo = 3;//ルーム状況

	final int MaxUseCard = 3;//使えるカード枚数の限度

	final int CardInfo = 9;//カードの情報（IDやCT、ダメージ、対応ID、カード種別）

	final int WithHold = 3;//配列内を退避させるため

	final int TextField = 7;//プレイヤーテキストの行

	final int JudgePlayer1 = 1; //プレイヤー１かどうかの判定

	final int JudgePlayer2 = 2;//プレイヤー２かどうかの判定

	final int JudgeATKCard = 0;//攻撃カードかどうかの判定

	final int JudgeCard = -1;//カードの有無・対応についての判定

	final int[] JudgePlayering =
	{ 0, 1, 2 };//ゲームの進行度を判定

	//DBクラスのインスタンス
	DataBaseConnectRead DBC = new DataBaseConnectRead();

	//テキストの読み込みクラス
	TextRead txR = new TextRead();

	//テキストの書き込みクラス
	TextWrite txW = new TextWrite();

	//カードテキストクラスのインスタンス
	CardText cardtext = new CardText();

	int[] player;//ルーム・プレイヤーの状況を判定するための配列
	int[][] p1_card;//統合処理の時の、カード判定時に使う
	int[][] p2_card;//上に同じ

	int[] hp;//ｈｐの情報を格納するための配列
	int[] move_pt;//行動値の情報を格納するための配列

	int[] p1_cardinfo;//DBから持ってきたカード情報を退避させるための配列(p1
	int[] p2_cardinfo;//DBから持ってきたカード情報を退避させるための配列(p2

	int[] textW;//テキストファイルの内容を一時的に避難させるための１次元配列
	int[] textF;//テキストファイルの内容を一時的に避難させるための可変可能な１次元配列
	int w;//一時退避変数
	int MaxCardNum;//カードの枚数を格納するための変数
	int[][] textmain;//避難させた内容を格納するための配列

	//攻撃が通せるかどうか判定するための変数とフラグ
	int ATKcount;
	boolean ATKflag;

	//ソート前のカード情報を格納するための配列
	int[][] bfrcard;

	int[][] CT;//クールタイムの情報を格納するための配列

	//クールタイムの更新した情報をテキストに書き込む時に使う１次元配列
	int[] CTwrite;

	//コンストラクタ
	GameProject_Main()
	{
		player = new int[RoomInfo];
		p1_card = new int[MaxUseCard][CardInfo];
		p2_card = new int[MaxUseCard][CardInfo];

		textW = new int[WithHold];
		textmain = new int[TextField][MaxUseCard];

		ATKcount = 0;
		ATKflag = false;

		bfrcard = new int[UserInfo][MaxUseCard];
		/*---bfrcardの配列概要---*/
		/*	０行目：統合処理を行っているプレイヤーの使った順カードID
			１行目：統合処理を行っているプレイヤーの発生したダメージを使った順に並び替え格納
			２行目：統合処理に入っていないプレイヤーの使った順カードID
			３行目：統合処理に入っていないプレイヤーの発生したダメージを使った順に並び替え格納	*/

		MaxCardNum = cardtext.CardCount();//DBに登録されているカード枚数を取得

		//カード枚数に応じて配列を作成
		CT = new int[Users][MaxCardNum];
		CTwrite = new int[MaxCardNum];

	}

	/*infoの配列内容------------------------/useの配列の内容--------/
	/										/						/
	/	[ユーザID][ルームID][ユーザ番号]	/	[カードID][][]...	/
	/										/						/
	/---------------------------------------/----------------------*/
	void main(int[] info, int[] use)
	{
		//ゲームのターン最初の処理クラス
		GameProject_startup GPS = new GameProject_startup();

		//ゲームのテキスト書き読み込みをするためのクラス
		GameProject_txtReadWrite GPRW = new GameProject_txtReadWrite();

		//ゲームのplayer1の統合処理クラス
		GameProject_Integrated_P1 GPIP1 = new GameProject_Integrated_P1();

		//ゲームのplayer2の統合処理クラス
		GameProject_Integrated_P2 GPIP2 = new GameProject_Integrated_P2();

		//textmainの内容を初期化
		GPS.start(info);

		System.out.println("ここから、ゲームスタート");

		//テキストを読み込み、書き換え
		GPRW.txtReadWrite(info, use);

		System.out.println("テキストの書き読み完了");

		//ルーム状況表から情報をもってくる[ルームID][共有ファイルについてなので、３][ユーザ番号で行数指定]
		player = txR.read(info[1], 3, 1);

		//それぞれのプレイヤーが処理が終わっているかどうかの判定
		if (player[1] == JudgePlayering[1] && player[2] == JudgePlayering[1])
		{
			//プレイヤー１
			if (info[2] == JudgePlayer1)
			{
				//ｐ１で入った場合の統合処理
				GPIP1.IntegratedP1(info);

				//クールタイム処理
				GPIP1.setCT(info);
			}

			//プレイヤー２
			else if (info[2] == JudgePlayer2)
			{
				//ｐ２で入った場合の統合処理
				GPIP2.IntegratedP2(info);

				//クールタイム処理
				GPIP2.setCT(info);
			}

		}
	}

}
