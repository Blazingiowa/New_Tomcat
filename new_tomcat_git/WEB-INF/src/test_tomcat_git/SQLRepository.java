package test_tomcat_git;

public class SQLRepository
{
	private String sql;
	private String[] sqls;

	String SelectCard()//指定したカードを検索
	{
		sql = "SELECT * FROM card WHERE card_id = ?;";
		return sql;
	}

	String SelectAllCard()//すべてのカードを検索
	{
		sql = "SELECT * FROM card;";
		return sql;
	}

	String SelectCountCard()//データベース上にあるカードの枚数を検索する
	{
		sql = "SELECT COUNT(*) AS number FROM card;";
		return sql;
	}

	String SelectEmptyUser()//空いているユーザーを検索
	{
		sql =  "SELECT * FROM user WHERE user_name is null ORDER BY user_id LIMIT 1;" ;
		return sql;
	}

	String SelectEmptyRoom(int reserve)//ルームを検索または検索し予約
	{
		if(reserve ==0)//通常のマッチ
		{
			sql = "SELECT * FROM room WHERE user_id = 0 ORDER BY room_id LIMIT 1;";
			System.out.println("クイックマッチ");
		}
		else if(reserve == 1)//部屋作成
		{
			sql = "SELECT * FROM room WHERE user_id = 0 AND player_number = 1 ORDER BY room_id LIMIT 1;";
		}
		return sql;
	}

	String SelectReserveRoom()//予約してもらったルームを検索
	{
		sql = "SELECT * FROM room WHERE user_id = -1 AND room_id = ? ORDER BY user_id LIMIT 1;";
		return sql;
	}

	String UpdateLoginUser()//ユーザー表にユーザーを登録
	{
		sql = "UPDATE user SET user_name = ? WHERE user_id = ?;";
		return sql;
	}

	String UpdateLoginRoom()//ルーム表にユーザーを登録
	{
		sql = "UPDATE room SET user_id = ? WHERE room_id = ? AND player_number = ?;";
		return sql;
	}

	String UpdateReserveRoom()//ルームを予約
	{
		sql = "UPDATE room SET user_id = -1 WHERE room_id = ? AND player_number = 2;";
		return sql;
	}

	String UpdateLogoutUser()//ユーザー表からユーザーを削除
	{
		sql = "UPDATE user FROM user_name = NULL WHERE user_id =?;";
		return sql;
	}

	String UpdateLogoutRoom()//ルーム表からユーザーを削除
	{
		sql = "UPDATE room FROM user_id = 0 WHERE user_id =?;";
		return sql;
	}

	String SelectRoomUser()//ルームにいるユーザーを確認
	{
		sql = "SELECT * FROM room WHERE room_id = ?;";
		return sql;
	}

	String SelectMaxUserid()//ユーザーIDの最大値を検索
	{
		sql = "SELECT MAX(user_id) as maxno FROM user;";
		return sql;
	}

	String SelectMaxRoom()//ルームIDの最大値を検索
	{
		sql = "SELECT MAX(room_id) as maxno FROM room;";
		return sql;
	}

	String InsertUserid()//ユーザーIDを追加
	{
		sql = "INSERT INTO user VALUES(?,NULL);";
		return sql;
	}

	String[] InsertRoom()//ルームIDを追加
	{
		sqls = new String[2];
		sqls[0] = "INSERT INTO room VALUES(?,1,0);";
		sqls[1] = "INSERT INTO room VALUES(?,2,0);";
		return sqls;
	}

	String SelectRoomExist()//入ろうとしているルームが存在しているか確認する
	{
		sql = "SELECT DISTINCT room_id FROM room;";
		return sql;
	}

	String SelectRoomFull()//入ろうとしているルームに空きがあるか確認する
	{
		sql = "SELECT * FROM room WHERE room_id = ? AND player_number = 2 ;";
		return sql;
	}

}
