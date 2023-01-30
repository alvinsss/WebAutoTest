package com.mfexchange.test.japi.verify;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class JsonVerifyTest {
    @Test
    public void testEqualToString() {
        verify.equalToString("$.store.bicycle.color", "red");
        verify.equalToString("store.bicycle.color", "red");
        verify.equalToString("store.book[0].category", "reference");
        verify.equalToString("$.store.bicycle.id", "中国跑车");
    }

    @Test
    public void testEqualToStringIgnoreCase() {
        verify.equalToStringIgnoreCase("$.store.bicycle.color", "RED");
        verify.equalToStringIgnoreCase("store.bicycle.color", "rEd");
    }

    @Test
    public void testIsEmptyCollection() {
        verify.isEmptyCollection("$.store.book[?(@.category == 'x')]");
        verify.isEmptyCollection("store.book[?(@.category == 'x')]");
    }

    @Test
    public void testEqualToInt() {
        verify.equalToInt("store.book[1].price", "12");
        verify.equalToInt("$.store.book[1].price", "12");
    }

    @Test
    public void testEqualToDouble() {
        verify.equalToDouble("$.store.bicycle.price", "19.95D");
    }

    @Test
    public void testCollectionSizeEqualTo() {
        verify.collectionSizeEqualTo("store.book", "4");
        verify.collectionSizeEqualTo("$.store.book", "4");
        verify.collectionSizeEqualTo("$..author", "4");
        verify.collectionSizeEqualTo("$..category", "4");
    }

    @Test
    public void testCollectionHasItem() {
        verify.collectionHasItem("$..author", "Nigel Rees");
        verify.collectionHasItem("$..author", "Evelyn Waugh");
    }

    @Test
    public void testHasKey() {
        verify.hasKey("$.store.book[0]", "author");
    }

    @BeforeClass
    public void setUpBeforeMethod() {
        verify = new JsonVerify(JSON);
    }

    private JsonVerify verify;
    private static final String JSON = "{ \"store\": {\n" +
            "    \"book\": [ \n" +
            "      { \"category\": \"reference\",\n" +
            "        \"author\": \"Nigel Rees\",\n" +
            "        \"title\": \"Sayings of the Century\",\n" +
            "        \"price\": 8.95\n" +
            "      },\n" +
            "      { \"category\": \"fiction\",\n" +
            "        \"author\": \"Evelyn Waugh\",\n" +
            "        \"title\": \"Sword of Honour\",\n" +
            "        \"price\": 12\n" +
            "      },\n" +
            "      { \"category\": \"fiction\",\n" +
            "        \"author\": \"Herman Melville\",\n" +
            "        \"title\": \"Moby Dick\",\n" +
            "        \"isbn\": \"0-553-21311-3\",\n" +
            "        \"price\": 8.99\n" +
            "      },\n" +
            "      { \"category\": \"fiction\",\n" +
            "        \"author\": \"J. R. R. Tolkien\",\n" +
            "        \"title\": \"The Lord of the Rings\",\n" +
            "        \"isbn\": \"0-395-19395-8\",\n" +
            "        \"price\": 22.99\n" +
            "      }\n" +
            "    ],\n" +
            "    \"bicycle\": {\n" +
            "      \"id\": \"中国跑车\",\n" +
            "      \"color\": \"red\",\n" +
            "      \"price\": 19.95,\n" +
            "      \"atoms\": " + Long.MAX_VALUE + ",\n" +
            "    }\n" +
            "  }\n" +
            "}";
}
