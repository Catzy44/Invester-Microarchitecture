package me.catzy.invester.processor.infrastructure.ai.lmstudio;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.catzy.invester.processor.application.dto.AIProcessingResult;
import me.catzy.invester.processor.infrastructure.ai.lmstudio.dto.AIMessage;
import me.catzy.invester.processor.infrastructure.ai.lmstudio.dto.LMStudioAPIResponse;
import me.catzy.invester.processor.infrastructure.ai.lmstudio.mapper.LMStudioCompletionMapper;
import me.catzy.invester.processor.infrastructure.ai.lmstudio.mapper.LMStudioResponseMapper;
import me.catzy.invester.processor.util.Utils;

@Service
public class LMStudioClient {
	private static ObjectMapper objectMapper = new ObjectMapper();
	private static HttpClient client = HttpClient.newHttpClient();
	private static final Logger logger = LoggerFactory.getLogger(LMStudioClient.class);
	
	@Autowired LMStudioResponseMapper responseMapper;
	@Autowired LMStudioCompletionMapper completionMapper;
	
	boolean LMStudioWaked = false;
	private void wakeLMStudioIfNeeded() throws IOException, InterruptedException {
		if(LMStudioWaked) {
			return;
		}
		logger.info("starting LSM...");
		Process p = new ProcessBuilder("lms","server","start").start();
		Utils.dumpUntilExahausted(p.getInputStream());
		Utils.dumpUntilExahausted(p.getErrorStream());
		p.waitFor();
		Thread.sleep(1000);
		
		//to be sure
		logger.info("unloading old LLM model...");
		p = new ProcessBuilder("lms","unload","kot").start();
		//Utils.dumpUntilExahausted(p.getInputStream());
		//Utils.dumpUntilExahausted(p.getErrorStream());
		p.waitFor();
		Thread.sleep(1000);
		
		logger.info("loading LLM model...");
		p = new ProcessBuilder("lms","load","14B","--identifier","kot").start();
		Utils.dumpUntilExahausted(p.getInputStream());
		Utils.dumpUntilExahausted(p.getErrorStream());
		p.waitFor();
		Thread.sleep(1000);
		
		logger.info("LSM up and running!");
		
		LMStudioWaked = true;
		
	}
	
	public AIProcessingResult askAI(List<AIMessage> messages) throws URISyntaxException, IOException, InterruptedException {
		wakeLMStudioIfNeeded();
		
		// convert AIMessage DTO into JSON String
		String requestBody = objectMapper
				.writerWithDefaultPrettyPrinter()
				.writeValueAsString(completionMapper.build(messages));

		//web request to LMStudio (chooosen AI model has to be started first through GUI)
		HttpRequest request = HttpRequest.newBuilder()
	        	.version(HttpClient.Version.HTTP_1_1)
	            .uri(new URI("http://127.0.0.1:1234/api/v0/chat/completions"))
	            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
	            .header("Content-Type", "application/json; charset=utf-8")
	            .header("User-Agent", "JavaHttpClient/1.0")
	            .header("Accept", "*/*")
	            .timeout(Duration.ofMinutes(20))
	            .build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		
		// convert LMStudioAPI JSON String response into LMStudioAPIResponse DTO
		LMStudioAPIResponse res = objectMapper
				.readValue(response.body(), LMStudioAPIResponse.class);

		// try to parse AI response !THROWS!
		return responseMapper.build(res);
	}
}
