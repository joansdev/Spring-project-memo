package com.example.memopractice.entity;

import com.example.memopractice.dto.RequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Memo {

    // 속성
    private Long id;
    private String title;
    private String contents;

    // 수정 메소드
    public void update(RequestDto dto) {
        this.title = dto.getTitle();
        this.contents = dto.getContents();
    }

    // 제목 수정 메소드
    public void updateTitle(RequestDto dto) {
        this.title = dto.getTitle();
    }
}
