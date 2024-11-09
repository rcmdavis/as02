package com.example.musicFinder;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/***
 * This class is used to handle exceptions thrown by the MusicFinderController class.
 * 
 * It provides specific handlers for different types of exceptions:
 * 
 * - handleIOException: Handles IOException and returns a response with HTTP status 500 (Internal Server Error).
 * - handleLyricsNotFoundException: Handles LyricsNotFoundException and returns a response with HTTP status 404 (Not Found).
 */
@ControllerAdvice
public class MusicFinderExceptionHandler {
  // Handle IOException exceptions in the Controller
  // Return a 500 Internal Server Error response
  @ExceptionHandler(IOException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<Map<String, String>> handleIOException(IOException ex) {
    Map<String, String> response = new HashMap<>();
    response.put("error", "Internal server error");
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  // Handle LyricsNotFoundException exceptions in the Controller
  // Return a 404 Not Found response
  @ExceptionHandler(LyricsNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<Map<String, String>> handleLyricsNotFoundException(LyricsNotFoundException ex) {
    Map<String, String> response = new HashMap<>();
    response.put("error", "Lyrics not found");
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  // Handle ArtistNotFoundException exceptions in the Controller
  // Return a 404 Not Found response
  @ExceptionHandler(ArtistNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<Map<String, String>> handleArtistNotFoundException(ArtistNotFoundException ex) {
    Map<String, String> response = new HashMap<>();
    response.put("error", "Artist not found");
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }
}
