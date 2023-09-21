package com.ohgiraffers.comprehensive.common.paging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@AllArgsConstructor // 매개변수 생성자
public class SelectCriteria { // 조회 조건이라는 뜻

    private int page;
    private int totalCount;
    private int limit;
    private int buttonAmount;
    private int maxPage;
    private int startPage;
    private int endPage;
    private int startRow;
    private int endRow;
    private String searchCondition;
    private String searchValue;

}
