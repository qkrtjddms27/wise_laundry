package com.ssafy.wiselaundry.domain.user.service; //비즈니스 로직 처리를 위한 서비스


import com.ssafy.wiselaundry.domain.user.db.entity.User;
import com.ssafy.wiselaundry.domain.user.request.UserRegisterPostReq;
import com.ssafy.wiselaundry.domain.user.request.UserUpdatePostReq;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.HashMap;


/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface UserService {
	User createUser(UserRegisterPostReq userRegisterInfo, MultipartHttpServletRequest img);
	User createKakaoUser(HashMap info);
	User updateUser(UserUpdatePostReq userUpdatePostReq, MultipartHttpServletRequest img);
	User findByUserId(Long userId);
	User findByUserEmail(String userEmail);
	boolean emailCheck(String userEmail);
	boolean nickCheck(String userNick);
}
