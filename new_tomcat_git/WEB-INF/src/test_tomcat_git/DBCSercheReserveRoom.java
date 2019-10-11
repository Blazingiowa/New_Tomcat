package test_tomcat_git;

public class DBCSercheReserveRoom extends DataBaseConnectUpdate
{
	int[] entryroom(String user_name,int room_id)
	{
		sqls[0] = "SELECT * FROM user WHERE user_name is null ORDER BY user_id LIMIT 1;" ;
		sqls[1] = "SELECT * FROM room WHERE user_id = -1 AND room_id = "+room_id+ " ORDER BY user_id LIMIT 1;" ;
		System.out.println("○○○○○○○○○○○○○○○○○○○○○○お部屋探し○○○○○○○○○○○○○○○○○○○○○○○○○○○○○○○○○");
		userinfo=DBCB.beforeupdate(sqls);
		update(user_name,userinfo,0);
		return userinfo;
	}
}
