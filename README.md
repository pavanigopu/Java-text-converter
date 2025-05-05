## Application name
Java text converter

## Introduction
This application takes text file as input, reads the text and converts it into csv and xml files satisfying below requirements.
1) Parses the text, and breaks it into sentences and words, and words are sorted.
2) Handles the large files. Added JVM argument(-Xmx32m) to be able to test locally. 
3) Converts to both XML and CSV formatted files.
4) Parser allows whitespaces. And considers only punctuations [.?!] as end of the sentence.
5) Abbreviations like "Mr." are not considered as end of the sentence.
6) CSV header will contain header with words of maxWordCount like ", Word 1, Word 2, Word 3...maxWordCountOfAllSentences"
7) Added unit tests covering the above cases.

Note: Made Sentence class usable as key for Map but not used in this Application. Because

To be able to run huge files with small memory, We can not store all sentences inside a Map until the end.

The approach that I followed now
1) Read chunk
2) Accumulate full sentence
3) As soon as full sentence is ready, Write it to CSV and XML
4) Discard from memory
5) Repeat

## Getting started
```
mvn clean compile package
```

## Running
This application can be run by running the below command at the terminal.

```java -Xmx32m -jar target/text-converter-1.0-SNAPSHOT.jar resources/small.in```

Change file name ```resources/small.in``` to whatever file that need to be tested.

## Debugging

Below application configuration can be found at ```.run```  folder. 
```Application.run.xml``` 
