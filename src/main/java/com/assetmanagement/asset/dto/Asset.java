package com.assetmanagement.asset.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class Asset {
    private  Long row_num;
    private Long id;           // 자산 ID
    private List<Long> mappingIds;
    private Long mappingId;

    @NotBlank(message = "자산명은 필수 입력 항목입니다")
    @Size(min = 2, max = 50, message = "자산명은 2글자 이상 50글자 이하로 입력해주세요")
    private String name;       // 자산명

    @NotBlank(message = "IP는 필수 입력 항목입니다")
    private String ip;         // 자산 IP

    @NotBlank(message = "자산종류는 필수 선택 항목입니다")
    private String type;       // 자산 종류

    @NotBlank(message = "시리얼 번호는 필수 입력 항목입니다")
    @Size(min = 5, max = 20, message = "시리얼 번호는 5글자 이상 20글자 이하로 입력해주세요")
    private String serialNumber; // 시리얼 번호

    @Size(max = 255, message = "메모는 최대 255자까지 입력 가능합니다.")
    private String memo;       // 메모
    private String typeName;
    private int partCount;
    private DEL_YN delYn;

    private List<Long> partIds;   // 선택한 부품 ID 리스트 (자산 등록 시 사용)
    private List<Part> parts;  // 부품 리스트 (자산 조회 시 사용)
}
