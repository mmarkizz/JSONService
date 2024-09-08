package com.example.JSONService.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="files")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date", nullable = false)
    @CreationTimestamp
    private Timestamp creationDate;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "file_content", nullable = false)
    private byte[] fileContent;

    @Column(name = "title", nullable = false)
    private String title;


}
