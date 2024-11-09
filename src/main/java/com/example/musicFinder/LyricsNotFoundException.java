package com.example.musicFinder;

/**
 * Exception thrown when lyrics are not found.
 */
public class LyricsNotFoundException extends RuntimeException {
  public LyricsNotFoundException() {
    super("Lyrics not found");
  }
}
