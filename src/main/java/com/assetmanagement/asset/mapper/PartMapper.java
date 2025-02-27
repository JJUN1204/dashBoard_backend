package com.assetmanagement.asset.mapper;

import com.assetmanagement.asset.dto.DEL_YN;
import com.assetmanagement.asset.dto.Part;
import com.assetmanagement.asset.dto.SearchDto;
import com.assetmanagement.asset.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PartMapper {
    List<Part> findPartList(SearchDto searchDto); // 부품 리스트 조회
    int count(SearchDto searchDto);              // 부품 수 조회
    void insertPart(Part part);                  // 부품 등록
    void updatePart(Part part);
    Part getPartById(int id);
    void deletePart(@Param("delyn") DEL_YN delyn, @Param("id") Long id);
    List<Part> findUnassignedParts();
    Part findBySerialNumber(String serialNumber);
    Part getSerialNumber();
}
