package cn.eaglec.controller;

import org.springframework.stereotype.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.eaglec.service.ConsumerService;
import cn.eaglec.service.ProducerService;



import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.TextMessage;

@Controller
public class MessageController {

	 private Logger logger = LoggerFactory.getLogger(MessageController.class);
	    @Resource(name = "demoQueueDestination")
	    private Destination destination;

	    //队列消息生产者
	    @Resource
	    private ProducerService producer;

	    //队列消息消费者
	    @Resource
	    private ConsumerService consumer;

	    @RequestMapping(value = "/SendMessage", method = RequestMethod.GET)
	    @ResponseBody
	    public void send(String msg) {
	    	logger.info(Thread.currentThread().getName()+"------------开始发送消息");
	        producer.sendMessage(msg);
	        logger.info(Thread.currentThread().getName()+"------------发送完毕");
	    }

	    @RequestMapping(value= "/ReceiveMessage",method = RequestMethod.GET)
	    @ResponseBody
	    public Object receive(){
	    	logger.info(Thread.currentThread().getName()+"------------开始接受消息");
	        TextMessage tm = consumer.receive(destination);
	        logger.info(Thread.currentThread().getName()+"------------接受完毕");
	        return tm;
	    }


}
