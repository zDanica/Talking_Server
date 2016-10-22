package edu.zh.config;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import edu.zh.TuringConfiguration;
import edu.zh.util.Aes;
import edu.zh.util.Md5;
import edu.zh.util.PostServer;

/**
 * 加密请求测试类
 * @author 图灵机器人
 *
 */
@Component
public class RPCConversationImpl implements RPCConversation{
	
	public static void main(String[] args) {
		new RPCConversationImpl();
	}
	private TuringConfiguration turingConfiguration;  
	public RPCConversationImpl() {
		// TODO Auto-generated constructor stub
		turingConfiguration = new TuringConfiguration();
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("turing.properties");
		InputStreamReader reader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(reader);
		try {
			String secret = bufferedReader.readLine();
			String apiKey = bufferedReader.readLine();
			String address = bufferedReader.readLine();
			
			
			secret = secret.substring(secret.indexOf("=") + 1).trim();
			apiKey = apiKey.substring(apiKey.indexOf("=") + 1).trim();
			address = address.substring(address.indexOf("=") + 1).trim();
			
			turingConfiguration.setSecret(secret);
			turingConfiguration.setApiKey(apiKey);
			turingConfiguration.setAddress(address);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				bufferedReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally{
					try {
						inputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
	}
	
	@Override
	public String sendMsg(String msg){
		
		String secret = turingConfiguration.getSecret();
		String apiKey = turingConfiguration.getApiKey();
		String address = turingConfiguration.getAddress();
		
		String cmd = msg;
		//待加密的json数据
		String data = "{\"key\":\""+apiKey+"\",\"info\":\""+cmd+"\"}";
		//获取时间戳
		String timestamp = String.valueOf(System.currentTimeMillis());
		
		//生成密钥
		String keyParam = secret+timestamp+apiKey;
		String key = Md5.MD5(keyParam);
		
		//加密
		Aes mc = new Aes(key);
		data = mc.encrypt(data);		
		
		//封装请求参数
		JSONObject json = new JSONObject();
		json.put("key", apiKey);
		json.put("timestamp", timestamp);
		json.put("data", data);
		
		String result = PostServer.SendPost(json.toString(), address);
		return result;
	}
}