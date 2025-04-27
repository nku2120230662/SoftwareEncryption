package com.guohao;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Demo {
    private static final String ALGORITHM = "AES";

    public static void main(String[] args) {
        try {
            // 生成密钥
            KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
            keyGen.init(128);
            SecretKey secretKey = keyGen.generateKey();

            // 读取 Innormal.class 文件
            String filePath = "/Users/bytedance/develop/SoftwareEncryption/src/main/java/com/guohao/Innormal.class";
            byte[] classBytes = loadClassBytes(filePath);

            // 加密文件内容
            byte[] encryptedBytes = encrypt(classBytes, secretKey);

            // 保存加密后的文件
            String encryptedFilePath = "/Users/bytedance/develop/SoftwareEncryption/src/main/java/com/guohao/Innormal_encrypted.class";
            saveBytesToFile(encryptedBytes, encryptedFilePath);

            System.out.println("文件加密完成，保存到: " + encryptedFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] loadClassBytes(String filePath) throws IOException {
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

    private static byte[] encrypt(byte[] plainBytes, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(plainBytes);
    }

    private static void saveBytesToFile(byte[] bytes, String filePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(bytes);
        }
    }
}