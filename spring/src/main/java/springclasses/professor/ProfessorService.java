package springclasses.professor;

import springclasses.professor.dto.ProfessorRequestDto;
import springclasses.professor.dto.ProfessorResponseDto;

import java.util.List;
import java.util.Optional;

public interface ProfessorService {

    ProfessorResponseDto gravarProfessor(ProfessorRequestDto professor);
    List<ProfessorResponseDto> listarProfessors(Optional<String> prefixo);
    ProfessorResponseDto buscarProfessor(int id);
    void apagarProfessor(int id);
    ProfessorResponseDto atualizarProfessor(int id, ProfessorRequestDto professor);
}
