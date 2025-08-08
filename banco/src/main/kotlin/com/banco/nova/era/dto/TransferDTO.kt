package com.banco.nova.era.dto

data class TransferDTO(
    var origem: String = "",
    var destino: String = "",
    var valor: Double = 0.0
)

enum class TransferLogStatus(val status: String) {
    SUCCESS("Sucesso"),
    FAILED("Não realizado")
}
