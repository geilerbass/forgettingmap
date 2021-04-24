package com.geilerbass;

import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ForgettingMapTest {

    public static final String FIRST_KEY = "testKey";
    public static final String FIRST_CONTENT = "testData";
    public static final String SECOND_KEY = "anotherTestKey";
    public static final String SECOND_CONTENT = "moreTestData";
    public static final String THIRD_KEY = "yetAnotherTestKey";
    public static final String THIRD_CONTENT = "yetMoreData";

    @Test
    public void addAssociationToMap() {
        // Given
        final int x = 1;
        final ForgettingMap<String, String> unitUnderTest = new ForgettingMapImpl<>(x);

        // When
        unitUnderTest.add(FIRST_KEY, FIRST_CONTENT);

        // Then
        assertThat(unitUnderTest.find(FIRST_KEY), is(Optional.of(FIRST_CONTENT)));
    }

    @Test
    public void addAssociationToFullMapKeepsNewerAssociation() {
        // Given
        final int x = 1;
        final ForgettingMap<String, String> unitUnderTest = new ForgettingMapImpl<>(x);
        unitUnderTest.add(FIRST_KEY, FIRST_CONTENT);

        // When
        unitUnderTest.add(SECOND_KEY, SECOND_CONTENT);

        // Then
        assertThat(unitUnderTest.find(FIRST_KEY), is(Optional.empty()));
        assertThat(unitUnderTest.find(SECOND_KEY), is(Optional.of(SECOND_CONTENT)));
    }

    @Test
    public void addAssociationToFullMapKeepsMostUsed() {
        // Given
        final int x = 2;
        final ForgettingMap<String, String> unitUnderTest = new ForgettingMapImpl<>(x);
        unitUnderTest.add(FIRST_KEY, FIRST_CONTENT);
        unitUnderTest.add(SECOND_KEY, SECOND_CONTENT);

        unitUnderTest.find(FIRST_KEY);

        // When
        unitUnderTest.add(THIRD_KEY, THIRD_CONTENT);

        // Then
        assertThat(unitUnderTest.find(FIRST_KEY), is(Optional.of(FIRST_CONTENT)));
        assertThat(unitUnderTest.find(SECOND_KEY), is(Optional.empty()));
    }

    @Test
    public void addAssociationToFullMapKeepsLastAccessedWhenFrequencyIsEqual() {
        // Given
        final int x = 2;
        final ForgettingMap<String, String> unitUnderTest = new ForgettingMapImpl<>(x);
        unitUnderTest.add(FIRST_KEY, FIRST_CONTENT);
        unitUnderTest.add(SECOND_KEY, SECOND_CONTENT);

        unitUnderTest.find(SECOND_KEY);
        unitUnderTest.find(FIRST_KEY);

        // When
        unitUnderTest.add(THIRD_KEY, THIRD_CONTENT);

        // Then
        assertThat(unitUnderTest.find(FIRST_KEY), is(Optional.of(FIRST_CONTENT)));
        assertThat(unitUnderTest.find(SECOND_KEY), is(Optional.empty()));
        assertThat(unitUnderTest.find(THIRD_KEY), is(Optional.of(THIRD_CONTENT)));
    }
}
