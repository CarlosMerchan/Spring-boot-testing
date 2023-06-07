package org.accmm.test.springboot.app.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.accmm.test.springboot.app.Datos;
import org.accmm.test.springboot.app.models.TransaccionDTO;
import org.accmm.test.springboot.app.services.CuentaService;
import org.h2.command.dml.MergeUsing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CuentaController.class)
class CuentaControllerTest {

    @Autowired
    private MockMvc mvc;//Implementacion de mockito para hacer pruebas a controladores.

    @MockBean
    private CuentaService cuentaService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper(); //objeto incluido en spring para pasar objetos a json y viceversa
    }

    @Test
    void cuentaDetalle() throws Exception {
        //Given
        when(cuentaService.findById(1L)).thenReturn(Datos.crearCuenta001().orElseThrow());
        //When
        //se realiza la peticion a traves del mvc , se pasa la url y se el expect es la respuesta esperado, en este caso el status ok
        mvc.perform(get("/api/cuentas/cuentaDetalles/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.persona").value("Carlos"))//el . significa la raiz del json
                .andExpect(jsonPath("$.saldo").value("1000"));

        verify(cuentaService).findById(1L);
    }

    @Test
    void testTransferir() throws Exception{
        TransaccionDTO dto  = new TransaccionDTO();
        dto.setCuentaOrigen(1L);
        dto.setCuentaDestion(2L);
        dto.setBancoId(1L);
        dto.setMonto(new BigDecimal("500"));

         System.out.println(objectMapper.writeValueAsString(dto));

        Map<String,Object> response  = new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status", "OK");
        response.put("mensaje", "Transferencia realizada con exito");
        response.put("transaccion",dto);

        System.out.println(objectMapper.writeValueAsString(response));


        mvc.perform(post("/api/cuentas/transferir").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
                ).andExpect(status().isOk()
                ).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.mensaje").value("Transferencia realizada con exito"))
                .andExpect(jsonPath("$.transaccion.cuentaOrigen").value(1L))
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }
}