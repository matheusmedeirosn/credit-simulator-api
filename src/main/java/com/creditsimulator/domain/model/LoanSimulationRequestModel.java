package com.creditsimulator.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class LoanSimulationRequestModel extends LoanBase{

    private LocalDate birthDate;
    private Integer months;

}
