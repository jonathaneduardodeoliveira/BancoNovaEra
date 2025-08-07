package com.banco.nova.era.model

import jakarta.persistence.*

@Entity
data class Usuario(
    @Id
    val cpf: String,

    val senha: String,

    val nome: String,

    var valor: Double,
)
