package com.assetmanagement.asset.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class Part {
    private  Long row_num;
    private Long id;            // 부품 ID
    private Long assetId;        // 자산 ID (해당 부품이 속한 자산)
    private Long mappingId;
    private List<Long> mappingIds;

    @NotBlank(message = "부품명은 필수 입력 항목입니다")
    @Size(min = 2, max = 50, message = "부품명은 2글자 이상 50글자 이하로 입력해주세요")
    private String name;        // 부품명

    @NotBlank(message = "시리얼 번호는 필수 입력 항목입니다")
    @Size(min = 5, max = 20, message = "시리얼 번호는 5글자 이상 20글자 이하로 입력해주세요")
    private String serialNumber; // 시리얼 번호

    @NotBlank(message = "제조사는 필수 입력 항목입니다")
    @Size(min = 2, max = 50, message = "제조사는 5글자 이상 50글자 이하로 입력해주세요")
    private String manufacturer; // 제조사
    private DEL_YN delYn;
}
