package com.aitalk;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrokApiClient {

    private static final String API_URL = "https://api.x.ai/v1/chat/completions";
    private final String apiKey;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GrokApiClient(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getResponse(String userMessage, String systemPrompt) throws IOException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(API_URL);
            post.setHeader("Authorization", "Bearer " + apiKey);
            post.setHeader("Content-Type", "application/json");

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "grok-4.3");  // or grok-beta, check latest
            requestBody.put("temperature", 0.7);

            List<Map<String, String>> messages = new ArrayList<>();
            messages.add(createMessage("system", systemPrompt));
            messages.add(createMessage("user", userMessage));
            requestBody.put("messages", messages);

            String jsonBody = objectMapper.writeValueAsString(requestBody);
            post.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));

            return client.execute(post, response -> {
                if (response.getCode() == 200) {
                    Map<String, Object> responseMap = objectMapper.readValue(response.getEntity().getContent(), Map.class);
                    List<Map<String, Object>> choices = (List<Map<String, Object>>) responseMap.get("choices");
                    if (!choices.isEmpty()) {
                        Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                        return (String) message.get("content");
                    }
                } else {
                    return "Error: " + response.getCode();
                }
                return "No response";
            });
        }
    }

    private Map<String, String> createMessage(String role, String content) {
        Map<String, String> message = new HashMap<>();
        message.put("role", role);
        message.put("content", content);
        return message;
    }
}