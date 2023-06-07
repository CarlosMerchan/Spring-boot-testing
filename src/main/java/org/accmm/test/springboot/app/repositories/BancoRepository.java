package org.accmm.test.springboot.app.repositories;

import org.accmm.test.springboot.app.models.Banco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BancoRepository  extends JpaRepository<Banco,Long> {

    //List<Banco> findAll();

//    Banco findById2(Long id);
//
//    void update (Banco banco);
}
