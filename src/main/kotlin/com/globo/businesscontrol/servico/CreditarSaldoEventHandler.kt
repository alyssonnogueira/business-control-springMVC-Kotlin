package com.globo.businesscontrol.servico

import com.globo.businesscontrol.dominio.Conta
import com.globo.businesscontrol.dominio.eventos.CreditarSaldoEvent
import com.globo.businesscontrol.infra.IContaRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class CreditarSaldoEventHandler(val contaRepository: IContaRepository) {

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun processar(event: CreditarSaldoEvent) {
        val conta: Conta = event.conta
        conta.creditarSaldo(event.valor)
        contaRepository.save(conta)
    }
}