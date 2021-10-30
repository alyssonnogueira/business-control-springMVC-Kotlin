package com.globo.businesscontrol.infra

import com.globo.businesscontrol.dominio.Transacao
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ITransacaoRepository : JpaRepository<Transacao, Long>