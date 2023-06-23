package com.meteorcat.mix.core.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.Random;

/**
 * 加密库扩展
 * @author MeteorCat
 */
public class DigestExtends {


    /**
     * MD5处理
     * @param data 数据
     * @return String|Empty
     */
    public static Optional<String> md5(byte[] data){
        try{
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(data);
            return Optional.of(new BigInteger(1,digest.digest()).toString(16));
        }catch (NoSuchAlgorithmException exception){
            exception.printStackTrace();
            return Optional.empty();
        }
    }


    /**
     * Sha1哈希
     * @param data 数据
     * @return String|Empty
     */
    public static Optional<String> sha1(byte[] data){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA1");
            digest.update(data);

            byte[] bytes = digest.digest();
            int sz = bytes.length;
            StringBuilder buffer = new StringBuilder(sz * 2);

            final char[] hexs = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            for(byte b: bytes){
                buffer.append(hexs[ (b>>4) & 0x0f ]);
                buffer.append(hexs[b & 0x0f]);
            }
            return Optional.of(buffer.toString());
        }catch (NoSuchAlgorithmException exception){
            exception.printStackTrace();
            return Optional.empty();
        }
    }


    /**
     * 获取盐值
     * @param length 盐值长度
     * @return String|Empty
     */
    public static Optional<String> salt(int length){
        final char[] words = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        final int[] numbers = {0,1,2,4,5,6,7,8,9};

        Random random = new Random();
        StringBuilder builder = new StringBuilder(length);
        while (builder.length() < length){
            if(random.nextBoolean()){
                builder.append(words[random.nextInt(words.length)]);
            }else{
                builder.append(numbers[random.nextInt(numbers.length)]);
            }
        }
        return Optional.of(builder.toString());
    }



}
