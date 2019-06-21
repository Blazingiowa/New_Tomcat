package test_tomcat_git;

import java.io.BufferedWriter;
import java.io.PrintWriter;

public class TextWriter extends TextRead
{
	BufferedWriter bw;
	PrintWriter pw;

	void write(int room_id,int player_number,int line_number,int[] write)
	{
		settext(room_id,player_number);
	}
}
