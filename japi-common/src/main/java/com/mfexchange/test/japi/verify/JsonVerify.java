package com.elong.test.japi.verify;

import org.hamcrest.Matchers;

import com.jayway.jsonassert.JsonAssert;

public class JsonVerify {
    protected String json;

    public JsonVerify(String json) {
        this.json = json;
    }

    @Verify(description="验证jsonPath对应的字段是boolean值expected")
    public void equalToBoolean(String jsonPath, String expected) {
        Boolean expectBool = Boolean.parseBoolean(expected.trim());
        JsonAssert.with(json).assertThat(jsonPath, Matchers.equalTo(expectBool));
    }

    @Verify(description="验证jsonPath对应的字段是short值expected")
    public void equalToShort(String jsonPath, String expected) {
        Short expectShort = Short.parseShort(expected.trim());
        JsonAssert.with(json).assertThat(jsonPath, Matchers.equalTo(expectShort));
    }

    @Verify(description="验证jsonPath对应的字段比short值expected大")
    public void greaterThanShort(String jsonPath, String expected) {
        Short expectShort = Short.parseShort(expected.trim());
        JsonAssert.with(json).assertThat(jsonPath, Matchers.greaterThan(expectShort));
    }

    @Verify(description="验证jsonPath对应的字段比short值expected小")
    public void lessThanShort(String jsonPath, String expected) {
        Short expectShort = Short.parseShort(expected.trim());
        JsonAssert.with(json).assertThat(jsonPath, Matchers.lessThan(expectShort));
    }

    @Verify(description="验证jsonPath对应的字段比short值expected大或是相等")
    public void greaterThanOrEqualToShort(String jsonPath, String expected) {
        Short expectShort = Short.parseShort(expected.trim());
        JsonAssert.with(json).assertThat(jsonPath, Matchers.greaterThanOrEqualTo(expectShort));
    }

    @Verify(description="验证jsonPath对应的字段比short值expected小或是相等")
    public void lessThanOrEqualToShort(String jsonPath, String expected) {
        Short expectShort = Short.parseShort(expected.trim());
        JsonAssert.with(json).assertThat(jsonPath, Matchers.lessThanOrEqualTo(expectShort));
    }

    @Verify(description="验证jsonPath对应的字段是int值expected")
    public void equalToInt(String jsonPath, String expected) {
        Integer expectInt = Integer.parseInt(expected.trim());
        JsonAssert.with(json).assertThat(jsonPath, Matchers.equalTo(expectInt));
    }

    @Verify(description="验证jsonPath对应的字段比int值expected大")
    public void greaterThanInt(String jsonPath, String expected) {
        Integer expectInt = Integer.parseInt(expected.trim());
        JsonAssert.with(json).assertThat(jsonPath, Matchers.greaterThan(expectInt));
    }

    @Verify(description="验证jsonPath对应的字段比int值expected小")
    public void lessThanInt(String jsonPath, String expected) {
        Integer expectInt = Integer.parseInt(expected.trim());
        JsonAssert.with(json).assertThat(jsonPath, Matchers.lessThan(expectInt));
    }

    @Verify(description="验证jsonPath对应的字段比int值expected大或是相等")
    public void greaterThanOrEqualToInt(String jsonPath, String expected) {
        Integer expectInt = Integer.parseInt(expected.trim());
        JsonAssert.with(json).assertThat(jsonPath, Matchers.greaterThanOrEqualTo(expectInt));
    }

    @Verify(description="验证jsonPath对应的字段比int值expected小或是相等")
    public void lessThanOrEqualToInt(String jsonPath, String expected) {
        Integer expectInt = Integer.parseInt(expected.trim());
        JsonAssert.with(json).assertThat(jsonPath, Matchers.lessThanOrEqualTo(expectInt));
    }

    @Verify(description="验证jsonPath对应的字段是long值expected")
    public void equalToLong(String jsonPath, String expected) {
        Long expectLong = Long.parseLong(expected.trim());
        JsonAssert.with(json).assertThat(jsonPath, Matchers.equalTo(expectLong));
    }

    @Verify(description="验证jsonPath对应的字段比long值expected大")
    public void greaterThanLong(String jsonPath, String expected) {
        Long expectLong = Long.parseLong(expected.trim());
        JsonAssert.with(json).assertThat(jsonPath, Matchers.greaterThan(expectLong));
    }

    @Verify(description="验证jsonPath对应的字段比long值expected小")
    public void lessThanLong(String jsonPath, String expected) {
        Long expectLong = Long.parseLong(expected.trim());
        JsonAssert.with(json).assertThat(jsonPath, Matchers.lessThan(expectLong));
    }

