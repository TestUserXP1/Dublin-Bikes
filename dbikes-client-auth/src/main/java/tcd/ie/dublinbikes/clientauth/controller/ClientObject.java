package tcd.ie.dublinbikes.clientauth.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientObject {

    private final String content;

    @JsonCreator
    public ClientObject(@JsonProperty("content") String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientObject greeting = (ClientObject) o;

        return content.equals(greeting.content);

    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }

    @Override
    public String toString() {
        return "Login{" +
                "content='" + content + '\'' +
                '}';
    }
}
