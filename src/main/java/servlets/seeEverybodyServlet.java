/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import gr.csd.uoc.cs359.winter2020.photobook.db.UserDB;
import gr.csd.uoc.cs359.winter2020.photobook.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sipit
 */
@WebServlet(name = "seeEverybodyServlet", urlPatterns = {"/seeEverybodyServlet"})
public class seeEverybodyServlet extends HttpServlet {
    /*
     * Function that sanitizes the inputs and prevents an XSS attack
     */
    protected User sanitize(User user) {
        User retUser;
        retUser = user;
        String username = user.getUserName();
        username = username.replace("<", "&lt");
        username = username.replace(">", "&gt");
        username = username.replace("\"", "&quot");
        username = username.replace("&", "&amp");
        retUser.setUserName(username);

        String email = user.getEmail();
        email = email.replace("<", "&lt");
        email = email.replace(">", "&gt");
        email = email.replace("\"", "&quot");
        email = email.replace("&", "&amp");
        retUser.setEmail(email);

        String firstName = user.getFirstName();
        firstName = firstName.replace("<", "&lt");
        firstName = firstName.replace(">", "&gt");
        firstName = firstName.replace("\"", "&quot");
        firstName = firstName.replace("&", "&amp");
        retUser.setFirstName(firstName);

        String lastName = user.getLastName();
        lastName = lastName.replace("<", "&lt");
        lastName = lastName.replace(">", "&gt");
        lastName = lastName.replace("\"", "&quot");
        lastName = lastName.replace("&", "&amp");
        retUser.setLastName(lastName);

        String password = user.getPassword();
        password = password.replace("<", "&lt");
        password = password.replace(">", "&gt");
        password = password.replace("\"", "&quot");
        password = password.replace("&", "&amp");
        retUser.setPassword(password);

        String country = user.getCountry();
        country = country.replace("<", "&lt");
        country = country.replace(">", "&gt");
        country = country.replace("\"", "&quot");
        country = country.replace("&", "&amp");
        retUser.setCountry(country);

        String address = user.getAddress();
        address = address.replace("<", "&lt");
        address = address.replace(">", "&gt");
        address = address.replace("\"", "&quot");
        address = address.replace("&", "&amp");
        retUser.setAddress(address);

        String hometown = user.getTown();
        hometown = hometown.replace("<", "&lt");
        hometown = hometown.replace(">", "&gt");
        hometown = hometown.replace("\"", "&quot");
        hometown = hometown.replace("&", "&amp");
        retUser.setTown(hometown);

        String occupation = user.getOccupation();
        occupation = occupation.replace("<", "&lt");
        occupation = occupation.replace(">", "&gt");
        occupation = occupation.replace("\"", "&quot");
        occupation = occupation.replace("&", "&amp");
        retUser.setOccupation(occupation);

        String interests = user.getInterests();
        if (interests != null) {
        interests = interests.replace("<", "&lt");
        interests = interests.replace(">", "&gt");
        interests = interests.replace("\"", "&quot");
            interests = interests.replace("&", "&amp");
        }
        retUser.setInterests(interests);

        String general = user.getInfo();
        if (general != null) {
        /* replace potentially harmfull characters*/
        general = general.replace("<", "&lt");
        general = general.replace(">", "&gt");
        general = general.replace("\"", "&quot");
        general = general.replace("&", "&amp");
            retUser.setInfo(general);
        }

        String birthDate = user.getBirthDate();
        birthDate = birthDate.replace("<", "&lt");
        birthDate = birthDate.replace(">", "&gt");
        birthDate = birthDate.replace("\"", "&quot");
        birthDate = birthDate.replace("&", "&amp");
        retUser.setBirthDate(birthDate);

        return retUser;
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.lang.ClassNotFoundException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {

        response.setContentType("text/html;charset=UTF-8");
        String returnString = "";
        List<User> users = UserDB.getUsers();

        for (User userIt : users) {
            // XSS SANITIZATIPON
            /*
                COMMENT THE BELOW COMMAND TO BE ABLE TO PASS XSS INJECTIONS ------------------------------------------------------------------------------
             */
            userIt = sanitize(userIt);
            System.out.println("User Sanitized");

            returnString = returnString + "<p>First Name: <span id='nameSpan'>" + userIt.getFirstName() + " </span>, Last Name: <span id='lnameSpan'>" + userIt.getLastName() + "</span>, Email: <span id='emailSpan'>" + userIt.getEmail() + "</span></p>"
                    + "<hr width='50%'> Birth: <span id='DoBSpan'>" + userIt.getBirthDate() + "</span>, Country: <span id='countrySpan'>" + userIt.getCountry() + "</span>, City: <span id='citySpan'>" + userIt.getTown() + "</span></p>"
                    + "<hr width='50%'><p>Address: <span id='addrSpan'>text placeholder</span>, Occupation: <span id='occupationSpan'>text placeholder</span></p>"
                    + "<hr width='50%'><p>Interests: <span id='interestSpan'>" + userIt.getInterests() + "</span></p><p>General:<span id='generalSpan'>" + userIt.getInfo() + "</span></p>"
                    + "<hr width='50%'><p> Created at:  <span id='generalSpan1'>" + userIt.getRegisteredSince() + "</span></p><br><br>";
        }
        try (PrintWriter out = response.getWriter()) {

            /* TODO output your page here. You may use following sample code. */
            out.println(returnString);
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(seeEverybodyServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(seeEverybodyServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
