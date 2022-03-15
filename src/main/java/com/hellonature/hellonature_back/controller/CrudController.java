package com.hellonature.hellonature_back.controller;

import com.hellonature.hellonature_back.ifs.Crudinterface;
import com.hellonature.hellonature_back.model.network.Header;
import com.hellonature.hellonature_back.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

public abstract class CrudController<Req, Res, Entity> implements Crudinterface<Req, Res> {

    @Override
    public Header<Res> create(@RequestBody Header<Req> request) {
        return null;
    }

    @Override
    public Header<Res> read(@PathVariable Long idx) {
        return null;
    }

    @Override
    public Header<Res> update(@RequestBody Header<Req> request) {
        return null;
    }

    @Override
    public Header<Res> delete(@PathVariable Long idx) {
        return null;
    }

}