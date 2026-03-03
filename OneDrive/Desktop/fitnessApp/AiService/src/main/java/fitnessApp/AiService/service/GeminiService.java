package fitnessApp.AiService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Service
public class GeminiService {
    WebClient webClient;

    @Value("${gemini.api.url}")
    public String geminiAPIurl;
    @Value("${gemini.api.key}")
    public String getGeminiAPIkey;

    public GeminiService(WebClient.Builder webclientBuilder)
    {
        this.webClient = webclientBuilder.build();
    }

    public String getAnswer(String question){
        Map<String,Object> request= Map.of(
                "contents",new Object[]{
                        Map.of(
                                "parts",new Object[]{
                                        Map.of("text",question)
                                }
                        )
                }
        );
        String response = webClient.post()
                .uri(geminiAPIurl+getGeminiAPIkey)
                .header("Content-Type","application/json")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return response;

    }
}
