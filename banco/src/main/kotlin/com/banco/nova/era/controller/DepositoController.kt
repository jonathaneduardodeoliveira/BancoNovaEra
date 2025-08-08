package com.banco.nova.era.controller

import com.banco.nova.era.dto.DepositoDTO
import com.banco.nova.era.service.DepositoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/deposito")
@Tag(name = "Depósito", description = "Operações de depósito em conta")
class DepositoController(private val depositoService: DepositoService) {

    @PostMapping
    @Operation(summary = "Realizar depósito", description = "Deposita um valor na conta do usuário")
    fun depositar(@RequestBody payload: DepositoDTO): ResponseEntity<Any> {
        val permitido = depositoService.verificar(payload)
        return if (permitido) {
            ResponseEntity.ok("Depósito realizado com sucesso!")
        } else {
            ResponseEntity.status(401).body("Quantia inválida ou usuário não encontrado!")
        }
    }
}
