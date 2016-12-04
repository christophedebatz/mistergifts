package com.debatz.gifts.model;

/**
 * .
 */
public enum ParticipationType {

    RELATIVE("rel"),

    ABSOLUTE("abs"),

    ALL("entire");

    private String type;

    ParticipationType(String type) {
        this.type = type;
    }

    String getType() {
        return type;
    }

}
