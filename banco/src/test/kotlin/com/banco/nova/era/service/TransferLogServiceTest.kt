package com.banco.nova.era.service

import com.banco.nova.era.dto.TransferDTO
import com.banco.nova.era.dto.TransferLogStatus
import com.banco.nova.era.model.TransferLog
import com.banco.nova.era.repository.TransferLogRepository
import org.junit.jupiter.api.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class TransferLogServiceTest {

    private val transferLogRepository = mock<TransferLogRepository>()
    private val transferLogService = TransferLogService(transferLogRepository)

    @Test
    fun `deve salvar log de transferencia com sucesso`() {
        val transferDTO = TransferDTO(
            origem = "Jonathan Morone",
            destino = "Gabriel",
            valor = 150.0
        )
        val status = TransferLogStatus.SUCCESS

        transferLogService.logTransfer(transferDTO, status)

        val captor = argumentCaptor<TransferLog>()
        verify(transferLogRepository).save(captor.capture())

        val logSalvo = captor.firstValue
        assert(logSalvo.origem == "Jonathan Morone")
        assert(logSalvo.destino == "Gabriel")
        assert(logSalvo.valor == 150.0)
        assert(logSalvo.status == "Sucesso")
    }
}
