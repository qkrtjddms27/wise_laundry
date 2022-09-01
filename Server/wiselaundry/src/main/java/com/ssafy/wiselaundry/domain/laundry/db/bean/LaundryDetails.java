package com.ssafy.wiselaundry.domain.laundry.db.bean;

import com.ssafy.wiselaundry.domain.laundry.db.entity.CareLabels;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LaundryDetails {
    private Long laundryId;
    private String laundryImg;
    private List<CareLabels> careLabels;
    private List<String> laundryInfo;
    private String laundryOwnerNick;
    private Long laundryOwnerId;
    private String laundryMemo;
}
