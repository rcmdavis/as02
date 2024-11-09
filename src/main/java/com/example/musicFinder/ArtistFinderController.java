package com.example.musicFinder;

import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RestController
public class ArtistFinderController {
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> apiResponse;

    // Fetch artist information from Wikipedia API
    @GetMapping("/artist/{name}")
    public ResponseEntity<String> getArtistInfo(@PathVariable String name) throws ArtistNotFoundException {
        String apiUrl = "https://en.wikipedia.org/api/rest_v1/page/summary/"+ name;

        try {
            apiResponse = restTemplate.getForEntity(apiUrl, String.class);
        } catch (RestClientException e) {
            throw new ArtistNotFoundException();
        }
        // Return the JSON response
        return apiResponse;
    }
}
