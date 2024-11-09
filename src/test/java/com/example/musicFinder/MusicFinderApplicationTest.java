package com.example.musicFinder;

import com.fasterxml.jackson.databind.node.ObjectNode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * This class contains unit tests for the MusicFinder application.
 * It uses Spring Boot's test framework to start the application on a random port
 * and TestRestTemplate to perform HTTP requests.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MusicFinderApplicationTest {

	@LocalServerPort
	private int port;

	private TestRestTemplate testRestTemplate = new TestRestTemplate();

	@Test
	void testFetchLyrics_ValidSong() {
		// We're not metalheads, but this is the shortest song in the world - so good for testing!
		String artist = "Napalm Death";
		String song = "You Suffer";

		String url = "http://localhost:" + port + "/song/" + artist + "/" + song;
		ResponseEntity<ObjectNode> response = testRestTemplate.getForEntity(url, ObjectNode.class);

		// Should return a 200 OK response with formatted song details
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("You Suffer", response.getBody().get("song").asText());
		assertEquals("Napalm Death", response.getBody().get("artist").asText());
		assertEquals("https://www.youtube.com/results?search_query=Napalm+Death+You+Suffer", response.getBody().get("youtubeSearch").asText());
		assertEquals("You suffer...<br>But why?", response.getBody().get("lyrics").asText());
	}

	
	@Test
	void testFetchLyrics_InvalidSong() {
		// Try with an invalid song
		String artist = "Unknown Artist";
		String song = "Unknown Song";

		String url = "http://localhost:" + port + "/song/" + artist + "/" + song;
		ResponseEntity<ObjectNode> response = testRestTemplate.getForEntity(url, ObjectNode.class);

		// Should return a 404 Not Found response with body "error": "Lyrics not found"
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("Lyrics not found", response.getBody().get("error").asText());
		assertEquals(1, response.getBody().size());
	}
}