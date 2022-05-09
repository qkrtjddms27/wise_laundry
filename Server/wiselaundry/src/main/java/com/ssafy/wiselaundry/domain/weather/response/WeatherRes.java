package com.ssafy.wiselaundry.domain.weather.response;


import com.ssafy.wiselaundry.global.model.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("WeatherRes")
public class WeatherRes extends BaseResponseBody {

    String info;

    public static WeatherRes of(Integer statusCode, String message, String info){
        WeatherRes res = new WeatherRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setInfo(info);
        return res;
    }

}
