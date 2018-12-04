package com.yash;

import org.springframework.stereotype.Component;

@Component
public class Apple implements Mobile {
    @Override
    public void ram() {
        System.out.println("8 GB");
    }

    @Override
    public void processor() {
        System.out.println("895 chipset");
    }
}
