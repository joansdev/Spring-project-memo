package com.example.memopractice.dto;

import com.example.memopractice.entity.Memo;
import lombok.Getter;

@Getter
public class ResponseDto {

    // 속성
    private Long id;
    private String title;
    private String contents;

    public ResponseDto(Memo memo) {
        this.id = memo.getId();
        this. title = memo.getTitle();
        this.contents = memo.getContents();
    }

}
