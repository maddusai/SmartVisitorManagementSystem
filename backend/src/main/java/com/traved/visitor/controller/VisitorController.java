package com.traved.visitor.controller;

import com.traved.visitor.model.Visitor;
import com.traved.visitor.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visitors")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VisitorController {

    private final VisitorService visitorService;

    // Register a new visitor
    @PostMapping
    public Visitor registerVisitor(@RequestBody Visitor visitor) {
        return visitorService.registerVisitor(visitor);
    }

    // Get all visitors
    @GetMapping
    public List<Visitor> getAllVisitors() {
        return visitorService.getAllVisitors();
    }

    // Get visitor by ID
    @GetMapping("/{id}")
    public Visitor getVisitorById(@PathVariable Long id) {
        return visitorService.getVisitorById(id);
    }
}
