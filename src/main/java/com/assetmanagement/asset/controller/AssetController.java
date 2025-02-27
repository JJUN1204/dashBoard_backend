package com.assetmanagement.asset.controller;

import com.assetmanagement.asset.dto.Asset;
import com.assetmanagement.asset.dto.AssetType;
import com.assetmanagement.asset.dto.Part;
import com.assetmanagement.asset.dto.SearchDto;
import com.assetmanagement.asset.service.AssetService;
import com.assetmanagement.asset.util.Result;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    /**
     * 자산 리스트 조회 API
     *
     * @param searchDto 검색 조건
     * @return 자산 리스트
     */
    @GetMapping("/assets")
    public ResponseEntity<Map<String, Object>> getAssets(SearchDto searchDto) {
        List<Asset> assets = assetService.findAssetList(searchDto);
        int totalCount = assetService.count(searchDto);

        Map<String, Object> response = new HashMap<>();
        response.put("data", assets);
        response.put("total", totalCount);
        return ResponseEntity.ok(response);
    }

    /**
     * 자산 등록 API
     *
     * @param asset 등록 조건
     * @return Result
     */
    @PostMapping("/assets/registerWithParts")
    public ResponseEntity<Result> insertAssetWithParts(@Valid @RequestBody Asset asset) {
        return ResponseEntity.ok(assetService.insertAssetWithParts(asset));
    }

    /**
     * 특정 자산의 부품 리스트 조회 API
     *
     * @param assetId 조회 조건
     * @return List<Part>
     */
    @GetMapping("/assets/{assetId}/parts")
    public ResponseEntity<List<Part>> getPartsByAssetId(@PathVariable int assetId) {
        return ResponseEntity.ok(assetService.getPartsByAssetId(assetId));
    }

    /**
     * 자산 업데이트 API
     *
     * @param asset 수정 조건
     * @return Result
     */
    //
    @PutMapping("/assets/updateAsset")
    public ResponseEntity<Result> updateAsset(@Valid @RequestBody Asset asset) {
        return ResponseEntity.ok(assetService.updateAsset(asset));
    }

    /**
     * 특정 자산 조회 API
     *
     * @param id 조회 조건
     * @return Asset
     */
    //
    @GetMapping("/assets/{id}")
    public ResponseEntity<Asset> getAssetById(@PathVariable int id) {
        Asset asset = assetService.getAssetById(id);
        if (asset != null) {
            return ResponseEntity.ok(asset);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * 아이디를 통한 자산 삭제 API
     *
     * @param id 조회 조건
     * @return Result
     */
    //
    @PutMapping("/assets/delete/{id}")
    public ResponseEntity<Result> deleteAsset(@PathVariable Long id) {
        return ResponseEntity.ok(assetService.deleteAsset(id));
    }

    /**
     * 자산 종류 API
     *
     *
     * @return List<AssetType>
     */
    @GetMapping("/assets/asset-types")
    public ResponseEntity<List<AssetType>> getAllAssetTypes() {
        return ResponseEntity.ok(assetService.getAllAssetTypes());
    }





//    @PostMapping("/assets/register")
//    public ResponseEntity<Result> insertAsset(@RequestBody Ass assetReg){
//        return ResponseEntity.ok(assetService.insertAsset(assetReg));
//    }
}
