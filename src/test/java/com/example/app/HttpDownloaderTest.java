package com.example.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HttpDownloaderTest
{
    @Test
    public void shouldGetSomething()
    {
        String result = new HttpDownloader().get("http://www.gutenberg.org/files/2701/2701-0.txt");
        assertEquals("The Projec", result.substring(1, 11));
    }
}
