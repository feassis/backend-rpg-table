package br.com.panmar.rpgtable.controller;

import org.springframework.web.bind.annotation.RestController;

import br.com.panmar.rpgtable.service.TableService;
import br.com.panmar.rpgtable.table.Master;
import br.com.panmar.rpgtable.table.Player;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class TableController {
	
	private final TableService tableService;
	
	@Autowired
	public TableController(TableService tableService) {
		this.tableService = tableService;
	}
	
	@PostMapping("/createtable")
	public String CreateTable(@RequestBody Master master) {
		return this.tableService.CreateTable(master);
	}
	
	@PutMapping("/player/jointable")
	public String JoinTable(@RequestParam String id, @RequestBody Player player) {		
		return this.tableService.JoinTable(id, player);
	}
}