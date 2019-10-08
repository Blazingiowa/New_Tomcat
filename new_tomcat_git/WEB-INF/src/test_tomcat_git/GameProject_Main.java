package test_tomcat_git;

public class GameProject_Main
{

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
	int[][] textmain;//避難させた内容を格納するための配列

	//攻撃が通せるかどうか判定するための変数とフラグ
	int count;
	boolean flag;

	//ソート前のカード情報を格納するための配列
	int[][]bfrcard;

	int[][]CT;//クールタイムの情報を格納するための配列

	//クールタイムの更新した情報をテキストに書き込む時に使う１次元配列
	int[]CTwrite;


	//コンストラクタ
	GameProject_Main()
	{
		player = new int[3];
		p1_card = new int[3][8];
		p2_card = new int[3][8];


		textW = new int[3];
		textmain = new int[7][3];

		count = 0;
		flag = false;

		bfrcard = new int[4][3];
		/*---bfrcardの配列概要---*/
		/*０行目：統合処理を行っているプレイヤーの使った順カードID*/
		/*１行目：統合処理を行っているプレイヤーの発生したダメージを使った順に並び替え格納*/
		/*２行目：統合処理に入っていないプレイヤーの使った順カードID*/
		/*３行目：統合処理に入っていないプレイヤーの発生したダメージを使った順に並び替え格納*/


		w = cardtext.CardCount();//DBに登録されているカード枚数を取得

		//カード枚数に応じて配列を作成
		CT = new int[2][w];
		CTwrite = new int[w];

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
		if (player[1] == 1 && player[2] == 1)
		{
			if (info[2] == 1)
			{
				//ｐ１で入った場合の統合処理
				GPIP1.IntegratedP1(info);

				//クールタイム処理
				GPIP1.setCT(info);
			}
			else if (info[2] == 2)
			{
				//ｐ２で入った場合の統合処理
				GPIP2.IntegratedP2(info);

				//クールタイム処理
				GPIP2.setCT(info);
			}

		}
	}


}
