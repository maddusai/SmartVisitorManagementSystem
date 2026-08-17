package com.traved.visitor.service;

import com.traved.visitor.model.Visitor;

import java.util.List;

public interface VisitorService {
    Visitor registerVisitor(Visitor visitor);
    List<Visitor> getAllVisitors();
    Visitor getVisitorById(Long id);
}
