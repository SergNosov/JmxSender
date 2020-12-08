package gov.kui.jmssender.model;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileEntity implements Serializable {
    private int id;
    private String filename;
    private String contentType;
    private long fileSize;

    @ToString.Exclude
    private transient byte[] byteOfUploadFile;

    public static FileEntity of(MultipartFile multipartFile){
        Assert.notNull(multipartFile,"Ошибка загрузки файла. MultipartFile is null.");
        Assert.isTrue(!multipartFile.isEmpty(),"Ошибка загрузки файла. MultipartFile is empty.");

        try {
            FileEntity fileEntity = new FileEntity();
            fileEntity.setFilename(multipartFile.getOriginalFilename());
            fileEntity.setContentType(multipartFile.getContentType());
            fileEntity.setFileSize(multipartFile.getSize());
            fileEntity.setByteOfUploadFile(multipartFile.getBytes());

            return fileEntity;
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки файла. multipartFile: "
                    + multipartFile.getOriginalFilename() + "; " + e.getMessage());
        }
    }
}
