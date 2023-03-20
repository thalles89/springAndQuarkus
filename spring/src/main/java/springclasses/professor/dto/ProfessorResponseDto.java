package springclasses.professor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import springclasses.professor.model.Professor;

@Getter
@AllArgsConstructor
public class ProfessorResponseDto {
    private int id;
    private String nome;

    public static ProfessorResponseDto from(Professor professor) {
        return new ProfessorResponseDto(
                professor.getId(), professor.getNome()
        );
    }
}
