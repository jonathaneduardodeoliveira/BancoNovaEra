package com.banco.nova.era.controller

import com.banco.nova.era.dto.TransferDTO
import com.banco.nova.era.service.TransferService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/transferencia")
@Tag(name = "Transferência", description = "Operações de transferência entre contas")
class TransferController(private val transferService: TransferService) {

    @PostMapping
    @Operation(summary = "Realizar transferência", description = "Transfere valor de uma conta para outra")
    fun transferir(@RequestBody payload: TransferDTO): ResponseEntity<Any> {
        val permitido = transferService.verificar(payload)
        return if (permitido) {
            ResponseEntity.ok("Transação realizada com sucesso!")
        } else {
            ResponseEntity.status(401).body("Falha na transação!")
        }
    }
}
