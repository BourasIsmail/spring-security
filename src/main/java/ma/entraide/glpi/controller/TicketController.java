package ma.entraide.glpi.controller;

import ma.entraide.glpi.entity.Ticket;
import ma.entraide.glpi.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = ticketService.getAllTickets();
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        Ticket ticket = ticketService.getTicketById(id);
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    @GetMapping("/byNomAuteur/{name}")
    public ResponseEntity<List<Ticket>> getTicketsByNomAuteur(@PathVariable String name) {
        List<Ticket> tickets = ticketService.getTicketByNomAuteur(name);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @GetMapping("/byEtat/{etat}")
    public ResponseEntity<List<Ticket>> getTicketsByEtat(@PathVariable Boolean etat) {
        List<Ticket> tickets = ticketService.getTicketByEtat(etat);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @GetMapping("/byUser/{user}")
    public ResponseEntity<List<Ticket>> getTicketsByUser(@PathVariable String user) {
        List<Ticket> tickets = ticketService.getTicketByUser(user);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTicket(@PathVariable Long id) {
        String message = ticketService.deleteTicket(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Ticket> saveTicket(@RequestBody Ticket ticket) {
        Ticket savedTicket = ticketService.saveTicket(ticket);
        return new ResponseEntity<>(savedTicket, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable Long id, @RequestBody Ticket newTicket) {
        Ticket updatedTicket = ticketService.updateTicket(id, newTicket);
        return new ResponseEntity<>(updatedTicket, HttpStatus.OK);
    }

    @GetMapping("/paginationAndSortByEtat/{offset}/{pageSize}/{etat}")
    public ResponseEntity<Page<Ticket>> findTicketsWithPaginationAndSortingByEtat(
            @PathVariable int offset, @PathVariable int pageSize, @PathVariable String etat) {
        Page<Ticket> tickets = ticketService.findUsersWithPaginationAndSortingByEtat(offset, pageSize, etat);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }
}
