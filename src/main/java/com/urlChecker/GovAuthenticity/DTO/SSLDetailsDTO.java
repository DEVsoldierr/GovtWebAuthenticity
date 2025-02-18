package com.urlChecker.GovAuthenticity.DTO;



import lombok.Data;

@Data
public class SSLDetailsDTO {
    private String issuer;
    private String validFrom;
    private String validTo;
    private boolean isTrusted;

    public String getIssuer() {
        return issuer;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public String getValidTo() {
        return validTo;
    }

    public boolean isTrusted() {
        return isTrusted;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public void setValidTo(String validTo) {
        this.validTo = validTo;
    }

    public void setTrusted(boolean trusted) {
        isTrusted = trusted;
    }
}
