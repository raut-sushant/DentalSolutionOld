package com.yash;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("snapDragon")
public class SnapDragon implements MobileProcessor {
    @Override
    public void process() {
        System.out.println("Snap Dragon Processor");
    }
}
