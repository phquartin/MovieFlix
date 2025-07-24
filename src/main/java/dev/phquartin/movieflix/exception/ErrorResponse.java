package dev.phquartin.movieflix.exception;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse {

    private String timestamp;
    private Integer status;
    private String message;
    private String path;

}
