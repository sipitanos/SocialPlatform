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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.*;

/**
 *
 * @author sipit
 */
@WebServlet(name = "AddToDataBase", urlPatterns = {"/AddToDataBase"})
public class AddToDataBase extends HttpServlet {

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
            throws ServletException, IOException, ClassNotFoundException, NoSuchAlgorithmException {

        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String birthDate = request.getParameter("birthDate");
        String country = request.getParameter("country");
        String town = request.getParameter("town");
        String address = request.getParameter("address");
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(StandardCharsets.UTF_8.encode(password));
        String newPassword = String.format("%032x", new BigInteger(1, md5.digest()));
        newPassword = newPassword + "salt";

        if (address == null) {

            address = "n/a";
        }
        String gender = request.getParameter("gender"); // TO BE IMPLEMENTED
        String interests = request.getParameter("interests");
        String occupation = request.getParameter("occupation");
        /* replace potentially harmfull characters*/
        // comment them to check for xss attack
        interests = interests.replace("<", "&lt");
        interests = interests.replace(">", "&gt");
        interests = interests.replace("\"", "&quot");
        interests = interests.replace("&", "&amp");

        String general = request.getParameter("general");
        /* replace potentially harmfull characters*/
        // comment them to check for xss attack
        general = general.replace("<", "&lt");
        general = general.replace(">", "&gt");
        general = general.replace("\"", "&quot");
        general = general.replace("&", "&amp");


        User user = new User();

        user.setUserName(userName);
        user.setEmail(email);
        user.setPassword(newPassword);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setBirthDate(birthDate);
        user.setCountry(country);
        user.setTown(town);
        user.setAddress(address);
        user.setOccupation(occupation);
        user.setGender(gender);
        user.setInterests(interests);
        user.setInfo(general);

        String returnString = "";

        if (UserDB.checkValidUserName(userName)) {

            // Add user to database
            System.out.println("==>Adding user");
            UserDB.addUser(user);
            System.out.println(user.toString());
            System.out.println("==>Added user");
            response.setContentType("application/json;charset=UTF-8");
            returnString = "{ \"username\": \"" + user.getUserName() + "\", \"email\": \"" + user.getEmail() + "\" , \"firstName\": \"" + user.getFirstName() + "\", \"lastName\": \"" + user.getLastName() + "\""
                    + ", \"birthDate\": \"" + user.getBirthDate() + "\", \"registeredSince\": \"" + user.getRegisteredSince() + "\", \"gender\": \"" + user.getGender() + "\", \"country\": \"" + user.getCountry() + "\""
                    + ", \"town\": \"" + user.getTown() + "\", \"address\": \"" + user.getAddress() + "\", \"occupation\": \"" + user.getOccupation() + "\", \"interests\": \"" + user.getInterests() + "\", \"info\": \"" + user.getInfo() + "\"}";
            PrintWriter out = response.getWriter();

            out.print(returnString);
            out.flush();

        } else {
            System.out.println("User already exists.... No more " + userName + " please!");
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            PrintWriter out = response.getWriter();

            out.print("{ \"User\": \"Already Exists\" }");
            out.flush();
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
        System.out.println("We got in the redirection");

        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AddToDataBase.class.getName()).log(Level.SEVERE, null, ex);
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
        System.out.println("We got in the redirection");

        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AddToDataBase.class.getName()).log(Level.SEVERE, null, ex);
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
