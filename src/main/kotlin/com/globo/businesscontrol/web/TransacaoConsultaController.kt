package com.globo.businesscontrol.web

import com.globo.businesscontrol.dominio.Transacao
import com.globo.businesscontrol.dominio.enumeradores.TipoTransacaoEnum
import com.globo.businesscontrol.servico.ITransacaoConsultaService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDate
import java.time.LocalDateTime

@Controller
@RequestMapping("/v1/transacoes")
class TransacaoConsultaController(val transacaoConsultaService: ITransacaoConsultaService) {

    @GetMapping
    fun obterTodasAsTransacoes(): ResponseEntity<List<Transacao>> = ResponseEntity.ok(
        transacaoConsultaService.obterTodasTransacoes()
    )

    @GetMapping("paginado")
    fun obterTodasAsTransacoesComFiltro(
        @RequestParam(required = false) descricaoCategoria: String?,
        @RequestParam(required = false) idsResponsaveis: List<Long>?,
        @RequestParam(required = false) idsContas: List<Long>?,
        @RequestParam(required = false) tiposTransacao: List<TipoTransacaoEnum>?,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(required = false) dataInicial: LocalDate?,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(required = false) dataFinal: LocalDate?,
        @RequestParam(required = false, defaultValue = "0") pagina: Int,
        @RequestParam(required = false, defaultValue = "20") limite: Int,
        @RequestParam(required = false, defaultValue = "DESC") ordem: Sort.Direction,
        @RequestParam(required = false, defaultValue = "data") colunaDeOrdenacao: String
    ): ResponseEntity<Page<Transacao>> {
        val dataInicialDateTime: LocalDateTime? = dataInicial?.atStartOfDay()
        val dataFinalDateTime: LocalDateTime? = dataFinal?.atStartOfDay()
        return ResponseEntity.ok(
            transacaoConsultaService.obterTodasTransacoes(
                descricaoCategoria,
                idsResponsaveis,
                idsContas,
                tiposTransacao,
                dataInicialDateTime,
                dataFinalDateTime,
                PageRequest.of(pagina, limite, ordem, colunaDeOrdenacao)
            )
        )
    }

    @GetMapping("/{idTransacao}")
    fun obterTransacao(@PathVariable idTransacao: Long): ResponseEntity<Transacao> = ResponseEntity.ok(
        transacaoConsultaService.obterTransacao(idTransacao)
    )
}