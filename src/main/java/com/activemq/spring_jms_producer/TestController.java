package com.activemq.spring_jms_producer;

import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.activemq.spring_jms_producer.MessageSendService;

@Controller
public class TestController {
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	private MessageSendService messageSendService;
	
	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public String init(Locale locale, Model model) {
		logger.info("init Page {}.", locale);
		
		//基本方式发送消息
    	//messageSendService.sendMessage("测试消息 -> Normal");
    	//使用线程池发送消息
    	messageSendService.sendMessageUsingThreadPool("测试消息 -> ThreadPool");
		
		return "home";
	}
}