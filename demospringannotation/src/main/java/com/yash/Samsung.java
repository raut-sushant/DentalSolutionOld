package com.yash;

import org.springframework.stereotype.Component;

@Component
public class Samsung implements Mobile {

    MobileProcessor mobileProcessor;
    @Override
    public void ram() {
        System.out.println("8 GB");
    }

    @Override
    public void processor() {
        System.out.println("845 chipset");
    }
}
