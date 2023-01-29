package com.sc.utilities;

import com.sc.models.billing.Client;

public class JSONClientFormat {

    public Client client;

    public JSONClientFormat(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
