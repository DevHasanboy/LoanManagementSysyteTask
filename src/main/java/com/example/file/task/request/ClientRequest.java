package com.example.file.task.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientRequest {
    @NotBlank(message = "Name cannot be null or empty")
    private String name;
    @NotBlank(message = "Email cannot be null or empty")
    private String email;
    @NotBlank(message = "Phone cannot be null or empty")
    private String phone;
    private Long userId;
}
