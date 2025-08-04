package com.banco.nova.era.repository

import com.banco.nova.era.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository

interface UsuarioRepository : JpaRepository<Usuario, String>
