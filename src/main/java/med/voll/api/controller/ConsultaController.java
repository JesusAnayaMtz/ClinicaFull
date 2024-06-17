package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaDeConsultaService;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosDetalleConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultas")
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


}
