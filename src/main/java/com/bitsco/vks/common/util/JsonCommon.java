package com.bitsco.vks.common.util;

import com.bitsco.vks.common.constant.Constant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : TruongNQ
 * @date created : Apr 7, 2018
 * @describe : The class contains methods for actions with json
 */
public class JsonCommon {

    private static final Logger LOGGER = LogManager.getLogger(Constant.LOG_APPENDER.COMMON);

    public static byte[] convertObjects2Byte(Object ob) {
        ObjectWriter ow = new ObjectMapper().writer();
        try {
            return ow.writeValueAsBytes(ob);
        } catch (JsonProcessingException e) {
            LOGGER.error("Exception when converting to json: ", e);
            return new byte[0];
        }
    }

    public static boolean isNullJsonObject(JSONObject obj, String key) {
        return JSONObject.NULL.equals(obj.get(key));
    }

    public static String mapToJson(Map map) {
        Gson gson = new Gson();
        return gson.toJson(map);
    }

    public static String objectToJsonNotNull(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static String objectToJsonFull(Object object) {
        ObjectWriter ow = new ObjectMapper().writer();
        try {
            if (object == null)
                return "object is null";
            else
                return ow.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            LOGGER.error("Exception when objectToJsonFull: ", e);
            return "Exception when objectToJsonFull";
        }
    }

    public static String objectToJsonLog(Object object) {
        ObjectWriter ow = new ObjectMapper().writer();
        try {
            if (object == null)
                return "object is null";
            else {
                String a = ow.writeValueAsString(object);
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonParser jp = new JsonParser();
                JsonElement je = jp.parse(a);
                return gson.toJson(je);
            }
        } catch (JsonProcessingException e) {
            LOGGER.error("Exception when objectToJsonLog: ", e);
            return "Exception when objectToJsonLog";
        }
    }

    public static Map<String, Object> jsonToMap(String json) {
        Map<String, Object> map = null;
        if (!StringCommon.isNullOrBlank(json)) {
            json = StringUtils.replace(json.trim(), "\\", "");
            map = new Gson().fromJson(json, HashMap.class);
        }
        return map;
    }

    public static Object stringToObject(String s) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(s, Object.class);
        } catch (IOException e) {
            LOGGER.error("Exception when stringToObject : ", e);
            return null;
        }
    }

    public static List<Object> getListObjectFromJsonNode(JsonNode node) {
        List<Object> result = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            if (node != null && node instanceof ArrayNode) {
                for (JsonNode nd : node) {
                    result.add(mapper.readValue(nd.toString(), Object.class));
                }
            }
            return result;
        } catch (IOException e) {
            LOGGER.error("Exception when getListObjectFromJsonNode : ", e);
            return result;
        }
    }

    public static String string2Json(String a) {
        try {
            if (!StringCommon.isNullOrBlank(a)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonParser jp = new JsonParser();
                JsonElement je = jp.parse(a);
                return gson.toJson(je);
            } else {
                return "";
            }
        } catch (Exception e) {
            LOGGER.error("Exception when string2Json ", e);
            return "Exception when string2Json";
        }
    }

    public static boolean isJsonValid(String jsonString) {
        try {
            new JSONObject(jsonString);
        } catch (JSONException e) {
            try {
                new JSONArray(jsonString);
            } catch (JSONException ex) {
                return false;
            }
        }
        return true;
    }

    public static JsonNode setStringConstructor(String attribute, String value) throws IOException {
        ObjectMapper om = new ObjectMapper();
        JsonNode jsonNode = om.readValue("{}", JsonNode.class);
        ((ObjectNode) jsonNode).put(attribute, value);
        return jsonNode;
    }

    public static JsonNode setIntegerConstructor(String attribute, Integer value) throws IOException {
        ObjectMapper om = new ObjectMapper();
        JsonNode jsonNode = om.readValue("{}", JsonNode.class);
        ((ObjectNode) jsonNode).put(attribute, value);
        return jsonNode;
    }

    public static JsonNode setLongConstructor(String attribute, Long value) throws IOException {
        ObjectMapper om = new ObjectMapper();
        JsonNode jsonNode = om.readValue("{}", JsonNode.class);
        ((ObjectNode) jsonNode).put(attribute, value);
        return jsonNode;
    }

    public static JsonNode setObjectConstructor(String attribute, Object o) throws IOException {
        ObjectMapper om = new ObjectMapper();
        JsonNode jsonNode = om.readValue("{}", JsonNode.class);
        ((ObjectNode) jsonNode).putPOJO(attribute, JsonCommon.objectToJsonFull(o));
        return jsonNode;
    }

    public static JsonNode setStringValue(JsonNode jsonNode, String attribute, String value) {
        ((ObjectNode) jsonNode).put(attribute, value);
        return jsonNode;
    }

    public static JsonNode setIntegerValue(JsonNode jsonNode, String attribute, Integer value) {
        ((ObjectNode) jsonNode).put(attribute, value);
        return jsonNode;
    }

    public static JsonNode setLongValue(JsonNode jsonNode, String attribute, Long value) {
        ((ObjectNode) jsonNode).put(attribute, value);
        return jsonNode;
    }

    public static JsonNode setObjectValue(JsonNode jsonNode, String attribute, Object o) {
        ((ObjectNode) jsonNode).putPOJO(attribute, JsonCommon.objectToJsonFull(o));
        return jsonNode;
    }

    public static String mapToJsonFull(Map map) throws Exception {
        return new ObjectMapper().writeValueAsString(map);
    }

    public static String getTextValue(String jsonInput, String attribute) throws IOException {
        ObjectMapper om = new ObjectMapper();
        JsonNode jsonNode = om.readValue(jsonInput, JsonNode.class);
        JsonNode jsonNodeValue = jsonNode.get(attribute);
        if (jsonNodeValue != null)
            return jsonNodeValue.asText();
        else
            return "";
    }

    public static String getTextValue(JsonNode jsonNode, String attribute) throws IOException {
        JsonNode jsonNodeValue = jsonNode.get(attribute);
        if (jsonNodeValue != null)
            return jsonNodeValue.asText();
        else
            return "";
    }

    public static Integer getIntegerValue(JsonNode jsonNode, String attribute) throws IOException {
        JsonNode jsonNodeValue = jsonNode.get(attribute);
        if (jsonNodeValue != null)
            return jsonNodeValue.asInt();
        else
            return null;
    }

    public static Long getLongValue(JsonNode jsonNode, String attribute) throws IOException {
        JsonNode jsonNodeValue = jsonNode.get(attribute);
        if (jsonNodeValue != null)
            return jsonNodeValue.asLong();
        else
            return null;
    }
}
