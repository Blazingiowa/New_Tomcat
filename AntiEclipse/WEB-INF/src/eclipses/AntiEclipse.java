package eclipses;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "AntiEclipse", urlPatterns = { "/photonisgood" }, asyncSupported = true)

public class AntiEclipse extends HttpServlet{

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			  throws ServletException, IOException{
			    doPost(request, response);
			  }

			  //doPostメソッド
			  public void doPost(HttpServletRequest request, HttpServletResponse response)
			  throws ServletException, IOException{

			    int EclipseNo;//カスタマーNo
			    String varsion;//名前
			    int osusume;//住所
			    String gazo;//電話番号
			    String url = "jdbc:mysql://localhost:3306/antieclipse?user=root&password=yasutaka13&useUnicode=true&characterEncoding=utf-8&serverTimezone=JST";

			    try{
			      // JDBCドライバをロードする
			      Class.forName("com.mysql.jdbc.Driver").newInstance();

			      // DBへ接続する
			      Connection con = DriverManager.getConnection(url);

			      // SQLのコンテナを作成する
			      Statement stmt = con.createStatement();

			      // SQLを実行する
			      String query = "SELECT 一覧番号,バージョン,おすすめ度,アイコン FROM eclipses";
			      ResultSet rs = stmt.executeQuery(query);

			      // 結果がある間、検索結果を取り出す
			      ArrayList<EclipsesBean> list = new ArrayList<EclipsesBean>();
			      while(rs.next()){
			        EclipseNo = rs.getInt("一覧番号");
			       varsion = rs.getString("バージョン");
			        osusume = rs.getInt("おすすめ度");
			        gazo = rs.getString("アイコン");
			        list.add(new EclipsesBean(EclipseNo,varsion,osusume,gazo));
			      }

			      request.setAttribute("list", list);

			      // JSPの表示
			      ServletContext context = getServletContext();
			      RequestDispatcher rd = context.getRequestDispatcher("/JetBrains.jsp");
			      rd.forward(request, response);

			      // DBから切り離す
			      rs.close();
			      stmt.close();
			      con.close();

			    }catch (Exception ex) {
			      ex.printStackTrace();
			    }
			  }
			}
