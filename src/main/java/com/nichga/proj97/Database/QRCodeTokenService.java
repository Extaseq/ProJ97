package com.nichga.proj97.Database;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.UUID;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class QRCodeTokenService {

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public static String getExpiryTime() {
        LocalDateTime expiryTime = LocalDateTime.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return expiryTime.format(formatter);
    }

    public static void storeTokenInDatabase(String token, String expiryTime, String bookId) {
        try (Connection conn = DatabaseConnector.getInstance().getConnection()) {
            String sql = "INSERT INTO qr_tokens (token, expiry_time, book_id) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, token);
                stmt.setString(2, expiryTime);
                stmt.setString(3, bookId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static boolean isValidToken(String token) {
        try (Connection conn = DatabaseConnector.getInstance().getConnection()) {
            String sql = "SELECT * FROM qr_tokens WHERE token = ? AND used = FALSE";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, token);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String expiryTime = rs.getString("expiry_time");
                    LocalDateTime expiry = LocalDateTime.parse(expiryTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    if (expiry.isAfter(LocalDateTime.now())) {
                        return true;  // Token còn hiệu lực
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void markTokenAsUsed(String token) {
        try (Connection conn = DatabaseConnector.getInstance().getConnection()) {
            String sql = "UPDATE qr_tokens SET used = TRUE WHERE token = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, token);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static BufferedImage generateQRCodeImage(String text, int width, int height) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
            }
        }

        return image;
    }

}
