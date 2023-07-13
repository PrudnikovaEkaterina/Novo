package ru.dom_novo.web.enumsWeb;

public enum UserPhoneEnum {
    _72342123122("72342123122"),
    _79764334241("79764334241"),
    _77000980987("77000980987"),
    _79262231000("79262231000"),
    _79520867229("79520867229"),
    _79085040794("79085040794");

    public final String phone;
    UserPhoneEnum(String phone){
        this.phone=phone;
    }

    public String toString() {
        return phone;
    }
}
