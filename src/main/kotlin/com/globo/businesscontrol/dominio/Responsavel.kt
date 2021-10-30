package com.globo.businesscontrol.dominio

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.Where
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "RESPONSAVEL")
@Where(clause = "DATA_EXCLUSAO IS NULL")
@SQLDelete(sql = "UPDATE RESPONSAVEL SET DATA_EXCLUSAO=CURRENT_TIMESTAMP WHERE ID=?")
class Responsavel(
    nome: String,
    @CreatedDate
    @Column(name = "DATA_CRIACAO", nullable = false, updatable = false)
    val dataCriacao: LocalDateTime,
    @Column(name = "DATA_EXCLUSAO", nullable = true)
    var dataExclusao: LocalDateTime? = null
) {

    constructor(nome: String) : this(
        nome = nome,
        dataCriacao = LocalDateTime.now()
    )

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    var id: Long? = null
        private set

    @Column(name = "NOME", nullable = false)
    var nome: String = nome
        set(value) {
            field = value
            this.dataAtualizacao = LocalDateTime.now()
        }

    @UpdateTimestamp
    @Column(name = "DATA_ATUALIZACAO", nullable = false)
    var dataAtualizacao: LocalDateTime = LocalDateTime.now()
        private set
}