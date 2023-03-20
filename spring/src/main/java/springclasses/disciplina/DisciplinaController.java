package springclasses.disciplina;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springclasses.disciplina.dto.DisciplinaRequestDto;
import springclasses.disciplina.dto.DisciplinaResponseDto;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Disciplinas")
public class DisciplinaController {
    final private DisciplinaService servico;

    public DisciplinaController(DisciplinaService servico) {
        this.servico = servico;
    }

    @PostMapping
    public DisciplinaResponseDto criarDisciplina(
            @RequestBody final DisciplinaRequestDto request
    ) {
        return servico.gravarDisciplina(request);
    }

    @GetMapping
    public List<DisciplinaResponseDto> listarDisciplina(
            @RequestParam final Optional<String> prefixo
    ) {
        return servico.listarDisciplina(prefixo);
    }

    @GetMapping("/{id}")
    public DisciplinaResponseDto buscarDisciplina(
            @PathVariable("id") final int id
    ) {
        return servico.buscarDisciplina(id);
    }

    @PutMapping("/{id}")
    public DisciplinaResponseDto atualizarDisciplina(
            @PathVariable final int id,
            @RequestBody final DisciplinaRequestDto request
    ) {
        return servico.atualizarDisciplina(id, request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void apagarDisciplina(
            @PathVariable final int id
    ) {
        servico.apagarDisciplina(id);
    }

    @PatchMapping("/{id}/disciplina")
    public DisciplinaResponseDto adicionarDisciplina(
            @PathVariable final int id,
            @RequestBody final DisciplinaRequestDto request
    ) {
        return servico.adicionarDisciplina(id, request);
    }

}
