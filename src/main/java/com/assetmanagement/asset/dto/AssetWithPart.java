package com.assetmanagement.asset.dto;

import lombok.Data;

import java.util.List;

@Data
public class AssetWithPart {
    private Long id;           // 자산 ID
    private List<Long> partIds;   // 선택한 부품 ID 리스트 (자산 등록 시 사용)
}
