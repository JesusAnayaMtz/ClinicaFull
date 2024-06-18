package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosCancelarConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioCancelamiento implements ValidarCancelamientoConsulta{

    private ConsultaRepository consultaRepository;

    @Override
    public void validar(DatosCancelarConsulta datosCancelarConsulta) {
        var consulta = consultaRepository.getReferenceById(datosCancelarConsulta.idConsulta());
        var ahora = LocalDateTime.now();
        var diferenciaEnHoras = Duration.between(ahora, consulta.getFecha()).toHours();

        if (diferenciaEnHoras < 24){
            throw new ValidationException("Consulta solo puede ser cancelada con minimo de 24 hrs");
        }
    }
}
