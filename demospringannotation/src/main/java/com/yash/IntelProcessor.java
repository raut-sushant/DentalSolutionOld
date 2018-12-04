package com.yash;

import org.springframework.stereotype.Component;

//@Component
public class IntelProcessor implements MobileProcessor {
    @Override
    public void process() {
        System.out.println("Intel Processor");
    }
}
