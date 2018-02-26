package com.techscore.springboot;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ChatMessage implements Serializable {

    @NotNull
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
