package com.assetmanagement.asset.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchDto {
    private int page;             // 현재 페이지 번호
    private int recordSize;       // 페이지당 출력할 데이터 개수
    private String keyword;       // 검색 키워드
    private String searchType;    // 검색 유형
    private String sortColumn;    // 정렬할 컬럼명
    private String sortDirection; // 정렬 방향 (ASC, DESC)
    private int totalCount;

    public SearchDto() {
        this.page = 1;
        this.recordSize = 10; // 기본 10개씩 조회
        this.sortColumn = "row_num";  // 기본 정렬 컬럼
        this.sortDirection = "DESC";  // 기본 정렬 방향
    }

    public int getOffset() {
        return (page - 1) * recordSize; // OFFSET 계산
    }
}
