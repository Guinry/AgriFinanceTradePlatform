package com.qst.agrifinancetrade.service;

import com.qst.agrifinancetrade.entity.Address;

import java.util.List;

public interface AddressService {
    void add(Address address);

    List<Address> selectByOwnName();

    Address selectDefaultByOwnName();


    void defaultAddressInfoUpdate(Address address);

    void update(Address address);

    boolean delete(Integer id);
}
