package com.globo.businesscontrol.infra

import com.globo.businesscontrol.dominio.Conta
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IContaRepository : JpaRepository<Conta, Long>