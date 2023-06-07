package org.accmm.test.springboot.app;

import org.accmm.test.springboot.app.Exceptions.DineroInsuficienteException;
import org.accmm.test.springboot.app.models.Banco;
import org.accmm.test.springboot.app.models.Cuenta;
import org.accmm.test.springboot.app.repositories.BancoRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.accmm.test.springboot.app.repositories.CuentaRepository;
import org.accmm.test.springboot.app.services.CuentaService;
import org.accmm.test.springboot.app.services.CuentaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
class SpringbootTestApplicationTests {

	@MockBean
	CuentaRepository cuentaRepository;
	@MockBean
	BancoRepository bancoRepository;
	@Autowired
	CuentaService service;

	@BeforeEach
	void setUp() {
//		cuentaRepository = mock(CuentaRepository.class);
//		bancoRepository = mock(BancoRepository.class);
//		service = new CuentaServiceImpl(bancoRepository,cuentaRepository);
	}

	@Test
	void contextLoads() {
		when(cuentaRepository.findById(1L)).thenReturn(Datos.crearCuenta001());
		when(cuentaRepository.findById(2L)).thenReturn(Datos.crearCuenta002());
		when(bancoRepository.findById(1L)).thenReturn(Datos.crearBanco001());

		BigDecimal saldoOrigen = service.revisarSaldo(1L);
		BigDecimal saldoDestino = service.revisarSaldo(2L);
		assertEquals("1000",saldoOrigen.toPlainString());
		assertEquals("2000",saldoDestino.toPlainString());

		service.transferir(1L,2L,new BigDecimal("200"),1l);
		assertEquals("800",service.revisarSaldo(1l).toPlainString());
		assertEquals("2200",service.revisarSaldo(2l).toPlainString());

		Banco banco = bancoRepository.findById(1L).orElseThrow();
		assertEquals(1,banco.getTotalTransferencia());

		verify(cuentaRepository,times(3)).findById(1L);
		verify(cuentaRepository,times(3)).findById(2L);
		verify(cuentaRepository,times(2)).save(any(Cuenta.class));
		verify(bancoRepository,atLeastOnce()).findById(1L);
		verify(bancoRepository).save(any(Banco.class));
		verify(cuentaRepository,never()).findAll();
	}

	@Test
	@DisplayName("Manejo de excepciones")
	void contextLoads2() {
		when(cuentaRepository.findById(1L)).thenReturn(Datos.crearCuenta001());
		when(cuentaRepository.findById(2L)).thenReturn(Datos.crearCuenta002());
		when(bancoRepository.findById(1L)).thenReturn(Datos.crearBanco001());

		BigDecimal saldoOrigen = service.revisarSaldo(1L);
		BigDecimal saldoDestino = service.revisarSaldo(2L);
		assertEquals("1000",saldoOrigen.toPlainString());
		assertEquals("2000",saldoDestino.toPlainString());

		assertThrows(DineroInsuficienteException.class , () ->{
			service.transferir(1L,2L,new BigDecimal("1200"),1l);
		});

		assertEquals("1000",service.revisarSaldo(1l).toPlainString());
		assertEquals("2000",service.revisarSaldo(2l).toPlainString());

		verify(cuentaRepository,atLeastOnce()).findById(1L);
		verify(cuentaRepository,atLeastOnce()).findById(2L);
		verify(cuentaRepository,never()).save(any(Cuenta.class));


	}
	@Test
	@DisplayName("assert same")
	void contextLoads3() {
		when(cuentaRepository.findById(1L)).thenReturn(Datos.crearCuenta001());

		Optional cuenta1 = cuentaRepository.findById(1L);
		Optional cuenta2 = cuentaRepository.findById(1L);

		assertSame(cuenta2.get(),cuenta1.get());

	}
}
