package com.assetmanagement.asset.controller;

import com.assetmanagement.asset.dto.DEL_YN;
import com.assetmanagement.asset.dto.Part;
import com.assetmanagement.asset.dto.SearchDto;
import com.assetmanagement.asset.service.PartService;
import com.assetmanagement.asset.util.Result;
import jakarta.validation.Valid;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PartController {

    private final PartService partService;

    public PartController(PartService partService) {
        this.partService = partService;
    }

    /**
     * 부품 리스트 조회 API
     *
     * @param searchDto 검색 조건
     * @return 부품 리스트
     */
    @GetMapping("/parts")
    public ResponseEntity<Map<String, Object>> getParts(SearchDto searchDto) {
        List<Part> parts = partService.findPartList(searchDto);

        int totalCount = partService.count(searchDto);

        Map<String, Object> response = new HashMap<>();
        response.put("data", parts);
        response.put("total", totalCount);
        return ResponseEntity.ok(response);
    }

    /**
     * 부품 등록 API
     *
     * @param part 등록 조건
     * @return Result
     */
    @PostMapping("/parts/register")
    public ResponseEntity<Result> insertPart(@Valid @RequestBody Part part){
        return ResponseEntity.ok(partService.insertPart(part));
    }

    /**
     * 부품 수정 API
     *
     * @param part 수정 조건
     * @return Result
     */
    @PutMapping("/parts/updatePart")
    public ResponseEntity<Result> updatePart(@Valid @RequestBody Part part) {
        return ResponseEntity.ok(partService.updatePart(part));
    }

    /**
     * 특정 부품 리스트 조회 API
     *
     * @param id 검색 조건
     * @return Part
     */
    @GetMapping("/parts/{id}")
    public ResponseEntity<Part> getPartById(@PathVariable int id) {
        Part part = partService.getPartById(id);
        if (part != null) {
            return ResponseEntity.ok(part);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

//    @DeleteMapping("/parts/delete/{id}")
//    public ResponseEntity<Result> deletePart(@PathVariable int id) {
//        return ResponseEntity.ok(partService.deletePart(id));
//    }

    /**
     * 부품 삭제 API
     *
     * @param id 검색 조건
     * @return Result
     */
    @PutMapping("/parts/delete/{id}")
    public ResponseEntity<Result> deletePart(@PathVariable Long id) {
        return ResponseEntity.ok(partService.deletePart(id));
    }

    /**
     * 부품 리스트 조회 API
     *
     * @return <List<Part>
     */
    @GetMapping("/parts/unassigned")
    public ResponseEntity<List<Part>> getUnassignedParts() {
        return ResponseEntity.ok(partService.getUnassignedParts());
    }

    /**
     * 시리얼 번호 중복조회 API
     *
     * @param serialNumber 검색 조건
     * @return Result
     */
    @GetMapping("/parts/isSerialNumberAvailable/{serialNumber}")
    public ResponseEntity<Result> isSerialNumberAvailable(@PathVariable String serialNumber) {
        return ResponseEntity.ok(partService.isSerialNumberAvailable(serialNumber));
    }



}
