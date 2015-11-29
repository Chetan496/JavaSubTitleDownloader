## A Java Client for downloading Movie SubTitles.

[SubDB](http://thesubdb.com/api) provides an API for searching, downloading and even uploading subtitles for movies. 

SubDB has a simple idea to find subtitles.
You first need to compute a MD5 hash by reading the first 64 Kb and last 64Kb from the video file.
Then simply send this hash when calling their API, and also specify the actions.(searching, downloading or uploading.)

SubDB will search their database for the hash. 
If it is found, it will return search results, else an error code (404) will be returned.

This program downloads the relevant English Subtitle in the appropriate directory i.e the directory in which the Movie file is located.

### Why Java?

Many clients have been written before which use SubDB.
But, I did not find any client which was implemented in Java. Most were written in Python/Perl/Ruby.

Java has it's own obvious advantages. 
Since I like movies, and subtitles are a good to have, I decided to write one after I found SubDB.

### Building this Project
1. Git clone this repo. Make sure you have [maven](http://maven.apache.org/) installed.
2. Import into Eclipse. Make sure you have `m2eclipse` plugin.
3. Right click on project in left navigation and select "Run as". Then Select "maven install"
4. A jar file ending with `-jar-with-dependencies.jar`  will be generated under the directory `target`.


### Usage
1. You can copy the jar file anywhere on your computer after you build the project.
2. To get the subtitles: `cd` to the directory containing the jar and issue the command in a terminal
`java -jar SubtitleDownloader-0.0.1-SNAPSHOT-jar-with-dependencies.jar absolutePathToMovieFile`. Here `absolutePathToMovieFile` is the full path to the movie file.
3. The English subtitles will be downloaded in the appropriate directory. If the subtitles already exist for the movie, they will not be over-written.

### Implementation Details:
I have created my own wrapper over `HttpUrlCOnnection`. It acts as a simple HttpClient for making Http requests. 

### Testing Status
I have tested on Linux but not on Windows.
I was able to get English SubTitles for most of the Hollywood movies.
I have also tested it with some Bollywood movies, but the success rate for them was less.
Also, it is likely to fail for old English movies.
The good thing is, if a subTitle is found, it is likely to be a correct one for the movie, since the API relies on the hash computed from the movie file itself.


### License
MIT
 

### TODO

1.  Add support for downloading subtitles via Proxy connection.Currently, it works only over direct connections.
2.  Add Multi-Threading support, so that subtitles for multiple movies can downloaded in batch-mode.
3.  Fail graciously if no subtitles are found. Sometimes Subtitles do not exist in [SubDB](http://thesubdb.com). Currently, the program fails abruptly. Need to fix this, so that a message(and not an StackTrace of Exceptions) gets shown that SubTitles are not found on server. 
4.  Possibly, a Swing/JavaFX UI. 
5. In case the subtitles are not found, try another source for getting the SubTitles.