package com.tum.sqliteapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;


public class BasicCrypto {
    
    public static String hashMessage(String message, String hashFunction){
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance(hashFunction);
            digest.update(message.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static Boolean encryptFile(String inFile, String outFile, String encryptionKey, String encryptionAlgorithm){
        try{
            // Encrypt the file residing in [filePath]
            FileInputStream fis = new FileInputStream(inFile);
            FileOutputStream fos = new FileOutputStream(outFile);
            SecretKeySpec sks = new SecretKeySpec(encryptionKey.getBytes(), encryptionAlgorithm);
            Cipher cipher = Cipher.getInstance(encryptionAlgorithm);
            cipher.init(Cipher.ENCRYPT_MODE, sks);
            CipherOutputStream cos = new CipherOutputStream(fos, cipher);
            int b;
            byte[] d = new byte[8];
            while((b = fis.read(d)) != -1) {
                cos.write(d, 0, b);
            }
            cos.flush();
            cos.close();
            fis.close();
        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
        // Delete the plaintext file
        File f = new File(inFile);
        f.delete();
        return true;
    }

    public static Boolean decryptFile(String inFile, String outFile, String decryptionKey, String decryptionAlgorithm){
        try{
            // Decrypt the file residing in [inFile]
            FileInputStream fis = new FileInputStream(inFile);
            FileOutputStream fos = new FileOutputStream(outFile);
            SecretKeySpec sks = new SecretKeySpec(decryptionKey.getBytes(), decryptionAlgorithm);
            Cipher cipher = Cipher.getInstance(decryptionAlgorithm);
            cipher.init(Cipher.DECRYPT_MODE, sks);
            CipherInputStream cis = new CipherInputStream(fis, cipher);
            int b;
            byte[] d = new byte[8];
            while((b = cis.read(d)) != -1) {
                fos.write(d, 0, b);
            }
            fos.flush();
            fos.close();
            cis.close();
        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
