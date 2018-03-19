package cn.eaglec.service;

import javax.jms.Destination;
import javax.jms.TextMessage;

public interface ConsumerService {
	
	public  TextMessage receive(Destination destination);


}
