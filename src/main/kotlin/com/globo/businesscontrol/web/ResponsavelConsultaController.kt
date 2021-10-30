package com.globo.businesscontrol.web

import com.globo.businesscontrol.dominio.Responsavel
import com.globo.businesscontrol.servico.IResponsavelConsultaService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/v1/responsaveis")
class ResponsavelConsultaController(val responsavelConsultaService: IResponsavelConsultaService) {

    @GetMapping
    fun obterTodosResponsaveis(): ResponseEntity<List<Responsavel>> = ResponseEntity.ok(
        responsavelConsultaService.obterTodosOsResponsaveis()
    )

    @GetMapping("/{idResponsavel}")
    fun obterResponsavel(@PathVariable idResponsavel: Long): ResponseEntity<Responsavel> = ResponseEntity.ok(
        responsavelConsultaService.obterResponsavel(idResponsavel)
    )

}