package com.banco.nova.era.repository

import com.banco.nova.era.model.TransferLog
import org.springframework.data.jpa.repository.JpaRepository

interface TransferLogRepository: JpaRepository<TransferLog, Long>