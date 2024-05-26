package utils;

import lombok.Getter;
import lombok.Value;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Value
public class AvailableTableInfo {
    private int tableNumber;
    private int seatsNumber;
    List<ZonedDateTime> availableTimes;
}
