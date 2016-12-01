package com.debatz.gifts.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping(path = "/file")
public class FileStorageController {

    /**
     *
     * @param file
     * @return
     */
    @RequestMapping(path = "/{file:.+}", method = RequestMethod.GET)
    public void getPicture(@PathVariable(value = "file") String file, HttpServletResponse response) {

        String[] pictureExtArray = file.split("\\.");
        if (pictureExtArray.length == 2) {
            String pictureExt = pictureExtArray[pictureExtArray.length - 1];

            try {
                InputStream is = new FileInputStream(new File(ListController.UPLOADS_DIRECTORY + file));
                org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
                response.setHeader("Content-Type", "image/" + pictureExt);
                response.flushBuffer();
            } catch (IOException ex) {
                throw new RuntimeException("IOError writing file to output stream");
            }
        }
    }

}
