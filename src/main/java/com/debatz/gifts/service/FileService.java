package com.debatz.gifts.service;

import java.net.*;

public class FileService {

    public static boolean checkIfExists(String picture) {
        try {
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection conn = (HttpURLConnection) new URL(picture).openConnection();
            conn.setRequestMethod("HEAD");

            return conn.getResponseCode() == HttpURLConnection.HTTP_OK;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
