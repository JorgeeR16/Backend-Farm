package co.edu.usa.farm.servicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.usa.farm.entidad.Finca;
import co.edu.usa.farm.respositorio.FincaRepositorio;

import java.util.Optional;


@Service
public class FincaServicio {
    @Autowired
    private FincaRepositorio Crudfinca;

    public List<Finca> getAll(){
        return Crudfinca.getAll();
    }
    
    public Optional<Finca> getFinca(Long idFinca){
        return  Crudfinca.getFinca(idFinca);
    }

    public Finca save(Finca finca){
        if (finca.getId()==null) {
            return Crudfinca.save(finca);
        } else {
            Optional<Finca> evt=Crudfinca.getFinca(finca.getId()); 
            if (evt.isEmpty()) {
                return Crudfinca.save(finca);
                
            } else {
                return finca;
            }     
        }
    }

    public Finca update(Finca farm){
        if(farm.getId()!=null){
            Optional<Finca> e=Crudfinca.getFinca(farm.getId());
            if(!e.isEmpty()){
                if(farm.getName()!=null){
                    e.get().setName(farm.getName());
                }
                if(farm.getAddress()!=null){
                    e.get().setAddress(farm.getAddress());
                }
                if(farm.getExtension()!=null){
                    e.get().setExtension(farm.getExtension());
                }
                if(farm.getDescription()!=null){
                    e.get().setDescription(farm.getDescription());
                }
                if(farm.getCategory()!=null){
                    e.get().setCategory(farm.getCategory());
                }
                Crudfinca.save(e.get());
                return e.get();
            }else{
                return farm;
            }
        }else{
            return farm;
        }
    }

    public boolean delete(Long farmId) {
        Boolean aBoolean = getFinca(farmId).map(farm -> {
            Crudfinca.delete(farm);
            return true;
        }).orElse(false);
        return aBoolean;
    }
}
