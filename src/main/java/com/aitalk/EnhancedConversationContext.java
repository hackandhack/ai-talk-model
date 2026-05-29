package com.aitalk;

import java.util.*;

/**
 * Enhanced conversation context that uses Grok for intelligent summarization.
 */
public class EnhancedConversationContext {
    private final List<String> fullHistory = new ArrayList<>();
    private String summary = "";
    private final GrokApiClient grokClient;

    public EnhancedConversationContext(GrokApiClient grokClient) {
        this.grokClient = grokClient;
    }

    public void addMessage(String role, String content) {
        fullHistory.add(role + ": " + content);
        // Keep full history but maintain summary
        if (fullHistory.size() > 20) {
            summarizeWithGrok();
        }
    }

    private void summarizeWithGrok() {
        try {
            String prompt = "Summarize the following conversation history concisely, preserving key facts, user preferences, and topics:\n\n" 
                + String.join("\n", fullHistory);
            String newSummary = grokClient.generateResponse(prompt, "You are a helpful conversation summarizer.");
            this.summary = newSummary;
            // Optionally trim fullHistory
        } catch (Exception e) {
            // Fallback
            this.summary = "Previous conversation summary: " + fullHistory.get(fullHistory.size()-10);
        }
    }

    public String getContextForPrompt() {
        StringBuilder sb = new StringBuilder();
        if (!summary.isEmpty()) {
            sb.append("Conversation Summary: ").append(summary).append("\n\n");
        }
        sb.append("Recent messages:\n");
        int start = Math.max(0, fullHistory.size() - 10);
        for (int i = start; i < fullHistory.size(); i++) {
            sb.append(fullHistory.get(i)).append("\n");
        }
        return sb.toString();
    }

    public void clear() {
        fullHistory.clear();
        summary = "";
    }
}