package com.banco.nova.era.controller

import com.banco.nova.era.dto.CadastroUsuarioDTO
import com.banco.nova.era.model.Usuario
import com.banco.nova.era.service.CadastroService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cadastro")
@CrossOrigin(origins = ["http://localhost:3000", "http://localhost:8081"])
@Tag(name = "Cadastro", description = "Cadastro de novos usuários")
class CadastroController(private val cadastroService: CadastroService) {

    @PostMapping
    @Operation(summary = "Cadastrar novo usuário", description = "Recebe os dados do usuário e realiza o cadastro")
    fun cadastrar(@RequestBody dto: CadastroUsuarioDTO): ResponseEntity<Usuario> {
        val usuario = cadastroService.cadastrar(dto)
        return ResponseEntity.ok(usuario)
    }
}
