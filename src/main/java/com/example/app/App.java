package com.example.app;

import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
public class App 
{
    public static final String[] COMMON_WORDS = new String[] {
            "the", "of", "to", "and", "a", "in", "is", "it", "you", "that", "he", "was",
            "for", "on", "are", "with", "as", "I", "his", "they", "be", "at", "one", "have",
            "this", "from", "or", "had", "by", "not", "word", "but", "what", "some", "we",
            "can", "out", "other", "were", "all", "there", "when", "up", "use", "your",
            "how", "said", "an", "each", "she",
    };

    public static void main( String[] args )
    {
        String book = new HttpDownloader().get("http://www.gutenberg.org/files/2701/2701-0.txt");
        new WordCounter().getTopN(book, 50, COMMON_WORDS)
                .forEach(System.out::println);
    }
}
