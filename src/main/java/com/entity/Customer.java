package com.entity;

import com.entity.BoughtServices.BoughtServicesProps;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "kontrahenci")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nazwisko")
    private String lastName;

    @Column(name = "imie")
    private String firstName;

    @Column(name = "firma_nazwa")
    private String companyName;

    @NotNull(message = "To pole jest wymagane")
    @Column(name = "nip_pesel", nullable = false)
    private String taxIdentifier;

    @NotNull(message = "To pole jest wymagane")
    @Column(name = "ulica_nr_mieszkania", nullable = false)
    private String address;

    @NotNull(message = "To pole jest wymagane")
    @Column(name = "kod_pocztowy", nullable = false)
    private String postalCode;

    @NotNull(message = "To pole jest wymagane")
    @Column(name = "miejscowosc", nullable = false)
    private String city;

    @NotNull(message = "To pole jest wymagane")
    @Column(name = "sposob_zaplaty", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @NotNull(message = "To pole jest wymagane")
    @Column(name = "uwzglednij_numer_faktury", nullable = false)
    private boolean includeInCount;

    @NotNull(message = "To pole jest wymagane")
    @Column(name = "alias", nullable = false)
    private String alias;

    @Transient
    private CustomerProps customerProps;

    // do not implement
    public Customer() {
    }

    public Customer(String lastName, String firstName, String companyName, String taxIdentifier, String address,
                    String postalCode, String city, PaymentMethod paymentMethod, boolean includeInCount, String alias) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.companyName = companyName;
        this.taxIdentifier = taxIdentifier;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.paymentMethod = paymentMethod;
        this.includeInCount = includeInCount;
        this.alias = alias;
    }

    //region getters and setters
    public CustomerProps getCustomerProps() {
        return customerProps;
    }

    public void setCustomerProps(CustomerProps customerProps) {
        this.customerProps = customerProps;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTaxIdentifier() {
        return taxIdentifier;
    }

    public void setTaxIdentifier(String taxIdentifier) {
        this.taxIdentifier = taxIdentifier;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isIncludeInCount() {
        return includeInCount;
    }

    public void setIncludeInCount(boolean includeInCount) {
        this.includeInCount = includeInCount;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
    //endregion

    public class CustomerProps {
        private SimpleStringProperty lastNameProp;
        private SimpleStringProperty firstNameProp;
        private SimpleStringProperty companyNameProp;
        private SimpleStringProperty taxIdProp;
        private SimpleStringProperty addressProp;
        private SimpleStringProperty postalCodeProp;
        private SimpleStringProperty cityProp;
        private SimpleStringProperty aliasProp;
        private SimpleBooleanProperty countProp;
        private SimpleObjectProperty<PaymentMethod> paymentProp;
        private ObservableList<BoughtServicesProps> boughtServicesProps = FXCollections.observableArrayList();

        public CustomerProps() {
            this.lastNameProp = new SimpleStringProperty(lastName);
            this.firstNameProp = new SimpleStringProperty(firstName);
            this.companyNameProp = new SimpleStringProperty(companyName);
            this.taxIdProp = new SimpleStringProperty(taxIdentifier);
            this.addressProp = new SimpleStringProperty(address);
            this.postalCodeProp = new SimpleStringProperty(postalCode);
            this.cityProp = new SimpleStringProperty(city);
            this.aliasProp = new SimpleStringProperty(alias);
            this.countProp = new SimpleBooleanProperty(includeInCount);
            this.paymentProp = new SimpleObjectProperty<>(paymentMethod);
            Customer.this.setCustomerProps(this);
        }

        //region getters and setters
        public Customer getCustomer() {
            return Customer.this;
        }

        public String getLastNameProp() {
            return lastNameProp.get();
        }

        public void setLastNameProp(String lastNameProp) {
            Customer.this.setLastName(lastNameProp);
            this.lastNameProp.set(lastNameProp);
        }

        public SimpleStringProperty lastNamePropProperty() {
            return lastNameProp;
        }

        public String getFirstNameProp() {
            return firstNameProp.get();
        }

        public void setFirstNameProp(String firstNameProp) {
            Customer.this.setFirstName(firstNameProp);
            this.firstNameProp.set(firstNameProp);
        }

        public SimpleStringProperty firstNamePropProperty() {
            return firstNameProp;
        }

        public String getCompanyNameProp() {
            return companyNameProp.get();
        }

        public void setCompanyNameProp(String companyNameProp) {
            Customer.this.setCompanyName(companyNameProp);
            this.companyNameProp.set(companyNameProp);
        }

        public SimpleStringProperty companyNamePropProperty() {
            return companyNameProp;
        }

        public String getTaxIdProp() {
            return taxIdProp.get();
        }

        public void setTaxIdProp(String taxIdProp) {
            Customer.this.setTaxIdentifier(taxIdProp);
            this.taxIdProp.set(taxIdProp);
        }

        public SimpleStringProperty taxIdPropProperty() {
            return taxIdProp;
        }

        public String getAddressProp() {
            return addressProp.get();
        }

        public void setAddressProp(String addressProp) {
            Customer.this.setAddress(addressProp);
            this.addressProp.set(addressProp);
        }

        public SimpleStringProperty addressPropProperty() {
            return addressProp;
        }

        public String getPostalCodeProp() {
            return postalCodeProp.get();
        }

        public void setPostalCodeProp(String postalCodeProp) {
            Customer.this.setPostalCode(postalCodeProp);
            this.postalCodeProp.set(postalCodeProp);
        }

        public SimpleStringProperty postalCodePropProperty() {
            return postalCodeProp;
        }

        public String getCityProp() {
            return cityProp.get();
        }

        public void setCityProp(String cityProp) {
            Customer.this.setCity(cityProp);
            this.cityProp.set(cityProp);
        }

        public SimpleStringProperty cityPropProperty() {
            return cityProp;
        }

        public String getAliasProp() {
            return aliasProp.get();
        }

        public void setAliasProp(String aliasProp) {
            Customer.this.setAlias(aliasProp);
            this.aliasProp.set(aliasProp);
        }

        public SimpleStringProperty aliasPropProperty() {
            return aliasProp;
        }

        public boolean getCountProp() {
            return countProp.get();
        }

        public SimpleBooleanProperty countPropProperty() {
            return countProp;
        }

        public PaymentMethod getPaymentProp() {
            return paymentProp.get();
        }

        public void setPaymentProp(PaymentMethod paymentProp) {
            Customer.this.setPaymentMethod(paymentProp);
            this.paymentProp.set(paymentProp);
        }

        public SimpleObjectProperty<PaymentMethod> paymentPropProperty() {
            return paymentProp;
        }

        public boolean isCountProp() {
            return countProp.get();
        }

        public void setCountProp(boolean countProp) {
            Customer.this.setIncludeInCount(countProp);
            this.countProp.set(countProp);
        }

        public ObservableList<BoughtServicesProps> getBoughtServicesProps() {
            return boughtServicesProps;
        }

        public void addBoughtServicesProps(BoughtServicesProps props) {
            this.boughtServicesProps.add(props);
        }

        public void removeBoughtSerbicesProps(BoughtServicesProps props) {
            this.boughtServicesProps.remove(props);
        }

        //endregion
    }
}
