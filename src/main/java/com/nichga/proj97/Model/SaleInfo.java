package com.nichga.proj97.Model;

import com.fasterxml.jackson.annotation.*;

public class SaleInfo {
    private String country;
    private String saleability;
    private boolean isEbook;

    @JsonProperty("country")
    public String getCountry() { return country; }
    @JsonProperty("country")
    public void setCountry(String value) { this.country = value; }

    @JsonProperty("saleability")
    public String getSaleability() { return saleability; }
    @JsonProperty("saleability")
    public void setSaleability(String value) { this.saleability = value; }

    @JsonProperty("isEbook")
    public boolean getIsEbook() { return isEbook; }
    @JsonProperty("isEbook")
    public void setIsEbook(boolean value) { this.isEbook = value; }
}
