package med.voll.api.domain.consulta;

import jakarta.validation.ValidationException;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultaService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    public void agendar(DatosAgendarConsulta datosAgendar){

        if (pacienteRepository.findById(datosAgendar.idPaciente()).isPresent()){
            throw new ValidacionDeIntegridad("Este id para el paciente no fue encontrado");
        }

        if (datosAgendar.idMedico()!=null && medicoRepository.existsById(datosAgendar.idMedico())){
            throw new ValidacionDeIntegridad("Este id para el medico no fue encontrado");
        }

        var paciente = pacienteRepository.findById(datosAgendar.idPaciente()).get();
        var medico = seleccionarMedico(datosAgendar);

        var consulta = new Consulta(null, paciente, medico, datosAgendar.fecha());
        consultaRepository.save(consulta);
    }

    private Medico seleccionarMedico(DatosAgendarConsulta datosAgendar) {
        if (datosAgendar.idMedico()!=null){
            return medicoRepository.getReferenceById(datosAgendar.idMedico());
        }
        if (datosAgendar.especialidad()==null){
            throw new ValidacionDeIntegridad("Debe seleccionarse una especilidad para el medico");
        }

        return medicoRepository.seleccionarMedicoConEspecilidadEnFecha(datosAgendar.especialidad(), datosAgendar.fecha());
    }
}
