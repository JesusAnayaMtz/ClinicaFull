package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class HorarioDeAnticipacion implements ValidacionDeConsultas{

        public void validar(DatosAgendarConsulta datosAgendarConsulta){
            //validamos en caso de que la anticipacion no sea mayor a 30 min
            var horaActual = LocalDateTime.now();
            var horaConsulta = datosAgendarConsulta.fecha();
            //calculamos la diferencia entre una y otra que sea menor a 30 min
            var diferenciaConsultaHactual = Duration.between(horaActual, horaConsulta).toMinutes()<30;

            if (diferenciaConsultaHactual){
                throw new ValidationException("Las consultas deben programarse con mas de 30 min de anticipacion");
            }
        }
}
