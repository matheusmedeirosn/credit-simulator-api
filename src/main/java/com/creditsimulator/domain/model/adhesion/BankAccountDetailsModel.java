package com.creditsimulator.domain.model.adhesion;

import lombok.Data;

@Data
public class BankAccountDetailsModel {

    private String bankCode;
    private String branchNumber;
    private String accountNumber;
    private String accountType;

}
