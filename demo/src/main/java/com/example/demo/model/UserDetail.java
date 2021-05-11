package com.example.demo.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetail {

    private UserAccount userAccount;
    private JobHunterModel jobHunterModel;
}
