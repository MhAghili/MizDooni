package models;

import java.util.Optional;

public class Response<T> {
    private boolean isSuccessful;
    private String data;

    public Response(boolean isSuccessful, Optional<String> data) {
        this.isSuccessful = isSuccessful;
    }
}
