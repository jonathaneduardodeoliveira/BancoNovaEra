package com.banco.nova.era.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class TransferLog(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var origem: String = "",

    @Column(nullable = false)
    var destino: String = "",

    @Column(nullable = false)
    var valor: Double = 0.0,

    @Column(nullable = false)
    var timestamp: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var status: String = ""
)
