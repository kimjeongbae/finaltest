package com.example.finaltest2.article;


import com.example.finaltest2.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 200)
    private String title;
    @Column
    private String content;
    @CreatedDate
    private LocalDateTime createDate;

    @ManyToOne
    private SiteUser author;
}
