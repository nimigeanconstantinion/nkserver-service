package com.example.nkserver.intercom.Command;

import com.example.nkserver.model.MapStocOpt;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "command-service",url = "http://"+"${param.command-service}"+":8081/api/v1/command")
public interface CommandClient {
   @GetMapping("/getallmap")
   ResponseEntity<List<MapStocOpt>> getAllMapStoc();


   @PostMapping("/add")
   ResponseEntity<MapStocOpt> addMapStoc(@RequestBody MapStocOpt mp);

   @DeleteMapping("/del/{idp}")
   ResponseEntity<Boolean> delMapStoc(@PathVariable String idp);

   @PostMapping("/update")
   ResponseEntity<MapStocOpt> updateMapStoc(@RequestBody MapStocOpt updMap);

   @PostMapping("/bulk")
   ResponseEntity<Boolean> addBulkMapStoc(@RequestBody List<MapStocOpt> addList);
}
