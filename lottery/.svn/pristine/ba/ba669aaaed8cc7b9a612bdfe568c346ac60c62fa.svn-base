package com.lottery.net.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * 发送验证码
 * @author SuperdRream
 *
 */
public class SendCode {
	
	/**
	 * 参数请求
	 * @param postData
	 * @param postUrl
	 * @return
	 */
	public static String SMS(String postData, String postUrl) {
        try {
            //发送POST请求
            URL url = new URL(postUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Length", "" + postData.length());
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(postData);
            out.flush();
            out.close();
            //获取响应状态
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("connect failed!");
                return "";
            }
            //获取响应内容体
            String line, result = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
            in.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return "";
    }
	public static void main(String[] args) throws IOException {
		String code = (int) (Math.random() * 9000 + 1000) + "";
//		String postData = "prefix=0086&phone=13880779493&content=【签名】"+code+"&type=21";
////		String postData = "sname=dlswzh00&spwd=uVoag50X&scorpid=&sprdid=1012818&sdst=13880779493&smsg="
////				+ java.net.URLEncoder.encode("您的验证码是:" + code + "请在3分钟内输入【】", "utf-8");
//		String ret =SMS(postData, "http://47.75.68.141:8088/sms/send");
		
		Map<String,String> param=new HashMap<String,String>();
		param.put("prefix", "0086");
		param.put("phone", "15608020582");
		param.put("content", "【签名】您的验证码是:" + code + "请在3分钟内输入");
		param.put("type", "21");
		JSONObject object=BodyPost("http://47.75.68.141:8088/sms/send",param);
		System.out.println(object);
	}
	
	/**
	 * body请求
	 * @param actionUrl
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public static JSONObject BodyPost(String actionUrl, Map<String, String> params) throws IOException {
		OutputStreamWriter out = null;
		InputStream is = null;
		try {
			URL url = new URL(actionUrl);// 创建连接
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST"); // 设置请求方式
			connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
			connection.connect();
			out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
			out.append(JSONObject.fromObject(params).toString());
			out.flush();
			out.close();

			// 读取响应
			is = connection.getInputStream();
			int length = (int) connection.getContentLength();// 获取长度
			if (length != -1) {
				byte[] data = new byte[length];
				byte[] temp = new byte[512];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = is.read(temp)) > 0) {
					System.arraycopy(temp, 0, data, destPos, readLen);
					destPos += readLen;
				}
				String result = new String(data, "UTF-8"); // utf-8编码

				return JSONObject.fromObject(result);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
}
