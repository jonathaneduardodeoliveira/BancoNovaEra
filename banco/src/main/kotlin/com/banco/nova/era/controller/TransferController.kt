package com.banco.nova.era.controller

import com.banco.nova.era.dto.TransferDTO
import com.banco.nova.era.service.TransferService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/transferencia")
class TransferController(private val TransferService: TransferService) {
    @PostMapping
    fun transferir(@RequestBody payload: TransferDTO): ResponseEntity<Any>{
        val permitido: Boolean = TransferService.verificar(payload)
        return if (permitido) {
            ResponseEntity.ok("Transação realizado com sucesso!")
        } else {
            ResponseEntity.status(401).body("Falha na Transação!")
        }
    }
}