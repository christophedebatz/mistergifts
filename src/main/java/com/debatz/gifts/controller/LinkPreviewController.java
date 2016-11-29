package com.debatz.gifts.controller;

import com.debatz.gifts.service.HttpImageProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/link-images")
public class LinkPreviewController {

    /**
     *
     * @param url
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<String>> getLinkImages(@RequestParam(value = "url") final String url) {
        List<String> images = new ArrayList<>();
        if (!url.startsWith("http")) {
            return ResponseEntity.badRequest().body(images);
        }
        try {
            return ResponseEntity.ok(HttpImageProvider.getImages(url));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(images);
        }
    }

}
