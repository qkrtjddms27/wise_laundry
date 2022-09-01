package com.ssafy.wiselaundry.domain.user.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "user")
@ApiModel(value = "User", description = "유저 정보")
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_nick", nullable = false, unique = true)
    private String userNick;

    @Column(name = "user_img")
    private String userImg;

    @Column(name = "password")
    private String password;

    @Column(name = "kakao_img")
    private String kakaoImg;

}
