package com.banco.nova.era.service

import com.banco.nova.era.dto.TransferDTO
import com.banco.nova.era.dto.TransferLogStatus
import com.banco.nova.era.model.TransferLog
import com.banco.nova.era.repository.TransferLogRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TransferLogService(
    private val transferLogRepository: TransferLogRepository
) {
    @Transactional
    fun logTransfer(
        payload: TransferDTO,
        status: TransferLogStatus
    ) {
        val log = TransferLog(
            origem = payload.origem,
            destino = payload.destino,
            valor = payload.valor,
            status = status.status
        )
        transferLogRepository.save(log)
    }
}