package com.healthcare.patient.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthcare.provider.service.SlackService;

@RestController
@RequestMapping("/github")
public class GithubWebhookController {
    private final SlackService slackService;

    public GithubWebhookController(SlackService slackService) {
        this.slackService = slackService;
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(
            @RequestHeader("X-GitHub-Event") String event,
            @RequestBody String payload) {

        if ("pull_request".equals(event)) {
            handlePullRequest(payload);
        }
        return ResponseEntity.ok("OK");
    }

    private void handlePullRequest(String payload) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(payload);

            String action = root.get("action").asText();
            String title = root.get("pull_request").get("title").asText();
            String url = root.get("pull_request").get("html_url").asText();
            String user = root.get("pull_request").get("user").get("login").asText();
            boolean merged = root.get("pull_request").get("merged").asBoolean();

            if ("opened".equals(action)) {
                slackService.sendMessage(
                        "🚀 *PR Opened*\n" +
                        "*Title:* " + title + "\n" +
                        "*By:* " + user + "\n" +
                        "<" + url + "|View PR>"
                );
            }

            if ("closed".equals(action) && merged) {
                slackService.sendMessage(
                        "✅ *PR Merged*\n" +
                        "*Title:* " + title + "\n" +
                        "*By:* " + user + "\n" +
                        "<" + url + "|View PR>"
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
