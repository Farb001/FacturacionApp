package com.sc.enums;

import java.util.Arrays;

public enum PaymentTypeEnum {

    CASH("Efectivo"), CARD("Tarjeta de credito");

    private String option;

    private PaymentTypeEnum(String option) {
        this.option = option;
    }

    public static PaymentTypeEnum findPayment(String value){
        switch (value) {
            case "CASH":
                return PaymentTypeEnum.CASH;
            case "CARD":
                return PaymentTypeEnum.CARD;
        }
        return null;
    }

    public String toString() {
        return option;
    }
}
