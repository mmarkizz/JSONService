package com.example.JSONService.Service;

import com.example.JSONService.Entity.File;
import com.example.JSONService.Repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort.Direction;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public Long createFile(File file) {
        File savedFile = fileRepository.save(file);
        return savedFile.getId();
    }

    public File getFile(Long id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));
    }

    public FileService(FileRepository fileRepository){
        this.fileRepository=fileRepository;
    }

    public Page<File> getAllFiles(int page, int size, Sort.Direction sortDirection){    //для метода получения всех данных
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection.name()), "creationDate");
        return fileRepository.findAllByOrderByCreationDateDesc(pageable);
    }
}
