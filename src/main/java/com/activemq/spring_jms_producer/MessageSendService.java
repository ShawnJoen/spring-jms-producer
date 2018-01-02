package com.activemq.spring_jms_producer;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.springframework.stereotype.Component;

import com.activemq.spring_jms_producer.TestVO;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Component("messageSendService")
public class MessageSendService {

	@Autowired
	private ThreadPoolTaskExecutor notifyServiceThreadPool;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
    private Destination queueKEY;

    public void sendMessage(final String message) {
    	
    	TestVO data = new TestVO();
    	data.setName(message);
    	
    	Object JsonData = JSONObject.toJSON(data);
    	final String jsonString = JsonData.toString();

    	//发送消息
        jmsTemplate.send(queueKEY, new MessageCreator() {
        	public Message createMessage(Session session) throws JMSException {
                 return session.createTextMessage(jsonString);
            }
        });
    }
    
    public void sendMessageUsingThreadPool(final String message) {
    	
    	TestVO data = new TestVO();
    	data.setName(message);
    	
    	Object JsonData = JSONObject.toJSON(data);
    	final String jsonString = JsonData.toString();

		try {
			
			notifyServiceThreadPool.execute(new Runnable() {  
				@Override
				public void run() {
					//配置消息 Destination KEY
					jmsTemplate.setDefaultDestinationName("CustomQueueKEY3");
					//发送消息
					jmsTemplate.send(new MessageCreator() {
						public Message createMessage(Session session) throws JMSException {
							return session.createTextMessage(jsonString);
						}
					});
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}