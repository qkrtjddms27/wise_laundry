package com.ssafy.wiselaundry.domain.laundry.controller;

import com.ssafy.wiselaundry.domain.laundry.db.bean.LaundryAll;
import com.ssafy.wiselaundry.domain.laundry.db.bean.LaundryDetails;
import com.ssafy.wiselaundry.domain.laundry.db.entity.CareLabels;
import com.ssafy.wiselaundry.domain.laundry.request.LaundryModifyPostRep;
import com.ssafy.wiselaundry.domain.laundry.request.UserLaundryRegisterPostReq;
import com.ssafy.wiselaundry.domain.laundry.response.CareLabelsAllRes;
import com.ssafy.wiselaundry.domain.laundry.response.LaundryAllRes;
import com.ssafy.wiselaundry.domain.laundry.response.LaundryDetailsRes;
import com.ssafy.wiselaundry.domain.laundry.service.LaundryServiceImpl;
import com.ssafy.wiselaundry.global.model.response.BaseResponseBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

@Api("옷 API")
@Slf4j
@RestController
@RequestMapping("/api/laundry")
public class LaundryController {
    @Autowired
    LaundryServiceImpl laundryService;

    @GetMapping("/{userId}/all")
    @ApiOperation(value = "내 옷장 전체 목록")
    public ResponseEntity<LaundryAllRes> userLaundryAll (@ApiParam(value = "유저번호") @PathVariable("userId") int userId){
        log.info("userLaundryAll - Call");
        List<LaundryAll> list = laundryService.findUserLaundryAll(userId);

        if(list != null && !list.isEmpty()) {
            return ResponseEntity.status(200).body(LaundryAllRes.of(200, "Success", list));
        }else {
            log.error("laundry doesn't exist");
            return ResponseEntity.status(200).body(LaundryAllRes.of(200, "laundry doesn't exist", null));
        }
    }

    @GetMapping("/{laundryId}")
    @ApiOperation(value = "옷 detail 조회")
    public ResponseEntity<LaundryDetailsRes> laundryDetails (@ApiParam(value = "옷 번호") @PathVariable("laundryId") int laundryId){
        log.info("laundryDetails - Call");
        LaundryDetails list = laundryService.findLaundryDetails(laundryId);

        if(list != null) {
            return ResponseEntity.status(200).body(LaundryDetailsRes.of(200, "Success", list));
        }else {
            log.error("laundryId doesn't exist");
            return ResponseEntity.status(403).body(LaundryDetailsRes.of(403, "laundryId doesn't exist", null));
        }
    }



    @PostMapping(value = "",consumes= {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value = "내 옷 등록")
    public ResponseEntity<? extends BaseResponseBody> userLaundryRegister (@RequestPart(value = "laundryRegister") UserLaundryRegisterPostReq userLaundryRegisterPostReq, MultipartHttpServletRequest request){
        log.info("userLaundryRegister - Call");

        if(laundryService.laundryRegisterByUser(userLaundryRegisterPostReq,request) == 1){
            return ResponseEntity.status(201).body(BaseResponseBody.of(201, "Success"));
        }else{
            log.error("Fail");
            return ResponseEntity.status(403).body(BaseResponseBody.of(403, "Fail"));
        }
    }


    @PutMapping(value = "/",consumes= {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value = "내 옷 수정")
    public ResponseEntity<? extends BaseResponseBody> userLaundryDetailModify(@RequestPart LaundryModifyPostRep laundryModifyPostRep,MultipartHttpServletRequest request){
        log.info("userLaundryDetailModify - Call");

        if(laundryService.modifyLaundryDetails(laundryModifyPostRep,request )== 1) {
            return ResponseEntity.status(200).body(BaseResponseBody.of(201, "Success"));
        }else {
            log.error("laundry update fail");
            return ResponseEntity.status(403).body(BaseResponseBody.of(403, "laundry update fail"));
        }


    }

    @DeleteMapping("/{laundryId}")
    @ApiOperation(value = "내 옷 삭제")
    public ResponseEntity< ? extends BaseResponseBody> userLaundryDetailDelete(@PathVariable("laundryId") int laundryId){
        log.info("userLaundryDetailDelete - Call");

        if(laundryService.deleteLaundry(laundryId) == 1){
            return ResponseEntity.status(204).body(BaseResponseBody.of(204, "Success"));
        }else{
            log.error("Fail");
            return ResponseEntity.status(403).body(BaseResponseBody.of(403, "Fail"));
        }

    }

    @GetMapping("/all")
    @ApiOperation(value = "모든 옷장 전체 목록")
    public ResponseEntity<LaundryAllRes> LaundryAll () {
        log.info("LaundryAll - Call");
        List<LaundryAll> list = laundryService.findLaundryAll();

        if(list != null && !list.isEmpty()) {
            return ResponseEntity.status(200).body(LaundryAllRes.of(200, "Success", list));
        }else {
            log.error("All cloth doesn't exist");
            return ResponseEntity.status(200).body(LaundryAllRes.of(200, "All cloth doesn't exist", null));
        }
    }

    @GetMapping("/carelabel")
    @ApiOperation(value = "케어라벨 전체 목록")
    public ResponseEntity<CareLabelsAllRes> CareLabelAll () {
        log.info("CareLabelAll - Call");
        List<CareLabels> list = laundryService.findCareLabelsAll();

        if(list != null && !list.isEmpty()) {
            return ResponseEntity.status(200).body(CareLabelsAllRes.of(200, "Success", list));
        }else {
            log.error("carelabel doesn't exist");
            return ResponseEntity.status(200).body(CareLabelsAllRes.of(200, "carelabel doesn't exist", null));
        }
    }
}
