package es.uc3m.tiw.controller;

import java.util.List;
//import java.util.Optional;

import es.uc3m.tiw.repository.EventDAO;
import es.uc3m.tiw.repository.UserDAO;
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
	TicketDAO daoti;
	@Autowired
	EventDAO daoev;
	@Autowired
	UserDAO daous;


	// ----------------- GET ALL TICKETS ----------------------
	@RequestMapping(value= "/tickets", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Ticket>> getTickets(){
		List<Ticket> ticketList;
		try {
			ticketList = daoti.findAll();
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
		 Ticket tic = daoti.findById(id).orElse(null);
		 if (tic == null){
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		 }
		 return new ResponseEntity<>(tic, HttpStatus.OK);
	}


	// ----------------- SAVE TICKET ----------------------
	@PostMapping("/tickets")
	public ResponseEntity<Ticket> saveTicket(@RequestBody Ticket pticket){
		ResponseEntity<Ticket> response;
		User us = daous.findById(pticket.getIduser()).orElse(null);
		Event ev = daoev.findById(pticket.getIdevent()).orElse(null);
		if (us == null || ev == null){
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return response;
		}
		Ticket newTicket = daoti.save(pticket);
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
		System.out.println(pTicket.getIduser());
		System.out.println(pTicket.getPrice());
		Ticket tic = daoti.findById(id).orElse(null);
		if (tic == null) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			if (pTicket.getIduser() != null){
				User us = daous.findById(pTicket.getIduser()).orElse(null);
				if (us == null){
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}else{
					tic.setIduser(pTicket.getIduser());
				}
			}
			if (pTicket.getIdevent() != null){
				Event ev = daoev.findById(pTicket.getIdevent()).orElse(null);
				if (ev == null){
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}else{
					tic.setIdevent(pTicket.getIdevent());
				}
			}
			if (!pTicket.getType().equals("")){
				tic.setType(pTicket.getType());
			}
			if (pTicket.getPrice() == 0){
				tic.setPrice(pTicket.getPrice());
			}
			response = new ResponseEntity<>(daoti.save(tic), HttpStatus.OK);
		}
		return response;
	}

	// ----------------- DELETE Ticket ----------------------
	@RequestMapping(value= "/tickets/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<Ticket> deleteEvent(@PathVariable @Validated Long id) {
		ResponseEntity<Ticket> response;
		Ticket tic = daoti.findById(id).orElse(null);
		if (tic == null) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			daoti.delete(tic);
			response = new ResponseEntity<>(HttpStatus.OK);
		}
		return response;
	
	}
	
}
	

