/*
 * Copyright (C) 2016-2026 phantombot.github.io/PhantomBot
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package kentobot;

import com.gmt2001.httpclient.HttpClient;
import com.gmt2001.httpclient.HttpClientResponse;
import com.gmt2001.httpclient.URIUtil;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import org.json.JSONException;
import org.json.JSONObject;
import tv.phantombot.CaselessProperties;

/**
 * Communicates with Kentobot API v1
 * 
 * Provides integration for song requests and other Kentobot features
 *
 * @author kentobot
 */
public final class KentobotAPIv1 {

    /**
     * @botproperty kentobotapi.baseurl - Base URL for the Kentobot API. Default: http://localhost:8080/api/v1
     * @botpropertycatsort kentobotapi.baseurl 10 900 Kentobot
     */
    private static final String DEFAULT_BASE_URL = "http://localhost:8080/api/v1";
    
    /**
     * @botproperty kentobotapi.token - Authentication token for Kentobot API (if auth is required)
     * @botpropertycatsort kentobotapi.token 20 900 Kentobot
     */
    
    /**
     * @botproperty kentobotapi.timeout - Request timeout in seconds for Kentobot API calls. Default: 10
     * @botpropertycatsort kentobotapi.timeout 30 900 Kentobot
     */
    private static final int DEFAULT_TIMEOUT = 10;

    private KentobotAPIv1() {
        // Utility class - prevent instantiation
    }

    /**
     * Gets the configured base URL for the Kentobot API
     * 
     * @return the base URL from configuration, or default if not set
     */
    private static String getBaseUrl() {
        return CaselessProperties.instance().getProperty("kentobotapi.baseurl", DEFAULT_BASE_URL);
    }

    /**
     * Gets the configured authentication token for the Kentobot API
     * 
     * @return the auth token from configuration, or null if not set
     */
    private static String getAuthToken() {
        return CaselessProperties.instance().getProperty("kentobotapi.token", "");
    }

    /**
     * Creates HTTP headers for Kentobot API requests
     * 
     * @param isPost true if this is a POST request (sets content-type)
     * @param includeAuth true to include authentication header
     * @return configured headers
     */
    private static HttpHeaders createHeaders(boolean isPost, boolean includeAuth) {
        HttpHeaders headers = HttpClient.createHeaders(isPost, true); // JSON requests
        
        // Add authentication if token is configured
        if (includeAuth && !getAuthToken().isEmpty()) {
            headers.add("Authorization", "Bearer " + getAuthToken());
        }
        
        // Add custom user agent
        headers.add("User-Agent", "PhantomBot-Kentobot-Client/1.0");
        
        return headers;
    }

    /**
     * Makes a POST request to the Kentobot API
     * 
     * @param endpoint the API endpoint (e.g., "/songs/request")
     * @param payload the JSON payload to send
     * @param requireAuth whether authentication is required for this endpoint
     * @return JSONObject containing the response and metadata
     */
    private static JSONObject makePostRequest(String endpoint, JSONObject payload, boolean requireAuth) {
        JSONObject result = new JSONObject();
        
        try {
            String url = getBaseUrl() + endpoint;
            HttpHeaders headers = createHeaders(true, requireAuth);
            
            com.gmt2001.Console.debug.println("Kentobot API POST: " + url);
            com.gmt2001.Console.debug.println("Payload: " + payload.toString());
            
            HttpClientResponse response = HttpClient.post(URIUtil.create(url), headers, payload);
            
            // Add response metadata
            result.put("_success", response.isSuccess());
            result.put("_status", response.responseCode().code());
            result.put("_url", url);
            result.put("_endpoint", endpoint);
            
            if (response.isSuccess()) {
                if (response.hasJson()) {
                    // Merge the actual response data
                    JSONObject responseData = response.json();
                    responseData.keys().forEachRemaining(key -> {
                        try {
                            result.put(key, responseData.get(key));
                        } catch (JSONException e) {
                            // Skip problematic keys
                        }
                    });
                } else {
                    // Handle non-JSON success response
                    result.put("message", response.responseBody());
                }
            } else {
                result.put("_error", "HTTP " + response.responseCode().code());
                result.put("_errorMessage", response.responseBody());
                
                if (response.hasException()) {
                    result.put("_exception", response.exception().getMessage());
                }
            }
            
        } catch (Exception ex) {
            result.put("_success", false);
            result.put("_error", "Request failed");
            result.put("_exception", ex.getMessage());
            com.gmt2001.Console.err.printStackTrace(ex);
        }
        
        return result;
    }

