package test_tomcat_git;

import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnectUpdate extends DataBaseConnectRead
{
	int[] userinfo;//ユーザID,ルームID,プレイヤー番号
	DBCbeforeUpdate DBCB = new DBCbeforeUpdate();

	int[] update(String username)//受け渡されたusernameをデータベースへインサートする
	{
		/*url = "jdbc:mysql://localhost:3306/u22?characterEncoding=UTF-8&serverTimezone=JST";
		user = "root";
		password = "ncc_NCC2019";*/

		userinfo=DBCB.beforeupdate();//空いているルームと攻守を検索
		System.out.println("DataBaseUpdateクラス上での情報だお");
		System.out.println("user_id:"+userinfo[0]);
		System.out.println("room_id:"+userinfo[1]);
		System.out.println("player_number:"+userinfo[2]);

		/*try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (ClassNotFoundException e1)
		{

			e1.printStackTrace();
		}*/

		try
		{
			Statement stmt1 = CC.createstatement(conn = CC.createconnection());
			Statement stmt2 = CC.createstatement(conn = CC.createconnection());


			stmt1.executeUpdate("UPDATE user SET user_name = '"+username+"' WHERE user_id = "+userinfo[0]+";");
			stmt2.executeUpdate("UPDATE room SET user_id = "+userinfo[0]+" WHERE room_id = "+userinfo[1]+" AND player_number = "+userinfo[2]+";");
		}
		catch(SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			CC.close();
		}

		return userinfo;


	}

	void reset()
	{

	}

}
