package edu.zh.config;

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
public class Conversation {
	
	@Autowired  
	TuringConfiguration turingConfiguration;  
	
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