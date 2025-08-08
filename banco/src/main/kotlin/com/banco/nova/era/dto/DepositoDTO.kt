package com.banco.nova.era.dto

data class DepositoDTO(
    val nome: String = "",
    val cpf: String = "",
    val senha: String = "",
    val email: String = "",
    val telefone: String = "",
    val conta: String = "",
    val endereco: EnderecoDTO = EnderecoDTO(),
    val valor: Double = 0.0
)
