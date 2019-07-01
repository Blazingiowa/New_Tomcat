package test_tomcat_git;

import java.io.IOException;
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
	private GameProject game_project = new GameProject();

	private String[] str_user_info = new String[3]; //順番 0 ユーザーID, 1 ルームID, 2 プレイヤー番号
	private int[] int_user_info = new int[3];

	private String[] str_use_hand = new String[3];
	private int[] use_hand = new int[3];

	private String us_id;
	private String room_id;
	private String us_num;
	private String name;
	private String flag;

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
		//test();
		//一番下に作ったメソッド  Unityから送られてくるものをセットしておける
		us_id = request.getParameter("userID");
		room_id = request.getParameter("roomID");
		us_num = request.getParameter("user_number");
		name = request.getParameter("name");
		flag = request.getParameter("flag");
		str_use_hand [0] = request.getParameter("usehand1");
		str_use_hand [1] = request.getParameter("usehand2");
		str_use_hand [2] = request.getParameter("usehand3");


		System.out.println(request);
		System.out.println("userID:"+ us_id);
		System.out.println("roomID:"+ room_id);
		System.out.println("user_number:"+ us_num);
		System.out.println("name:" + name);
		System.out.println("flag:" + flag);

		System.out.println("使ったカード1" + str_use_hand [0]);
		System.out.println("使ったカード2" + str_use_hand [1]);
		System.out.println("使ったカード3" + str_use_hand [2]);
		//ここまでテストコード


		if(flag == null)//game継続
		{
			if (room_id == null)//ルームID値持っていないとき始めてきたと認識
			{
				//リクエスト内に[name]パラメーターで名前を入れてもらう

				str_user_info = game_start.createdirectry(name);

				ub.setUserID(str_user_info[0]);
				ub.setRoomID(str_user_info[1]);
				ub.setUserNumber(str_user_info[2]);

				System.out.println("return チェック");
				System.out.println(str_user_info[0]);
				System.out.println(str_user_info[1]);
				System.out.println(str_user_info[2]);

				//JSONを生成
				response.setContentType("application/json");
				response.setCharacterEncoding("utf-8");
				response.getWriter().println(gson.toJson(
					      Collections.singletonMap("param", gson.toJson(ub))
					    ));
			}


			else
			{
				//int変換でNULLを入れるのを防ぐ
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

				info();

				game_project.main(int_user_info, use_hand);

			}
		}
		//flagが立った!
		else if(flag.equals("1"))//Clientが更新したい時用
		{

		}
		else if(flag.equals("2"))//Clientが落としたい時用
		{
			//何かしらの値を入れないといけない。テスト的に値を入れてある
			info();

			game_end.main(int_user_info);
		}
	}
	void info()
	{
		if(us_id ==  null)
		{
			int_user_info[0] = 1;
			System.out.println("UserID入ってない");
		}
		else
		{
			int_user_info[0] =Integer.parseInt(us_id);
		}


		if(room_id ==  null)
		{
			int_user_info[1] = 111;
			System.out.println("ルームID入ってな");
		}
		else
		{
			int_user_info[1] =Integer.parseInt(room_id);
		}


		if(us_num ==  null)
		{
			int_user_info[2] = 2;
			System.out.println("ナンバー入ってない");
		}
		else
		{
			int_user_info[2] =Integer.parseInt(us_num);
		}

	}


}
