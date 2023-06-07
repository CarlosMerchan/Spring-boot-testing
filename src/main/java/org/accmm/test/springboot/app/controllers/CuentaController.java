package org.accmm.test.springboot.app.controllers;

import org.accmm.test.springboot.app.models.Cuenta;
import org.accmm.test.springboot.app.models.TransaccionDTO;
import org.accmm.test.springboot.app.services.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestController
@CrossOrigin("localhost:4200")
@RequestMapping("/api/cuentas/")
public class CuentaController {

    @Autowired
    CuentaService cuentaService;

    @GetMapping("cuentaDetalles/{id}")
    @ResponseStatus(OK)
    public Cuenta cuentaDetalle(@PathVariable Long id){
        return cuentaService.findById(id);
    }

    @PostMapping("transferir")
    public ResponseEntity<?> transferir(@RequestBody TransaccionDTO datos){
        cuentaService.transferir(datos.getCuentaOrigen(), datos.getCuentaDestion(), datos.getMonto(),datos.getBancoId());
        Map<String,Object> response  = new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status", "OK");
        response.put("mensaje", "Transferencia realizada con exito");
        response.put("transaccion",datos);
        return ResponseEntity.status(OK).body(response);
    }
}
