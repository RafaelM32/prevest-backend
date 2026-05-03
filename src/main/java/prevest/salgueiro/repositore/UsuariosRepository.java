package prevest.salgueiro.repositore;

import io.agroal.api.AgroalDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.inject.Inject;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

import prevest.salgueiro.record.TipoDeUsuarios;
import prevest.salgueiro.util.Util;
import prevest.salgueiro.enums.TipoUsuarioEnum;


@ApplicationScoped
public class UsuariosRepository {

    @Inject
    AgroalDataSource dataSource;

    public List<TipoDeUsuarios> getTiposUsuarios() throws SQLException {

        String sql = "SELECT id, nome FROM TIPO_DE_USUARIOS";
        List<TipoDeUsuarios> tiposUsuarios = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String nome = resultSet.getString("nome");
                tiposUsuarios.add(new TipoDeUsuarios(id, nome));
            }
            return tiposUsuarios;
        } catch (SQLException e) {
            e.printStackTrace();
            return tiposUsuarios;
        }
    }


    public void cadastrarUsuario(String email, String senha, String dataNascimento, String cpf, String tipoUsuarioId, String fotoUrl, String nome, String telefone) throws SQLException {
        String sql = "INSERT INTO USUARIOS (cpf, nome, email, senha, telefone, data_nascimento, tipo_usuario, imagem_perfil) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        boolean isAdmin = TipoUsuarioEnum.valueOf(tipoUsuarioId) == TipoUsuarioEnum.ADMINISTRADOR;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, cpf);
            statement.setString(2, nome);
            statement.setString(3, email);
            statement.setString(4, senha);
            statement.setString(5, telefone);
            statement.setDate(6, Util.parseDate(dataNascimento));
            if(isAdmin) {
                statement.setNull(7, 0);
            }else {
                statement.setLong(7, TipoUsuarioEnum.valueOf(tipoUsuarioId).getId());
            }
            statement.setString(8, fotoUrl);

            statement.executeUpdate();

            if(isAdmin) {
                adicionarRequisicaoAdm(cpf);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean usuarioJaCadastrado(String cpf) throws SQLException {
        String sql = "SELECT COUNT(*) FROM USUARIOS WHERE cpf = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, cpf);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void adicionarRequisicaoAdm(String cpf){
        String sql = "INSERT INTO REQUISICOES_ADM (cpf) VALUES (?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, cpf);
            statement.executeUpdate();
            System.out.println("Requisição de administrador adicionada para CPF: " + cpf);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}