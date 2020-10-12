package com.udacity.jwdnd.course1.cloudstorage.services;

import java.util.List;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    private static Logger logger = LoggerFactory.getLogger(FileService.class);

    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public void deleteFile(Integer fileId, Integer userid) {
        fileMapper.deleteFile(fileId, userid);
    }

    public File getFile(Integer fileId, Integer userid) {
        return fileMapper.getFile(fileId, userid);
    }

    public List<File> getFileNames(Integer userid) {
        return fileMapper.getFiles(userid);
    }

    public void storeFile(MultipartFile file, Integer userid) {
        try {
            File storageFile = new File();
            storageFile.setUserid(userid);
            storageFile.setFilename(file.getOriginalFilename());
            storageFile.setFilesize(file.getSize());
            storageFile.setContenttype(file.getContentType());
            storageFile.setFiledata(file.getBytes());
            fileMapper.insert(storageFile);
        } catch (Exception e) {
            logger.error("Error during saving file to db", e);
        }
    }
}
