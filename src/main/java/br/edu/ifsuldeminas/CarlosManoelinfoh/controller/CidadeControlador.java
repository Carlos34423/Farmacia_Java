/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsuldeminas.CarlosManoelinfoh.controller;

import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.dao.CidadeDao;
import br.edu.ifsuldeminas.CarlosManoelinfoh.modelo.entity.Cidade;
import br.edu.ifsuldeminas.CarlosManoelinfoh.servico.WebConstante;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(WebConstante.BASE_PATH + "/CidadeControlador")

public class CidadeControlador extends HttpServlet {

    Cidade objCidade ;
    CidadeDao objCidadeDao ;
    String nome = "";
    String uf = "";
    String opcao = "";
    String codigoCidade = "";
    
    
     @Override
    public void init() throws ServletException {
        objCidadeDao = new CidadeDao();
        objCidade = new Cidade();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");
        try {
            opcao = request.getParameter("opcao");
            if (opcao == null || opcao.isEmpty()) {
                opcao = "cadastrar";
            }
            codigoCidade = request.getParameter("codigoCidade");
            nome = request.getParameter("nome");
            uf = request.getParameter("uf");
            switch (opcao) {
                case "cadastrar":cadastrar(request, response);  break;
                case "enviarAlterar":enviarAlterar(request, response);  break;
                case "enviarExcluir":enviarExcluir(request, response);  break;
                case "confirmarAlterar":confirmarAlterar(request, response);  break;
                case "confirmarExcluir":confirmarExcluir(request, response);  break;
                case "cancelar":cancelar(request, response);  break;
                default:throw new IllegalArgumentException("Opção inválida"+opcao);
            }
            
        } catch (NumberFormatException e) {
            response.getWriter().println("Erro: um ou mais parâmetros não são número válidos." + e.getMessage());
        } catch (IllegalArgumentException e) {
            response.getWriter().println("Erro: " + e.getMessage());
        }
        
    }
    
    private void cadastrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        objCidade.setNome(nome);
        objCidade.setUf(uf);
        objCidadeDao.salvar(objCidade);
        request.setAttribute("mensagem", "Cidade cadastrada com sucesso!");
        encaminharParaPagina(request, response);

    }
private void enviarAlterar(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {


    int codigoCidade = Integer.parseInt(request.getParameter("codigoCidade"));


    Cidade cidade = objCidadeDao.buscarCidadePorId(codigoCidade);


    request.setAttribute("codigoCidade", cidade.getCodigoCidade());

    request.setAttribute("nome", cidade.getNome());

    request.setAttribute("uf", cidade.getUf());


   request.setAttribute("opcao", "confirmarAlterar");


    encaminharParaPagina(request, response);

}
     private void enviarExcluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     request.setAttribute("codigoCidade", codigoCidade);
     request.setAttribute("nome", nome);
     request.setAttribute("uf", uf);
     request.setAttribute("opcao", "confirmarExcluir");
     request.setAttribute("mensagem", "Confirme os dados e clique em Salvar para excluir");
     encaminharParaPagina(request, response);
     }
     
       private void confirmarAlterar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       objCidade.setCodigoCidade(Integer.valueOf(codigoCidade));
       objCidade.setNome(nome);
       objCidade.setUf(uf);
       objCidadeDao.alterar(objCidade);
       encaminharParaPagina(request, response);
       }
       private void confirmarExcluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       objCidade.setCodigoCidade(Integer.valueOf(codigoCidade));

       objCidadeDao.excluir(objCidade);
       encaminharParaPagina(request, response);
       }
    
    
      private void encaminharParaPagina(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Cidade> listaCidade = objCidadeDao.buscarTodasCidades();
        request.setAttribute("cidades", listaCidade);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/CadastroCidade.jsp");
        dispatcher.forward(request, response);
        

    }
      
      
      private void cancelar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      request.setAttribute("codigoCidade", "0");
      request.setAttribute("opcao", "cadastrar");
      request.setAttribute("nome", "");
      request.setAttribute("uf", "");
      encaminharParaPagina(request, response);
      }
    
}
