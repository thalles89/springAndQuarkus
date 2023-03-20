package springclasses.professor;

import org.springframework.data.repository.Repository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import springclasses.professor.dto.ProfessorRequestDto;
import springclasses.professor.dto.ProfessorResponseDto;
import springclasses.professor.model.Professor;
import springclasses.professor.persistance.ProfessorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfessorServiceImpl implements ProfessorService {

    final private ProfessorRepository repositorio;

    public ProfessorServiceImpl(ProfessorRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public ProfessorResponseDto gravarProfessor(ProfessorRequestDto request) {
        final Professor professor = new Professor();
        professor.setNome(request.getNome());
        return ProfessorResponseDto.from(repositorio.save(professor));
    }

    @Override
    public List<ProfessorResponseDto> listarProfessors(Optional<String> prefixo) {
        final List<Professor> listaProfessors = prefixo.isPresent() ? repositorio.findByNomeStartsWith(prefixo.get()) : repositorio.findAll();
        return listaProfessors.stream().map(ProfessorResponseDto::from).collect(Collectors.toList());
    }

    @Override
    public ProfessorResponseDto buscarProfessor(int id) {
        return ProfessorResponseDto
                .from(repositorio.findById(id).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor" +
                " não encontrado")));
    }

    @Override
    public void apagarProfessor
            (int id) {
        repositorio.deleteById(id);
    }

    @Override
    public ProfessorResponseDto atualizarProfessor(int id, ProfessorRequestDto request) {
        final Professor professor = repositorio.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor" +
                " não encontrado"));
        professor.setNome(request.getNome());
        return ProfessorResponseDto.from(repositorio.save(professor));
    }
}
