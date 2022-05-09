package com.ssafy.wiselaundry.domain.weather.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service("weatherService")
public class WeatherServiceImpl implements WeatherService  {

    private String[] timeSet = {"2300", "2300", "2300", "2300", "0200", "0200", "0500", "0500", "0500", "0800", "0800", "0800", "1100", "1100", "1100", "1400", "1400", "1400", "1700", "1700", "1700", "2000", "2000", "2000"};

    @Override
    public JSONObject weatherInfo(int nx, int ny) throws IOException, ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        JSONObject ret = new JSONObject();
        String today;
        LocalDate now;

        String apiKey = "kkNi2YvTPb5lpBDI2u1egclLhZSof9slb34a%2Bq6v12MZkvsa4yUCHUm5Fefi%2F%2F3w9ontFKZEP0Bk2cflY2E4SQ%3D%3D";

        String time = new SimpleDateFormat("HH").format(new Date());
        int intTime = Integer.parseInt(time);
        // 3시 이전일 경우 전날 기준으로 수행
        now = intTime<=3?LocalDate.now().minusDays(1L):LocalDate.now();
        today = now.format(formatter);
        time = timeSet[intTime];

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + apiKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON)JSON*/
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(today, "UTF-8")); /*발표 날짜*/
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(time, "UTF-8")); /*발표시간(정시단위) -> 0600 */
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(String.valueOf(nx), "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(String.valueOf(ny), "UTF-8")); /*예보지점의 Y 좌표값*/

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader br;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        conn.disconnect();
        System.out.println(sb.toString());

        JSONParser parser = new JSONParser();
        ret = (JSONObject) parser.parse(sb.toString());

        System.out.println(ret.entrySet());
        System.out.println(ret.values());

        return ret;
    }
}
