package com.example.titlegen.format;

import java.util.ArrayList;
import java.util.List;

public class formatString {
    Boolean error;
    Object[] data;
    String type;
    String message;

    public formatString(Boolean error, Object[] data, String type, String message) {
        this.error = error;
        this.data = data;
        this.type = type;
        this.message = message;
    }
}
