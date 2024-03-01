package utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomTimeDeserializer extends JsonDeserializer<Date> {

    private final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String timeAsString = jsonParser.getText();
        try {
            return formatter.parse(timeAsString);
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse time: " + timeAsString, e);
        }
    }
}

