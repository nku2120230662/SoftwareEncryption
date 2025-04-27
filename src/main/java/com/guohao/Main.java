package com.guohao;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import static java.lang.Thread.sleep;

public class Main {

    public static void main(String[] args) throws Exception {
        // 生成密钥
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();

        // 使用自定义类加载器加载类
        CustomClassLoader classLoader = new CustomClassLoader(secretKey);
        Class<?> clazz = classLoader.loadClass("com.guohao.Innormal");
        Object instance = clazz.getDeclaredConstructor().newInstance();
        clazz.getMethod("printHello").invoke(instance);
    }
}