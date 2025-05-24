package com.example.memopractice.controller;

import com.example.memopractice.dto.RequestDto;
import com.example.memopractice.dto.ResponseDto;
import com.example.memopractice.entity.Memo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/memos")
public class Controller {

    // 메모 목록 담아줄 맵 형태 객체
    private final Map<Long, Memo> memoList = new HashMap<>();

    // 메모 작성 API - Post
    @PostMapping
    public ResponseEntity<ResponseDto> createMemo(@RequestBody RequestDto dto) {
        // 식별자 +1
        Long memoId = memoList.isEmpty() ? 1 : Collections.max(memoList.keySet()) + 1;
        // Memo 객체 생성
        Memo memo = new Memo(memoId, dto.getTitle(), dto.getContents());
        // 데이터 저장해주기
        memoList.put(memoId, memo); // 잘 빼먹던 부분 ***

        return new ResponseEntity<>(new ResponseDto(memo), HttpStatus.CREATED);
    }

    @GetMapping
    public List<ResponseDto> findAllMemos() {

        // 리스트 초기화
        List<ResponseDto> responseList = new ArrayList<>();

        // HashMap<Memo> -> List<ResponseDto>
        for (Memo memo : memoList.values()) {
            ResponseDto responseDto1 = new ResponseDto(memo);
            responseList.add(responseDto1);
        }

        // Map to List
        // responseList = memoList.values().stream().map(ResponseDto::new).toList();

        return responseList;
    }

    // 메모 조회 API - Get
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getMemoById(@PathVariable Long id) {
        Memo memo = memoList.get(id);

        if (memo==null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ResponseDto(memo), HttpStatus.OK);
    }

    // 메모 수정 API - Put
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> updateMemoById(
            @PathVariable Long id,
            @RequestBody RequestDto dto
    ) {
        Memo memo = memoList.get(id);

        if (memo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (dto.getTitle() == null || dto.getContents() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        memo.update(dto);

        return new ResponseEntity<>(new ResponseDto(memo), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto> updateTitle(
            @PathVariable Long id,
            @RequestBody RequestDto dto
    ) {
        Memo memo = memoList.get(id);

        if (memo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (dto.getTitle() == null || dto.getContents() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        memo.updateTitle(dto);

        return new ResponseEntity<>(new ResponseDto(memo), HttpStatus.OK);

    }

    // 메모 삭제 API - Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemoById(@PathVariable Long id) {

        if (memoList.containsKey(id)) {
            memoList.remove(id);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
