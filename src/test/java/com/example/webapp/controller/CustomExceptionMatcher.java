package com.example.webapp.controller;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class CustomExceptionMatcher extends
        TypeSafeMatcher<RuntimeException> {

    private String actual;
    private String expected;

    private CustomExceptionMatcher (String expected) {
        this.expected = expected;
    }

    public static CustomExceptionMatcher assertSomeThing(String expected) {
        return new CustomExceptionMatcher (expected);
    }

    @Override
    protected boolean matchesSafely(RuntimeException exception) {
        actual = exception.getMessage();
        return actual.equals(expected);
    }

    @Override
    public void describeTo(Description desc) {
        desc.appendText("Actual =").appendValue(actual)
                .appendText(" Expected =").appendValue(
                        expected);

    }
}