package com.banco.nova.era.service

import com.banco.nova.era.dto.DepositoDTO
import com.banco.nova.era.dto.TransferDTO
import com.banco.nova.era.dto.TransferLogStatus
import com.banco.nova.era.repository.UsuarioRepository
import org.springframework.stereotype.Service

@Service
class DepositoService(private val usuarioRepository: UsuarioRepository, private val TransferLogService: TransferLogService) {
    fun verificar(payload: DepositoDTO): Boolean{
        val usuario = usuarioRepository.findById(payload.cpf).orElse(null)

        val transferPayload: TransferDTO = TransferDTO(origem = payload.cpf, destino = payload.cpf, valor = payload.valor)

        if((payload.valor > 0) && (usuario != null))  {
            usuario.valor += payload.valor
            return true

            TransferLogService.logTransfer(transferPayload, TransferLogStatus.SUCCESS)
        }

        TransferLogService.logTransfer(transferPayload, TransferLogStatus.FAILED)

        return false
    }

}