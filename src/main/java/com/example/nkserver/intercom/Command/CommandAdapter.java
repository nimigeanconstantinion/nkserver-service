package com.example.nkserver.intercom.Command;
//
//import com.example.nkserver.model.MapStocOpt;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;

import com.example.nkserver.model.MapStocOpt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandAdapter {
    private CommandClient commandClient;


    public CommandAdapter(CommandClient commandClient) {
        this.commandClient = commandClient;
    }

    public List<MapStocOpt> getComAll(){
        try {
            ResponseEntity<List<MapStocOpt>> resp=commandClient.getAllMapStoc();
            return resp.getBody();
        }catch (RuntimeException e){
            throw new RuntimeException("Nu am reusit preluarea listei din serverul de lucru!!");

        }
   }

    public MapStocOpt addMapStoc(MapStocOpt mp){
        ResponseEntity<MapStocOpt> response=commandClient.addMapStoc(mp);

        if(response.getStatusCode()== HttpStatus.OK){

            return response.getBody();
        }
        throw new RuntimeException("Nu am reusit adaugarea!!");
    }

    public boolean deleteMapStoc(String idP){
        try{
            return commandClient.delMapStoc(idP).getBody();

        }catch (RuntimeException e){
            return false;
        }
    }

    public MapStocOpt updMap(MapStocOpt uMap){
            ResponseEntity<MapStocOpt> ump=commandClient.updateMapStoc(uMap);
            if(ump.getStatusCode()==HttpStatus.OK){
                return ump.getBody();
            }
            throw new RuntimeException("Can't update product");
    }

    public boolean addBulkMapStoc(List<MapStocOpt> lmp){
        ResponseEntity<Boolean> response=commandClient.addBulkMapStoc(lmp);

        if(response.getStatusCode()== HttpStatus.OK){
            return response.getBody();
        }
        throw new RuntimeException("Nu am reusit adaugarea Bulk!!");
    }
}
