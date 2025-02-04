package com.example.file.task.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientResponse {
    private Long id;
    private Long userid;
    private String name;
    private String email;
    private String phone;
}
