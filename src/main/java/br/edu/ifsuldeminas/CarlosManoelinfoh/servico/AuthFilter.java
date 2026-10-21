package br.edu.ifsuldeminas.CarlosManoelinfoh.servico;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class AuthFilter implements Filter {
    
@Override
public void doFilter(ServletRequest request, ServletResponse response,
        FilterChain chain) throws IOException, ServletException {

    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    String path = httpRequest.getRequestURI()
            .substring(httpRequest.getContextPath().length());

    String caminhoLoginControlador =
            WebConstante.BASE_PATH + "/LoginControlador";

    if (path.equals("/login.jsp")
            || path.equals("/CadastroUsuario.jsp")
            || path.equals("/RedefinirSenha.jsp")
            || path.equals("/index.jsp")
            || path.equals("/menu.jsp")
            || path.equals(caminhoLoginControlador)
            || path.startsWith("/public/")
            || path.contains("/css/")
            || path.contains("/estilo/")
            || path.contains("/images/")
            || path.contains("/img/")) {

        chain.doFilter(request, response);
        return;
    }

    HttpSession session = httpRequest.getSession(false);

    if (session != null
            && session.getAttribute("usuarioLogado") != null) {

        chain.doFilter(request, response);

    } else {

        httpResponse.sendRedirect(
                httpRequest.getContextPath() + "/login.jsp");
    }
}

@Override
public void init(jakarta.servlet.FilterConfig filterConfig)
        throws ServletException {
}

@Override
public void destroy() {
}
}
