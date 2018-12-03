package com.yash;

public class Car implements Vehicle {

    Tyre tyre;

    @Override
    public void drive() {
        System.out.println("Car Driving");
    }

    public Tyre getTyre() {
        return tyre;
    }

    public void setTyre(Tyre tyre) {
        this.tyre = tyre;
    }
}