    @Verify(description="验证jsonPath对应的字段比long值expected大或相等")
    public void greaterThanOrEqualToLong(String jsonPath, String expected) {
        Long expectLong = Long.parseLong(expected.trim());
        JsonAssert.with(json).assertThat(jsonPath, Matchers.greaterThanOrEqualTo(expectLong));
    }

    @Verify(description="验证jsonPath对应的字段比long值expected小或是相等")
    public void lessThanOrEqualToLong(String jsonPath, String expected) {
        Long expectLong = Long.parseLong(expected.trim());
        JsonAssert.with(json).assertThat(jsonPath, Matchers.lessThanOrEqualTo(expectLong));
    }

    @Verify(description="验证jsonPath对应的字段是float值expected")
    public void equalToFloat(String jsonPath, String expected) {
        Float expectFloat = Float.parseFloat(expected.trim());
        JsonAssert.with(json).assertThat(jsonPath, Matchers.equalTo(expectFloat));
    }

    @Verify(description="验证jsonPath对应的字段比float值expected大")
    public void greaterThanFloat(String jsonPath, String expected) {
        Float expectFloat = Float.parseFloat(expected.trim());
        JsonAssert.with(json).assertThat(jsonPath, Matchers.greaterThan(expectFloat));
    }

    @Verify(description="验证jsonPath对应的字段比float值expected小")
    public void lessThanFloat(String jsonPath, String expected) {
        Float expectFloat = Float.parseFloat(expected.trim());
        JsonAssert.with(json).assertThat(jsonPath, Matchers.lessThan(expectFloat));
    }

    @Verify(description="验证jsonPath对应的字段比float值expected大或相等")
    public void greaterThanOrEqualToFloat(String jsonPath, String expected) {
        Float expectFloat = Float.parseFloat(expected.trim());
        JsonAssert.with(json).assertThat(jsonPath, Matchers.greaterThanOrEqualTo(expectFloat));
    }

    @Verify(description="验证jsonPath对应的字段比float值expected小或相等")
    public void lessThanOrEqualToFloat(String jsonPath, String expected) {
        Float expectFloat = Float.parseFloat(expected.trim());
        JsonAssert.with(json).assertThat(jsonPath, Matchers.lessThanOrEqualTo(expectFloat));
    }

    @Verify(description="验证jsonPath对应的字段是double值expected")
    public void equalToDouble(String jsonPath, String expected) {
        Double expectDouble = Double.parseDouble(expected.trim());
        JsonAssert.with(json).assertThat(jsonPath, Matchers.equalTo(expectDouble));
    }

    @Verify(description="验证jsonPath对应的字段与double值expected在error范围内相等")
    public void closeTo(String jsonPath, String expected, String error) {
        Double expectDouble = Double.parseDouble(expected.trim());
        Double errorDouble = Double.parseDouble(error.trim());
        JsonAssert.with(json).assertThat(jsonPath, Matchers.closeTo(expectDouble, errorDouble));
    }

    @Verify(description="验证jsonPath对应的字段比double值expected大")
    public void greaterThanDouble(String jsonPath, String expected) {
        Double expectDouble = Double.parseDouble(expected.trim());
        JsonAssert.with(json).assertThat(jsonPath, Matchers.greaterThan(expectDouble));
    }

    @Verify(description="验证jsonPath对应的字段比double值expected小")
    public void lessThanDouble(String jsonPath, String expected) {
        Double expectDouble = Double.parseDouble(expected.trim());
        JsonAssert.with(json).assertThat(jsonPath, Matchers.lessThan(expectDouble));
    }

    @Verify(description="验证jsonPath对应的字段比double值expected大或相等")
    public void greaterThanOrEqualToDouble(String jsonPath, String expected) {
        Double expectDouble = Double.parseDouble(expected.trim());
        JsonAssert.with(json).assertThat(jsonPath, Matchers.greaterThanOrEqualTo(expectDouble));
    }

    @Verify(description="验证jsonPath对应的字段比double值expected小或相等")
    public void lessThanOrEqualToDouble(String jsonPath, String expected) {
        Double expectDouble = Double.parseDouble(expected.trim());
        JsonAssert.with(json).assertThat(jsonPath, Matchers.lessThanOrEqualTo(expectDouble));
    }

    @Verify(description="验证jsonPath对应的字段与string值expected一样")
    public void equalToString(String jsonPath, String expected) {
        JsonAssert.with(json).assertThat(jsonPath, Matchers.equalTo(expected));
    }

