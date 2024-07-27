package org.nam.core.models;

import lombok.Data;
import lombok.Getter;

@Data
public class SignIn {

    @Getter
    private static int row;

    @Getter
    private static String testCaseName = "TESTCASENAME";

    @Getter
    private static String email = "EMAIL";

    @Getter
    private static String password = "PASSWORD";

    @Getter
    private static String expectedTitle = "EXPECTED_TITLE";

    @Getter
    private static String expectedError = "EXPECTED_ERROR";

    @Getter
    private static String expectedUrl = "EXPECTED_URL";
}