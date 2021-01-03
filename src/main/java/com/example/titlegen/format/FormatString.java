package com.example.titlegen.format;

import java.util.ArrayList;
import java.util.List;

public class FormatString {
    Boolean error;
    Object[] data;
    String type;
    String message;

    public FormatString(Boolean error, Object[] data, String type, String message) {
        this.error = error;
        this.data = data;
        this.type = type;
        this.message = message;
    }
}
