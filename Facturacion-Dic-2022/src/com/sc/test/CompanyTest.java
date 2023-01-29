package com.sc.test;

import com.sc.enums.DepartmentsEnum;
import com.sc.models.Company;
import com.sc.models.GeneralManager;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class CompanyTest {

    private GeneralManager manager;

    @Before
    public void initManager() {
        manager = new GeneralManager();
    }

    @Test
    public void addCompanyTest(){
        Company company = new Company("Supermercado","Avenida 9 #8-15", "Tunja", DepartmentsEnum.BOYACA, "3158716534", "supermercado@gmail.com");
        try {
            manager.addCompanyImg(company.getName(), company.getAddress(), company.getCity(), company.getDepartment(), company.getPhoneNumber(), company.getEmail(), "path");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void addCompanyTest2(){
        Company company = new Company("Supermercado 2","Avenida 9 #8-15", "Duitama", DepartmentsEnum.BOYACA, "3158716534", "supermercado2@gmail.com");
        try {
            manager.addCompanyImg(company.getName(), company.getAddress(), company.getCity(), company.getDepartment(), company.getPhoneNumber(), company.getEmail(), "path2");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
