package test_tomcat_git;

import java.util.Arrays;

public class GameProject_Main
{
	//送られてきた使ったカードIDを昇順にソートし格納するための配列
	int[]SortedCard;

	//ルーム状況を入れるための配列
	int[] player = new int[3];

	int[][] p1_card = new int[3][8];//統合処理の時の、カード判定時に使う
	int[][] p2_card = new int[3][8];//上に同じ

	int[] hp;//ｈｐの情報を格納するための配列
	int[] move_pt;//行動値の情報を格納するための配列

	int[] p1_cardinfo;//DBから持ってきたカード情報を退避させるための配列(p1
	int[] p2_cardinfo;//DBから持ってきたカード情報を退避させるための配列(p2

	int[] textW = new int[3];//テキストファイルの内容を一時的に避難させるための１次元配列
	int[] textF;//テキストファイルの内容を一時的に避難させるための可変可能な１次元配列
	int w;//一時退避変数
	int[][] textmain = new int[7][3];//避難させた内容を格納するための配列

	//攻撃が通せるかどうか判定するための変数とフラグ
	int count = 0;
	boolean flag = false;

	//クールタイムの情報を入れるための配列
	int[][]CT = new int[2][20];

	//クールタイムの更新した情報をテキストに書き込む時に使う１次元配列
	int[]CTwrite = new int[20];

	//DBクラスのインスタンス
	DataBaseConnectRead DBC = new DataBaseConnectRead();

	//テキストの読み込みクラス
	TextRead txR = new TextRead();

	//テキストの書き込みクラス
	TextWrite txW = new TextWrite();

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

		//使ったカードIDを昇順にソート
		int[]SortedCard = sort(use);

		//テキストを読み込み、書き換え
		GPRW.txtReadWrite(info, SortedCard);

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

	int[] sort(int[]usecard)
	{
		int[] w = new int[usecard.length];
		Arrays.sort(usecard);
		for(int i=0,j=usecard.length-1;i<usecard.length;i++,j--)
		{
			w[i] = usecard[j];
		}


		return w;
	}
}
