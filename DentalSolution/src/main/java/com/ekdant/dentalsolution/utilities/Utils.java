/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.utilities;

import com.ekdant.dentalsolution.dao.TokensDao;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

/**
 *
 * @author dinesh.mali
 */
public class Utils {
    static TokensDao tokensDao = new TokensDao();
    private static final DateFormat displayDateFormat = new SimpleDateFormat("dd-MMM-yyyy");

    public static byte[] getByteArray(String string) {
        String str[] = string.split(" ");
        byte[] decryptedBytArr = new byte[str.length];

        for (int i = 0; i < str.length; i++) {
            decryptedBytArr[i] = new Byte(str[i]);
        }

        return decryptedBytArr;
    }

    public static String getValidString(String str) {
        String validStringAppender = "\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";
        return (str + validStringAppender).substring(0, 16);
    }

    public static boolean demoVersion() {
        boolean demoVersion;
        try {
            String motherboardSN = getMotherboardSN();
            byte[] decryptedBytArr = tokensDao.getTokenByte("K2");
            String databaseSecret = AES.decrypt(decryptedBytArr);
            demoVersion = !motherboardSN.contains(databaseSecret.trim());
        } catch (Exception ex) {
            demoVersion = true;
        }
        return demoVersion;
    }
    
    public static boolean activationExpired() {
        boolean activationExpired = false;
        try {
            byte[] decryptedBytArr = tokensDao.getTokenByte("K4");
            String databaseSecret = AES.decrypt(decryptedBytArr);
            Date activationExpirationDate = displayDateFormat.parse(databaseSecret);
            if(activationExpirationDate.before(new Date())){
                activationExpired = true;
            }
        } catch (Exception ex) {
            activationExpired = true;
        }
        return activationExpired;
    }
    
    public static boolean activationExpiring(int days) {
        boolean activationExpiring = false;
        try {
            byte[] decryptedBytArr = tokensDao.getTokenByte("K4");
            String databaseSecret = AES.decrypt(decryptedBytArr);
            Date activationExpirationDate = displayDateFormat.parse(databaseSecret);
            
            Calendar activationExpirationCal = new GregorianCalendar();
            activationExpirationCal.setTime(new Date());
            activationExpirationCal.add(Calendar.DATE, days);
            
            if(activationExpirationDate.before(new Date(activationExpirationCal.getTimeInMillis()))){
                activationExpiring = true;
            }
        } catch (Exception ex) {
            activationExpiring = true;
        }
        return activationExpiring;
    }
    
    public static int activationRemaining() {
        int activationRemaining = 0;
        try {
            byte[] decryptedBytArr = tokensDao.getTokenByte("K4");
            String databaseSecret = AES.decrypt(decryptedBytArr);
            Date activationExpirationDate = displayDateFormat.parse(databaseSecret);
            activationRemaining = (int)((activationExpirationDate.getTime() - new Date().getTime()) / (1000*60*60*24));
        } catch (Exception ex) {}
        return activationRemaining;
    }

    public static String getMotherboardSN() {
        String result = "";
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);

            String vbs
                    = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                    + "Set colItems = objWMIService.ExecQuery _ \n"
                    + "   (\"Select * from Win32_BaseBoard\") \n"
                    + "For Each objItem in colItems \n"
                    + "    Wscript.Echo objItem.SerialNumber \n"
                    + "    exit for  ' do the first cpu only! \n"
                    + "Next \n";

            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input
                    = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch (Exception e) {
        }
        result = result.trim();
        return getValidString(formatFileName(result)).trim();
    }

    private static String formatFileName(String fileName){
        String normalized = Normalizer.normalize(fileName, Normalizer.Form.NFD);
        String result = normalized.replaceAll("[^A-Za-z0-9]", "");
        return result;
    }
}
