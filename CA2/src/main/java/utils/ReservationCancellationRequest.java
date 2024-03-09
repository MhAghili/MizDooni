package utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(force = true)
public class ReservationCancellationRequest {
    private int reservationNumber;
    private String username;

    public ReservationCancellationRequest(int reservationNumber, String username) {
        this.reservationNumber = reservationNumber;
        this.username = username;
    }

}
