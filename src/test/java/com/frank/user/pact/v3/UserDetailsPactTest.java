package com.frank.user.pact.v3;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = com.frank.user.MsUserApplication.class)
public class UserDetailsPactTest {

    @LocalServerPort
    int port;

    private final ObjectMapper mapper = new ObjectMapper();

    private void addBasicAuth(RequestEntity.BodyBuilder builder, JsonNode request) {
        if (request.has("headers") && request.get("headers").has("Authorization")) {
            builder.header("Authorization", request.get("headers").get("Authorization").asText());
        }
    }

    @Test
    void verifyPactsPresentAndBasicResponses() throws Exception {
        File pactDir = new File("src/test/resources/pacts/v3");
        RestTemplate rest = new RestTemplate();
        for (File f : pactDir.listFiles((d, name) -> name.endsWith(".json"))) {
            JsonNode pact = mapper.readTree(f);
            JsonNode interactions = pact.get("interactions");
            if (interactions == null || !interactions.isArray()) continue;
            Iterator<JsonNode> it = interactions.elements();
            while (it.hasNext()) {
                JsonNode interaction = it.next();
                JsonNode request = interaction.get("request");
                JsonNode response = interaction.get("response");
                String method = request.get("method").asText();
                String path = request.get("path").asText();
                // Build URI to local provider, adding /api prefix
                String uri = "http://localhost:" + port + "/api" + path;
                RequestEntity.BodyBuilder builder = RequestEntity.method(HttpMethod.valueOf(method.toUpperCase()), new java.net.URI(uri));
                addBasicAuth(builder, request);
                
                Object body = null;
                if (request.has("body")) {
                    body = request.get("body").toString();
                    builder.contentType(org.springframework.http.MediaType.APPLICATION_JSON);
                }
                
                RequestEntity<?> req = body != null ? builder.body(body) : builder.build();
                ResponseEntity<String> resp = rest.exchange(req, String.class);
                int expectedStatus = response.has("status") ? response.get("status").asInt() : 200;
                assertEquals(expectedStatus, resp.getStatusCodeValue(), "Status mismatch for " + f.getName() + " " + path);
                // (Optional) could validate body structure here using response.body/json schema, but keep minimal for now
            }
        }
    }
}
