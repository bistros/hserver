package com.bistros.hauto.common;

public enum DIRECTION {
    UP("북"), RIGHT("동"), LEFT("서"), DOWN("남");

    private String description;

    DIRECTION(String description) {
        this.description = description;
    }
}
