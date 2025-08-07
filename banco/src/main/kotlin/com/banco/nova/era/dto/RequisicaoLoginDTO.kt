package com.banco.nova.era.dto

data class RequisicaoLoginDTO(
    val cpf: String? = null,
    val conta: Int? = null,
    val senha: String = ""
)
