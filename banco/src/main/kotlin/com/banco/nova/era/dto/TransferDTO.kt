package com.banco.nova.era.dto

data class TransferDTO(
    val origem: String,
    val destino: String,
    val valor: Double,
)

enum class TransferLogStatus(val status: String) {
    SUCCESS("Sucesso"),
    FAILED("Não realizado")
}