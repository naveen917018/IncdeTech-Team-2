package com.incede.validation.validParam;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.incede.Exception.BusinessException;

public class ParamValueValidator {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void validate(String dataType, String value) {
    	System.out.println(dataType +"   "+value);
        if (value == null) {
            throw new IllegalArgumentException("Value must not be null");
        }

        switch (dataType.toLowerCase()) {
            case "string":
                // any string is fine
                break;

            case "integer":
                if (!value.matches("\\d+")) {
                    throw new BusinessException("Value must be a whole integer");
                }
                break;

            case "number":
                try {
                    new BigDecimal(value);
                } catch (NumberFormatException e) {
                    throw new BusinessException("Value must be numeric (decimal allowed)");
                }
                break;

            case "boolean":
                if (!value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false")) {
                    throw new IllegalArgumentException("Value must be true or false");
                }
                break;

            case "date":
                try {
                    LocalDate.parse(value); // ISO yyyy-MM-dd
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("Value must be a valid date (yyyy-MM-dd)");
                }
                break;

            case "array":
                try {
                    objectMapper.readTree(value);
                    if (!value.trim().startsWith("[")) {
                        throw new IllegalArgumentException("Value must be a JSON array");
                    }
                } catch (IOException e) {
                    throw new IllegalArgumentException("Invalid JSON array format");
                }
                break;

            case "object":
                try {
                    objectMapper.readTree(value);
                    if (!value.trim().startsWith("{")) {
                        throw new IllegalArgumentException("Value must be a JSON object");
                    }
                } catch (IOException e) {
                    throw new IllegalArgumentException("Invalid JSON object format");
                }
                break;

            default:
                throw new BusinessException("Unsupported data type: " + dataType);
        }
    }
}
