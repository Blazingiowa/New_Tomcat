package test_tomcat_git;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/Servlet")
public class Servlet extends HttpServlet
{
	private Gson gson = new Gson();

	private UserBean ub = new UserBean();
	private Gamestart game_start = new Gamestart();
	private GameEND game_end = new GameEND();

	private GameProject_Main gpm = new GameProject_Main();

	private Rematch rematch = new Rematch();

	private String[] str_user_info = new String[3]; //順番 0 ユーザーID, 1 ルームID, 2 プレイヤー番号
	private int[] int_user_info = new int[3];

	private String[] str_use_hand = new String[3];
	private int[] use_hand = new int[3];

	private String us_id;
	private String room_id;
	private String us_num;
	private String name;
	private String flag;

	private String reserve;

	private int int_room_id;
	private int int_reserve;

	//固定変数用
	final int final_user = 0;
	final int final_room = 1;
	final int final_pl_num = 2;


	private boolean flag_name;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		doPost(request, response);
	}//基本的に使わないけど保険で入れた

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");

		//以下テストコード自由に変えてよし

		System.out.println("■■■■■■■■■■■■■■■■■■■■");
		LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);
		//一番下に作ったメソッド  Unityから送られてくるものをセットしておける
		us_id = request.getParameter("userID");
		room_id = request.getParameter("roomID");
		us_num = request.getParameter("userNumber");
		name = request.getParameter("name");
		flag = request.getParameter("flag");
		str_use_hand [0] = request.getParameter("usehand1");
		str_use_hand [1] = request.getParameter("usehand2");
		str_use_hand [2] = request.getParameter("usehand3");

		reserve = request.getParameter("reserve");

		//ロビー用にint変換

		int_room_id = conversion(room_id);
		int_reserve = conversion(reserve);

		//名前に特殊文字が含まれているか
		check(name);

		//送られてくるデータの表示（デバック用）
		System.out.println("userID:"+ us_id);
		System.out.println("roomID:"+ room_id);
		System.out.println("userNumber:"+ us_num);
		System.out.println("name:" + name);
		System.out.println("flag:" + flag);
		System.out.println("reserve:" + reserve);

		System.out.println("使ったカード1:" + str_use_hand [0]);
		System.out.println("使ったカード2:" + str_use_hand [1]);
		System.out.println("使ったカード3:" + str_use_hand [2]);

		//ubのnullはキャッシュが配布されるから許されないらしい
		ub.setError("");
		ub.setUserNumber("-1");
		ub.setUserID("-1");
		ub.setRoomID("-1");

		if(flag == null)//game継続
		{
			if (us_id == null)//ID値持っていないとき始めてきたと認識
			{
				//リクエスト内に[name]パラメーターで名前を入れてもらう
				if(flag_name == false)
				{
					System.out.println("ネームエラーによりID,ナンバー'-1'で返す");
					ub.setError("禁止文字が含まれています");
				}
				//始めてきた人用の処理
				else
				{
					//str name int reserve int room
					str_user_info = game_start.createdirectry(name, int_reserve, int_room_id);

					ub.setUserID(str_user_info[0]);
					ub.setRoomID(str_user_info[1]);
					ub.setUserNumber(str_user_info[2]);

					System.out.println("return チェック");
					System.out.println(str_user_info[final_user]);
					System.out.println(str_user_info[final_room]);
					System.out.println(str_user_info[final_pl_num]);
				}

				//JSONを生成
				response.setContentType("application/json");
				response.setCharacterEncoding("utf-8");
				response.getWriter().println(gson.toJson(
					      Collections.singletonMap("param", gson.toJson(ub))
					    ));
			}

			//ゲーム中の処理
			else
			{
				//int変換でNULLを入れるのを防ぐ
				//nullなら使ってないとして扱う
				if(str_use_hand [0] ==  null)
				{
					use_hand[0] = -1;
				}
				else
				{
					use_hand[0] = Integer.parseInt(str_use_hand [0]);
				}

				if(str_use_hand [1] ==  null)
				{
					use_hand[1] = -1;
				}
				else
				{
					use_hand[1] = Integer.parseInt(str_use_hand [1]);
				}

				if(str_use_hand [2] ==  null)
				{
					use_hand[2] = -1;
				}
				else
				{
					use_hand[2] = Integer.parseInt(str_use_hand [2]);
				}

				//何かしらの値を入れないといけない。テスト的に値を入れてある
				//正直いらないが念の為
				info();

				//GameProject_Mainに送られてきたユーザーの情報と使ったカードを送る
				gpm.main(int_user_info, use_hand);
			}
		}
		//flagが立った!
		else if(flag.equals("1"))//Clientが更新したい時用
		{
			//実装間に合わず
		}
		else if(flag.equals("2"))//Clientが落としたい時用
		{
			//何かしらの値を入れないといけない。テスト的に値を入れてある
			info();
			game_end.logout(int_user_info);
		}
		else if(flag.equals("3"))//再戦要求
		{
			//何かしらの値を入れないといけない。テスト的に値を入れてある
			info();
			rematch.wantremacth(int_user_info);
		}
	}
	void info()
	{
		if(us_id ==  null)
		{
			int_user_info[final_user] = 1;

		}
		else
		{
			int_user_info[final_user] =Integer.parseInt(us_id);
		}


		if(room_id ==  null)
		{
			int_user_info[final_room] = 111;

		}
		else
		{
			int_user_info[final_room] =Integer.parseInt(room_id);
		}


		if(us_num ==  null)
		{
			int_user_info[final_pl_num] = 2;

		}
		else
		{
			int_user_info[final_pl_num] =Integer.parseInt(us_num);
		}

	}
	//String→int変換
	int conversion(String s)
	{
		int i = -1;

		if(s == null)
		{

		}
		else
		{
			i = Integer.parseInt(s);
		}
		return i;

	}
	//特殊文字探査
	void check(String s)
	{
		flag_name = true;
		System.out.println("check通った 値 : " + s);
		if (s==null || !s.matches("^[ぁ-んァ-ン一-龥０-９ａ-ｚＡ-Ｚa-zA-Z0-9]+$") || s.equals("null") || s.equals("NULL"))
		{
			flag_name = false;

		}
	}
}
