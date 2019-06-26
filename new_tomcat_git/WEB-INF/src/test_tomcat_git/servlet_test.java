package test_tomcat_git;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/servlet_test")
public class servlet_test extends HttpServlet
{
	private HttpServletRequest req;
	//private HttpServletResponse res;

	private Gson gson = new Gson();

	private UserBean ub = new UserBean();
	private Gamestart game_start = new Gamestart();
	private GameEND game_end = new GameEND();
	//private Gamemain game_main = new Gamemain();


	private GameProject game_project = new GameProject();

	private String name_val;
	private String[] str_test;
	private String[] str_user_info;
	private int[] int_user_info;
	private int[] use_hand;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		doPost(request, response);
	}//基本的に使わないけど保険で入れた

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");

		str_test = new String[3];
		str_user_info = new String[3];//順番 0 ユーザーID, 1 ルームID, 2 プレイヤー番号
		int_user_info = new int[3];
		use_hand = new int[3];


		req =request;

		//以下テストコード自由に変えてよし
		System.out.println(request);

//		check();
//		System.out.println(request);


		//ここまでテストコード

		substitution();


		if(req.getParameter("flag") == null)//game継続
		{
			if (req.getParameter("roomID") == null)//ルームID値持っていないとき始めてきたと認識
			{
				new_connect();
				//JSONを生成
				response.setContentType("application/json");
				response.setCharacterEncoding("utf-8");
				response.getWriter().println(gson.toJson(
					      Collections.singletonMap("param", gson.toJson(ub))
					    ));
			}
			else
			{
				connect();

			}

		}
		//flagが立った!
		else if(req.getParameter("flag").equals("1"))//Clientが更新したい時用
		{

		}
		else if(req.getParameter("flag").equals("2"))//Clientが落としたい時用
		{
			game_end.main(int_user_info);
		}
	}
	void substitution()
	{
		if(req.getParameter("userID") ==  null)
		{
			int_user_info[0] = 1;
		}
		else
		{
			int_user_info[0] =Integer.parseInt(req.getParameter("userID"));
		}


		if(req.getParameter("roomID") ==  null)
		{
			int_user_info[1] = 111;
		}
		else
		{
			int_user_info[1] =Integer.parseInt(req.getParameter("roomID"));
		}


		if(req.getParameter("user_number") ==  null)
		{
			int_user_info[2] = 2;
		}
		else
		{
			int_user_info[2] =Integer.parseInt(req.getParameter("user_number"));
		}
	}

	void new_connect()
	{
		name_val = req.getParameter("name"); //リクエスト内に[name]パラメーターで名前を入れてもらう

		str_user_info = game_start.createdirectry(name_val);

		ub.setUserID(str_user_info[0]);
		ub.setRoomID(str_user_info[1]);
		ub.setUserNumber(str_user_info[2]);

	}

	void connect()
	{
		if(req.getParameter("user_number1") ==  null)
		{
			use_hand[0] = -1;
		}
		else
		{
			use_hand[0] = Integer.parseInt(req.getParameter("user_number1"));
		}

		if(req.getParameter("user_number2") ==  null)
		{
			use_hand[1] = -1;
		}
		else
		{
			use_hand[1] = Integer.parseInt(req.getParameter("user_number2"));
		}

		if(req.getParameter("user_number3") ==  null)
		{
			use_hand[2] = -1;
		}
		else
		{
			use_hand[2] = Integer.parseInt(req.getParameter("user_number3"));
		}

		game_project.main(int_user_info, use_hand);
	}
	/*
	int[] conversion(String[] str)//使っていない
	{
		int[] h_int = new int[str.length];
		for (int i = 0; i < h_int.length; i++)
		{
			h_int[i] = Integer.parseInt(str[i]);
		}

		return h_int;

	}
	*/

}
