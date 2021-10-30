package com.globo.businesscontrol.infra

import com.globo.businesscontrol.dominio.Responsavel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import javax.persistence.EntityNotFoundException

@Repository
class ResponsavelConsultaRepository(val responsavelJpaRepository: ResponsavelJpaRepository) :
    IResponsavelConsultaRepository {
    override fun obterResponsavel(idResponsavel: Long): Responsavel {
        return responsavelJpaRepository.findByIdOrNull(idResponsavel)
            ?: throw EntityNotFoundException("Responsavel não encontrado")
    }

    override fun obterTodosOsResponsaveis(): List<Responsavel> = responsavelJpaRepository.findAll()

    interface ResponsavelJpaRepository : JpaRepository<Responsavel, Long>
}