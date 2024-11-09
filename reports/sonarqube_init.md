# SonarQube Initial Report

Our first run of SonarQube found 1 open issue related to reliability and one open issue related to maintainability. 

No security vulnerabilites were found.

## Reliability Issues
### MusicFinderController.java L43: A "NullPointerException" could be thrown; "getBody()" can return null.

This is caused by not checking for a null response in the body of the API response:
```java
private String getFormattedLyrics(String artist, String song) throws LyricsNotFoundException, IOException {
  // ...
  // Fetch the raw JSON response
  String rawJson = apiResponse.getBody().toString();
  // ...
}
```

To alleviate this - a null check can be added which can throw a LyricsNotFoundException if the body is null:
```java
private String getFormattedLyrics(String artist, String song) throws LyricsNotFoundException, IOException {
  // ...
  // Check if the response or its body is null
  if (apiResponse.getBody() == null) {
      throw new LyricsNotFoundException();
  }

  // Fetch the raw JSON response
  String rawJson = apiResponse.getBody().toString();
  // ...
}
```

## Maintainability Issues (In order of Severity of Impact)
### LyricsNotFoundException.java L4: Add a nested comment explaining why this method is empty, throw an UnsupportedOperationException or complete the implementation.
This was caused by the LyricsNotFoundException having an empty constructor.
```java
public class LyricsNotFoundException extends RuntimeException {
  public LyricsNotFoundException() {}
}
```

This can be resolved by adding a quick `super("Lyrics not found")`
```java
public class LyricsNotFoundException extends RuntimeException {
  public LyricsNotFoundException() {
    super("Lyrics not found");
  }
}
```


### MusicFinderController.java L53: Replace this call to "replaceAll()" by a call to the "replace()" method.
This was caused by a call to `replaceAll` that can be easily swapped out with `replace` without changing functionality.
```java
// Step 1: Remove carriage returns (\r) [First argument is not a regex]
String formattedLyrics = rawLyrics.replaceAll("\\r", "");

// Step 2: Replace single newlines (\n) with a single <br>
formattedLyrics = formattedLyrics.replaceAll("\\n+", "<br>");
```
`replaceAll` should only be used when the first argument is also a regular expression.
```java
// Step 1: Remove carriage returns (\r)
String formattedLyrics = rawLyrics.replace("\r", "");

// Step 2: Replace single newlines (\n) with a single <br>
formattedLyrics = formattedLyrics.replaceAll("\\n+", "<br>");
```

### MusicFinderController.java L46: Rename "objectMapper" which hides the field declared at line 21
This was caused by an accidental redeclaration of a ObjectMapper
```java
// ObjectMapper to help with JSON formatting
private final ObjectMapper objectMapper = new ObjectMapper();
// ...

// Fetch lyrics from Lyrics.ovh API and clean newline characters
private String getFormattedLyrics(String artist, String song) throws LyricsNotFoundException, IOException {
  // ...
  // Parse the JSON to extract the lyrics
  ObjectMapper objectMapper = new ObjectMapper();
  // ...
}
// ...
```

Line 46 can be removed entirely.

### MusicFinderApplicationTest.java L55: This block of commented-out lines of code should be removed.
This was caused by a comment explaining the expected JSON output from the API when an invalid song is searched for. 
```java
// Should return a 404 Not Found response with body {"error": "Lyrics not found"}
assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
```

This is a false positive - but I'll remove the curly braces as a workaround

### LyricsNotFoundException.java L1: Rename this package name to match the regular expression '^[a-z_]+(\.[a-z_][a-z0-9_]*)*$'.
### MusicFinderApplication.java L1: Rename this package name to match the regular expression '^[a-z_]+(\.[a-z_][a-z0-9_]*)*$'.
### MusicFinderController.java L1: Rename this package name to match the regular expression '^[a-z_]+(\.[a-z_][a-z0-9_]*)*$'.
### MusicFinderExceptionHandler.java L1: Rename this package name to match the regular expression '^[a-z_]+(\.[a-z_][a-z0-9_]*)*$'.

These are all caused by the package name having a capital
```java
package com.example.musicFinder;
```

This may be resolved by setting musicFinder to be lowercase - which can be left for a seperate PR.


### MusicFinderApplicationTest.java L7: Remove this unused import 'org.junit.jupiter.api.BeforeEach'.
Line 7 has a unused import BeforeEach - this line can be removed.
```java
import org.junit.jupiter.api.BeforeEach;
```

### MusicFinderApplicationTest.java L21: Remove this 'public' modifier
The MusicFinderApplicationTest class does not need to be public - the modifier can be removed.
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class MusicFinderApplicationTest { /* ... */}
```

### MusicFinderApplicationTest.java L29: Remove the declaration of thrown exception 'java.lang.Exception', as it cannot be thrown from method's body.
### MusicFinderApplicationTest.java L47: Remove the declaration of thrown exception 'java.lang.Exception', as it cannot be thrown from method's body.
### MusicFinderApplicationTest.java L29: Remove this 'public' modifier.
### MusicFinderApplicationTest.java L47: Remove this 'public' modifier.
Neither test throws an Exception or needs to be public so both the throws and public modifiers can be removed:
```java
// ...
@Test
public void testFetchLyrics_ValidSong() throws Exception { /*...*/}
// ...
@Test
public void testFetchLyrics_InvalidSong() throws Exception { /*...*/}
```

