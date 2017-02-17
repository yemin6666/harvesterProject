package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class ClientHttp {
	public static void main(String[] args) throws Exception {
		Map<String, String> params = new TreeMap<String, String>();
		params.put("method", "login");
		params.put("userName", "test");
		params.put("password", "test");
		System.out.println(httpRequest("http://127.0.0.1:8080/harvester/",
				params));
	}

	public static String httpRequest(String httpUrl, Map<String, String> params) {
		URL url;
		try {
			// 构建请求参数
			StringBuffer sb = new StringBuffer();
			if (params != null) {
				String temp = "";
				for (Entry<String, String> e : params.entrySet()) {
					if (!e.getKey().equals("method")) {
						temp = temp + e.getValue();
						sb.append(e.getKey());
						sb.append("=");
						sb.append(e.getValue());
						sb.append("&");
					} else {
						httpUrl += e.getValue();
					}
				}
				String sign = MD5.digest(temp + Constant.MD5_KEY);
				sb.append("sign");
				sb.append("=");
				sb.append(sign);
			}
			url = new URL(httpUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			System.out.println("send_url:" + url);
			System.out.println("send_data:" + sb.toString());
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			// conn.setRequestProperty("Content-Type",
			// "application/x-www-form-urlencoded");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("ContentType", "text/xml;charset=utf-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					conn.getOutputStream(), "UTF-8");
			writer.write(sb.toString());
			writer.flush();
			writer.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String temp = "";
			StringBuffer buffer = new StringBuffer();
			while ((temp = reader.readLine()) != null) {
				buffer.append(temp);
			}
			// 服务器返回的结果
			String result = buffer.toString();
			reader.close();
			return result;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}
}