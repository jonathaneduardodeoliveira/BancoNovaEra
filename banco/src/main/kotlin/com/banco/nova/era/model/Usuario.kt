package com.banco.nova.era.model

import jakarta.persistence.*
import java.io.Serializable

@Entity
data class Usuario(
    @Id
    var cpf: String = "",

    var nome: String = "",

    @Column(unique = true)
    var conta: Int = 0,

    var senha: String = "",

    var email: String = "",

    var telefone: String = "",

    @Embedded
    var endereco: Endereco = Endereco(),

    var valor: Double = 0.0
) : Serializable
