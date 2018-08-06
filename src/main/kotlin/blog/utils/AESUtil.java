package blog.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.SecureRandom;

public class AESUtil {
    public static void genAESKeyPair(String filePath) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");

        try {
            String publicKeyString = Base64.encodeBase64String(keySpec.getEncoded());
            System.out.println(publicKeyString);

            FileWriter pubfw = new FileWriter(filePath + "AESKey.key");
            BufferedWriter pubbw = new BufferedWriter(pubfw);
            pubbw.write(publicKeyString);

            pubbw.flush();
            pubbw.close();
            pubfw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getSignature(String data, String MD5key) throws Exception {
        data = data + "|" + MD5key; // data��keyһ��ǩ��
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] b = md.digest(data.getBytes("UTF-8")); // md5ǩ��
        return Base64.encodeBase64String(b); // base64ת��
    }


    /*
    加密
     */
    public static String encryptAES(String plainTextData, String key) throws Exception {

        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(key.getBytes());
        kgen.init(128, secureRandom);

        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encryptedData = cipher.doFinal(plainTextData.getBytes("UTF-8"));

        return Base64.encodeBase64String(encryptedData); // base64ת��
    }


    //解密方法，我们要用的
    public static String decryptAES(String encryptedData, String key) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(key.getBytes());
        kgen.init(128, secureRandom);
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decryptedData = cipher.doFinal(Base64.decodeBase64(encryptedData));

        return new String(decryptedData, "UTF-8");
    }

    public static boolean verify(String encryptedSignature, String plainTextData, String key) throws Exception {
        String signature = getSignature(plainTextData, key);

        if (encryptedSignature.equals(signature)) {
            return true;
        }

        return false;
    }

}
