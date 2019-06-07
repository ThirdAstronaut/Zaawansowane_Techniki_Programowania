package com.pwr.ztp_lab.controllers;

import com.pwr.ztp_lab.DTO.ImageDto;
import com.pwr.ztp_lab.models.Image;
import com.pwr.ztp_lab.repositories.ImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
@Slf4j
public class ImageController {

    private final ImageRepository imageRepository;

    public ImageController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @GetMapping(value = "/images", produces = MediaType.IMAGE_JPEG_VALUE)
    public void getImages(Model model) {
        List<ImageDto> list = new ArrayList<>();
        for (Image image : imageRepository.findAll()) {
            list.add(new ImageDto(image.getFileName(), image.getUploadTime(), Base64.getEncoder().encodeToString(image.getData())));
        }
        model.addAttribute("images", list);
    }


    @GetMapping("/")
    public String upload() {
        return "upload";
    }

    @ResponseBody
    @PostMapping("/upload")
    public ResponseEntity<ImageDto> singleFileUpload(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else if (!file.getContentType().equals("image/png")) {
            return ResponseEntity.status(409).build();
        }
        Image image = null;
        try {
            byte[] bytes = file.getBytes();
            image = new Image(file.getOriginalFilename(), bytes);
            imageRepository.save(image);
            log.debug("saved " + file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageDto imageDto = new ImageDto(image);
        return ResponseEntity.ok().body(imageDto);
    }
}

 /* @RequestMapping(value = "/image/{id:.+}", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id, HttpServletResponse response) {
        byte[] image = imageRepository.findById(id).get().getData();
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        return ResponseEntity.ok(image);
    }
*/