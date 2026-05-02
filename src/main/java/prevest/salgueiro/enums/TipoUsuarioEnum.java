package prevest.salgueiro.enums;

public enum TipoUsuarioEnum {
    ALUNO(1L),
    PROFESSOR(2L),
    ADMINISTRADOR(3L);

    private final Long id;

    TipoUsuarioEnum(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
