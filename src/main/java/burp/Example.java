package burp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class Example {

    private static String authKey;
    private static long timestamp;

    public static void main(String[] args) {
        generateAuthKey();
        System.out.println("auth_key = " + authKey);
        System.out.println("timestamp = " + timestamp);
    }

    public static void generateAuthKey() {
        long now = new Date().getTime() / 1000L;
        timestamp = now;
        String input = String.valueOf(now);
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md.digest(input.getBytes());
            authKey = bytesToHex(md5Bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static String getAuthKey() {
        generateAuthKey();
        return authKey;
    }

    public static long getTimestamp() {
        generateAuthKey();
        return timestamp;
    }
}
