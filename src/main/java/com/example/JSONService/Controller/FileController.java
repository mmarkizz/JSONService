package com.example.JSONService.Controller;

import com.example.JSONService.Entity.File;
import com.example.JSONService.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping
    public ResponseEntity<Long> createFile(@RequestBody File file) {
        Long fileId = fileService.createFile(file);
        return ResponseEntity.ok(fileId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<File> getFile(@PathVariable Long id){
        File file = fileService.getFile(id);
        return ResponseEntity.ok(file);
    }

    /*public FileController(FileService fileService){//для метода получения всех данных
        this.fileService=fileService;
    }

    @GetMapping
    public ResponseEntity<Page<File>> getAllFiles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection) {
        Page<File> files = fileService.getAllFiles(page, size, sortDirection);
        return ResponseEntity.ok(files);
    }*/
}
