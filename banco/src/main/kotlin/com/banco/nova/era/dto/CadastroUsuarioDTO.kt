package com.banco.nova.era.dto

data class CadastroUsuarioDTO(
    val nome: String = "",
    val cpf: String = "",
    val senha: String = "",
    val email: String = "",
    val telefone: String = "",
    val conta: Int = 0,
    val endereco: EnderecoDTO = EnderecoDTO()
)

data class EnderecoDTO(
    val cep: String = "",
    val logradouro: String = "",
    val bairro: String = "",
    val cidade: String = "",
    val uf: String = "",
    val numero: String = "",
    val complemento: String = ""
)
