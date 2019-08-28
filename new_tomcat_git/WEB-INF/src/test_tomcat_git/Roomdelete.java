package test_tomcat_git;

import java.io.File;

public class Roomdelete
{
		void delete(int room_id)
		{
			File file = new File("/var/www/html/game/"+ room_id);
			File[] files = file.listFiles();
			for(int i = 0; i<files.length;i++)
			{
				if(files[i].exists())
				{
					if(files[i].delete())
					{
						System.out.println(files[i]+"ファイルを消せたよ");
					}
					else
					{
						System.out.println(files[i]+"ファイルを消せなかったよ");
					}
				}
			}

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
