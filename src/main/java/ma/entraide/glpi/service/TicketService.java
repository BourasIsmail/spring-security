package ma.entraide.glpi.service;


import ma.entraide.glpi.entity.Materiel;
import ma.entraide.glpi.entity.Ticket;
import ma.entraide.glpi.entity.UserInfo;
import ma.entraide.glpi.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> getAllTickets(){
        return ticketRepository.findAll();
    }

    public Ticket getTicketById(Long id){
        Optional<Ticket> ticketOp = ticketRepository.findById(id);
        Ticket ticket = null;
        if(ticketOp.isPresent()){
            ticket = ticketOp.get();
        }
        else{
            throw new ResourceNotFoundException("Ticket not found");
        }
        return ticket;
    }

    public List<Ticket> getTicketByNomAuteur(String name){
        return ticketRepository.getTicketByNomAuteur(name);
    }

    public List<Ticket> getTicketByEtat(Boolean etat){
        return ticketRepository.getTicketByEtat(etat);
    }

    public List<Ticket> getTicketByUser(String user){
        return ticketRepository.getTicketByUserName(user);
    }

    public String deleteTicket(Long id){
        ticketRepository.deleteById(id);
        return "ticket supprimé";
    }

    public Ticket saveTicket(Ticket ticket){
        return ticketRepository.save(ticket);
    }

    public Ticket updateTicket(Long id,Ticket newTicket){
        Ticket ticket = ticketRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Ticket not found"));
        ticket.setEtat(newTicket.getEtat());
        ticket.setNomAuteur(newTicket.getNomAuteur());
        ticket.setMessage(newTicket.getMessage());
        ticket.setUserInfo(newTicket.getUserInfo());

        return ticket;
    }

    public Page<Ticket> findUsersWithPaginationAndSortingByEtat(int offset, int pageSize, String etat){
        Page<Ticket> tickets = ticketRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(etat)));
        return  tickets;
    }

}
