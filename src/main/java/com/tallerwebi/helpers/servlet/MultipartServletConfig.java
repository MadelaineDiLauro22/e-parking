package com.tallerwebi.helpers.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@MultipartConfig
@WebServlet(urlPatterns = {"/mobile/parking/register"}, name = "register")
public class MultipartServletConfig extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int i = 0;
        for (Part part : req.getParts()) {
            if (part.getSubmittedFileName() != null) {
                part.write(String.format("part-%02d.dat", i++));
                req.setAttribute("message", part.getSubmittedFileName() + " File uploaded successfully!");
            }
        }

        req.getRequestDispatcher("/mobile/parking/register/add").forward(req, resp);
    }

}
