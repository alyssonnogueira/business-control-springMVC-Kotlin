package com.globo.businesscontrol.crosscutting

open class ResponsavelDTO(val nome: String)

class ResponsavelAtualizadoDTO(val id: Long, nome: String) : ResponsavelDTO(nome)