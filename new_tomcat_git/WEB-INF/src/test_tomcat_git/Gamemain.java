package test_tomcat_git;

import java.util.Random;

public class Gamemain//システム変更につき現在停止中
{
	Text tx = new Text();

	Random rand = new Random(); //見栄えよくするためランダムはこれを使って下せぇ

	int kouyama[]=new int[12];//攻撃側山札
	int move_pt=0;	//防御側行動ポイント
	int trust_pt=100;//防御側の信頼度
	int continu_def=0;//連続して攻撃が防がれた回数

	//カードの情報を呼び出すときは1枚ずつデータベースに問い合わせてください、複数枚呼び出すときはループ処理で配列に格納願います。

	void ymsyokisyori()//攻撃側山札の中身を全部0に..初期処理を兼ねる
	{
		for(int j=0;j<12;j++)
		{
			kouyama[j]=0;
		}
	}

	void yamasyori()//攻撃側山札シャッフル
	{
		for(int j=1;j<13;j++)
		{
			int ran=rand.nextInt(12);

			while(kouyama[ran]!=0)
			{
				ran=rand.nextInt(12);
			}
			kouyama[ran]=j;
		}

		for(int j=0;j<12;j++)
		{
			System.out.println(kouyama[j]);
		}
	}

	void pt_plus()//防御側行動ポイント回復
	{
		move_pt+=10;

	}

	void win_judg()//勝利判定
	{
		if(trust_pt<=0)//攻撃側が勝利した場合
		{

		}

		else if (continu_def==3)//防御側が勝利した場合
		{

		}

	}

	void attack_judg()
	{

	}
}
