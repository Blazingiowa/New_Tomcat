package test_tomcat_git;

public class GameProject_Main
{
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
	int w;//みんな大好き一時退避変数だよ！＜＜０にｗを代入＞＞
	int[][] textmain = new int[7][3];//避難させた内容を格納するための配列

	//攻撃が通せるかどうか判定するための変数とフラグ
	int count = 0;
	boolean flag = false;

	//DBクラスのインスタンス
	DataBaseConnectRead DBC = new DataBaseConnectRead();

	//テキストの読み込みクラス
	TextRead txR = new TextRead();

	//テキストの書き込みクラス
	TextWrite txW = new TextWrite();

	//ゲームのターン最初の処理クラス
	GameProject_startup GPS = new GameProject_startup();

	//ゲームのテキスト書き読み込みをするためのクラス
	GameProject_txtReadWrite GPRW = new GameProject_txtReadWrite();

	//ゲームのplayer1の統合処理クラス
	GameProject_Integrated_P1 GIP1 = new GameProject_Integrated_P1();

	//ゲームのplayer2の統合処理クラス
	GameProject_Integrated_P2 GIP2 = new GameProject_Integrated_P2();

	/*infoの配列内容------------------------/useの配列の内容--------/
	/										/						/
	/	[ユーザID][ルームID][ユーザ番号]	/	[カードID][][]...	/
	/										/						/
	/---------------------------------------/----------------------*/

	void main(int[] info, int[] use)
	{

	}
}