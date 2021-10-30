package co.edu.usa.farm.entidad;
/**
 * 
 * Se importan las librerias
 */

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**
 * se coloca la etiqueta Entity que nos indicara
 * que que este archivo es el modelo y tambiens e encarga de realziar el modelo en la base de datos
 */
@Entity 
/**
 * Se utiliza la etiqueta table para generar las tablas
 */
@Table(name="fincas")
public class Finca implements Serializable {
    /**
     * Etiqueta par indicar la llave de la tabla
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**
     * Se construyen las filas de las tablas a travez de los atirbutos declarados
     */
    private Long id;
    private String name;
    private String address;
    private Long extension;    
    private String description;

    /**
     * se realiza la realcion con el modelo category
     */
    @ManyToOne
    @JoinColumn(name="idCategoria")
    @JsonIgnoreProperties("farms")
    private Categoria category;
    /**
     * se realiza la realcion con el modelo message
     */

    @OneToMany(cascade = {CascadeType.PERSIST},mappedBy = "farm")
    @JsonIgnoreProperties({"farm", "client"})
    private List<Mensaje> messages;

    /**
     * se realiza la realcion con el modelo reservations
     */
    @OneToMany(cascade = {CascadeType.PERSIST},mappedBy = "farm")
    @JsonIgnoreProperties({"farm", "client"})
    private List<Reserva> reservations;

    /**
     * se generan los metodos accesores para los diferentes atributos de la tabla y su relaciones 
     */
                          
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getExtension() {
        return extension;
    }

    public void setExtension(Long extension) {
        this.extension = extension;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Categoria getCategory() {
        return category;
    }

    public void setCategory(Categoria category) {
        this.category = category;
    }

    public List<Mensaje> getMessages() {
        return messages;
    }

    public void setMessages(List<Mensaje> messages) {
        this.messages = messages;
    }

    public List<Reserva> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reserva> reservations) {
        this.reservations = reservations;
    }
    
}
