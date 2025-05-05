package com.guohao;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CustomClassLoader extends ClassLoader {
    private static final String ALGORITHM = "AES";
    private final SecretKey secretKey;

    public CustomClassLoader(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            String filePath = "/Users/bytedance/develop/SoftwareEncryption/src/main/java/com/guohao/Innormal_encrypted.class";
            byte[] encryptedBytes = loadClassBytes(filePath);
            byte[] decryptedBytes = decrypt(encryptedBytes, secretKey);
            return defineClass(name, decryptedBytes, 0, decryptedBytes.length);
        } catch (Exception e) {
            throw new ClassNotFoundException("Failed to load class: " + name, e);
        }
    }

    private byte[] loadClassBytes(String filePath) throws IOException {
        File file = new File(filePath);
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            return bos.toByteArray();
        }
    }

    private byte[] decrypt(byte[] encryptedBytes, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(encryptedBytes);
    }
}