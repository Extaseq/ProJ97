package com.nichga.proj97.Services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.nichga.proj97.Database.TokenRepository;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.UUID;

public class TokenProvider {
    private static final DatabaseService dbs = new DatabaseService();

    private static final int IMG_SIZE = 512;

    public TokenProvider() {

    }

    public static BufferedImage generateQRCode(String text) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, IMG_SIZE, IMG_SIZE);

        BufferedImage image = new BufferedImage(IMG_SIZE, IMG_SIZE, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();

        for (int x = 0; x < IMG_SIZE; x++) {
            for (int y = 0; y < IMG_SIZE; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
            }
        }

        return image;
    }

    public static String generateToken(String memberID, String bookID) {
        if (!dbs.getBookRepo().bookAvailable(bookID)) {
            return null;
        }
        String token = UUID.randomUUID().toString();
        if (dbs.getTokenRepo().pushToken(token, memberID, bookID)) {
            return token;
        }
        return null;
    }

    public static String[] getTokenInfo(String token) {
        return dbs.getTokenRepo().getTokenInfo(token);
    }

    public static boolean deleteToken(String token) {
        return dbs.getTokenRepo().deleteToken(token);
    }
}
