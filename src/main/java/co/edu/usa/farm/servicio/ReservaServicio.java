package co.edu.usa.farm.servicio;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/**
 * se importan las librerias
 */
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.usa.farm.Operacion.ContadorCliente;
import co.edu.usa.farm.Operacion.StatusReserva;
import co.edu.usa.farm.entidad.Reserva;
import co.edu.usa.farm.respositorio.ReservaRepositorio;
/**
 * Se utiliza la etiqueta Service, para inidacr que esta archivo se manegara la seguridad
 * del backend
 */
@Service
public class ReservaServicio {

    @Autowired
    private ReservaRepositorio Crudreserva;
/**
 * metodo que se encagara de obtener en una lista de todo lo alamacenado en enla base de datos
 * en este caso solicitara la informacion de reservation
 * @return
 */
    public List<Reserva> getAll(){
        return Crudreserva.getAll();
    }
    /**
     * se solicita la informacion de manera mas puntual indicando un valor especifico de reservation
     * @param reservationId
     * @return
     */

    public Optional<Reserva> getReservation(Long reservationId) {
        return Crudreserva.getReservation(reservationId);
    }

    /**
     * se guarda los valores en la base de datos validando algunas difereste parametros 
     * @param reservation
     * @return
     */

    public Reserva save(Reserva reservation){
        if(reservation.getIdReservation()==null){
            return Crudreserva.save(reservation);
        }else{
            Optional<Reserva> evt= Crudreserva.getReservation(reservation.getIdReservation());
            if(evt.isEmpty()){
                return Crudreserva.save(reservation);
            }else{
                return reservation;
            }
        }
    }

    /**
     * se realiza la actualizacion de los datos almacenados
     * @param reservation
     * @return
     */
    public Reserva update(Reserva reservation){
        if(reservation.getIdReservation()!=null){
            Optional<Reserva> evt= Crudreserva.getReservation(reservation.getIdReservation());
            if(!evt.isEmpty()){

                if(reservation.getStartDate()!=null){
                    evt.get().setStartDate(reservation.getStartDate());
                }
                if(reservation.getDevolutionDate()!=null){
                    evt.get().setDevolutionDate(reservation.getDevolutionDate());
                }
                if(reservation.getStatus()!=null){
                    evt.get().setStatus(reservation.getStatus());
                }
                Crudreserva.save(evt.get());
                return evt.get();
            }else{
                return reservation;
            }
        }else{
            return reservation;
        }
    }
    /**
     * se borran los datos 
     * @param reservationId
     * @return
     */

    public boolean delete(Long reservationId) {
        Boolean aBoolean = getReservation(reservationId).map(reservation -> {
            Crudreserva.delete(reservation);
            return true;
        }).orElse(false);
        return aBoolean;
    }

    public StatusReserva reporteStatusServicio (){
        List<Reserva>completed= Crudreserva.ReservacionStatusRepositorio("completed");
        List<Reserva>cancelled= Crudreserva.ReservacionStatusRepositorio("cancelled");
        
        return new StatusReserva(completed.size(), cancelled.size() );
    }

    public List<Reserva> reporteTiempoServicio (String datoA, String datoB){
        SimpleDateFormat parser = new SimpleDateFormat ("yyyy-MM-dd");
        
        Date datoUno = new Date();
        Date datoDos = new Date();
        
        try{
             datoUno = parser.parse(datoA);
             datoDos = parser.parse(datoB);
        }catch(ParseException evt){
            evt.printStackTrace();
        }if(datoUno.before(datoDos)){
            return Crudreserva.ReservacionTiempoRepositorio(datoUno, datoDos);
        }else{
            return new ArrayList<>();
        
        } 
    }

    public List<ContadorCliente> reporteClientesServicio(){
        return Crudreserva.getClientesRepositorio();
    }

}
