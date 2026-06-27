package com.chitkara.bfhl.service;

import com.chitkara.bfhl.config.UserProperties;
import com.chitkara.bfhl.dto.BfhlRequest;
import com.chitkara.bfhl.dto.BfhlResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BfhlServiceImplTest {

    private BfhlService bfhlService;

    @BeforeEach
    void setUp() {
        UserProperties userProperties = new UserProperties();
        userProperties.setFullName("john doe");
        userProperties.setDob("17091999");
        userProperties.setEmail("john@xyz.com");
        userProperties.setRollNumber("ABCD123");
        bfhlService = new BfhlServiceImpl(userProperties);
    }

    @Test
    void exampleA() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("a", "1", "334", "4", "R", "$"));
        BfhlResponse response = bfhlService.process(request);

        assertTrue(response.isSuccess());
        assertEquals("john_doe_17091999", response.getUserId());
        assertEquals("john@xyz.com", response.getEmail());
        assertEquals("ABCD123", response.getRollNumber());
        assertEquals(List.of("1"), response.getOddNumbers());
        assertEquals(List.of("334", "4"), response.getEvenNumbers());
        assertEquals(List.of("A", "R"), response.getAlphabets());
        assertEquals(List.of("$"), response.getSpecialCharacters());
        assertEquals("339", response.getSum());
        assertEquals("Ra", response.getConcatString());
    }

    @Test
    void exampleB() {
        BfhlRequest request = new BfhlRequest(
                Arrays.asList("2", "a", "y", "4", "&", "-", "*", "5", "92", "b"));
        BfhlResponse response = bfhlService.process(request);

        assertTrue(response.isSuccess());
        assertEquals(List.of("5"), response.getOddNumbers());
        assertEquals(List.of("2", "4", "92"), response.getEvenNumbers());
        assertEquals(List.of("A", "Y", "B"), response.getAlphabets());
        assertEquals(List.of("&", "-", "*"), response.getSpecialCharacters());
        assertEquals("103", response.getSum());
        assertEquals("ByA", response.getConcatString());
    }

    @Test
    void exampleC() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("A", "ABCD", "DOE"));
        BfhlResponse response = bfhlService.process(request);

        assertTrue(response.isSuccess());
        assertEquals(Collections.emptyList(), response.getOddNumbers());
        assertEquals(Collections.emptyList(), response.getEvenNumbers());
        assertEquals(List.of("A", "ABCD", "DOE"), response.getAlphabets());
        assertEquals(Collections.emptyList(), response.getSpecialCharacters());
        assertEquals("0", response.getSum());
        assertEquals("EoDdCbAa", response.getConcatString());
    }

    @Test
    void emptyDataArray() {
        BfhlRequest request = new BfhlRequest(Collections.emptyList());
        BfhlResponse response = bfhlService.process(request);

        assertTrue(response.isSuccess());
        assertEquals(Collections.emptyList(), response.getOddNumbers());
        assertEquals(Collections.emptyList(), response.getEvenNumbers());
        assertEquals(Collections.emptyList(), response.getAlphabets());
        assertEquals(Collections.emptyList(), response.getSpecialCharacters());
        assertEquals("0", response.getSum());
        assertEquals("", response.getConcatString());
    }

    @Test
    void alphanumericTreatedAsSpecialCharacter() {
        BfhlRequest request = new BfhlRequest(List.of("a1", "12b"));
        BfhlResponse response = bfhlService.process(request);

        assertEquals(Collections.emptyList(), response.getOddNumbers());
        assertEquals(Collections.emptyList(), response.getEvenNumbers());
        assertEquals(Collections.emptyList(), response.getAlphabets());
        assertEquals(List.of("a1", "12b"), response.getSpecialCharacters());
        assertEquals("0", response.getSum());
        assertEquals("", response.getConcatString());
    }

    @Test
    void negativeOddNumber() {
        BfhlRequest request = new BfhlRequest(List.of("-3"));
        BfhlResponse response = bfhlService.process(request);

        assertEquals(List.of("-3"), response.getOddNumbers());
        assertEquals("-3", response.getSum());
    }

    @Test
    void negativeEvenNumber() {
        BfhlRequest request = new BfhlRequest(List.of("-4"));
        BfhlResponse response = bfhlService.process(request);

        assertEquals(List.of("-4"), response.getEvenNumbers());
        assertEquals("-4", response.getSum());
    }

    @Test
    void zeroIsEven() {
        BfhlRequest request = new BfhlRequest(List.of("0"));
        BfhlResponse response = bfhlService.process(request);

        assertEquals(List.of("0"), response.getEvenNumbers());
        assertEquals("0", response.getSum());
    }

    @Test
    void nullElementsAreSkipped() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("a", null, "2"));
        BfhlResponse response = bfhlService.process(request);

        assertEquals(List.of("A"), response.getAlphabets());
        assertEquals(List.of("2"), response.getEvenNumbers());
        assertEquals("2", response.getSum());
    }
}
