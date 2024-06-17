package med.voll.api.domain.consulta;

import med.voll.api.domain.consulta.validaciones.ValidacionDeConsultas;
import med.voll.api.domain.medico.DatosListadoMedico;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultaService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    //implementamos la interface
    @Autowired
    List<ValidacionDeConsultas> validadores;

    public DatosDetalleConsulta agendar(DatosAgendarConsulta datosAgendar){

        if (!pacienteRepository.findById(datosAgendar.idPaciente()).isPresent()){
            throw new ValidacionDeIntegridad("Este id para el paciente no fue encontrado");
        }

        if (datosAgendar.idMedico()!=null && !medicoRepository.existsById(datosAgendar.idMedico())){
            throw new ValidacionDeIntegridad("Este id para el medico no fue encontrado");
        }

        //validaciones
        validadores.forEach(v-> v.validar(datosAgendar));

        var paciente = pacienteRepository.findById(datosAgendar.idPaciente()).get();
        var medico = seleccionarMedico(datosAgendar);

        if (medico == null){
            throw new ValidacionDeIntegridad("No existen medicos disponibles para este horario y especialidad");
        }

        var consulta = new Consulta(null, paciente, medico, datosAgendar.fecha());
        consultaRepository.save(consulta);

        return new DatosDetalleConsulta(consulta);
    }

    private Medico seleccionarMedico(DatosAgendarConsulta datosAgendar) {
        if (datosAgendar.idMedico()!=null){
            return medicoRepository.getReferenceById(datosAgendar.idMedico());
        }
        if (datosAgendar.especialidad()==null){
            throw new ValidacionDeIntegridad("Debe seleccionarse una especilidad para el medico");
        }

        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datosAgendar.especialidad(), datosAgendar.fecha());
    }
    
    public List<DatosDetalleConsulta> listarConsultas(){
        List<Consulta> consultas = consultaRepository.findAll();
        List<DatosDetalleConsulta> datosDetalles = consultas.stream().map(
                consulta -> new DatosDetalleConsulta(consulta.getId(),consulta.getPaciente().getId(), consulta.getMedico().getId(), consulta.getFecha())
        ).toList();
        return datosDetalles;
    }

    public void eliminar(Long id){
        consultaRepository.deleteById(id);
    }


}
