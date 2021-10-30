package com.globo.businesscontrol.infra

import com.globo.businesscontrol.dominio.Conta
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import javax.persistence.EntityNotFoundException

@Repository
class ContaConsultaRepository(private val contaJpaRepository: ContaJpaRepository) : IContaConsultaRepository {

    override fun obterConta(idConta: Long): Conta {
        return contaJpaRepository.findByIdOrNull(idConta) ?: throw EntityNotFoundException("Conta não encontrada")
    }

    override fun obterTodasAsContas(): List<Conta> = contaJpaRepository.findAll()

    interface ContaJpaRepository : JpaRepository<Conta, Long>
}