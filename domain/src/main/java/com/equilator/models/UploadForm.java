package com.equilator.models;

import org.springframework.web.multipart.MultipartFile;

public class UploadForm {
    private MultipartFile fileDatas;

    public MultipartFile getFileDatas() {
        return fileDatas;
    }

    public void setFileDatas(MultipartFile fileDatas) {
        this.fileDatas = fileDatas;
    }
}