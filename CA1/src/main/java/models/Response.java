package models;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class Response {
    private boolean success;
    private String data;

    @Override
    public String toString() {
        return "{\"success\": " + success + ", \"data\": \"" + data + "\"}";
    }
}
