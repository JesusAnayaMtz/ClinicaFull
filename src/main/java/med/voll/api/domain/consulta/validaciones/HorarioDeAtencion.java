package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class HorarioDeAtencion implements ValidacionDeConsultas{

    public void validar(DatosAgendarConsulta datosAgendarConsulta){
        //validamos en caso de que sea domingo
        var domingo = DayOfWeek.SUNDAY.equals(datosAgendarConsulta.fecha().getDayOfWeek());
        var horaApertura= datosAgendarConsulta.fecha().getHour()<7;
        var horaCierre = datosAgendarConsulta.fecha().getHour()>19;

        if (domingo || horaApertura || horaCierre){
            throw new ValidationException("El horario de atencion es de lunes a sabado en horario de 7 a 19 hrs");
        }
    }
}
