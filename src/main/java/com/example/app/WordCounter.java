package com.example.app;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordCounter {
    public Stream<Word> getTopN(String input, long max, String[] excluding) {
        return Pattern.compile("(?:(?!(?<=\\w)[-'’]\\w)\\W)+") // groups of `\W` (non-word character) that aren't `[-'’]` surrounded by `\w` (word character)
                .splitAsStream(input)
                .map(Word.converter())
                .filter(word -> Stream.of(excluding).noneMatch(excluded -> Objects.equals(excluded, word.word)))
                .collect(Collectors.toMap(word -> word.word, Function.identity(), Word::sum))
                .values()
                .stream()
                .sorted(Word.DESCENDING_COUNT_COMPARATOR)
                .limit(max);
    }

    public static class Word {
        public final String word;
        public final Integer firstIndexInSource; // Needed to maintain sort stability
        public final Integer count;

        public Word(String word, Integer firstIndexInSource, Integer count) {
            this.word = word;
            this.firstIndexInSource = firstIndexInSource;
            this.count = count;
        }

        @Override
        public String toString() {
            return String.format("[%d] %s : %d", firstIndexInSource, word, count);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Word word1 = (Word) o;
            return Objects.equals(word, word1.word) && Objects.equals(firstIndexInSource, word1.firstIndexInSource) && Objects.equals(count, word1.count);
        }

        @Override
        public int hashCode() {
            return Objects.hash(word, firstIndexInSource, count);
        }

        public static Function<String, Word> converter() {
            return new Function<String, Word>() {
                private int index = 0;

                @Override
                public Word apply(String str) {
                    return new Word(str.toLowerCase(), index++, 1);
                }
            };
        }

        public static Word sum(Word word1, Word word2) {
            return new Word(word1.word, Math.min(word1.firstIndexInSource, word2.firstIndexInSource), word1.count + word2.count);
        }

        public static final Comparator<Word> DESCENDING_COUNT_COMPARATOR = (o1, o2) ->
                Objects.equals(o1.count, o2.count)
                        ? Integer.compare(o1.firstIndexInSource, o2.firstIndexInSource)
                        : Integer.compare(o2.count, o1.count);
    }
}
