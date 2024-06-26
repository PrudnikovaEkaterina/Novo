package ru.dom_novo.web.enumsWeb;

public enum UserPhoneEnum {
    _72342123122("72342123122"),
    _79764334241("79764334241"),
    _77000980987("77000980987"),
    _79262231000("79262231000"),
    _79520867229("79520867229"),
    _79085040794("79085040794"),
    _79994817999("79994817999"),
    _79293118880("79293118880"),
    _79672236496("79672236496"),
    _70003423423("70003423423"),
    _74734906753("74734906753"),
    _75442348704("75442348704"),
    _74869385646("74869385646");

    public final String phone;
    UserPhoneEnum(String phone){
        this.phone=phone;
    }

    public String toString() {
        return phone;
    }
}
