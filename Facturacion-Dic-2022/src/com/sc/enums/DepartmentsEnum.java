package com.sc.enums;

public enum DepartmentsEnum {

    AMAZONAS("Amazonas"), ANTIOQUIA("Antioquia"), ARAUCA("Arauca"), ATLANTICO("Atlantico"), BOLIVAR("Bolivar"), BOYACA("Boyaca"),
    CALDAS("Caldas"), CAQUETA("Caqueta"), CASANARE("Casanare"), CAUCA("Cauca"), CESAR("Cesar"), CHOCO("Choco"), CORDOBA("Cordoba"),
    CUNDINAMARCA("Cundinamarca"), GUAINIA("Guainia"), GUAVIARE("Guaviare"), HUILA("Huila"), GUAJIRA("La Guajira"), MAGDALENA("Magdalena"),
    META("Meta"), NARIÑO("Nariño"), NORTEDESANTANDER("Norte de Santander"), PUTUMAYO("Putumayo"), QUINDIO("Quindio"), RISARALDA("Risaralda"),
    SANANDRES("San Andres y Providencia"), SANTANDER("Santander"), SUCRE("Sucre"), TOLIMA("Tolima"), VALLEDELCAUCA("Valle del Cauca"),
    VAUPES("Vaupes"), VICHADA("Vichada");

    private String option;

    private DepartmentsEnum(String option) {
        this.option = option;
    }

    public static DepartmentsEnum findDepartment(String value) {
        switch (value) {
            case "AMAZONAS":
                return DepartmentsEnum.AMAZONAS;
            case "ANTIOQUIA":
                return DepartmentsEnum.ANTIOQUIA;
            case "ARAUCA":
                return DepartmentsEnum.ARAUCA;
            case "ATLANTICO":
                return DepartmentsEnum.ATLANTICO;
            case "BOLIVAR":
                return DepartmentsEnum.BOLIVAR;
            case "BOYACA":
                return DepartmentsEnum.BOYACA;
            case "CALDAS":
                return DepartmentsEnum.CALDAS;
            case "CAQUETA":
                return DepartmentsEnum.CAQUETA;
            case "CASANARE":
                return DepartmentsEnum.CASANARE;
            case "CAUCA":
                return DepartmentsEnum.CAUCA;
            case "CESAR":
                return DepartmentsEnum.CESAR;
            case "CHOCO":
                return DepartmentsEnum.CHOCO;
            case "CORDOBA":
                return DepartmentsEnum.CORDOBA;
            case "CUNDINAMARCA":
                return DepartmentsEnum.CUNDINAMARCA;
            case "GUAINIA":
                return DepartmentsEnum.GUAINIA;
            case "GUAVIARE":
                return DepartmentsEnum.GUAVIARE;
            case "GUAJIRA":
                return DepartmentsEnum.GUAJIRA;
            case "HUILA":
                return DepartmentsEnum.HUILA;
            case "MAGDALENA":
                return DepartmentsEnum.MAGDALENA;
            case "META":
                return DepartmentsEnum.META;
            case "NARIÑO":
                return DepartmentsEnum.NARIÑO;
            case "NORTEDESANTANDER":
                return DepartmentsEnum.NORTEDESANTANDER;
            case "PUTUMAYO":
                return DepartmentsEnum.PUTUMAYO;
            case "QUINDIO":
                return DepartmentsEnum.QUINDIO;
            case "RISARALDA":
                return DepartmentsEnum.RISARALDA;
            case "SANANDRES":
                return DepartmentsEnum.SANANDRES;
            case "SANTANDER":
                return DepartmentsEnum.SANTANDER;
            case "SUCRE":
                return DepartmentsEnum.SUCRE;
            case "TOLIMA":
                return DepartmentsEnum.TOLIMA;
            case "VALLEDELCAUCA":
                return DepartmentsEnum.VALLEDELCAUCA;
            case "VAUPES":
                return DepartmentsEnum.VAUPES;
            case "VICHADA":
                return DepartmentsEnum.VICHADA;
        }
        return null;
    }

    public String toString() {
        return option;
    }
}
