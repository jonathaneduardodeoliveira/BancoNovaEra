package com.banco.nova.era.controller

import com.banco.nova.era.service.LoginService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/login")
class LoginController(private val loginService: LoginService) {

    @PostMapping
    fun logar(@RequestParam cpf: String, @RequestParam senha: String): ResponseEntity<String> {
        val autenticado = loginService.autenticar(cpf, senha)
        return if (autenticado) {
            ResponseEntity.ok("Login realizado com sucesso!")
        } else {
            ResponseEntity.status(401).body("CPF ou senha inválidos.")
        }
    }
}
