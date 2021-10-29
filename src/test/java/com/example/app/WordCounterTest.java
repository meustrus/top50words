package com.example.app;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class WordCounterTest {
    @Test
    public void shouldSplitSomeWords() {
        Stream<WordCounter.Word> actualStream = new WordCounter().getTopN("one two three; - four", 5, new String[0]);
        List<WordCounter.Word> actual = actualStream.collect(Collectors.toList());

        List<WordCounter.Word> expected = new ArrayList<>();
        expected.add(new WordCounter.Word("one", 0, 1));
        expected.add(new WordCounter.Word("two", 1, 1));
        expected.add(new WordCounter.Word("three", 2, 1));
        expected.add(new WordCounter.Word("four", 3, 1));
        assertEquals(expected, actual);
    }

    @Test
    public void shouldFilterWords() {
        Stream<WordCounter.Word> actualStream = new WordCounter().getTopN("one two three; - four", 5, new String[] {"two"});
        List<WordCounter.Word> actual = actualStream.collect(Collectors.toList());

        List<WordCounter.Word> expected = new ArrayList<>();
        expected.add(new WordCounter.Word("one", 0, 1));
        expected.add(new WordCounter.Word("three", 2, 1));
        expected.add(new WordCounter.Word("four", 3, 1));
        assertEquals(expected, actual);
    }

    @Test
    public void shouldLimitWords() {
        Stream<WordCounter.Word> actualStream = new WordCounter().getTopN("one two three; - four four", 3, new String[0]);
        List<WordCounter.Word> actual = actualStream.collect(Collectors.toList());

        List<WordCounter.Word> expected = new ArrayList<>();
        expected.add(new WordCounter.Word("four", 3, 2));
        expected.add(new WordCounter.Word("one", 0, 1));
        expected.add(new WordCounter.Word("two", 1, 1));
        assertEquals(expected, actual);
    }

    @Test
    public void shouldHandleWordsWithPunctuation() {
        Stream<WordCounter.Word> actualStream = new WordCounter().getTopN("a-b c- -d a's b’s", 5, new String[0]);
        List<WordCounter.Word> actual = actualStream.collect(Collectors.toList());

        List<WordCounter.Word> expected = new ArrayList<>();
        expected.add(new WordCounter.Word("a-b", 0, 1));
        expected.add(new WordCounter.Word("c", 1, 1));
        expected.add(new WordCounter.Word("d", 2, 1));
        expected.add(new WordCounter.Word("a's", 3, 1));
        expected.add(new WordCounter.Word("b’s", 4, 1));
        assertEquals(expected, actual);
    }

    @Test
    public void shouldBeCaseInsensitive() {
        Stream<WordCounter.Word> actualStream = new WordCounter().getTopN("WORD wOrD two", 5, new String[0]);
        List<WordCounter.Word> actual = actualStream.collect(Collectors.toList());

        List<WordCounter.Word> expected = new ArrayList<>();
        expected.add(new WordCounter.Word("word", 0, 2));
        expected.add(new WordCounter.Word("two", 2, 1));
        assertEquals(expected, actual);
    }

}