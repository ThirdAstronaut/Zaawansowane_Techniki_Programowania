package com.pwr.ztp_lab.models;

import lombok.*;

import javax.persistence.*;
import java.util.Base64;
import java.util.Date;

@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private Date uploadTime;

    @Lob
    private byte[] data;

    public Image(String fileName, byte[] data) {
        this.fileName = fileName;
        this.data = data;
        this.uploadTime = new Date();
    }
}
