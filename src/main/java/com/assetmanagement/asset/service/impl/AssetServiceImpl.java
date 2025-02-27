package com.assetmanagement.asset.service.impl;

import com.assetmanagement.asset.dto.*;
import com.assetmanagement.asset.mapper.AssetMapper;
import com.assetmanagement.asset.service.AssetService;
import com.assetmanagement.asset.util.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AssetServiceImpl implements AssetService {

    private final AssetMapper assetMapper;

    public AssetServiceImpl(AssetMapper assetMapper) {
        this.assetMapper = assetMapper;
    }

    @Override
    public List<Asset> findAssetList(SearchDto searchDto) {

        int totalCount = assetMapper.count(searchDto);


        searchDto.setTotalCount(totalCount);

        return assetMapper.findAssetList(searchDto);
    }

    @Override
    public int count(SearchDto searchDto) {
        return assetMapper.count(searchDto);
    }

//    @Override
//    public Result insertAsset(Asset asset) {
//        return null;
//    }


    @Override
    @Transactional
    public Result insertAssetWithParts(Asset asset) {
        try {
            //  자산 등록
            assetMapper.insertAsset(asset);

            //  방금 삽입된 자산의 ID 가져오기
            Long assetId = asset.getId(); // ID가 자동 증가(auto_increment)로 설정된 경우
            if (assetId == null) {
                throw new RuntimeException("자산 ID를 가져올 수 없습니다.");
            }

            //  선택된 부품이 있는 경우, 부품과 자산을 연결
            if (asset.getPartIds() != null && !asset.getPartIds().isEmpty()) {
                System.out.println(assetId );
                System.out.println(asset.getPartIds());
                assetMapper.insertAssetWithParts(assetId, asset.getPartIds());

            }

            return new Result("INSERT_COMPLETE");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("error");
        }
    }


    @Override
    @Transactional
    public Result updateAsset(Asset asset) {
        try {
            //  1. 자산 정보 업데이트
            assetMapper.updateAsset(asset);

            //  2. 기존 매핑된 부품 ID 조회
            List<Long> existingMappingIds = assetMapper.getMappedPartIds(Math.toIntExact(asset.getId()));
            System.out.println("기존 매핑된 부품 IDS: " + existingMappingIds);

            List<Long> existingPartIds = assetMapper.getPartIdsByAssetId(Math.toIntExact(asset.getId()));
            System.out.println("기존 부품 IDS: " + existingPartIds);

            //  3. 삭제할 매핑 찾기
            List<Long> removedMappings = existingMappingIds.stream()
                    .filter(mappingId -> !asset.getMappingIds().contains(mappingId))
                    .collect(Collectors.toUnmodifiableList());

            //  4. 추가된 부품 찾기
            List<Long> addedParts = asset.getPartIds().stream()
                    .filter(partId -> !existingPartIds.contains(partId))
                    .collect(Collectors.toUnmodifiableList());

            //  5. 삭제할 매핑이 있으면 실행
            if (!removedMappings.isEmpty()) {
                System.out.println("삭제 중인 매핑 IDS: " + removedMappings);
                assetMapper.unassignPartsByMappingIds(removedMappings); //  기존 코드에서 버그 수정
            }

            //  6. 추가된 부품 처리
            if (!asset.getPartIds().isEmpty()) {
                System.out.println("추가 중인 부품 IDS: " + addedParts);
                assetMapper.insertAssetWithParts(asset.getId(), asset.getPartIds());
            }

            return new Result("UPDATE_COMPLETE");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("error");
        }
    }




    @Override
    public Asset getAssetById(int id) {
        Asset asset = assetMapper.getAssetById(id);
        if (asset != null) {
            // 해당 자산의 부품 리스트 추가
            List<Part> parts = assetMapper.getPartsByAssetId(id);
            asset.setParts(parts);
        }
        return asset;
    }

    @Override
    @Transactional
    public Result deleteAsset(Long id) {
        try {
            //  자산에 속한 부품 연결 해제
           // assetMapper.unassignPartsFromAsset(id);

            // 자산 삭제여부를 y로 변경하기
            assetMapper.deleteAsset(DEL_YN.Y, id);

            return new Result("DELETE_COMPLETE");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("error");
        }
    }

    //파츠 리스트 조회
    @Override
    public List<Part> getPartsByAssetId(int assetId) {
        return assetMapper.getPartsByAssetId(assetId);
    }

    //코드화된 타입 종류 조회
    @Override
    public List<AssetType> getAllAssetTypes() {
        return assetMapper.getAllAssetTypes();
    }
}
