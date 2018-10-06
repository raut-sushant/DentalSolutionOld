package com.ekdant.dentalsolution.principal;
import java.util.Date;
/**
 *
 * @author Sushant
 */
public class Data {

     String week, day, month, month_num, year;


    public void verData(){

        Date data = new Date();
        day = ""+data.getDate();
        year = ""+(1900 + data.getYear());

         switch(data.getDay()){
            case 0: week = "Sunday";
                break;
            case 1: week = "Monday";
                break;
            case 2: week = "Tuesday";
                break;
            case 3: week = "Wednesday";
                break;
            case 4: week = "Thursday";
                break;
            case 5: week = "Friday";
                break;
            case 6: week = "Saturday";
                break;
        }
        
        switch(data.getMonth()){
            case 0: month = "January";
                break;
            case 1: month = "February";
                break;
            case 2: month = "March";
                break;
            case 3: month = "April";
                break;
            case 4: month = "May";
                break;
            case 5: month = "June";
                break;
            case 6: month = "July";
                break;
            case 7: month = "August";
                break;
            case 8: month = "September";
                break;
            case 9: month = "October";
                break;
            case 10: month = "November";
                break;
            case 11: month = "December";
                break;           
        }

        switch(data.getMonth()){
            case 0: month_num = "01";
                break;
            case 1: month_num = "02";
                break;
            case 2: month_num = "03";
                break;
            case 3: month_num = "04";
                break;
            case 4: month_num = "05";
                break;
            case 5: month_num = "06";
                break;
            case 6: month_num = "07";
                break;
            case 7: month_num = "08";
                break;
            case 8: month_num = "09";
                break;
            case 9: month_num = "10";
                break;
            case 10: month_num = "11";
                break;
            case 11: month_num = "12";
                break;
        }
    }
}
