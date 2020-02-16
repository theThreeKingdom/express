package io.express;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletException;

import io.express.container.ContainerFactory;
import io.express.template.TemplateFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Encoder;

/**
 * Utils for create ContainerFactory, TemplateFactory, etc.
 */
public class Utils {
    private static final Logger log = LoggerFactory.getLogger(Utils.class);

    public static ContainerFactory createContainerFactory(String name) throws ServletException {
        ContainerFactory cf = tryInitContainerFactory(name);
        if (cf == null)
            cf = tryInitContainerFactory(ContainerFactory.class.getPackage().getName() + "." + name + ContainerFactory.class.getSimpleName());
        if (cf == null)
            throw new ConfigException("Cannot create container factory by name '" + name + "'.");
        return cf;
    }

    static ContainerFactory tryInitContainerFactory(String clazz) {
        try {
            Object obj = Class.forName(clazz).newInstance();
            if (obj instanceof ContainerFactory)
                return (ContainerFactory) obj;
        } catch (Exception e) {
        }
        return null;
    }

    public static TemplateFactory createTemplateFactory(String name) {
        TemplateFactory tf = tryInitTemplateFactory(name);
        if (tf == null)
            tf = tryInitTemplateFactory(TemplateFactory.class.getPackage().getName() + "." + name + TemplateFactory.class.getSimpleName());
        if (tf == null) {
            log.warn("Cannot init template factory '" + name + "'.");
            throw new ConfigException("Cannot init template factory '" + name + "'.");
        }
        return tf;
    }

    static TemplateFactory tryInitTemplateFactory(String clazz) {
        try {
            Object obj = Class.forName(clazz).newInstance();
            if (obj instanceof TemplateFactory)
                return (TemplateFactory) obj;
        } catch (Exception e) {
        }
        return null;
    }

    public static String getMd5Sum(String text) {
        if (text == null)
            return "";

        StringBuffer hexString = new StringBuffer();

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes());

            byte[] digest = md.digest();

            for (int i = 0; i < digest.length; i++) {
                text = Integer.toHexString(0xFF & digest[i]);
                if (text.length() < 2) {
                    text = "0" + text;
                }
                hexString.append(text);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return hexString.toString();
    }

    public static String generateToken() {
        String token = UUID.randomUUID().toString();
        token = getMd5Sum(token);
        return token;
    }

    /**
     * 生成N位随机数
     *
     * @param n
     * @return
     */
    public static String getRandom(int n) {
        Random r = new Random();
        if (n == 0)
            return "";
        String random = "";
        for (int k = 0; k < n; k++) {
            random += r.nextInt(9);
        }
        return random;
    }

    public static String encrypt(byte rawKeyData[], String str) throws InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeySpecException {
        KeyGenerator _generator = KeyGenerator.getInstance("DES");

        //防止linux下 随机生成key
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(rawKeyData);
        _generator.init(56, secureRandom);

        Key key = _generator.generateKey();
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, key, secureRandom);
        // 现在，获取数据并加密
        byte data[] = str.getBytes();
        // 正式执行加密操作
        byte[] encryptedData = cipher.doFinal(data);

        System.out.println("加密后===>" + encryptedData);
        BASE64Encoder encoder = new BASE64Encoder();
        String ret = encoder.encode(encryptedData);
        return ret;
    }
}
