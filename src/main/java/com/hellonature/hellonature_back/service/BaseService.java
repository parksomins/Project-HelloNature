package com.hellonature.hellonature_back.service;

import com.hellonature.hellonature_back.ifs.Crudinterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class BaseService<Req, Res, Entity> implements Crudinterface<Req, Res> {
}
