package com.assetmanagement.asset.service.impl;

import com.assetmanagement.asset.dto.Asset;
import com.assetmanagement.asset.dto.DEL_YN;
import com.assetmanagement.asset.dto.Part;
import com.assetmanagement.asset.dto.SearchDto;
import com.assetmanagement.asset.mapper.PartMapper;
import com.assetmanagement.asset.service.PartService;
import com.assetmanagement.asset.util.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartServiceImpl implements PartService {

    private final PartMapper partMapper;

    public PartServiceImpl(PartMapper partMapper) {
        this.partMapper = partMapper;
    }

    @Override
    public List<Part> findPartList(SearchDto searchDto) {

        int totalCount = partMapper.count(searchDto);

        searchDto.setTotalCount(totalCount);

        return partMapper.findPartList(searchDto);
    }

    @Override
    public int count(SearchDto searchDto) {
        return partMapper.count(searchDto);
    }

    @Override
    public Result insertPart(Part part) {
        try {
            if (partMapper.findBySerialNumber(part.getSerialNumber()) != null) {
                return new Result("DUPLICATED"); // 중복된 경우
            }

            partMapper.insertPart(part); //  중복이 아니면 정상적으로 INSERT 실행
            return new Result("INSERT_COMPLETE");

        } catch (Exception e) {
            e.printStackTrace();
            return new Result("error");
        }
    }


    @Override
    public Result updatePart(Part part) {
        try{
            partMapper.updatePart(part);
            return new Result("UPDATE_COMPLETE");
        }catch (Exception e){
            e.printStackTrace();
            return new Result("error");
        }
    }


    @Override
    public Part getPartById(int id) {
        return partMapper.getPartById(id);
    }

    @Override
    public Result deletePart(Long id) {
        try {
            partMapper.deletePart(DEL_YN.Y, id); // 'Y'로 변경하여 비활성화
            return new Result("DELETE_COMPLETE");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("error");
        }
    }


    @Override
    public List<Part> getUnassignedParts() {
        return partMapper.findUnassignedParts();
    }

    @Override
    public Part getSerialNumber() {
        return partMapper.getSerialNumber();
    }

    @Override
    public Result isSerialNumberAvailable(String serialNumber) {
        if (partMapper.findBySerialNumber(serialNumber) != null) {
            return new Result("CONFIRMED");
        }else{
            return new Result("DUPLICATED");
        }

    }
}
