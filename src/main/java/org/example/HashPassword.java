package org.example;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Base64;

public class HashPassword {
    private String pathFileKey = "/home/mpuss/kodingan/inteelij/kuncen/src/main/resources/ngeblak.jceks";


    // Method take key and generate key enkripsi
    public Key getOrGenerateSecretKey() throws Exception {
        // open key on file
        KeyStore keyStore = KeyStore.getInstance("JCEKS");
        char[] keystorePassword = "keystore_password".toCharArray();
        try (FileInputStream fileInputStream = new FileInputStream(pathFileKey)) {
            keyStore.load(fileInputStream, keystorePassword);
        } catch (java.io.FileNotFoundException e) {
            // if key not found, Make new file
            keyStore.load(null, keystorePassword);
        }

        // Check key
        if (keyStore.containsAlias("my_aes_key")) {
            // take key
            char[] keyPassword = "key_password".toCharArray(); // key password
            return keyStore.getKey("my_aes_key", keyPassword);
        } else {
            // make new key aes
            SecretKey secretKey = generateAESKey();

            // save key on file
            char[] keyPassword = "key_password".toCharArray(); // key password
            keyStore.setKeyEntry("my_aes_key", secretKey, keyPassword, null);

            // save key on file
            try (FileOutputStream fos = new FileOutputStream(pathFileKey)) {
                keyStore.store(fos, keystorePassword);
            }

            return secretKey;
        }
    }

    public SecretKey generateAESKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256, new SecureRandom());
        return keyGenerator.generateKey();
    }

    public String encrypt(String plaintext, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String encryptedText, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
