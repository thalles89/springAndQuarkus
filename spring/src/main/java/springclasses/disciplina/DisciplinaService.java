package springclasses.disciplina;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import springclasses.disciplina.dto.DisciplinaRequestDto;
import springclasses.disciplina.dto.DisciplinaResponseDto;
import springclasses.disciplina.persistance.Disciplina;
import springclasses.disciplina.persistance.DisciplinaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DisciplinaService {
    final private DisciplinaRepository repositorio;
    final private DisciplinaRepository disciplinaRepository;

    public DisciplinaService(DisciplinaRepository repositorio,
                             DisciplinaRepository disciplinaRepository) {
        this.repositorio = repositorio;
        this.disciplinaRepository = disciplinaRepository;
    }

    public DisciplinaResponseDto gravarDisciplina(DisciplinaRequestDto request) {
        final Disciplina disciplina = new Disciplina();
        disciplina.setNome(request.getNome());
        return DisciplinaResponseDto.from(repositorio.save(disciplina));
    }

    public List<DisciplinaResponseDto> listarDisciplina(Optional<String> prefixo) {
        final List<Disciplina> listaDisciplinas = prefixo.isPresent() ? repositorio.findByNomeStartsWith(prefixo.get()) : repositorio.findAll();
        return listaDisciplinas.stream().map(DisciplinaResponseDto::from).collect(Collectors.toList());
    }

    public DisciplinaResponseDto buscarDisciplina(int id) {
        return DisciplinaResponseDto.from(repositorio.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrado")));
    }

    public void apagarDisciplina(int id) {
        repositorio.deleteById(id);
    }

    public DisciplinaResponseDto atualizarDisciplina(int id, DisciplinaRequestDto request) {
        final Disciplina Disciplina = repositorio.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Disciplina não encontrado"));
        Disciplina.setNome(request.getNome());
        return DisciplinaResponseDto.from(repositorio.save(Disciplina));
    }

    public DisciplinaResponseDto adicionarDisciplina(int id, DisciplinaRequestDto request) {
        final Disciplina disc = new Disciplina();
        disc.setNome(request.getNome());
        disciplinaRepository.save(disc);
        return DisciplinaResponseDto.from(
                repositorio.findById(id)
                        .orElseThrow(
                                () -> new ResponseStatusException(
                                        HttpStatus.NOT_FOUND, "Disciplina não encontrado")));
    }
}
