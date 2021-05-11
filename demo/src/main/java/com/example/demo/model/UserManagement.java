package com.example.demo.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserManagement {
    private UserAccount userAccount;
    private CompanyModel companyModel;
}

