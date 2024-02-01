package com.bulade.donor.common.utils.crypto;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.bouncycastle.util.io.pem.PemWriter;

import javax.crypto.Cipher;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RsaUtils {
    record PPKeys(String privateKey, String publicKey) {
    }

    public static PPKeys createKeys(int keySize) throws NoSuchAlgorithmException, IOException {
        // Create keyPair
        var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);
        var keyPair = keyPairGenerator.generateKeyPair();

        // Convert PrivateKey to PEM format
        var privateWrite = new StringWriter();
        var privatePemWriter = new PemWriter(privateWrite);
        privatePemWriter.writeObject(new PemObject("privateKey", keyPair.getPrivate().getEncoded()));
        privatePemWriter.close();
        var privateKey = privateWrite.toString();
        privatePemWriter.close();
        privateWrite.close();

        // Convert PublicKey to PEM format
        var publicWrite = new StringWriter();
        var publicPemWriter = new PemWriter(publicWrite);
        privatePemWriter.writeObject(new PemObject("publicKey", keyPair.getPublic().getEncoded()));
        publicPemWriter.close();
        var publicKey = publicWrite.toString();
        publicPemWriter.close();
        publicWrite.close();

        return new PPKeys(privateKey, publicKey);
    }

    public static String encrypt(String publicKeyPem, String plainText) throws Exception {

        // Read PEM Format
        var pemReader = new PemReader(new StringReader(publicKeyPem));
        var content = pemReader.readPemObject().getContent();
        // Get X509EncodedKeySpec format
        var keySpec = new X509EncodedKeySpec(content);

        var kf = KeyFactory.getInstance("RSA");
        var publicKeySecret = kf.generatePublic(keySpec);

        var cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKeySecret);
        var encryptedBytes = cipher.doFinal(plainText.getBytes());

        return Base64.getEncoder().encodeToString(encryptedBytes);
    }


    public static String decrypt(String privateKeyPem, String encryptedString) throws Exception {
        // Read PEM Format
        var pemReader = new PemReader(new StringReader(privateKeyPem));
        var pemObject = pemReader.readPemObject();
        pemReader.close();

        // Get PKCS8EncodedKeySpec for decrypt
        var keySpec = new PKCS8EncodedKeySpec(pemObject.getContent());
        var kf = KeyFactory.getInstance("RSA");
        var privateKeySecret = kf.generatePrivate(keySpec);

        var cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKeySecret);
        return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedString)), StandardCharsets.UTF_8);

    }
}
