package com.sparta.hanghaememo.service;


import com.sparta.hanghaememo.dto.MemoRequestDto;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoService {
    private final MemoRepository memoRepository;
    //MemoRepository 연결되어 사용할 수 있다
    @Transactional
    public Memo createMemo(MemoRequestDto requestDto) {
        Memo memo = new Memo(requestDto);

        //자동으로 쿼리 생성 및 데이터베이스에 연결되어 저장
        memoRepository.save(memo);
        return memo;
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Memo> getMemos() {
        return memoRepository.findAllByOrderByModifiedAtDesc();
    }

    @Transactional
    public Long update(Long id, MemoRequestDto requestDto) {
        //수정하려는 메모가 데이터베이스에 있는지 유무 확인
        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

        memo.update(requestDto);
        return memo.getId();
    }

    @Transactional
    public Long delete(Long id) {
        //삭제하려는 메모가 데이터베이스에 있는지 유무 확인
        memoRepository.deleteById(id);
        return id;
    }
}