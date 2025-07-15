package com.creditsimulator.domain.model.simulation;

import com.creditsimulator.domain.model.LoanBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class LoanSimulationRequestModel extends LoanBase {

    private LocalDate birthDate;
    private Integer months;

}
