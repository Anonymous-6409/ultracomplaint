package com.ultrasoft.ultracomplaint.mapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.UUID;

public class UploadDocs {

    private static final String[] extensions = {"png","jpg","jpeg","pdf","mp4","avi","mkv","mov","wmv","flv","mpeg"};

    @Value(value = "${upload.photos.location}")
    private static String path;

    public static String image(MultipartFile file) throws Exception{
        try {
            String ext = StringUtils.getFilenameExtension(file.getOriginalFilename());
            if(ext != null) {
                boolean found = Arrays.stream(extensions).anyMatch(a-> a.equals(ext.toLowerCase()));
                if(!found) {
                    throw new Exception("Invalid Extension " +ext);
                }
            }
            String fileName = UUID.randomUUID().toString().concat("."+ext);
            File newFile = new File(
//                    path+fileName);
                    path+fileName);

            newFile.createNewFile();
            FileOutputStream myFile = new FileOutputStream(newFile);
            myFile.write(file.getBytes());
            myFile.close();
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }
}
