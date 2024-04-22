package br.com.panmar.rpgtable.controller;

import org.springframework.web.bind.annotation.RestController;

import br.com.panmar.rpgtable.service.TableService;
import br.com.panmar.rpgtable.table.Action;
import br.com.panmar.rpgtable.table.Master;
import br.com.panmar.rpgtable.table.Player;
import br.com.panmar.rpgtable.table.Table;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.MediaType;

@RestController
public class TableController {
	
	private final TableService tableService;
	
	@Autowired
	public TableController(TableService tableService) {
		this.tableService = tableService;
	}

	@GetMapping("/tables/master")
	@CrossOrigin("*")
	public String[] GetTables(@RequestParam String masterId){
		
		String[] tables = this.tableService.GetTablesByMasterId(masterId);
		System.out.println(tables);
		return tables;
	}
	
	@RequestMapping(value = "/events/master", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
	@CrossOrigin("*")
	public SseEmitter GetMasterEvent(@RequestParam String id) {
		System.out.println("Master requested event for table: " + id);
		return this.tableService.GetMasterEventEmitter(id);
	}
	
	@RequestMapping(value = "/events/players", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
	@CrossOrigin("*")
	public SseEmitter GetPlayersEvents(@RequestParam String id) {
		return this.tableService.GetPlayerEventEmitter(id);
	}
	
	@PostMapping("/createtable")
	@CrossOrigin("*")
	public String CreateTable(@RequestBody Master master) {
		System.out.println("Creating table for master: " + master.id);
		return this.tableService.CreateTable(master);
	}
	
	@PutMapping("/player/jointable")
	@CrossOrigin("*")
	public void JoinTableAsPlayer(@RequestParam String id, @RequestBody Player player) {		
		this.tableService.JoinTableAsPlayer(id, player);
	}
	
	@PutMapping("/master/jointable")
	@CrossOrigin("*")
	public void JoinTableAsMaster(@RequestParam String id, @RequestBody Master master) {		
		this.tableService.JoinTableAsMaster(id, master);
	}
	
	@PostMapping("/RequestAction")
	@CrossOrigin("*")
	public void RequestAction(@RequestParam String id, @RequestBody Action action) {
		this.tableService.RequestAction(id, action);
	}
}
