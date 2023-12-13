package com.ll.medium.domain.article.entity;

import com.ll.medium.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Article {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String body;

    private LocalDateTime createDate;

    private boolean isPublished;

    @ManyToOne
    private Member author;

    private LocalDateTime modifyDate;

    private Integer hit;
}
