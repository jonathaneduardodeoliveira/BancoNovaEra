package com.banco.nova.era.controller

import com.banco.nova.era.dto.RequisicaoLoginDTO
import com.banco.nova.era.dto.RespostaLoginDTO
import com.banco.nova.era.service.LoginService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/login")
@Tag(name = "Login", description = "Autenticação de usuários")
class LoginController(private val loginService: LoginService) {

    @PostMapping
    @Operation(summary = "Realizar login", description = "Autentica o usuário com CPF, conta e senha")
    fun logar(@RequestBody requisicao: RequisicaoLoginDTO): ResponseEntity<Any> {
        if (requisicao.senha.isBlank()) {
            val erro = mapOf("erro" to "Senha é obrigatória.")
            return ResponseEntity.badRequest().body(erro)
        }

        val usuario = loginService.autenticar(requisicao.cpf, requisicao.conta, requisicao.senha)

        return if (usuario != null) {
            val resposta = RespostaLoginDTO(
                mensagem = "Login realizado com sucesso!",
                conta = usuario.conta.toString()
            )
            ResponseEntity.ok(resposta)
        } else {
            val erro = mapOf("erro" to "CPF ou conta ou senha inválidos.")
            ResponseEntity.status(401).body(erro)
        }
    }
}
