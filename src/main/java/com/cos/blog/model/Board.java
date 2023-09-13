package com.cos.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob // 대용량 데이터 잡을 때
    private String content; // 섬머노트 라이브러리 <html>태그가 섞여서 디자인이 됨 -> 용량이 커짐

    private int count; // 조회수

    @ManyToOne(fetch = FetchType.EAGER)  // Many = Board, User = One
    @JoinColumn(name = "userId")
    private User user; // DB는 오브젝트를 저장할 수 없어서 FK를 사용하는데, 자바는 오브젝트를 저장할 수 있다.

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER) // 하나의 게시글은 여러개의 답글을 가질 수 있다
    @JsonIgnoreProperties({"board"})
    @OrderBy("id desc")
    private List<Reply> replys;

    @CreationTimestamp
    private Timestamp createDate;
}

// reply에 JoinColumn 을 쓰지 않는 이유? (=FK를 잡지 않는 이유?)
// 예를 들어 하나의 게시물을 select 했을 때 해당 게시물에는 여러개의 답글이 올 수 있다
// 그런데 FK를 잡아서 컬럼을 만들게 되면 하나의 컬럼에는 하나의 값만 와야 하는데 여러개의 답글이 오게되면
// 여러개의 값이 들어가기 때문에 DB의 정규화에 맞지 않게 된다

// mappedBy : 연관관계의 주인이 아니다 (FK가 아니다) -> DB에 컬럼을 만들지 말아라
// Board를 select할 때 join을 통해 값을 얻기위해 필요한 것이다

// fetchType.EAGER // 무조건 들고오라는 전략
// fetchType.LAZY // 필요할 때 들고오라는 전략