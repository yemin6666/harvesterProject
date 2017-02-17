package util;


public class JsonResponse {
	
	public static String success(){
		return success("\"success\"");
	}

	public static String success(String json){
		return "{\"code\":\"0\",\"data\":"+json+"}";
	}
	
	public static String fail(String reason){
		return "{\"code\":\"1\",\"data\":\""+reason+"\"}";
	}
	
	public static String error(String reason){
		return "{\"code\":\"-1\",\"data\":\""+reason+"\"}";
	}
}
