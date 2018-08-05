package external;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class TicketMasterAPI {
	private static final String URL = "https://app.ticketmaster.com/discovery/v2/events.json";
	private static final String DEFAULT_KEYWORD = "";
	// from TicketMaser
	private static final String API_KEY = "KAIALFNsDitUZR3QAnuitMQEehmmBauB";
	
	
	//首先确定response返回的是JSON，这里的keyword就是要查询的输入string
	public JSONArray search(double lat, double lon, String keyword) {
		//Encode keyword in URL 因为有可能有特殊字符
		if (keyword == null) {
			keyword = DEFAULT_KEYWORD;
		}
		try {
			keyword = java.net.URLEncoder.encode(keyword,  "UTF-8"); // 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//转化lat 和 lon, base32是不丢失信息的，所以完全可以反向hash
		String geoHash = GeoHash.encodeGeohash(lat, lon, 8); // 数字就是保留的精度 precision 一般 8 就够了 
		
		// Make URL "apikey = 12345&geopoint=abcd&keyword=music&radius=50"
		String query = String.format("apikey=%s&geopoint=%s&keyword=%s&radius=%s", 
				API_KEY, geoHash, keyword, 50);
		
		try {
			// open a HTTP connection between java app and TicketMaster based URL
			// https://app.ticketmaster.com/discovery/v2/events.json?apikey=12345&geoPoint=abcd&keyword=music&radius=50
			
			HttpURLConnection connection = (HttpURLConnection) new URL(URL + "?" + query).openConnection();
			// 虽然默认，但也可以自己手动设置一下
			connection.setRequestMethod("GET");
			
			// 向TicketMaster发起请求，并获得response
			int responseCode = connection.getResponseCode();
			
			System.out.println("\nSending 'Get' request to URL: " + URL + "?" + query);
			System.out.println("Response code: " + responseCode);
			
			// Now read response body to get events data
			StringBuilder response = new StringBuilder();
			// reader实现了 close，它会自动调用close
			try(BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
			}
			
			JSONObject obj = new JSONObject(response.toString());
			//如果不存在里面有 embedded的，就返回一个空的JSONArray
			if (obj.isNull("_embedded")) {
				return new JSONArray();
			}
			
			// 如果存在，就转换成event
			JSONObject embedded = obj.getJSONObject("_embedded");
			JSONArray events = embedded.getJSONArray("events");
			
			return events;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new JSONArray();
	}
	
	// 下面这个函数主要用来检测search JSON是否会成功
	private void queryAPI(double lat, double lon) {
		JSONArray events = search(lat, lon, null); // 为什么后面要加null
		try {
			for (int i = 0; i < events.length(); ++i) {
				JSONObject event = events.getJSONObject(i);
				System.out.println(event);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Main entry for sample TicketMaster API requests.
	 */
	public static void main(String[] args) {
		TicketMasterAPI tmApi = new TicketMasterAPI();
		// Mountain View, CA
		// tmApi.queryAPI(37.38, -122.08);
		// London, UK
		// tmApi.queryAPI(51.503364, -0.12);
		// Houston, TX
		tmApi.queryAPI(37.38, -122.08);
	}



}




