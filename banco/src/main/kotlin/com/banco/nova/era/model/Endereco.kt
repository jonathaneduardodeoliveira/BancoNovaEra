package com.banco.nova.era.model

import jakarta.persistence.Embeddable

@Embeddable
data class Endereco(
    val cep: String = "",
    val logradouro: String = "",
    val bairro: String = "",
    val cidade: String = "",
    val uf: String = "",
    val numero: String = "",
    val complemento: String = ""
)
