package hu.domain.account;

import hu.domain.account.Account;

public class ExternalService extends Account {

    private String companyName;

    public ExternalService(String name, int phoneNumber, String email, String responsibility, int cost, String companyName) {
        super(name, phoneNumber, email, responsibility, cost);
        this.companyName = companyName;
    }

    public ExternalService(int id, String name, int phoneNumber, String email, String responsibility, int cost, String companyName) {
        super(id, name, phoneNumber, email, responsibility, cost);
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "ExternalService{" +
                "companyName: " + companyName + '\'' +
                '}';
    }
}
