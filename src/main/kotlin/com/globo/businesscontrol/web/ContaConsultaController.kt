package com.globo.businesscontrol.web

import com.globo.businesscontrol.dominio.Conta
import com.globo.businesscontrol.servico.IContaConsultaService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/v1/contas")
class ContaConsultaController(val contaConsultaService: IContaConsultaService) {

    @GetMapping
    fun obterTodasAsContas(): ResponseEntity<List<Conta>> = ResponseEntity.ok(contaConsultaService.obterTodasAsContas())

    @GetMapping("/{idConta}")
    fun obterTodasAsContas(@PathVariable idConta: Long): ResponseEntity<Conta> =
        ResponseEntity.ok(contaConsultaService.obterConta(idConta))
}