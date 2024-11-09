package com.example.musicFinder;

/**
 * Exception thrown when artist is not found.
 */
public class ArtistNotFoundException extends RuntimeException {
  public ArtistNotFoundException() {
    super("Artist not found");
  }
}
