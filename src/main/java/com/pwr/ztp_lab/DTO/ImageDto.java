package com.pwr.ztp_lab.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pwr.ztp_lab.models.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto {


    private String fileName;

    private Date uploadTime;
   @JsonIgnore
    private String encodedImg;

    public ImageDto(Image image){
        this.fileName = image.getFileName();
        this.uploadTime = image.getUploadTime();
    }


}