    @Verify(description="验证jsonPath对应的字段与string值expected一样，大小写无关")
    public void equalToStringIgnoreCase(String jsonPath, String expected) {
        JsonAssert.with(json).assertThat(jsonPath, Matchers.equalToIgnoringCase(expected));
    }

    @Verify(description="验证jsonPath对应的字段与string值expected一样，忽略空白字符")
    public void equalToStringIgnoreWhiteSpace(String jsonPath, String expected) {
        JsonAssert.with(json).assertThat(jsonPath, Matchers.equalToIgnoringWhiteSpace(expected));
    }

    @Verify(description="验证jsonPath对应的字段以前缀值prefix开头")
    public void startsWith(String jsonPath, String prefix) {
        JsonAssert.with(json).assertThat(jsonPath, Matchers.startsWith(prefix));
    }

    @Verify(description="验证jsonPath对应的字段以后缀值suffix结尾")
    public void endsWith(String jsonPath, String suffix) {
        JsonAssert.with(json).assertThat(jsonPath, Matchers.endsWith(suffix));
    }

    @Verify(description="验证jsonPath对应的collection字段的size是expected")
    @SuppressWarnings("unchecked")
    public void collectionSizeEqualTo(String jsonPath, String expected) {
        Integer expectInt = Integer.valueOf(expected.trim());
        JsonAssert.with(json).assertThat(jsonPath,
                Matchers.is(JsonAssert.collectionWithSize(Matchers.equalTo(expectInt))));
    }

    @Verify(description="验证jsonPath对应的collection字段的size比expected大")
    @SuppressWarnings("unchecked")
    public void collectionSizeGreaterThan(String jsonPath, String expected) {
        Integer expectInt = Integer.valueOf(expected.trim());
        JsonAssert.with(json).assertThat(jsonPath,
                Matchers.is(JsonAssert.collectionWithSize(Matchers.greaterThan(expectInt))));
    }

    @Verify(description="验证jsonPath对应的collection字段的size比expected小")
    @SuppressWarnings("unchecked")
    public void collectionSizeLessThan(String jsonPath, String expected) {
        Integer expectInt = Integer.valueOf(expected.trim());
        JsonAssert.with(json).assertThat(jsonPath,
                Matchers.is(JsonAssert.collectionWithSize(Matchers.lessThan(expectInt))));
    }

    @Verify(description="验证jsonPath对应的collection字段的size比expected大或相等")
    @SuppressWarnings("unchecked")
    public void collectionSizeGreaterThanOrEqualTo(String jsonPath, String expected) {
        Integer expectInt = Integer.valueOf(expected.trim());
        JsonAssert.with(json).assertThat(jsonPath,
                Matchers.is(JsonAssert.collectionWithSize(Matchers.greaterThanOrEqualTo(expectInt))));
    }

    @Verify(description="验证jsonPath对应的collection字段的sizeexpected小或相等")
    @SuppressWarnings("unchecked")
    public void collectionSizeLessThanOrEqualTo(String jsonPath, String expected) {
        Integer expectInt = Integer.valueOf(expected.trim());
        JsonAssert.with(json).assertThat(jsonPath,
                Matchers.is(JsonAssert.collectionWithSize(Matchers.greaterThanOrEqualTo(expectInt))));
    }

    @Verify(description="验证jsonPath对应的collection字段包含expected")
    public void collectionHasItem(String jsonPath, String expected) {
        JsonAssert.with(json).assertThat(jsonPath, Matchers.hasItems(expected));
    }

    @Verify(description="验证jsonPath对应的字段包含键值expectKey")
    public void hasKey(String jsonPath, String expectKey) {
        JsonAssert.with(json).assertThat(jsonPath, Matchers.hasKey(expectKey));
    }

    @Verify(description="验证jsonPath对应的字段不为null")
    public void notNull(String jsonPath) {
        JsonAssert.with(json).assertNotNull(jsonPath);
    }

    @Verify(description="验证jsonPath对应的字段为null")
    public void isNull(String jsonPath) {
        JsonAssert.with(json).assertNull(jsonPath);
    }

    @Verify(description="验证jsonPath对应的字段未定义")
    public void notDefined(String jsonPath) {
        JsonAssert.with(json).assertNotDefined(jsonPath);
    }

    @Verify(description="验证jsonPath对应的字段为空collection")
    public void isEmptyCollection(String jsonPath) {
        JsonAssert.with(json).assertThat(jsonPath, JsonAssert.emptyCollection());
    }
}
