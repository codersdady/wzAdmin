package com.wzAdmin.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class Base64ImageUtils {
    public String encodeImageToBase64(MultipartFile file) {
        byte[] bytes = null;
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(bytes).trim();
    }

    /**
     * 将图片内容解码为输入流
     *
     * @param base
     * @return
     */
    public InputStream decodeBase64ToImage(String base) {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] decodeBytes = null;
        try {
            decodeBytes = decoder.decodeBuffer(base);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(decodeBytes);

    }
}
