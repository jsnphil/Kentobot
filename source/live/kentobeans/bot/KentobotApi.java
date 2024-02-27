/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package live.kentobeans.bot;

import com.gmt2001.httpclient.HttpClient;
import com.gmt2001.httpclient.HttpClientResponse;
import com.gmt2001.httpclient.URIUtil;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import org.json.JSONObject;

/**
 *
 * @author jsnph
 */
public class KentobotApi {

    // TODO Replace domain
    private static final String KENTOBOT_API_ENDPOINT = "031siirl26.execute-api.us-east-1.amazonaws.com";
    private static final String API_KEY_HEADER = "X-Api-Key";
    private static final String API_KEY = "";
    
    // TODO replace 'dev'
    private static final String REQUEST_SONG_URI = "/dev/song-requests/request/";

    private static KentobotApi instance;

    public static synchronized KentobotApi instance() {
        if (instance == null) {
            instance = new KentobotApi();
        }
        return instance;
    }

    private KentobotApi() {
        Thread.setDefaultUncaughtExceptionHandler(com.gmt2001.UncaughtExceptionHandler.instance());
    }

    public JSONObject searchForSongRequest(String videoId) {
        com.gmt2001.Console.err.println("Looking for video " + videoId);
 
        HttpClientResponse resp = HttpClient.get(URIUtil.create("https://" + KENTOBOT_API_ENDPOINT + REQUEST_SONG_URI + videoId));
        if (resp.hasJson()) {
            String responseBody = resp.responseBody();
            JSONObject jsonObj = new JSONObject(responseBody);
            return jsonObj;
        } else {
            return new JSONObject("{}");
        }
    }
    
    public void saveSongRequest() {
        // TODO Implement
    }
}
