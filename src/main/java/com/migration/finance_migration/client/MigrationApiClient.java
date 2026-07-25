package com.migration.finance_migration.client;

import com.migration.finance_migration.dto.response.ApiResponseDto;
import com.migration.finance_migration.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@Component
@RequiredArgsConstructor
public class MigrationApiClient {

    private final WebClient webClient;

    public <T> ApiResponseDto post(String url, T request, String sessionId) {

        try {
            log.info("Calling migration API: {}", url);

            return webClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.COOKIE,
                            Constants.COOKIE_SESSION_ID + "=" + sessionId)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(ApiResponseDto.class)
                    .block();

        } catch (WebClientResponseException ex) {

            log.error("Migration API returned {} : {}",
                    ex.getStatusCode(),
                    ex.getResponseBodyAsString());

            throw ex;

        } catch (Exception ex) {

            log.error("Error while calling migration API", ex);

            throw ex;
        }
    }
}