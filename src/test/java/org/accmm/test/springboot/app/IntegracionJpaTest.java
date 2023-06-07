package org.accmm.test.springboot.app;

import org.accmm.test.springboot.app.models.Cuenta;
import org.accmm.test.springboot.app.repositories.CuentaRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@DataJpaTest
public class IntegracionJpaTest {
    @Autowired
    CuentaRepository cuentaRepository;

    @Test
    void testFindById() {

        Optional<Cuenta> cuenta = cuentaRepository.findById(1L);
        assertTrue(cuenta.isPresent());
        assertEquals("Carlos",cuenta.orElseThrow().getPersona());
        assertEquals(1000,cuenta.orElseThrow().getSaldo().intValue());

    }

    @Test
    void testFindByPersona() {

        Optional<Cuenta> cuenta = cuentaRepository.findByPersona("Alejandra");
        assertTrue(cuenta.isPresent());
        assertEquals("Alejandra",cuenta.orElseThrow().getPersona());
        assertEquals(2000,cuenta.orElseThrow().getSaldo().intValue());

    }

    @Test
    void testFindByPersonaError() {

        Optional<Cuenta> cuenta = cuentaRepository.findByPersona("nadie");
        assertFalse(cuenta.isPresent());
        assertThrows(NoSuchElementException.class,cuenta::orElseThrow);

    }

    @Test
    void testFindByAll() {

        List<Cuenta> cuentas = cuentaRepository.findAll();
        assertFalse(cuentas.isEmpty());
        assertEquals(2,cuentas.size());

    }

    @Test
    void testSave() {

       Cuenta cuenta = new Cuenta(null,"Junior",new BigDecimal("100"));
       cuentaRepository.save(cuenta);
        Optional<Cuenta> nuevaCuenta = cuentaRepository.findByPersona("Junior");
        assertTrue(nuevaCuenta.isPresent());
        assertEquals("Junior",nuevaCuenta.orElseThrow().getPersona());
        assertEquals(3L,nuevaCuenta.orElseThrow().getId());

    }

    @Test
    void testDelete() {

        Cuenta cuenta = new Cuenta(null,"Junior",new BigDecimal("100"));
        Cuenta cuentaAlmacenada = cuentaRepository.save(cuenta);
        Optional<Cuenta> nuevaCuenta = cuentaRepository.findByPersona("Junior");
        assertTrue(nuevaCuenta.isPresent());
        cuentaRepository.delete(cuentaAlmacenada);
        assertFalse(cuentaRepository.existsById(cuentaAlmacenada.getId()));

    }

    @Test
    void testUpdate() {

         Cuenta cuenta = cuentaRepository.findByPersona("Carlos").orElseThrow();
         cuenta.credito(new BigDecimal(500));
         cuentaRepository.save(cuenta);
         Cuenta cuentaActualizada = cuentaRepository.findById(1L).orElseThrow();
         assertEquals(1500,cuentaActualizada.getSaldo().intValue());


    }


}
