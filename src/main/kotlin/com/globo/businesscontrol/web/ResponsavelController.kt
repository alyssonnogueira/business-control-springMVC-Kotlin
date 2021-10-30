package com.globo.businesscontrol.web

import com.globo.businesscontrol.crosscutting.ResponsavelAtualizadoDTO
import com.globo.businesscontrol.crosscutting.ResponsavelDTO
import com.globo.businesscontrol.servico.IResponsavelService
import com.sun.istack.NotNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/v1/responsaveis")
class ResponsavelController(val responsavelService: IResponsavelService) {

    @PostMapping
    fun criarResponsavel(@RequestBody responsavelDTO: ResponsavelDTO): ResponseEntity<Void> {
        responsavelService.criarResponsavel(responsavelDTO)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PutMapping
    fun atualizarResponsavel(@RequestBody responsavelAtualizadoDTO: ResponsavelAtualizadoDTO): ResponseEntity<Void> {
        responsavelService.atualizarResponsavel(responsavelAtualizadoDTO)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @DeleteMapping("/{idResponsavel}")
    fun excluirResponsavel(@PathVariable @NotNull idResponsavel: Long): ResponseEntity<Void> {
        responsavelService.excluirResponsavel(idResponsavel)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

}