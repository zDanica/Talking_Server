package edu.zh.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.beans.factory.annotation.Autowired;

import com.linda.framework.rpc.server.SimpleRpcServer;

import edu.zh.RPCConfiguration;
import edu.zh.config.RPCConversation;
import edu.zh.config.RPCConversationImpl;

public class RPCListener implements ServletContextListener{
	private SimpleRpcServer rpcServer;
	@Autowired  
	RPCConfiguration rpcConfiguration;  
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		rpcServer = new SimpleRpcServer();
		rpcServer.setHost(rpcConfiguration.getHost());
		rpcServer.setPort(rpcConfiguration.getPort());
		rpcServer.register(RPCConversation.class, new RPCConversationImpl());
		rpcServer.startService();
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		rpcServer.stopService();
	}
}
