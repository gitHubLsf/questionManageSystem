package com.five.questionSystem.common;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Set;


/**
 * OCR 文字识别
 */
@Component
public class OCRUtil {

    public static final String APP_ID = "17980160";
    public static final String API_KEY = "PxCnj0lqIGvjNCfGuGE6UpN8";
    public static final String SECRET_KEY = "ElyIB529TTv1gGq69crjoA5E66tFMnGY";

    public String ocr(String path) {
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        JSONObject res = client.basicGeneral(path, new HashMap<String, String>());

        StringBuffer stringBuffer = new StringBuffer();
        Set<String> keys = res.keySet();
        for (String k : keys) {
            if (k.equals("words_result")) {
                JSONArray jsonObject = (JSONArray) res.get(k);
                for (int i = 0; i < jsonObject.length(); i++) {
                    JSONObject js = jsonObject.getJSONObject(i);
                    String words = (String) js.get("words");
                    stringBuffer.append(words);
                }
            }
        }

        return stringBuffer.toString();
    }
}
