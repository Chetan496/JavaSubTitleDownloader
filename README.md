## A Java Client for downloading SubTitles.

[SubDB](http://thesubdb.com/api) provides an API for searching, downloading and even uploading subtitles for movies. 

SubDB has a simple idea to find subtitles.
You first need to compute a hash by reading the bytes from the video file.
Then simply send this hash when calling their API, and also specify the actions.(searching, downloading or uploading.)

SubDB will search their database for the hash. 
If it is found, it will return search results, else an error code (404) will be returned.

### Why Java

Many clients have been written before which use SubDB.
But, I did not find any client which was implemented in Java. Most were written in Python/Perl/Ruby.

Java has it's own obvious advantages. 
Since I like movies, and subtitles are a good to have, I decided to write one after I found SubDB.

### Implementation Details:


### License
MIT
 



