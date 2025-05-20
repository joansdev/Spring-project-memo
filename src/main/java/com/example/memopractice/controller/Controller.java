package com.example.memopractice.controller;

import com.example.memopractice.dto.RequestDto;
import com.example.memopractice.dto.ResponseDto;
import com.example.memopractice.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/memos")
public class Controller {

    // 메모 목록 담아줄 맵 형태 객체
    private final Map<Long, Memo> memoList = new HashMap<>();

    // 메모 작성 API - Post
    @PostMapping
    public ResponseDto createMemo(@RequestBody RequestDto dto) {
        // 식별자 +1
        Long memoId = memoList.isEmpty() ? 1 : Collections.max(memoList.keySet()) + 1;
        // Memo 객체 생성
        Memo memo = new Memo(memoId, dto.getTitle(), dto.getContents());
        // 데이터 저장해주기
        memoList.put(memoId, memo); // 잘 빼먹던 부분 ***

        return new ResponseDto(memo);
    }

    // 메모 조회 API - Get
    @GetMapping("/{id}")
    public ResponseDto getMemoById(@PathVariable Long id) {
        Memo memo = memoList.get(id);
        return new ResponseDto(memo);
    }

    // 메모 수정 API - Put
    @PutMapping("/{id}")
    public ResponseDto updateMemoById(
            @PathVariable Long id,
            @RequestBody RequestDto dto
    ) {
        Memo memo = memoList.get(id);

        memo.update(dto);

        return new ResponseDto(memo);
    }

    // 메모 삭제 API - Delete
    @DeleteMapping("/{id}")
    public void deleteMemoById(@PathVariable Long id) {
        memoList.remove(id);
    }
}
