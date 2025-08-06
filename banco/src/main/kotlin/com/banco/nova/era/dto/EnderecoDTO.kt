package com.banco.nova.era.dto

data class EnderecoDTO(
    val cep: String,
    val logradouro: String,
    val bairro: String,
    val cidade: String,
    val uf: String,
    val numero: String,
    val complemento: String?
)

