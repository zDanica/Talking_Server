package edu.zh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.linda.framework.rpc.client.SimpleRpcClient;

import edu.zh.RPCConfiguration;
import edu.zh.config.RPCConversation;
import edu.zh.mq.producer.ProducerService;

@Controller
public class Upload {
	@Autowired
	private ProducerService producerService;
	
	@Autowired
	private RPCConfiguration rpcConfiguration;

	@RequestMapping("/upload")
	public String uploadUE(@RequestParam("content") String content, Model model) {
		model.addAttribute("success", "yes, is success");
		
		//rpc 方式
		SimpleRpcClient rpcClient = new SimpleRpcClient();
		rpcClient.setHost(rpcConfiguration.getHost());
		rpcClient.setPort(rpcConfiguration.getPort());
		
		RPCConversation rpcConversation = rpcClient.register(RPCConversation.class);
		rpcClient.startService();
		String result = rpcConversation.sendMsg(content);
		rpcClient.stopService();
		System.out.println(result);

		// active MQ 发送消息不返回值
		// producerService.sendTextQueueMessage(content);
		return "success";
	}
}
