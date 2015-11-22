package com.debatz.gifts.service;

import java.io.*;
import java.net.URL;

public class RemoteUploadService
{
    public static void save(String remoteFileUrl, String localFileUrl) throws IOException {
        InputStream is;
        OutputStream os;

        URL url = new URL(remoteFileUrl);
        is = url.openStream();
        os = new FileOutputStream(localFileUrl);

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();
    }
}
