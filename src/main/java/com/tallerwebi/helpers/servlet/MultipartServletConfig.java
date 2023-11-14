package com.tallerwebi.helpers.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@MultipartConfig
@WebServlet(urlPatterns = {"/mobile/parking/register"}, name = "register")
public class MultipartServletConfig extends HttpServlet {

    private static final String UPLOAD_DIR = "uploads";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //resp.setContentType("text/plain");
        //PrintWriter out = resp.getWriter();

        int i = 0;
        for (Part part : req.getParts()) {
            if (part.getSubmittedFileName() != null) {
                //out.printf("Got part: name=%s, size=%d%n", part.getName(), part.getSize());
                part.write(String.format("part-%02d.dat", i++));
                req.setAttribute("message", part.getSubmittedFileName() + " File uploaded successfully!");
            }
        }

       // getServletContext().getRequestDispatcher("/response.jsp").forward(req, resp);
        req.getRequestDispatcher("/mobile/parking/register/add").forward(req, resp);
    }

    /*@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // gets absolute path of the web application
        String applicationPath = req.getServletContext().getRealPath("");
        // constructs path of the directory to save uploaded file
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;

        // creates the save directory if it does not exists
        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        System.out.println("Upload File Directory=" + fileSaveDir.getAbsolutePath());

        String fileName = null;
        //Get all the parts from request and write it to the file on server
        for (Part part : req.getParts()) {
            if (part.getSubmittedFileName() != null) {
                fileName = getFileName(part);
                String writeTo = uploadFilePath + File.separator + fileName;
                part.write(fileName);
            }
        }

        req.setAttribute("message", fileName + " File uploaded successfully!");
        getServletContext().getRequestDispatcher("/response.jsp").forward(
                req, resp);
    }*/

    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        System.out.println("content-disposition header= " + contentDisp);
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }
}
