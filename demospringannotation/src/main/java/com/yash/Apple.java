package com.yash;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Apple implements Mobile {

    @Autowired
    MobileProcessor mobileProcessor;
    @Override
    public void ram() {
        System.out.println("8 GB");
    }

    @Override
    public void processor() {
        System.out.println("895 chipset");
    }

    public MobileProcessor getMobileProcessor() {
        return mobileProcessor;
    }

    public void setMobileProcessor(MobileProcessor mobileProcessor) {
        this.mobileProcessor = mobileProcessor;
    }
}
