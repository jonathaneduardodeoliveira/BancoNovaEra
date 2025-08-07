package com.banco.nova.era.service

import com.banco.nova.era.dto.TransferDTO
import com.banco.nova.era.dto.TransferLogStatus
import com.banco.nova.era.repository.UsuarioRepository
import org.springframework.stereotype.Service

@Service
class TransferService(private val usuarioRepository: UsuarioRepository, private val TransferLogService: TransferLogService) {
    fun verificar(payload: TransferDTO): Boolean{
        val userOrigem = usuarioRepository.findById(payload.origem).orElse(null)
        val userDestino = usuarioRepository.findById(payload.destino).orElse(null)

        if((payload.valor > 0) && (userOrigem != null) && (userDestino != null))  {
            userOrigem.valor -= payload.valor
            userDestino.valor += payload.valor
            TransferLogService.logTransfer(payload, TransferLogStatus.SUCCESS)
            return true
        }
        TransferLogService.logTransfer(payload, TransferLogStatus.FAILED)

        return false
    }

}