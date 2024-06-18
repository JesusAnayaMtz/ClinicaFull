package med.voll.api.domain.consulta;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosCancelarConsulta(
        @NotNull
        Long idConsulta,
        @NotNull
        @NotNull
        MotivoCancelamiento motivo
) {
}
