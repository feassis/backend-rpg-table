package br.com.panmar.rpgtable.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import br.com.panmar.rpgtable.service.TableService;
import br.com.panmar.rpgtable.table.Action;
import br.com.panmar.rpgtable.table.Master;
import br.com.panmar.rpgtable.table.Player;
import br.com.panmar.rpgtable.table.PlayerPosRequest;

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
	
	@GetMapping("/events/master")
	@CrossOrigin("*")
	public SseEmitter SubscribeToMasterEvents(@RequestParam String id){
		return this.tableService.SubscribeToMaster(id);
	}
	
	@GetMapping("/events/players")
	@CrossOrigin("*")
	public SseEmitter SubscribeToPlayerEvents(@RequestParam String id){
		return this.tableService.SubscribeToPlayer(id);
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
		System.out.println(player.toString());
		this.tableService.JoinTableAsPlayer(id, player);
	}
	
	@PutMapping("/master/jointable")
	@CrossOrigin("*")
	public void JoinTableAsMaster(@RequestParam String id, @RequestBody Master master) {		
		this.tableService.JoinTableAsMaster(id, master);
	}

	@GetMapping("/teste")
	@CrossOrigin("*")
	public String notifySubscribers(@RequestParam String id) {
		System.out.println("notify");
		this.tableService.TestNotification(id);
		return "ok";
	}
	
	@PostMapping("/RequestAction")
	@CrossOrigin("*")
	public void RequestAction(@RequestParam String id, @RequestBody Action action) {
		this.tableService.RequestAction(id, action);
	}

	@PostMapping("/AproveAction")
	@CrossOrigin("*")
	public void AproveAction(@RequestParam String tableId,  @RequestBody Action action) {
		this.tableService.AproveAction(tableId, action.actionId);
	}

	@PostMapping("/RejectAction")
	@CrossOrigin("*")
	public void RejectAction(@RequestParam String tableId,  @RequestBody Action action) {
		this.tableService.RejectAction(tableId, action.actionId);
	}

	@PostMapping("/InitializeGrid")
	@CrossOrigin("*")
	public void InitializeGrid(@RequestParam String tableId, @RequestBody int[] size){
		this.tableService.InitializeGrid(tableId, size);
	}

	@PostMapping("/SetPlayerPos")
	@CrossOrigin("*")
	public void SetPlayerPos(@RequestParam String tableId, @RequestBody PlayerPosRequest playerPosRequest){
		this.tableService.SetPlayerPos(tableId, playerPosRequest);
	}
}
