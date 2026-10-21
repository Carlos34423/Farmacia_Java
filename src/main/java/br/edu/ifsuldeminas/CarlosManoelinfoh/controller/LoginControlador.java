package br.edu.ifsuldeminas.CarlosManoelinfoh.controller;

import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao.UsuarioDao;
import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.Usuario;
import br.edu.ifsuldeminas.CarlosManoelinfoh.servico.EmailService;
import br.edu.ifsuldeminas.CarlosManoelinfoh.servico.WebConstante;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Controlador responsável pela sessão de usuário: cadastro, login, logout
 * e recuperação de senha ("Esqueci minha senha").
 *
 * Segue o mesmo padrão dos demais controllers do projeto (HttpServlet +
 * WebConstante.BASE_PATH + RequestDispatcher.forward), com o parâmetro
 * "opcao" definindo a ação a ser executada.
 */
@WebServlet(WebConstante.BASE_PATH + "/LoginControlador")
public class LoginControlador extends HttpServlet {

    private static final long MINUTOS_VALIDADE_TOKEN = 30;

    private final UsuarioDao objUsuarioDao = new UsuarioDao();

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String opcao = request.getParameter("opcao");

        try {

            if (opcao == null || opcao.isBlank()) {
                logout(request, response);
                return;
            }

            switch (opcao) {

                case "redefinirSenha":
                    exibirRedefinicaoSenha(request, response);
                    break;

                case "novoCadastro":
                    encaminharParaCadastro(request, response);
                    break;

                default:
                    encaminharParaLogin(request, response);
                    break;
            }

        } catch (Exception e) {
            response.getWriter().println(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String opcao = request.getParameter("opcao");

        try {

            if (opcao == null) {
                opcao = "";
            }

            switch (opcao) {

                case "autenticar":
                    autenticar(request, response);
                    break;

                case "cadastrar":
                    cadastrar(request, response);
                    break;

                case "enviarRecuperacao":
                    enviarRecuperacao(request, response);
                    break;

                case "confirmarRedefinicao":
                    confirmarRedefinicao(request, response);
                    break;

                default:
                    encaminharParaLogin(request, response);
                    break;
            }

        } catch (Exception e) {
            response.getWriter().println(e.getMessage());
        }
    }

    // ---------------------------------------------------------------
    // Login / Logout
    // ---------------------------------------------------------------

    private void autenticar(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String nomeUsuario = request.getParameter("nomeusuario");
        String senha = request.getParameter("senha");

        Usuario usuario = objUsuarioDao.buscarPorNomeUsuario(nomeUsuario);

        boolean credenciaisValidas = usuario != null &&
                BCrypt.checkpw(senha, usuario.getSenhaHash());

        if (!credenciaisValidas) {
            request.setAttribute("mensagemErro", "Usuário ou senha inválidos.");
            encaminharParaLogin(request, response);
            return;
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("usuarioLogado", usuario);

        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

    private void logout(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

    // ---------------------------------------------------------------
    // Cadastro de usuário
    // ---------------------------------------------------------------

    private void cadastrar(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String nomeUsuario = request.getParameter("nomeusuario");
        String senha = request.getParameter("senha");
        String email = request.getParameter("email");

        if (nomeUsuario == null || nomeUsuario.isBlank() ||
                senha == null || senha.isBlank()) {

            request.setAttribute("mensagemErroCadastro",
                    "Informe usuário e senha.");
            encaminharParaCadastro(request, response);
            return;
        }

        if (objUsuarioDao.buscarPorNomeUsuario(nomeUsuario.trim()) != null) {

            request.setAttribute("mensagemErroCadastro",
                    "Já existe um usuário cadastrado com esse nome.");
            encaminharParaCadastro(request, response);
            return;
        }

        Usuario objUsuario = new Usuario();
        objUsuario.setNomeUsuario(nomeUsuario.trim());
        objUsuario.setSenhaHash(BCrypt.hashpw(senha, BCrypt.gensalt()));
        objUsuario.setEmail(email);

        objUsuarioDao.cadastrar(objUsuario);

        request.setAttribute("mensagem",
                "Cadastro realizado com sucesso. Faça login.");
        encaminharParaLogin(request, response);
    }

    // ---------------------------------------------------------------
    // Esqueci minha senha
    // ---------------------------------------------------------------

    private void enviarRecuperacao(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");

        Usuario usuario = objUsuarioDao.buscarPorEmail(email);

        if (usuario == null) {

            request.setAttribute("mensagemErro",
                    "Não existe usuário cadastrado com esse e-mail.");
            encaminharParaLogin(request, response);
            return;
        }

        String token = UUID.randomUUID().toString();

        Calendar expiracao = Calendar.getInstance();
        expiracao.add(Calendar.MINUTE, (int) MINUTOS_VALIDADE_TOKEN);

        objUsuarioDao.salvarTokenRecuperacao(usuario.getId(), token, expiracao);

        String linkRecuperacao = request.getRequestURL()
                .toString()
                .replace(request.getServletPath(), "")
                + WebConstante.BASE_PATH
                + "/LoginControlador?opcao=redefinirSenha&token=" + token;

        EmailService.enviarEmailRecuperacao(usuario.getEmail(), linkRecuperacao);

        request.setAttribute("mensagem",
                "Um link de recuperação foi enviado para o seu e-mail.");
        encaminharParaLogin(request, response);
    }

    private void exibirRedefinicaoSenha(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String token = request.getParameter("token");

        Usuario usuario = tokenValido(token);

        if (usuario == null) {

            request.setAttribute("mensagemErro",
                    "Link de recuperação inválido ou expirado.");
            encaminharParaLogin(request, response);
            return;
        }

        request.setAttribute("token", token);

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/RedefinirSenha.jsp");

        dispatcher.forward(request, response);
    }

    private void confirmarRedefinicao(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String token = request.getParameter("token");
        String novaSenha = request.getParameter("novaSenha");

        Usuario usuario = tokenValido(token);

        if (usuario == null) {

            request.setAttribute("mensagemErro",
                    "Link de recuperação inválido ou expirado.");
            encaminharParaLogin(request, response);
            return;
        }

        if (novaSenha == null || novaSenha.isBlank()) {

            request.setAttribute("mensagemErroRedefinicao",
                    "Informe a nova senha.");
            request.setAttribute("token", token);

            RequestDispatcher dispatcher =
                    request.getRequestDispatcher("/RedefinirSenha.jsp");
            dispatcher.forward(request, response);
            return;
        }

        objUsuarioDao.atualizarSenha(usuario.getId(),
                BCrypt.hashpw(novaSenha, BCrypt.gensalt()));

        objUsuarioDao.invalidarToken(usuario.getId());

        request.setAttribute("mensagem",
                "Senha alterada com sucesso. Faça login.");
        encaminharParaLogin(request, response);
    }

    /**
     * Retorna o usuário dono do token, apenas se o token existir e ainda
     * não tiver expirado. Caso contrário, retorna null.
     */
    private Usuario tokenValido(String token) {

        if (token == null || token.isBlank()) {
            return null;
        }

        Usuario usuario = objUsuarioDao.buscarPorToken(token);

        if (usuario == null || usuario.getTokenExpiracao() == null) {
            return null;
        }

        boolean expirado = Calendar.getInstance()
                .after(usuario.getTokenExpiracao());

        return expirado ? null : usuario;
    }

    // ---------------------------------------------------------------
    // Encaminhamentos
    // ---------------------------------------------------------------

    private void encaminharParaLogin(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/login.jsp");

        dispatcher.forward(request, response);
    }

    private void encaminharParaCadastro(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/CadastroUsuario.jsp");

        dispatcher.forward(request, response);
    }
}
