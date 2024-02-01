package com.bulade.donor.common.utils.crypto;

import com.bulade.donor.common.exception.BusinessException;
import com.bulade.donor.common.exception.DecryptException;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HexFormat;

public class CryptoUtils {
    public static String hmacSha256(String key, String plainText) {
        byte[] cipherText;

        try {
            var sha256HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(), "HmacSHA256");
            sha256HMAC.init(secret_key);
            cipherText = sha256HMAC.doFinal(plainText.getBytes());
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        return HexFormat.of().formatHex(cipherText);
    }

    public static String encryptByDesEcb(String key, String plainText) {
        byte[] cipherText;

        try {
            var cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, desKey(key));
            cipherText = cipher.doFinal(plainText.getBytes());
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        return Base64.getEncoder().encodeToString(cipherText);
    }

    public static String decryptByDesEcb(String key, String cipherText) {
        byte[] plainText;

        try {
            var cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, desKey(key));
            plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        } catch (Exception e) {
            throw new DecryptException(cipherText, e);
        }

        return new String(plainText);
    }

    public static String encryptByAesCbc(String secretKey, String hexIV, String plainText) {
        byte[] cipherText;

        try {
            var iv = new IvParameterSpec(HexFormat.of().parseHex(hexIV));
            var keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
            var cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
            cipherText = cipher.doFinal(plainText.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }

        return Base64.getEncoder().encodeToString(cipherText);
    }

    public static String decryptByAesCbc(String secretKey, String hexIV, String cipherText) {
        byte[] plainText;

        try {
            var iv = new IvParameterSpec(HexFormat.of().parseHex(hexIV));
            var keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
            var cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
            plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        return new String(plainText);
    }

    public static String decryptByAesEcb(String str, String skey) {
        try {
            byte[] data = Base64.getMimeDecoder().decode(str);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(skey.getBytes("UTF-8"), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            return new String(cipher.doFinal(data), "UTF-8");
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public static String encryptByAesEcb(String content, String password) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(password.getBytes("utf-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encrypted = cipher.doFinal(content.getBytes("utf-8"));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public static String encryptBySm4Ecb(String content, String key) {
        try {
            var cipher = Cipher.getInstance("SM4/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "SM4"));
            var res = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(res);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public static String decryptBySm4Ecb(String content, String key) {
        try {
            var rawContent = HexFormat.of().parseHex(content);
            var cipher = Cipher.getInstance("SM4/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "SM4"));
            return new String(cipher.doFinal(rawContent), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public static SecretKey desKey(String key) {
        SecretKey secretKey;
        try {
            var desKey = new DESKeySpec((key.getBytes()));
            var keyFactory = SecretKeyFactory.getInstance("DES");
            secretKey = keyFactory.generateSecret(desKey);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        return secretKey;
    }
}
