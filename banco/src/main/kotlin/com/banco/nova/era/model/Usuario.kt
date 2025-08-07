package com.banco.nova.era.model

import jakarta.persistence.*
import java.io.Serializable

@Entity
data class Usuario(
    @Id
    val cpf: String = "",

    val nome: String = "",

    @Column(unique = true)
    val conta: Int = 0,

    val senha: String = "",

    val email: String = "",

    val telefone: String = "",

    @Embedded
    val endereco: Endereco = Endereco(),

    var valor: Double = 0.0
) : Serializable
