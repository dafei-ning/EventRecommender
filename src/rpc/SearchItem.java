package rpc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.DBConnection;
import db.DBConnectionFactory;
import entity.Item;
import external.TicketMasterAPI;

/**
 * Servlet implementation class SearchItem
 */
@WebServlet("/search")  // 对应的是endpoint，所有调用eventRecommender/search这个请求都调用下面的函数
public class SearchItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchItem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
////		// TODO Auto-generated method stub		
//		response.setContentType("application/json");
//		PrintWriter out = response.getWriter();
//	    if ( request.getParameter("username")!= null) {
//	    	String username = request.getParameter("username");
//	    	response.getWriter().append("Served at: ").append(request.getContextPath());
//			out.println("<html><body>");
//			out.println("<h1>Hello " + username + "</h1>");
//			out.println("</body></html><br>");		
//			JSONArray array = new JSONArray();
//			JSONObject obj = new JSONObject();	
//			try {
//				obj.put("username", username);
//				//array.put(new JSONObject().put("username", "abcd"));
//				array.put(obj);
//				array.put(obj);
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//			out.print(obj);
//	    }
//		out.close();
//	}
    
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		  double lat = Double.parseDouble(request.getParameter("lat"));
//		  double lon = Double.parseDouble(request.getParameter("lon"));
//		  
//		  String keyWord = request.getParameter("term");
//		  
//		  TicketMasterAPI ticketMasterAPI = new TicketMasterAPI();
//		  List<Item> items = ticketMasterAPI.search(lat, lon, keyWord);
//		  
//		  JSONArray array = new JSONArray();
//		  
//		  try {
//			  for (Item item : items) {
//				  JSONObject object = item.toJSONObject();
//				  array.put(object);
//			  }
//		  } catch (Exception e) {
//			  e.printStackTrace();
//		  }
//		  RpcHelper.writeJsonArray(response, array);
//	}
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	double lat = Double.parseDouble(request.getParameter("lat"));
    	double lon = Double.parseDouble(request.getParameter("lon"));
    	// Term can be empty or null.
    	String term = request.getParameter("term");
    	DBConnection connection = DBConnectionFactory.getConnection();
    	try {
    		List<Item> items = connection.searchItems(lat, lon, term);

    		JSONArray array = new JSONArray();
    		for (Item item : items) {
    			array.put(item.toJSONObject());
    		}
    		RpcHelper.writeJsonArray(response, array);

    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		connection.close();
    	}

    }

	
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		  // TODO Auto-generated method stub
//		  //response.getWriter().append("Served at: ").append(request.getContextPath());
//		  
//		  response.setContentType("application/json");
//		  PrintWriter out = response.getWriter();
//		  JSONArray array = new JSONArray();
//
//		  try {
//		   array.put(new JSONObject().put("username", "abcd"));
//		   array.put(new JSONObject().put("username", "1234"));
//		  } catch (JSONException e) {
//		   e.printStackTrace();
//		  }
//		  out.print(array);
//		  out.close();
//	}
	
	
	
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		// allow access only if session exists
//		HttpSession session = request.getSession(false);
//		String userId = request.getParameter("user_id");
//		double lat = Double.parseDouble(request.getParameter("lat"));
//		double lon = Double.parseDouble(request.getParameter("lon"));
//		// Term can be empty or null.
//		String term = request.getParameter("term");
//		List<Item> items = conn.searchItems(userId, lat, lon, term);
//		List<JSONObject> list = new ArrayList<>();
//
//		Set<String> favorite = conn.getFavoriteItemIds(userId);
//		try {
//			for (Item item : items) {
//				// Add a thin version of restaurant object
//				JSONObject obj = item.toJSONObject();
//				// Check if this is a favorite one.
//				// This field is required by frontend to correctly display favorite items.
//				obj.put("favorite", favorite.contains(item.getItemId()));
//
//				list.add(obj);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		JSONArray array = new JSONArray(list);
//		RpcHelper.writeJsonArray(response, array);
//	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
