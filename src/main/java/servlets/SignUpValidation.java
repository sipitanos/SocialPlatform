/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sipit
 *
 * This function validates the inputs and forwards the request to the servlet
 * that sends it to the database
 */
@WebServlet(name = "SignUpValidation", urlPatterns = {"/SignUpValidation"})
public class SignUpValidation extends HttpServlet {

    @Override
    public void init(ServletConfig cfg) throws ServletException {
        System.out.println("Sign Up Validation Servlet: Initiated");
    }

    @Override
    public void destroy() {
        System.out.println("Sign Up Validation Servlet: Destroyed");
    }

    // validity starts as true and when something is not valid it gets turned to false
    protected boolean validity = true;
    // errorReturn returns the name of the first non valid string we got
    protected String errorReturn = "";

    protected void isValidUserName(String userName) {

        String userNameRegex = "^[a-zA-Z0-9]{8,}";
        Pattern pat = Pattern.compile(userNameRegex);
        boolean ret = pat.matcher(userName).matches();

        if (ret == false) {

            if ("".equals(errorReturn)) {

                errorReturn = "user name";
            }
            validity = false;
        }
    }

    protected void isValidEmail(String email) {

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."
                + "[a-zA-Z0-9_+&*-]+)*@"
                + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                + "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        boolean ret = pat.matcher(email).matches();

        if (ret == false) {

            if ("".equals(errorReturn)) {

                errorReturn = "email";
            }
            validity = false;
        }
    }


    private void isValidFirstName(String firstName) {

        String firstNameRegex = "^[a-zA-z]{3,15}$";
        Pattern pat = Pattern.compile(firstNameRegex);
        boolean ret = pat.matcher(firstName).matches();

        if (ret == false) {

            if ("".equals(errorReturn)) {

                errorReturn = "first name";
            }
            validity = false;
        }
    }

    private void isValidLastName(String lastName) {

        String lastNameRegex = "^[a-zA-z]{3,15}$";
        Pattern pat = Pattern.compile(lastNameRegex);
        boolean ret = pat.matcher(lastName).matches();

        if (ret == false) {

            if ("".equals(errorReturn)) {

                errorReturn = "last name";
            }
            validity = false;
        }

    }

    private void isValidTown(String town) {

        String townRegex = "^[a-zA-z]{3,20}$";
        Pattern pat = Pattern.compile(townRegex);
        boolean ret = pat.matcher(town).matches();

        if (ret == false) {

            if ("".equals(errorReturn)) {

                errorReturn = "town";
            }
            validity = false;
        }
    }

    private void isValidInterests(String interests) {

        if (interests.length() > 100) {

            validity = false;
            if ("".equals(errorReturn)) {

                errorReturn = "Interests exceed 100 chars.";
            }
        }

    }

    private void isValidGeneral(String general) {

        if (general.length() > 500) {

            validity = false;

            if ("".equals(errorReturn)) {

                errorReturn = "General exceeds 500 chars.";
            }
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain;charset=UTF-8");

        String userName = request.getParameter("userName");
        isValidUserName(userName);

        String email = request.getParameter("email");
        isValidEmail(email);

        //The password will be hashed
        String password = request.getParameter("password");


        String firstName = request.getParameter("firstName");
        isValidFirstName(firstName);

        String lastName = request.getParameter("lastName");
        isValidLastName(lastName);

        String birthDate = request.getParameter("birthDate");
        String country = request.getParameter("country");
        String town = request.getParameter("town");
        isValidTown(town);

        String gender = request.getParameter("gender"); // TO BE IMPLEMENTED

        // Checks for harmfull characters and changes are done to the other servlet
        // As thiss servlet checks that input is just valid.
        String interests = request.getParameter("interests");
        isValidInterests(interests);

        String general = request.getParameter("general");
        isValidGeneral(general);

        if ("".equals(firstName) || "".equals(lastName) || "".equals(email)
                || "".equals(userName) || "".equals(password) || "".equals(birthDate)
                || "".equals(town) || "".equals(country) || "".equals(interests) || "".equals(general)) {

            PrintWriter out = response.getWriter();
            out.print("Empty Inputs, Please check again");
            out.flush();
        } else {

            if (validity == false) {

                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                PrintWriter out = response.getWriter();
                out.print("INPUT: " + errorReturn + " NOT MATCHING CORRECT PATTERNS");
                errorReturn = "";
                validity = true;
                out.flush();
            } else {

                /*
                 * Send it to the other servlet that is responsible for adding the user
                 */
                RequestDispatcher view = request.getRequestDispatcher("/AddToDataBase");
                view.forward(request, response);
                response.setStatus(HttpServletResponse.SC_OK);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("get"); // TO BE DELETED
        processRequest(request, response);
        destroy();
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        System.out.println("POST"); // TO BE DELETED
        processRequest(request, response);
        destroy();
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
