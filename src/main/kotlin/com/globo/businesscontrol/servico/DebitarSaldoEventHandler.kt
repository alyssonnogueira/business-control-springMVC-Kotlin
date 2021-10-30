package com.globo.businesscontrol.servico

import com.globo.businesscontrol.dominio.Conta
import com.globo.businesscontrol.dominio.eventos.DebitarSaldoEvent
import com.globo.businesscontrol.infra.IContaRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class DebitarSaldoEventHandler(val contaRepository: IContaRepository) {

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun processar(event: DebitarSaldoEvent) {
        val conta: Conta = event.conta
        conta.debitarSaldo(event.valor)
        contaRepository.save(conta)
    }
}