package com.hellonature.hellonature_back.ifs;

import com.hellonature.hellonature_back.model.network.Header;

public interface Crudinterface<Req, Res> {
    Header<Res> create(Header<Req> request);

    Header<Res> read(Long id);

    Header<Res> update(Header<Req> request);

    Header<Res> delete(Long id);
}