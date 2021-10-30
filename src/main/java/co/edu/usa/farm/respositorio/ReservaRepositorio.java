package co.edu.usa.farm.respositorio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import co.edu.usa.farm.Operacion.ContadorCliente;
import co.edu.usa.farm.entidad.Cliente;
import co.edu.usa.farm.entidad.Reserva;
import co.edu.usa.farm.interfaz.ReservaCrudRepositorio;

@Repository
public class ReservaRepositorio {
    
    @Autowired
    private  ReservaCrudRepositorio reservaRepoitorio;

    public List<Reserva> getAll(){
        return (List<Reserva>) reservaRepoitorio.findAll();
    }
    public Optional<Reserva> getReservation(Long id){
        return reservaRepoitorio.findById(id);
    }
    public Reserva save(Reserva reservation){
        return reservaRepoitorio.save(reservation);
    }

    public void delete(Reserva reservation){
        reservaRepoitorio.delete(reservation);
    }

    
    public List<Reserva> ReservacionStatusRepositorio (String status){
        return reservaRepoitorio.findAllByStatus(status);
    }

    public List<Reserva> ReservacionTiempoRepositorio (Date a, Date b){
        return reservaRepoitorio.findAllByStartDateAfterAndStartDateBefore(a, b);

    }

    public List<ContadorCliente> getClientesRepositorio(){
        List<ContadorCliente> res = new ArrayList<>();
        List<Object[]> report = reservaRepoitorio.countTotalReservationsByClient();
        for(int i=0; i<report.size(); i++){
            res.add(new ContadorCliente((Long)report.get(i)[1],(Cliente) report.get(i)[0]));
        }
        return res;
    }
}
