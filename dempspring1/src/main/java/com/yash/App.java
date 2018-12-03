package com.yash;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        Vehicle obj = (Car)context.getBean("vehicle");
        obj.drive();
        System.out.println("Car having tyres of "+((Car) obj).getTyre().getBrand());
        Tyre tyre = (Tyre)context.getBean("tyre");
        System.out.println(tyre.getBrand());
    }
}
