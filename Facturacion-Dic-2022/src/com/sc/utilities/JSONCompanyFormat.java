package com.sc.utilities;

import com.sc.models.Company;

public class JSONCompanyFormat {

    public Company company;

    public JSONCompanyFormat(Company company) {
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