    /**
     * Makes a GET request to the Kentobot API
     * 
     * @param endpoint the API endpoint
     * @param requireAuth whether authentication is required
     * @return JSONObject containing the response and metadata
     */
    private static JSONObject makeGetRequest(String endpoint, boolean requireAuth) {
        JSONObject result = new JSONObject();
        
        try {
            String url = getBaseUrl() + endpoint;
            HttpHeaders headers = createHeaders(false, requireAuth);
            
            com.gmt2001.Console.debug.println("Kentobot API GET: " + url);
            
            HttpClientResponse response = HttpClient.get(URIUtil.create(url), headers);
            
            // Add response metadata
            result.put("_success", response.isSuccess());
            result.put("_status", response.responseCode().code());
            result.put("_url", url);
            result.put("_endpoint", endpoint);
            
            if (response.isSuccess()) {
                if (response.hasJson()) {
                    // Merge the actual response data
                    JSONObject responseData = response.json();
                    responseData.keys().forEachRemaining(key -> {
                        try {
                            result.put(key, responseData.get(key));
                        } catch (JSONException e) {
                            // Skip problematic keys
                        }
                    });
                } else {
                    result.put("message", response.responseBody());
                }
            } else {
                result.put("_error", "HTTP " + response.responseCode().code());
                result.put("_errorMessage", response.responseBody());
            }
            
        } catch (Exception ex) {
            result.put("_success", false);
            result.put("_error", "Request failed");
            result.put("_exception", ex.getMessage());
            com.gmt2001.Console.err.printStackTrace(ex);
        }
        
        return result;
    }

    // ========== SONG REQUESTS API ==========

    /**
     * Requests a song via the Kentobot API
     * 
     * @param songId the ID/identifier of the song to request
     * @param username the username of the person making the request
     * @return JSONObject containing the API response and metadata
     * @throws JSONException if there's an error creating the request payload
     */
    public static JSONObject requestSong(String songId, String username) throws JSONException {
        if (songId == null || songId.trim().isEmpty()) {
            throw new IllegalArgumentException("Song ID cannot be null or empty");
        }
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        
        JSONObject payload = new JSONObject();
        payload.put("songId", songId.trim());
        payload.put("username", username.trim());
        payload.put("timestamp", System.currentTimeMillis());
        
        JSONObject result = makePostRequest("/songs/request", payload, true);
        
        // Log the request for debugging
        if (result.optBoolean("_success", false)) {
            com.gmt2001.Console.debug.println("Song request successful: " + songId + " by " + username);
        } else {
            com.gmt2001.Console.debug.println("Song request failed: " + songId + " by " + username + 
                " - " + result.optString("_error", "Unknown error"));
        }
        
        return result;
    }

    /**
     * Gets the current song queue from the Kentobot API
     * 
     * @return JSONObject containing the queue data and metadata
     */
    public static JSONObject getSongQueue() {
        return makeGetRequest("/songs/queue", false);
    }

    /**
     * Gets the currently playing song from the Kentobot API
     * 
     * @return JSONObject containing the current song data and metadata
     */
    public static JSONObject getCurrentSong() {
        return makeGetRequest("/songs/current", false);
    }

    // ========== FUTURE API METHODS ==========
    // Add additional Kentobot API methods here as features are implemented
    
    /**
     * Gets API health/status information
     * 
     * @return JSONObject containing the API status
     */
    public static JSONObject getAPIStatus() {
        return makeGetRequest("/status", false);
    }

    /**
     * Tests connectivity to the Kentobot API
     * 
     * @return true if the API is reachable and responding
     */
    public static boolean testConnection() {
        try {
            JSONObject status = getAPIStatus();
            return status.optBoolean("_success", false) && status.optInt("_status", 0) == 200;
        } catch (Exception ex) {
            com.gmt2001.Console.debug.println("Kentobot API connection test failed: " + ex.getMessage());
            return false;
        }
    }
}