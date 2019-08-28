package test_tomcat_git;

import java.io.File;

public class Roomdelete
{
		void delete(int room_id)
		{
			File file = new File("/var/www/html/game/"+ room_id);
			if(file.exists())
			{
				if(file.delete())
				{
					System.out.println("ファイルを消せたよ");
				}
				else
				{
					System.out.println("ファイルを消せなかったよ");
				}
			}
		}
}
