package org.accmm.test.springboot.app.models;

import java.math.BigDecimal;
import java.util.Objects;

public class TransaccionDTO {

    Long cuentaOrigen;
    Long cuentaDestion;
    BigDecimal monto;
    Long bancoId;

    public TransaccionDTO() {
    }

    public TransaccionDTO(Long cuentaOrigen, Long cuentaDestion, BigDecimal monto, Long bancoId) {
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestion = cuentaDestion;
        this.monto = monto;
        this.bancoId = bancoId;
    }

    public Long getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(Long cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public Long getCuentaDestion() {
        return cuentaDestion;
    }

    public void setCuentaDestion(Long cuentaDestion) {
        this.cuentaDestion = cuentaDestion;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Long getBancoId() {
        return this.bancoId;
    }

    public void setBancoId(Long bancoId) {
        this.bancoId = bancoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransaccionDTO that = (TransaccionDTO) o;
        return Objects.equals(cuentaOrigen, that.cuentaOrigen) && Objects.equals(cuentaDestion, that.cuentaDestion) && Objects.equals(monto, that.monto) && Objects.equals(bancoId, that.bancoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cuentaOrigen, cuentaDestion, monto, bancoId);
    }


    @Override
    public String toString() {
        return "TransaccionDTO{" +
                "cuentaOrigen=" + cuentaOrigen +
                ", cuentaDestion=" + cuentaDestion +
                ", monto=" + monto +
                ", bancoId=" + bancoId +
                '}';
    }
}
