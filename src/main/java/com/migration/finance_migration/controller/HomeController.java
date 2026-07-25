package com.migration.finance_migration.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.migration.finance_migration.service.SheetMigrationStatusService;

import org.springframework.ui.Model;


@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

     private final SheetMigrationStatusService sheetMigrationStatusService;
    @GetMapping("/")
    public String migrationPage(Model model) {

        model.addAttribute("sheetMigrations", sheetMigrationStatusService.getSheetMigration());

        return "index";
    }
}