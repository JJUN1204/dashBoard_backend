package com.assetmanagement.asset.mapper;

import com.assetmanagement.asset.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AssetMapper {

    //  자산 리스트 조회
    List<Asset> findAssetList(SearchDto searchDto);

    //  자산 수 카운팅
    int count(SearchDto params);

    //  자산 등록
    void insertAsset(Asset asset);

    //  자산 업데이트
    void updateAsset(Asset asset);

    //  특정 자산 조회
    Asset getAssetById(int id);

    //  자산 삭제 (부품 연결 해제 후 삭제)
    void deleteAsset(@Param("delyn") DEL_YN delyn, @Param("id") Long id);

    //  특정 자산에 속한 부품 조회
    List<Part> getPartsByAssetId(int assetId);

    //  자산에 부품 할당 (부품의 asset_id 업데이트)
    void updatePartAssetId(@Param("assetId") Long assetId, @Param("partIds") List<Long> partIds);

    //  자산 삭제 시 해당 자산의 부품 asset_id를 NULL로 변경
    void unassignPartsFromAsset(@Param("assetId") Long assetId);


    void unassignPartsByIds(@Param("assetId") Long assetId, @Param("partIds") List<Long> partIds);

    // 특정 자산에서 부품 매핑 삭제 (mappingId 기반)
    void unassignPartsByMappingIds(@Param("mappingIds") List<Long> mappingIds);

    //자산, 부품 등록
    void insertAssetWithParts(@Param("assetId") Long assetId, @Param("partIds") List<Long> partIds);

    //부품 아이디 리스트
    List<Long> getPartIdsByAssetId(@Param("assetId") int assetId);

    List<Long> getMappedPartIds(@Param("assetId") int assetId);


    //자산 종류 리스트
    List<AssetType> getAllAssetTypes();

}