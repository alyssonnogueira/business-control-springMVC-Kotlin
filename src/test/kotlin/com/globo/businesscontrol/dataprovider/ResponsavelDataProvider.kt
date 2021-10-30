package com.globo.businesscontrol.dataprovider

import com.globo.businesscontrol.dominio.Responsavel
import org.mockito.Mockito
import org.mockito.kotlin.whenever

object ResponsavelDataProvider {
    fun criarResponsavel(): Responsavel {
        val nome = "João"
        val responsavel = Mockito.spy(Responsavel(nome))
        whenever(responsavel.id).thenReturn(1L)
            return responsavel
    }

    fun criarResponsavel(nome: String): Responsavel {
        val responsavel = Mockito.spy(Responsavel(nome))
        whenever(responsavel.id).thenReturn(1L)
        return responsavel
    }
}