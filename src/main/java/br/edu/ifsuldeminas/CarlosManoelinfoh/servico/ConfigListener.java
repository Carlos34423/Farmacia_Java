/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsuldeminas.CarlosManoelinfoh.servico;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
/**
 *
 * @author 02338079698
 */

@WebListener
public class ConfigListener implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent sce) {
      
        sce.getServletContext().setAttribute("URL_BASE", WebConstante.BASE_PATH);
        // estou usando no .jsp
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Limpeza, se necessário.
    }
}
