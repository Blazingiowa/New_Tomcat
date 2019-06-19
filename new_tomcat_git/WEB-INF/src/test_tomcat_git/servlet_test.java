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
	private HttpServletResponse res;

	private Gson gson = new Gson();

	private UserBean ub = new UserBean();
	private Gamestart game_start = new Gamestart();
	//private Gamemain game_main = new Gamemain();

	private GameProject game_project = new GameProject();

	private String name_val;
	private String[] user_info;
	private int[] user_session;
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


		user_info = new String[3];//順番 0 ユーザーID, 1 ルームID, 2 プレイヤー番号
		user_session = new int[3];
		use_hand = new int[3];


		DataBaseConnectRead dc = new DataBaseConnectRead();
		int[] a = dc.reference(1);
		for(int i = 0;i<a.length;i++)
		{
			System.out.println(a[i]);
		}
		DataBaseConnectUpdate DBCU = new DataBaseConnectUpdate();
		int[] b = DBCU.update("waithi");



		req =request;

		if(req.getParameter("flag") == null)//game継続
		{
			if (req.getParameter("roomID") == null)//ルームID値持っていないとき始めてきたと認識
			{
				test_new_connect();
				//JSONを生成
				response.setContentType("application/json");
				response.setCharacterEncoding("utf-8");
				response.getWriter().println(gson.toJson(
					      Collections.singletonMap("param", gson.toJson(ub))
					    ));
			}
			else
			{

				test_connect();

			}

		}
		else if(req.getParameter("flag") == "1")//Clientが更新したい時用
		{

		}
		else if(req.getParameter("flag") == "2")//Clientが落としたい時用
		{

		}


		/*
		//ゲーム途中で落とさなければ基本この中
		if(request.getParameter("end_flag") == null)
		{
			if (request.getParameter("roomID") == null)//ルームID値持っていないとき始めてきたと認識
			{
				new_connect(request);
				//JSONを生成
				response.setContentType("application/json");
				response.setCharacterEncoding("utf-8");
				response.getWriter().println(gson.toJson(
					      Collections.singletonMap("param", gson.toJson(ub))
					    ));
			}
			else if(request.getParameter("roomID") != null)
			{

				connect(request);

			}

		}
		else//Clientが落としたい時用
		{

		}
	*/
	}

	void test_new_connect()
	{
		name_val = req.getParameter("name"); //リクエスト内に[name]パラメーターで名前を入れてもらう

		user_info = game_start.createdirectry(name_val);

		ub.setUserID(user_info[0]);
		ub.setRoomID(user_info[1]);
		ub.setUserNumber(user_info[2]);

	}

	void test_connect()
	{

		user_session[0] =Integer.parseInt(req.getParameter("userID"));
		user_session[1] =Integer.parseInt(req.getParameter("roomID"));
		user_session[2] =Integer.parseInt(req.getParameter("user_number"));

		use_hand[0] =Integer.parseInt(req.getParameter("user_number1"));
		use_hand[1]=Integer.parseInt(req.getParameter("user_number2"));
		use_hand[2]=Integer.parseInt(req.getParameter("user_number3"));

		//use_hand = conversion((String[])request.getParameterValues("Use_hand"));


		game_project.main(user_session, use_hand);
	}

	/*
	void new_connect(HttpServletRequest request)
	{
		name_val = request.getParameter("name"); //リクエスト内に[name]パラメーターで名前を入れてもらう

		user_info = game_start.createdirectry(name_val);

		ub.setUserID(user_info[0]);
		ub.setRoomID(user_info[1]);
		ub.setUserNumber(user_info[2]);

	}

	void connect(HttpServletRequest request)
	{

		user_session[0] =Integer.parseInt(request.getParameter("userID"));
		user_session[1] =Integer.parseInt(request.getParameter("roomID"));
		user_session[2] =Integer.parseInt(request.getParameter("user_number"));

		use_hand[0] =Integer.parseInt(request.getParameter("user_number1"));
		use_hand[1]=Integer.parseInt(request.getParameter("user_number2"));
		use_hand[2]=Integer.parseInt(request.getParameter("user_number3"));

		//use_hand = conversion((String[])request.getParameterValues("Use_hand"));


		game_project.main(user_session, use_hand);
	}


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
