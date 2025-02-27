package com.assetmanagement.asset.util;

import lombok.Data;

@Data
public class Result {
    private String result;

    public Result(String result) {
        this.result = result;
    }
}
