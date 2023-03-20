package springclasses.professor;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springclasses.professor.dto.ProfessorRequestDto;
import springclasses.professor.dto.ProfessorResponseDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = {"/professor"})
public class ProfessorController {

    final private ProfessorService servico;

    public ProfessorController(ProfessorService servico) {
        this.servico = servico;
    }

    @PostMapping
    public ProfessorResponseDto criarProfessor(
        @Valid @RequestBody final ProfessorRequestDto request
        ) {
        return servico.gravarProfessor(request);
    }

    @GetMapping
    @SecurityRequirements
    public List<ProfessorResponseDto> listarProfessors(
        @RequestParam final Optional<String> prefixo
        ) {
        return servico.listarProfessors(prefixo);
    }

    @GetMapping("/{id}")
    public ProfessorResponseDto buscarProfessor(
        @PathVariable("id") final int id
    ) {
        return servico.buscarProfessor(id);
    }

    @SecurityRequirements(@SecurityRequirement(name = "JWT"))
    @PutMapping("/{id}")
    public ProfessorResponseDto atualizarProfessor(
        @PathVariable final int id,
        @RequestBody final ProfessorRequestDto request
    ) {
        return servico.atualizarProfessor(id, request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void apagarProfessor(
        @PathVariable final int id
    ) {
        servico.apagarProfessor(id);
    }
}
