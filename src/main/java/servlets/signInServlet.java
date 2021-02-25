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
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sipit
 */
@WebServlet(name = "signInServlet", urlPatterns = {"/signInServlet"})
public class signInServlet extends HttpServlet {

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
        interests = interests.replace("<", "&lt");
        interests = interests.replace(">", "&gt");
        interests = interests.replace("\"", "&quot");
        interests = interests.replace("&", "&amp");
        retUser.setInterests(interests);

        String general = user.getInfo();
        /* replace potentially harmfull characters*/
        general = general.replace("<", "&lt");
        general = general.replace(">", "&gt");
        general = general.replace("\"", "&quot");
        general = general.replace("&", "&amp");
        retUser.setInfo(general);

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
     * @throws java.security.NoSuchAlgorithmException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, NoSuchAlgorithmException {

        System.out.println("Sign In Servlet Starting");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(StandardCharsets.UTF_8.encode(password));
        String newPassword = String.format("%032x", new BigInteger(1, md5.digest()));
        newPassword = newPassword + "salt";

        User user = UserDB.getUser(userName);
        String returnString = "";
        response.setContentType("application/json;charset=UTF-8");

        if (user == null) {

            System.out.println("User does not exists");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        } else {
            if ((userName == null ? user.getUserName() == null : userName.equals(user.getUserName())) && (newPassword == null ? user.getPassword() == null : newPassword.equals(user.getPassword()))) {

                System.out.println("SUCCESSFULL AUTHENTICATION");

                HttpSession session = request.getSession(true);
                session.setMaxInactiveInterval(1000);
                System.out.println("We are in a successfull request and the session has been set");
                user = sanitize(user);
                session.setAttribute("username", user.getUserName());
                session.setAttribute("password", password);

                session.setMaxInactiveInterval(1000);
                Cookie cookie = new Cookie("JSESSIONID", session.getId());
                cookie.setMaxAge(1000);
                response.addCookie(cookie);
                String uname = (String) session.getAttribute("username");
                String pwrd = (String) session.getAttribute("password");


                returnString = "{ \"username\": \"" + user.getUserName() + "\", \"password\": \"" + user.getPassword() + "\", \"email\": \"" + user.getEmail() + "\" , \"firstName\": \"" + user.getFirstName() + "\", \"lastName\": \"" + user.getLastName() + "\""
                        + ", \"birthDate\": \"" + user.getBirthDate() + "\", \"registeredSince\": \"" + user.getRegisteredSince() + "\", \"gender\": \"" + user.getGender() + "\", \"country\": \"" + user.getCountry() + "\""
                    + ", \"town\": \"" + user.getTown() + "\", \"address\": \"" + user.getAddress() + "\", \"occupation\": \"" + user.getOccupation() + "\", \"interests\": \"" + user.getInterests() + "\", \"info\": \"" + user.getInfo() + "\"}";
            } else {

                System.out.println("AUTHENTICATION ERROR");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }

        try (PrintWriter out = response.getWriter()) {

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
            Logger.getLogger(signInServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(signInServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(signInServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(signInServlet.class.getName()).log(Level.SEVERE, null, ex);
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
