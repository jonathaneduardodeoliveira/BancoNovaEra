package com.banco.nova.era.controller

import com.banco.nova.era.dto.DepositoDTO
import com.banco.nova.era.service.DepositoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/deposito")
class DepositoController(private val DepositoService: DepositoService) {
    @PostMapping
    fun depositar (@RequestBody payload: DepositoDTO): ResponseEntity<Any>{
        val permitido: Boolean = DepositoService.verificar(payload)
        return if (permitido) {
            ResponseEntity.ok("Deposito realizado com sucesso!")
        } else {
            ResponseEntity.status(401).body("Quantia inválida ou Usuário não encontrado!")
        }
    }
}