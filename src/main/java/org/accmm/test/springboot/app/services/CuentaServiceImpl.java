package org.accmm.test.springboot.app.services;

import org.accmm.test.springboot.app.models.Banco;
import org.accmm.test.springboot.app.models.Cuenta;
import org.accmm.test.springboot.app.repositories.BancoRepository;
import org.accmm.test.springboot.app.repositories.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CuentaServiceImpl implements CuentaService{

    private BancoRepository bancoRepository;
    private CuentaRepository cuentaRepository;
    @Autowired
    public CuentaServiceImpl(BancoRepository bancoRepository, CuentaRepository cuentaRepository) {
        this.bancoRepository = bancoRepository;
        this.cuentaRepository = cuentaRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Cuenta findById(Long id) {
        return cuentaRepository.findById(id).orElseThrow();
    }

    @Transactional(readOnly = true)
    @Override
    public int revisarTotalTransferencias(Long bancoId) {
        Banco banco = bancoRepository.findById(bancoId).orElseThrow();
        return banco.getTotalTransferencia();
    }

    @Transactional(readOnly = true)
    @Override
    public BigDecimal revisarSaldo(Long cuentaId) {
        Cuenta cuenta  = cuentaRepository.findById(cuentaId).orElseThrow();
        return cuenta.getSaldo();
    }

    @Transactional
    @Override
    public void transferir(Long numCuentaOrigen, Long numCuentaDestino, BigDecimal monto,Long bancoId) {
        Cuenta cuentaOrigen = cuentaRepository.findById(numCuentaOrigen).orElseThrow();
        Cuenta cuentaDestino = cuentaRepository.findById(numCuentaDestino).orElseThrow();
        cuentaOrigen.debito(monto);
        cuentaDestino.credito(monto);
        cuentaRepository.save(cuentaOrigen);
        cuentaRepository.save(cuentaDestino);

        Banco banco = bancoRepository.findById(bancoId).orElseThrow();
        int totalTransferencias = banco.getTotalTransferencia();
        banco.setTotalTransferencia(++totalTransferencias);
        bancoRepository.save(banco);

    }
}
