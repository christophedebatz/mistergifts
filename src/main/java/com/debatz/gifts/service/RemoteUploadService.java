package com.debatz.gifts.service;

import java.io.*;
import java.net.URL;

public class RemoteUploadService
{
    private String localSavePlace;

    public RemoteUploadService(String localSavePlace) {
        this.localSavePlace = localSavePlace;
    }

    public void saveRemoteFile(String remoteFileUrl) throws IOException {
        URL url = null;

        try {
            url = new URL(remoteFileUrl);

            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(this.localSavePlace);

            byte[] b = new byte[2048];
            int length;

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }

            is.close();
            os.close();

        } catch (FileNotFoundException ex) {
            throw ex;
        } catch (IOException ex) {
            throw ex;
        }
    }
}
