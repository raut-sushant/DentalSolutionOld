/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.utilities;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Sushant Raut
 */
public class AES {
  
  static String IV = "AAAAAAAAAAAAAAAA";
  static String plaintext = "PCWHK001X5VY1PSR"; /*Note null padding*/
  static String encryptionKey = "0123456789abcdef";
  
  public static String getEncriptionString(String encriptionText) throws Exception{
      byte[] cipher = AES.encrypt(encriptionText);
      StringBuffer strBuffer = new StringBuffer();
      for (int i=0; i<cipher.length; i++){
        strBuffer.append(new Integer(cipher[i])).append(" ");
      }
      return strBuffer.toString();
  }
  
  public static byte[] encrypt(String plainText) throws Exception{
    Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
    SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
    cipher.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
    return cipher.doFinal(plainText.getBytes("UTF-8"));
  }

  public static String decrypt(byte[] cipherText) throws Exception{
    Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
    SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
    cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
    return new String(cipher.doFinal(cipherText),"UTF-8");
  }
}
