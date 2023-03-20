package springclasses.curso;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;
import springclasses.curso.dto.CursoRequestDto;
import springclasses.curso.dto.CursoResponseDto;
import springclasses.curso.persistance.Curso;
import springclasses.curso.persistance.CursoRepository;
import springclasses.disciplina.dto.DisciplinaRequestDto;
import springclasses.disciplina.persistance.Disciplina;
import springclasses.disciplina.persistance.DisciplinaRepository;

@RestController
@RequestMapping("/cursos")
public class CursoController {
    final private CursoService servico;

    public CursoController(CursoService servico) {
        this.servico = servico;
    }

    @PostMapping
    public CursoResponseDto criarCurso(
        @RequestBody final CursoRequestDto request
    ) {
        return servico.gravarCurso(request);
    }

    @GetMapping
    public List<CursoResponseDto> listarCurso(
        @RequestParam final Optional<String> prefixo
    ) {
        return servico.listarCurso(prefixo);
    }

    @GetMapping("/{id}")
    public CursoResponseDto buscarCurso(
        @PathVariable("id") final int id
    ) {
        return servico.buscarCurso(id);
    }

    @PutMapping("/{id}")
    public CursoResponseDto atualizarCurso(
        @PathVariable final int id,
        @RequestBody final CursoRequestDto request
    ) {
        return servico.atualizarCurso(id, request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void apagarCurso(
        @PathVariable final int id
    ) {
        servico.apagarCurso(id);
    }

    @PatchMapping("/{id}/disciplina")
    public CursoResponseDto adicionarDisciplina(
        @PathVariable final int id,
        @RequestBody final DisciplinaRequestDto request
        ) {
        return servico.adicionarDisciplina(id, request);
    }

}
