package com.example.nkserver.web;

//import com.example.nkserver.intercom.Command.CommandAdapter;
//import com.example.nkserver.intercom.Query.QueryAdapter;
//import com.example.nkserver.model.MapStocOpt;
//import com.example.nkserver.rabbitMqProducer.MessagePublisher;
//import com.example.nkserver.rabbitMqProducer.MyMessage;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;

import com.example.nkserver.intercom.Command.CommandAdapter;
import com.example.nkserver.intercom.Query.QueryAdapter;
import com.example.nkserver.model.MapStocOpt;
import com.example.nkserver.rabbitMqProducer.MessagePublisher;
import com.example.nkserver.rabbitMqProducer.MyMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/server")

public class NkserverController {

    private QueryAdapter queryAdapter;
    private CommandAdapter commandAdapter;
    private MessagePublisher messagePublisher;


    public NkserverController(QueryAdapter queryAdapter,
                              CommandAdapter commandAdapter,
                              MessagePublisher messagePublisher) {
        this.queryAdapter = queryAdapter;
        this.commandAdapter = commandAdapter;
        this.messagePublisher=messagePublisher;
    }


    @Tag(name = "Query-service",description = "query controller - get all External Products")
    @Operation(summary = "fetch data from external DB",description = "${springdoc.api-docs.query-serv.notes}")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Found the list",
    content = { @Content(mediaType = "application/json",
    schema = @Schema(implementation = List.class)) })})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/qallmap")
    public ResponseEntity<List<MapStocOpt>> queryAllMap(){
        try{
            List<MapStocOpt> response=queryAdapter.queryAllMap();
            log.info("Am reusit fetch din bd mapstoc",response.size());
            System.out.println("Am obtinut raspuns din query ");
            return ResponseEntity.ok(response);
        }catch (RuntimeException e){
            log.info("Eroare de fetch lista mapstoc!");
            throw e;
        }
    }



    @Tag(name = "Command-service")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/comallmap")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<MapStocOpt>> comAllMap(){
        try{
            List<MapStocOpt> response=commandAdapter.getComAll();
            log.info("Am reusit fetch din bd mapstoc lucru",response.size());
            return ResponseEntity.ok(response);
        }catch (RuntimeException e){
            log.info("Eroare de fetch lista mapstoc din serv lucru!");
            throw e;
        }
    }


    @Tag(name = "Command-service")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/mapbyid/{idProd}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<MapStocOpt> queryByIdProd(@PathVariable String idProd){
        try{
            MapStocOpt mp=queryAdapter.queryByIdProd(idProd);
            return ResponseEntity.ok(mp);
        }catch (RuntimeException e){
            throw e;
        }
    }


    @Tag(name = "Command-service")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/addmap")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<MapStocOpt> addMapStocOpt(@RequestBody MapStocOpt mp){
        try{

            MapStocOpt mpp=commandAdapter.addMapStoc(mp);
            log.info("_ADDED:"+mp.toString());
            return ResponseEntity.ok(mpp);

        }catch (RuntimeException e){
            throw e;
        }
    }


    @Tag(name = "Command-service")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/addbulk")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Boolean> addBulkMapStocOpt(@RequestBody List<MapStocOpt> mp){
        MyMessage myMessage=new MyMessage();
        myMessage.setPriority(1);
        myMessage.setContent(mp);
        myMessage.setMessage("UPD_PROD_BULK");
        boolean status=messagePublisher.sendMessageListMapStocOpt(myMessage);
        if(status==true){
            return ResponseEntity.ok(true);

        }else{
            return ResponseEntity.badRequest().body(false);
        }

    }


    @Tag(name = "Command-service")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/del/{idP}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Boolean> delMapByID(@PathVariable String idP){
        MyMessage myMessage=new MyMessage();
        myMessage.setPriority(1);
        myMessage.setContent(idP);
        myMessage.setMessage("DEL_PROD_ID");
        log.info("DELPROD_ANTE");
        boolean status=messagePublisher.sendMessageString(myMessage);

        log.info("DELPROD_AFTER "+status);
        if(status==true){
//            return ResponseEntity.ok(commandAdapter.deleteMapStoc(idP));
            return ResponseEntity.ok(true);
        }else{
            return ResponseEntity.badRequest().body(false);
        }
    }

    @Operation(tags = "Command-service",summary = "update product with body content",
            description = "${}")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/upd")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")

    public ResponseEntity<MapStocOpt> updateMapStoc(@RequestBody MapStocOpt uMap){
        MyMessage myMessage=new MyMessage();
        myMessage.setPriority(1);
        myMessage.setContent(uMap);
        myMessage.setMessage("UPD_PROD");
        boolean status=messagePublisher.sendMessageMapStocOpt(myMessage);
        if(status==true){

            return ResponseEntity.ok(uMap);
        }else{
            return ResponseEntity.badRequest().body(null);
        }
    }

//    @CrossOrigin(origins = {"http://localhost:3000"})

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/direct")
    public ResponseEntity<List<MapStocOpt>> directqueryAllMap(){
        try{
            List<MapStocOpt> response=queryAdapter.queryAllMap();
            log.info("Am reusit fetch din bd mapstoc",response.size());
            return ResponseEntity.ok(response);
        }catch (RuntimeException e){
            log.info("Eroare de fetch lista mapstoc!");
            throw e;
        }
    }


}
