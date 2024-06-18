package med.voll.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultaService agendaDeConsultaService;

    @PostMapping("/registrar")
    @Transactional
    public ResponseEntity agendarCconsulta(@RequestBody @Valid DatosAgendarConsulta datosAgendarConsulta){
        var response = agendaDeConsultaService.agendar(datosAgendarConsulta);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listar")
    public ResponseEntity listarConsultas(){
        return ResponseEntity.ok(agendaDeConsultaService.listarConsultas());
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity eliminarConsulta(@PathVariable Long id){
        agendaDeConsultaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/cancelar")
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DatosCancelarConsulta datosCancelarConsulta){
        agendaDeConsultaService.cancelar(datosCancelarConsulta);
        return ResponseEntity.noContent().build();
    }




}
