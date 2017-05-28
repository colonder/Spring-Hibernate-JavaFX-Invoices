package com.intermediary;

import com.entity.BoughtServices;
import com.entity.Customer;
import com.entity.ServiceEntity;
import com.service.IBoughtServicesService;
import com.service.ICustomerService;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Transient;
import java.math.BigDecimal;

@Component
public class PersonalData 
{    
    @Autowired
    private IBoughtServicesService boughtServicesService;

    // bought service properties
    private SimpleDoubleProperty quantity;
    private SimpleDoubleProperty quantityProp;
    private NumberBinding valWithoutTax;
    private NumberBinding taxVal;
    private NumberBinding totalVal;

    // service properties
    private SimpleStringProperty serviceNameProp;
    private SimpleStringProperty symbolProp;
    private SimpleStringProperty unitProp;
    private SimpleDoubleProperty netUnitPriceProp;
    private SimpleIntegerProperty vatProp;

    // customer properties
    private SimpleStringProperty lastNameProp;
    private SimpleStringProperty firstNameProp;
    private SimpleStringProperty companyNameProp;
    private SimpleStringProperty taxIdProp;
    private SimpleStringProperty addressProp;
    private SimpleStringProperty postalCodeProp;
    private SimpleStringProperty cityProp;
    private SimpleStringProperty aliasProp;

    private ObservableList<BoughtServices> boughtServicesList;
    private ObservableList<ServiceEntity> servicesList;
    
    /*public PersonalData(Customer customer)
    {
        lastNameProp = new SimpleStringProperty(customer.getLastName());
        firstNameProp = new SimpleStringProperty(customer.getFirstName());
        companyNameProp = new SimpleStringProperty(customer.getCompanyName());
        taxIdProp = new SimpleStringProperty();
        addressProp = new SimpleStringProperty();
        postalCodeProp = new SimpleStringProperty();
        cityProp = new SimpleStringProperty();
        aliasProp = new SimpleStringProperty();
        boughtServicesList = FXCollections.observableArrayList();
        quantity = new SimpleDoubleProperty();
        quantityProp = new SimpleDoubleProperty(quantity.doubleValue());
        valWithoutTax = quantityProp.multiply(netUnitPriceProp);
        taxVal = Bindings.multiply(valWithoutTax, vatProp).multiply(0.01);
        totalVal = valWithoutTax.add(taxVal);

        serviceNameProp = new SimpleStringProperty();
        symbolProp = new SimpleStringProperty();
        unitProp = new SimpleStringProperty();
        netUnitPriceProp = new SimpleDoubleProperty();
        vatProp = new SimpleIntegerProperty();
        boughtServicesList = FXCollections.observableList(customer.getBoughtServices());

    }*/

    public IBoughtServicesService getBoughtServicesService() {
        return boughtServicesService;
    }

    public void setBoughtServicesService(IBoughtServicesService boughtServicesService) {
        this.boughtServicesService = boughtServicesService;
    }

    public double getQuantity() {
        return quantity.get();
    }

    public SimpleDoubleProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity.set(quantity);
    }

    public double getQuantityProp() {
        return quantityProp.get();
    }

    public SimpleDoubleProperty quantityPropProperty() {
        return quantityProp;
    }

    public void setQuantityProp(double quantityProp) {
        this.quantityProp.set(quantityProp);
    }

    public Number getValWithoutTax() {
        return valWithoutTax.getValue();
    }

    public NumberBinding valWithoutTaxProperty() {
        return valWithoutTax;
    }

    public Number getTaxVal() {
        return taxVal.getValue();
    }

    public NumberBinding taxValProperty() {
        return taxVal;
    }

    public Number getTotalVal() {
        return totalVal.getValue();
    }

    public NumberBinding totalValProperty() {
        return totalVal;
    }

    public String getServiceNameProp() {
        return serviceNameProp.get();
    }

    public SimpleStringProperty serviceNamePropProperty() {
        return serviceNameProp;
    }

    public void setServiceNameProp(String serviceNameProp) {
        this.serviceNameProp.set(serviceNameProp);
    }

    public String getSymbolProp() {
        return symbolProp.get();
    }

    public SimpleStringProperty symbolPropProperty() {
        return symbolProp;
    }

    public void setSymbolProp(String symbolProp) {
        this.symbolProp.set(symbolProp);
    }

    public String getUnitProp() {
        return unitProp.get();
    }

    public SimpleStringProperty unitPropProperty() {
        return unitProp;
    }

    public void setUnitProp(String unitProp) {
        this.unitProp.set(unitProp);
    }

    public double getNetUnitPriceProp() {
        return netUnitPriceProp.get();
    }

    public SimpleDoubleProperty netUnitPricePropProperty() {
        return netUnitPriceProp;
    }

    public void setNetUnitPriceProp(double netUnitPriceProp) {
        this.netUnitPriceProp.set(netUnitPriceProp);
    }

    public int getVatProp() {
        return vatProp.get();
    }

    public SimpleIntegerProperty vatPropProperty() {
        return vatProp;
    }

    public void setVatProp(int vatProp) {
        this.vatProp.set(vatProp);
    }

    public String getLastNameProp() {
        return lastNameProp.get();
    }

    public SimpleStringProperty lastNamePropProperty() {
        return lastNameProp;
    }

    public void setLastNameProp(String lastNameProp) {
        this.lastNameProp.set(lastNameProp);
    }

    public String getFirstNameProp() {
        return firstNameProp.get();
    }

    public SimpleStringProperty firstNamePropProperty() {
        return firstNameProp;
    }

    public void setFirstNameProp(String firstNameProp) {
        this.firstNameProp.set(firstNameProp);
    }

    public String getCompanyNameProp() {
        return companyNameProp.get();
    }

    public SimpleStringProperty companyNamePropProperty() {
        return companyNameProp;
    }

    public void setCompanyNameProp(String companyNameProp) {
        this.companyNameProp.set(companyNameProp);
    }

    public String getTaxIdProp() {
        return taxIdProp.get();
    }

    public SimpleStringProperty taxIdPropProperty() {
        return taxIdProp;
    }

    public void setTaxIdProp(String taxIdProp) {
        this.taxIdProp.set(taxIdProp);
    }

    public String getAddressProp() {
        return addressProp.get();
    }

    public SimpleStringProperty addressPropProperty() {
        return addressProp;
    }

    public void setAddressProp(String addressProp) {
        this.addressProp.set(addressProp);
    }

    public String getPostalCodeProp() {
        return postalCodeProp.get();
    }

    public SimpleStringProperty postalCodePropProperty() {
        return postalCodeProp;
    }

    public void setPostalCodeProp(String postalCodeProp) {
        this.postalCodeProp.set(postalCodeProp);
    }

    public String getCityProp() {
        return cityProp.get();
    }

    public SimpleStringProperty cityPropProperty() {
        return cityProp;
    }

    public void setCityProp(String cityProp) {
        this.cityProp.set(cityProp);
    }

    public String getAliasProp() {
        return aliasProp.get();
    }

    public SimpleStringProperty aliasPropProperty() {
        return aliasProp;
    }

    public void setAliasProp(String aliasProp) {
        this.aliasProp.set(aliasProp);
    }

    public ObservableList<BoughtServices> getBoughtServicesList() {
        return boughtServicesList;
    }

    public void setBoughtServicesList(ObservableList<BoughtServices> boughtServicesList) {
        this.boughtServicesList = boughtServicesList;
    }

    public ObservableList<ServiceEntity> getServicesList() {
        return servicesList;
    }

    public void setServicesList(ObservableList<ServiceEntity> servicesList) {
        this.servicesList = servicesList;
    }
}
