package com.assetmanagement.asset.service;

import com.assetmanagement.asset.dto.Asset;
import com.assetmanagement.asset.dto.DEL_YN;
import com.assetmanagement.asset.dto.Part;
import com.assetmanagement.asset.dto.SearchDto;
import com.assetmanagement.asset.util.Result;

import java.util.List;

public interface PartService {
    List<Part> findPartList(SearchDto searchDto);
    int count(SearchDto searchDto);
    Result insertPart(Part part);
    Result updatePart(Part part);
    Part getPartById(int id);
    Result deletePart(Long id);
    List<Part> getUnassignedParts();
    Result isSerialNumberAvailable(String serialNumber);
    Part getSerialNumber();
}
