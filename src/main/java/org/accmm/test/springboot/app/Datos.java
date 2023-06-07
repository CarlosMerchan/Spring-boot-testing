package org.accmm.test.springboot.app;

import org.accmm.test.springboot.app.models.Banco;
import org.accmm.test.springboot.app.models.Cuenta;

import java.math.BigDecimal;
import java.util.Optional;

public class Datos {

    //public static final Cuenta CUENTA_001 = new Cuenta(1L,"Carlos",new BigDecimal("1000"));
    //public static final Cuenta CUENTA_002 = new Cuenta(2L,"Alejandra",new BigDecimal("2000"));
    //public static final Banco BANCO = new Banco(1L,"Banco Mundial",0);
    /*SE COMENTARON YA QUE AL HACER FINAL ESTATICA AL CAMBIAR EL MONTO EN UN TEST ANTERIOR SE VE AFECTADO EL OBJETO EN LA OTRA VALIDACION
    * YA QUE ESTOS SON UNA SOLA REFERENCIA POR ESO LO MEJOR ES CREAR UNA NUEVA INSTANCIA CON UN METODO*/

    public static Optional<Cuenta> crearCuenta001(){
        return Optional.of(new Cuenta(1L,"Carlos",new BigDecimal("1000")));
    }

    public static  Optional<Cuenta>  crearCuenta002(){
        return Optional.of(new Cuenta(2L,"Alejandra",new BigDecimal("2000")));
    }

    public static Optional<Banco>  crearBanco001(){
        return Optional.of(new Banco(1L,"Banco Mundial",0));
    }
}
