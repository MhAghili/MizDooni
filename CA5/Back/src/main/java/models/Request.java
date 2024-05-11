package models;

import lombok.Getter;
import lombok.Value;

@Value
@Getter
public class Request {
    private String command;
    private String jsonData;
}
