package com.example.titlegen.format;

import java.util.ArrayList;
import java.util.List;

public class formatString {
    Boolean error;
    Object[] data;
    String message;

    public formatString(Boolean error, Object[] data, String message) {
        this.error = error;
        this.data = data;
        this.message = message;
    }
}
