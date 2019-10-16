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

	private CardText card_text = new CardText();

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

	private String maxCard;

	private int int_room_id;
	private int int_reserve;


	final int final_user = 0;
	final int final_room = 1;
	final int final_pl_num = 2;
	final int final_res = 3;

	final int final_default = -1;

	final int room_Search =2;

	final String room_Exit ="2";
	final String room_Rematch ="3";

	final String st_default ="-1";

	final String error_name ="10";
	final String error_id ="20";

	final String separator =",";


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		doPost(request, response);
	}//基本的に使わないけど保険で入れた

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");

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


		//ubのnullはキャッシュが配布されるから許されないらしい
		ub.setError(st_default);
		ub.setUserNumber(st_default);
		ub.setUserID(st_default);
		ub.setRoomID(st_default);
		ub.setMaxCardnum(st_default);

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


		if(flag == null)//game継続
		{
			if (us_id == null)//ID値持っていないとき始めてきたと認識
			{

				//名前に特殊文字が含まれているか
				check(name);

				//ロビーに入るために入力した値が間違えてないか
				initial();

				//リクエスト内に[name]パラメーターで名前を入れてもらう
				if(ub.getError().equals(st_default))
				{
					str_user_info = game_start.createdirectry(name, int_reserve, int_room_id);

					maxCard = String.valueOf(card_text.CardCount());

					ub.setUserID(str_user_info[0]);
					ub.setRoomID(str_user_info[1]);
					ub.setUserNumber(str_user_info[2]);

					ub.setMaxCardnum(maxCard);

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

				for(int i=0;i<use_hand.length;i++)
				{
					use_hand[i]=i_check(str_use_hand[i]);
				}

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
		else if(flag.equals(room_Exit))//Clientが落としたい時用
		{
			//何かしらの値を入れないといけない。テスト的に値を入れてある
			info();
			game_end.logout(int_user_info);
		}
		else if(flag.equals(room_Rematch))//再戦要求
		{
			//何かしらの値を入れないといけない。テスト的に値を入れてある
			info();
			rematch.wantremacth(int_user_info);
		}
	}
	void info()
	{
		int_user_info[final_user] =i_check(us_id);
		int_user_info[final_room]  =i_check(room_id);
		int_user_info[final_pl_num]  =i_check(us_num);

	}
	//初期用
	void initial()
	{
		int_reserve = final_default;
		int_room_id = final_default;

		if(reserve == null)
		{
			error(error_id);
		}
		else
		{
			if(reserve.matches("[0-2]"))
			{
				int_reserve = Integer.parseInt(reserve);
				if(int_reserve == room_Search)
				{
					if(room_id.matches("[0]") || room_id.matches("[1-9][0-9]{1,}"))
					{
						int_room_id = Integer.parseInt(room_id);
					}
					else
					{
						error(error_id);
					}
				}
			}
			else
			{
				error(error_id);
			}
		}


	}
	//特殊文字探査
	void check(String s)
	{
		System.out.println("check通った 値 : " + s);
		if (s==null || !s.matches("^[ぁ-んァ-ン一-龥０-９ａ-ｚＡ-Ｚa-zA-Z0-9]+$") || s.equals("null") || s.equals("NULL"))
		{
			System.out.println("ネームエラーによりID,ナンバー'-1'で返す");
			error(error_name);

		}
	}
	//数値のみ許可→変換。違ったらデフォルト(-1)で返す
	int i_check(String s)
	{
		System.out.println("i_check通った 値 : " + s);
		int i=final_default;
		if(s.matches("[0]") || s.matches("[1-9][0-9]{0,}"))
		{
			System.out.println("i_check成功");
			i= Integer.parseInt(s);
		}

		return i;
	}
	void error(String s)
	{
		String e = ub.getError();
		//デフォルト値なら上書きして書き換える
		if(e.contains(st_default))
		{
			ub.setError(s);
		}
		else
		{
			//同じエラーコードがあるかどうかの確認
			if(e.contains(s))
			{

			}
			else
			{
				ub.setError(e + separator + s);
			}

		}
	}
}
