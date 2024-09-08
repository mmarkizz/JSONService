package com.example.JSONService.Repository;

import com.example.JSONService.Entity.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findByTitle(String title);
    Page<File> findAllByOrderByCreationDateDesc(Pageable pageable);//Для метода получения всех файлов

}
