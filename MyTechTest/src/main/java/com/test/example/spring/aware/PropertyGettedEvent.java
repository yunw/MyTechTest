package com.test.example.spring.aware;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class PropertyGettedEvent extends ApplicationEvent {

    public PropertyGettedEvent(Object source) {
        super(source);
    }

}
