package com.assetmanagement.asset.service;

import com.assetmanagement.asset.dto.Asset;
import com.assetmanagement.asset.dto.AssetType;
import com.assetmanagement.asset.dto.Part;
import com.assetmanagement.asset.dto.SearchDto;
import com.assetmanagement.asset.util.Result;

import java.util.List;

public interface AssetService {
    List<Asset> findAssetList(SearchDto searchDto);
    int count(SearchDto searchDto);
    //Result insertAsset(Asset asset);
    Result updateAsset(Asset asset);
    Asset getAssetById(int id);
    Result deleteAsset(Long id);

    //  자산 등록 시 부품 함께 추가
    Result insertAssetWithParts(Asset asset);

    //  특정 자산의 부품 리스트 조회
    List<Part> getPartsByAssetId(int assetId);

    List<AssetType> getAllAssetTypes();


}
