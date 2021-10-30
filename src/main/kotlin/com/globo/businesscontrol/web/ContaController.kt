package com.globo.businesscontrol.web

import com.globo.businesscontrol.crosscutting.ContaAtualizadaDTO
import com.globo.businesscontrol.crosscutting.ContaDTO
import com.globo.businesscontrol.servico.IContaService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/v1/contas")
class ContaController(val contaService: IContaService) {

    @PostMapping
    fun criarConta(@RequestBody contaDTO: ContaDTO): ResponseEntity<Void> {
        contaService.criarConta(contaDTO)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PutMapping
    fun atualizarConta(@RequestBody contaAtualizadaDTO: ContaAtualizadaDTO): ResponseEntity<Void> {
        contaService.atualizarConta(contaAtualizadaDTO)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @DeleteMapping("/{idConta}")
    fun excluirConta(@PathVariable idConta: Long): ResponseEntity<Void> {
        contaService.excluirConta(idConta)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

}