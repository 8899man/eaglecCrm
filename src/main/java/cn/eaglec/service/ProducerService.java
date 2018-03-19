package cn.eaglec.service;

import javax.jms.Destination;
import javax.jms.TextMessage;

public interface ProducerService {

    public void sendMessage(Destination destination,final String msg);

    public void sendMessage(final String msg);

}
