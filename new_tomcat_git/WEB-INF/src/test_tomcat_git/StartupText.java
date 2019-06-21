package test_tomcat_git;

public class StartupText extends TextWrite
{
	DataBaseConnectRead DBCR = new DataBaseConnectRead();

	void textfile(int room_id,int player_number,int line_number)
	{
		settext(room_id,player_number);
		text = ""; writetext = "";

		int[] defSet = new int[3];
		int[] hpSet = {-1,100,100};
		int[] manaSet = {-1,1,1};
		int[] defDamage = {0,0,0};

		for(int i = 0;i<defSet.length;i++)
		{
			defSet[i] = -1;
		}

		for(int i = 0;i<defSet.length;i++)//linestrに1行分の-1を挿入
		{
			writetext += defSet[i];

			if((i+1)<defSet.length)
			{
				writetext += ",";
			}
		}

	}
}
