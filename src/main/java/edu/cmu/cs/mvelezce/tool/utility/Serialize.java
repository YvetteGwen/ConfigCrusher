package edu.cmu.cs.mvelezce.tool.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Set;

public class Serialize {
  public static String serializeConf(Set<String> conf) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.writeValueAsString(conf);
    } catch (JsonProcessingException exception) {
      System.out.println(exception.getMessage());
      return null;
    }
  }

  public static String serializeConfs(Set<Set<String>> confs) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.writeValueAsString(confs);
    } catch (JsonProcessingException exception) {
      System.out.println(exception.getMessage());
      return null;
    }
  }
}