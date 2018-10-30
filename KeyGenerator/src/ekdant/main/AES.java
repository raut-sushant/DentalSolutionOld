/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ekdant.main;

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
  
  public static void main(String [] args) {
    try {
      
      System.out.println("==Java==");
      System.out.println("plain:   " + plaintext);

      byte[] cipher = encrypt(plaintext);
      String decryptedStr = "-39 2 23 88 122 -52 118 -4 -65 -30 40 -72 -51 75 -122 44";
      
      
      String str[] = decryptedStr.split(" ");
      byte[] decryptedBytArr = new byte[str.length];
      
      for (int i = 0; i < str.length; i++){
          decryptedBytArr[i] = new Byte(str[i]);
      }

      System.out.println("cipher:  " + cipher);
      for (int i=0; i<cipher.length; i++)
        System.out.print(new Integer(cipher[i])+" ");
      System.out.println("");
      
      for (int i=0; i<decryptedBytArr.length; i++)
        System.out.print(new Integer(decryptedBytArr[i])+" ");
      System.out.println("");

      String decrypted = decrypt(decryptedBytArr);

      System.out.println("decrypt: " + decrypted);

    } catch (Exception e) {
      e.printStackTrace();
    } 
  }

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
