package az.company.simplecrmtask.exception.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private UUID uuid;
    private HttpStatus status;
    private Object message;
    private LocalDateTime timestamp;

}