package com.lb.rocketmq;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lb.entity.Person;

@RequestMapping("/test")
@RestController
public class Producer {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
 
    @GetMapping("send")
    public void send(){
    	


    	
    	Person person = new Person();
    	person.setAge(11);
    	person.setId(1);
    	person.setName("lb");
    	
        Message<Person> message = MessageBuilder.withPayload(person).build();
        Message<Person> build = MessageBuilder.withPayload(person).setHeader("", "").build();
        SendResult sendResultObj = rocketMQTemplate.syncSend("test-topic", message);
    	
        rocketMQTemplate.convertAndSend("test-topic",person);
    }
}
