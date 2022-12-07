package es.uc3m.tiw.controller;

import java.util.List;
//import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.RestController;

import es.uc3m.tiw.entity.*;
import es.uc3m.tiw.repository.TicketDAO;

@Controller
@CrossOrigin
public class TicketController {

	@Autowired
	TicketDAO daous;


	// ----------------- GET ALL TICKETS ----------------------
	@RequestMapping(value= "/tickets", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Ticket>> getTickets(){
		List<Ticket> ticketList;
		try {
			ticketList = daous.findAll();
			if (ticketList.isEmpty()){
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch (Exception e){
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(ticketList, HttpStatus.OK);
	}


	// ----------------- GET TICKET ----------------------
	@RequestMapping(value= "/tickets/{id}",  method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Ticket> getEventByName(@PathVariable Long id){
		//return daous.findByName(name);
		 Ticket tic = daous.findById(id).orElse(null);
		 return new ResponseEntity<>(tic, HttpStatus.OK);
	}


	// ----------------- SAVE TICKET ----------------------
	@PostMapping("/tickets")
	public ResponseEntity<Ticket> saveTicket(@RequestBody Ticket pticket){

		ResponseEntity<Ticket> response;
		Ticket newTicket = daous.save(pticket);
		if (newTicket == null) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			response = new ResponseEntity<>(newTicket, HttpStatus.CREATED);
		}
		return response;
	}

	// ----------------- UPDATE TICKET ----------------------
	@RequestMapping(value= "/tickets/{id}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<Ticket> updateTicket(@PathVariable @Validated Long id, @RequestBody Ticket pTicket) {
		ResponseEntity<Ticket> response;
		//Optional<User> us = daous.findById(id);
		Ticket tic = daous.findById(id).orElse(null);
		if (tic == null) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			tic.setIdevent(pTicket.getIdevent());
			tic.setIduser(pTicket.getIduser());
			tic.setPrice(pTicket.getPrice());
			tic.setType(pTicket.getType());
			response = new ResponseEntity<>(daous.save(tic), HttpStatus.OK);
		}
		return response;
	}

	// ----------------- DELETE EVENT ----------------------
	@RequestMapping(value= "/tickets/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<Ticket> deleteEvent(@PathVariable @Validated Long id) {
		ResponseEntity<Ticket> response;
		Ticket tic = daous.findById(id).orElse(null);
		if (tic == null) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			daous.delete(tic);
			response = new ResponseEntity<>(HttpStatus.OK);
		}
		return response;
	
	}
	
}
	

