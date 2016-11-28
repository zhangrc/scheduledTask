package com.yinhai.sheduledTask.frame.plugin.message.impl;


import com.yinhai.sheduledTask.frame.plugin.message.IMQ;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
/**
 * Created by zrc on 2016/9/22.
 */
public class ActiveMqImpl implements IMQ {

    private JmsTemplate jmsTemplate;

    @Override
    public void pushData(String destention, final String param) {
        jmsTemplate.send(destention, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(param);
            }
        });
    }

    @Override
    public void pushData(final String param) {
        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(param);
            }
        });
    }

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

}
