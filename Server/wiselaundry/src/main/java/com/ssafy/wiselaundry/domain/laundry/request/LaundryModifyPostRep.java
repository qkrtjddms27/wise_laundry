package com.ssafy.wiselaundry.domain.laundry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("LaundryModifyPostRep")
public class LaundryModifyPostRep {
    @ApiModelProperty(name = "케어라벨")
    String[] careLabelName;

    @ApiModelProperty(name = "옷 정보")
    String[] laundryInfo;

    @ApiModelProperty(value = "옷 사진", example = "1asdfasd")
    String laundryImg;

    @ApiModelProperty(value = "옷 메모", example = "memo")
    String laundryMemo;


}