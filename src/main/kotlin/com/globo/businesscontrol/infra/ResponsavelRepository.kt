package com.globo.businesscontrol.infra

import com.globo.businesscontrol.dominio.Responsavel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
class ResponsavelRepository(var responsavelJpaRepository: ResponsavelJpaRepository) : IResponsavelRepository {

    override fun salvarResponsavel(responsavel: Responsavel) {
        this.responsavelJpaRepository.save(responsavel)
    }

    override fun excluirResponsavel(idResponsavel: Long) {
        this.responsavelJpaRepository.deleteById(idResponsavel)
    }

    interface ResponsavelJpaRepository : JpaRepository<Responsavel, Long> {}
}