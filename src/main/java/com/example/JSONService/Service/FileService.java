package com.example.JSONService.Service;

import com.example.JSONService.Entity.File;
import com.example.JSONService.Repository.FileRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;



@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private StringRedisTemplate redisTemplate;//Это позволяет вам взаимодействовать с Redis. Мы используем его для получения и сохранения строковых значений.

    public Long createFile(File file) {
        File savedFile = fileRepository.save(file);
        return savedFile.getId();
    }

    /*public File getFile(Long id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));
    }*/

    public File getFile(Long id){

        // Проверяем кэш Redis
        String cachedFile = redisTemplate.opsForValue().get("file:"+id);

        // Если файл кэширован, десериализуем его и возвращаем
        if(cachedFile !=null){
            return deserialize(cachedFile);
        }

        return fileRepository.findById(id)     //Если файл найден, кэшируем его
                .map(file -> {
                    cacheFile(file);
                    return file;
                })
                .orElseThrow(()-> new RuntimeException("File not found"));
    }

    private void cacheFile(File file){
        try {

            String serializedFile = serializeFile(file);

            //Метод set используется для сохранения значения в Redis под указанным ключом file:
            redisTemplate.opsForValue().set("file:"+ file.getId(), serializedFile);

        } catch (JsonProcessingException e){

            throw new RuntimeException("Failed to cache file", e);
        }
    }

    private String serializeFile(File file) throws JsonProcessingException{

        //Здесь создается новый экземпляр класса ObjectMapper. Этот класс является частью библиотеки Jackson и предоставляет функциональность для преобразования Java-объектов в JSON и обратно (десериализация из JSON в объекты).
        ObjectMapper mapper = new ObjectMapper();

        //Метод writeValueAsString принимает объект (в данном случае file) и преобразует его в строку JSON.
        return mapper.writeValueAsString(file);
    }

    private File deserialize(String raw) {
        try {

            //Здесь создается новый экземпляр класса ObjectMapper. Этот класс является частью библиотеки Jackson и предоставляет функциональность для преобразования Java-объектов в JSON и обратно (десериализация из JSON в объекты).
            ObjectMapper mapper = new ObjectMapper();

            //readValue разбирает JSON файлы на ключ и значение
            return mapper.readValue(raw, File.class);


        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }



    /*public FileService(FileRepository fileRepository){
        this.fileRepository=fileRepository;
    }

    public Page<File> getAllFiles(int page, int size, Sort.Direction sortDirection){    //для метода получения всех данных
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection.name()), "creationDate");
        return fileRepository.findAllByOrderByCreationDateDesc(pageable);
    }*/
}
