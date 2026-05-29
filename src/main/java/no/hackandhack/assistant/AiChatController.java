package no.hackandhack.assistant;

import no.hackandhack.assistant.dto.AssistantRequest;
import no.hackandhack.assistant.dto.AssistantResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/ai")
public class AiChatController {
    private final AssistantService service;

    public AiChatController(AssistantService service) {
        this.service = service;
    }

    @PostMapping(value = "/chat", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<AssistantResponse>> chat(@RequestBody AssistantRequest request) {
        return service.generate(request)
                .map(resp -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .body(resp));
    }
}
