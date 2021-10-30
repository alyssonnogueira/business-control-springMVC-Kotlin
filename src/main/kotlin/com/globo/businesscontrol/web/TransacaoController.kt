package com.globo.businesscontrol.web

import com.globo.businesscontrol.crosscutting.DespesaDTO
import com.globo.businesscontrol.crosscutting.ReceitaDTO
import com.globo.businesscontrol.crosscutting.TransferenciaDTO
import com.globo.businesscontrol.servico.ITransacaoService
import com.sun.istack.NotNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/v1/transacoes")
class TransacaoController(val transacaoService: ITransacaoService) {

    @PostMapping("/despesa")
    fun criarTransacao(@RequestBody despesaDTO: DespesaDTO): ResponseEntity<Void> {
        transacaoService.criarTransferencia(despesaDTO)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PostMapping("/receita")
    fun criarTransacao(@RequestBody receitaDTO: ReceitaDTO): ResponseEntity<Void> {
        transacaoService.criarTransferencia(receitaDTO)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PostMapping("/transferencia")
    fun criarTransacao(@RequestBody transferenciaDTO: TransferenciaDTO): ResponseEntity<Void> {
        transacaoService.criarTransferencia(transferenciaDTO)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @DeleteMapping("/{idTransacao}")
    fun excluirTransacao(@PathVariable idTransacao: Long): ResponseEntity<Void> {
        transacaoService.excluirTransacao(idTransacao)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

}