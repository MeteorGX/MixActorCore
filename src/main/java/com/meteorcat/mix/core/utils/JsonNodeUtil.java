package com.meteorcat.mix.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

/**
 * JsonNode simple tools
 * @author MeteorCat
 */
public final class JsonNodeUtil {

    /**
     * node exist?
     * @param node Json Node
     * @param name Json Name
     * @return boolean
     */
    public static boolean isEmpty(JsonNode node, String name){
        return node == null || !node.has(name);
    }


    /**
     * convert Int?
     * @param node Json Node
     * @param name Json Name
     * @return Optional<Integer>
     */
    public static Optional<Integer> isInteger(JsonNode node,String name){
        if(isEmpty(node, name)){
            return Optional.empty();
        }
        JsonNode nodeValue = node.get(name);
        return Optional.ofNullable( nodeValue.canConvertToInt() ? nodeValue.asInt() : null  );
    }


    /**
     * convert long?
     * @param node Json Node
     * @param name Json Name
     * @return Optional<Long>
     */
    public static Optional<Long> isLong(JsonNode node,String name){
        if(isEmpty(node, name)){
            return Optional.empty();
        }
        JsonNode nodeValue = node.get(name);
        return Optional.ofNullable( nodeValue.canConvertToLong() ? nodeValue.asLong() : null  );
    }


    /**
     * convert string?
     * @param node Json Node
     * @param name Json Name
     * @return Optional<String>
     */
    public static Optional<String> isText(JsonNode node,String name){
        if(isEmpty(node, name)){
            return Optional.empty();
        }
        JsonNode nodeValue = node.get(name);
        return Optional.ofNullable( nodeValue.isTextual() ? nodeValue.asText() : null );
    }


    /**
     * convert boolean?
     * @param node Json Node
     * @param name Json Name
     * @return Optional<Boolean>
     */
    public static Optional<Boolean> isBoolean(JsonNode node,String name){
        if(isEmpty(node, name)){
            return Optional.empty();
        }
        JsonNode nodeValue = node.get(name);
        return Optional.ofNullable( nodeValue.isBoolean() ? nodeValue.asBoolean() : null );
    }


    /**
     * convert object ?
     * @param node Json Node
     * @param name Json Name
     * @return Optional<JsonNode>
     */
    public static Optional<JsonNode> isObject(JsonNode node,String name){
        if(isEmpty(node, name)){
            return Optional.empty();
        }
        JsonNode nodeValue = node.get(name);
        return Optional.ofNullable( nodeValue.isObject() ? nodeValue : null );
    }


    /**
     * convert array?
     * @param node Json Node
     * @param name Json Name
     * @return Optional<JsonNode>
     */
    public static Optional<JsonNode> isArray(JsonNode node,String name){
        if(isEmpty(node, name)){
            return Optional.empty();
        }
        JsonNode nodeValue = node.get(name);
        return Optional.ofNullable( nodeValue.isArray() ? nodeValue : null );
    }




    /**
     * is exists args ?
     * @param node Json Node
     * @param args Json Name
     * @return boolean
     */
    public static boolean isExists(JsonNode node, String... args){
        if(node == null){
            return false;
        }
        for (String arg : args){
            if(isEmpty(node, arg)){
                return false;
            }
        }
        return true;
    }


    /**
     * convert Map
     * @param mapper ObjectMapper
     * @param data String
     * @return Optional<Map<K,V>>
     * @param <K> Map Key
     * @param <V> Map Value
     */
    @SuppressWarnings("unchecked")
    public static<K,V> Optional<Map<K,V>> toMap(ObjectMapper mapper,String data){
        try{
            return Optional.ofNullable(mapper.readValue(data,Map.class));
        }catch (JsonProcessingException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }


    /**
     * convert List
     * @param mapper ObjectMapper
     * @param data String
     * @return Optional<List<V>>
     * @param <V> List value
     */
    @SuppressWarnings("unchecked")
    public static<V> Optional<List<V>> toList(ObjectMapper mapper,String data){
        try{
            return Optional.ofNullable(mapper.readValue(data,List.class));
        }catch (Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }



    /**
     * List to json string
     * @param mapper ObjectMapper
     * @param list List
     * @return Optional<String>
     * @param <V> List value
     */
    public static <V> Optional<String> toListStr(ObjectMapper mapper, List<V> list){
        try{
            return Optional.ofNullable(mapper.writeValueAsString(list));
        }catch (Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }


    /**
     * Map to json string
     * @param mapper ObjectMapper
     * @param map Map
     * @return Optional<String>
     * @param <K> Map Key
     * @param <V> Map Value
     */
    public static <K,V> Optional<String> toMapStr(ObjectMapper mapper, Map<K,V> map){
        try{
            return Optional.ofNullable(mapper.writeValueAsString(map));
        }catch (Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }


    /**
     * Parse json { "1": { "key":111 } }
     * @param mapper ObjectMapper
     * @param node JsonNode
     * @param clazz Convert class
     * @return Optional<Map<String,V>>
     */
    public static<V> Optional<Map<String,V>> toEntity(ObjectMapper mapper,JsonNode node,Class<V> clazz){
        try{
            Iterator<Map.Entry<String,JsonNode>> elements = node.fields();
            Map<String,V> wrapper = new HashMap<>(node.size());
            while (elements.hasNext()){
                Map.Entry<String,JsonNode> element = elements.next();
                String key = element.getKey();
                V e = mapper.readValue(element.getValue().toString(),clazz);
                wrapper.put(key,e);
            }
            return Optional.of(wrapper);
        }catch (JsonProcessingException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }


    /**
     * Map convert JsonNode
     * @param mapper ObjectMapper
     * @param data Map
     * @return Optional<JsonNode>
     */
    public static<K,V> Optional<JsonNode> toNode(ObjectMapper mapper,Map<K,V> data){
        return Optional.ofNullable(mapper.convertValue(data, JsonNode.class));
    }



}
