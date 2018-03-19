package cn.eaglec.listener;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.eaglec.controller.MessageController;
import cn.eaglec.service.ConsumerService;

public class QueueMessageListener implements MessageListener{
	private Logger logger = LoggerFactory.getLogger(MessageController.class);
	//队列消息消费者
    @Resource
    private ConsumerService consumer;
    @Resource(name = "demoQueueDestination")
    private Destination destination;
	public void onMessage(Message message) {
       // TextMessage tm = (TextMessage) message;
        try {
            
            //消息监听器，接收消息
            logger.info(Thread.currentThread().getName()+"------------开始接受消息");
            TextMessage tm = (TextMessage) message;
	        System.out.println("QueueMessageListener监听到了文本消息：\t"
                    + tm.getText());
	        logger.info(Thread.currentThread().getName()+"------------接受完毕");
            //do something ...
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
