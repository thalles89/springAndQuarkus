package amandaqsena.cursos;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import amandaqsena.cursos.dto.CursoRequestDto;
import amandaqsena.cursos.dto.CursoResponseDto;
import amandaqsena.cursos.model.Curso;
import amandaqsena.cursos.model.CursoRepository;

@Path("/cursos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CursosController {
    final private CursoRepository repositorio;

    public CursosController(CursoRepository repositorio) {
        this.repositorio = repositorio;
    }

    @GET
    public List<CursoResponseDto> buscarCursos(@QueryParam("prefix") Optional<String> prefix) {
        return prefix
                .map(s -> repositorio
                        .findComPrefixo(s)
                        .stream()
                        .map(CursoResponseDto::from)
                        .collect(Collectors.toList()))
                .orElseGet(() -> Curso
                        .findAll()
                        .list()
                        .stream()
                        .map(it -> (Curso) it)
                        .map(CursoResponseDto::from)
                        .collect(Collectors.toList()));
    }

    @GET
    @Path("/{id}")
    public CursoResponseDto encontrarCurso(@PathParam("id") final int id) {
        return CursoResponseDto.from((Curso) Curso
                .findByIdOptional(id)
                .orElseThrow(NotFoundException::new));
    }

    @POST
    @Transactional
    public CursoResponseDto criarCurso(final CursoRequestDto request) {
        final Curso curso = Curso.fromCursoRequestDto(request);
        repositorio.persist(curso);

        return CursoResponseDto.from(curso);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void apagarCurso(@PathParam("id") int id) {
        Curso.findByIdOptional(id)
                .orElseThrow(NotFoundException::new).delete();
    }

    @Transactional
    @PATCH
    @Path("/{id}")
    public CursoResponseDto atualizarCurso(
            @PathParam("id") int id,
            final CursoRequestDto request) {
        Curso curso = (Curso) Curso.findByIdOptional(id).orElseThrow(NotFoundException::new);

        curso.setDuracao(request.getDuracao());
        curso.setDescricao(request.getDescricao());
        curso.setNome(request.getNome());
        curso.persist();
        return CursoResponseDto.from(curso);

    }
}
