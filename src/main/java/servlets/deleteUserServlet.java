/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import gr.csd.uoc.cs359.photo2020.photobook.model.Rating;
import gr.csd.uoc.cs359.winter2020.photobook.db.CommentDB;
import gr.csd.uoc.cs359.winter2020.photobook.db.PostDB;
import gr.csd.uoc.cs359.winter2020.photobook.db.RatingDB;
import gr.csd.uoc.cs359.winter2020.photobook.db.UserDB;
import gr.csd.uoc.cs359.winter2020.photobook.model.Comment;
import gr.csd.uoc.cs359.winter2020.photobook.model.Post;
import java.io.IOException;
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
@WebServlet(name = "deleteUserServlet", urlPatterns = {"/deleteUserServlet"})
public class deleteUserServlet extends HttpServlet {

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

        response.setContentType("application/json;charset=UTF-8");
        String userName = request.getParameter("userName");
        System.out.println(userName);

        if (UserDB.getUser(userName) == null) {

            System.out.println("Cannot find user " + userName);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {

            System.out.println("user " + userName + " has been found.");

            List<Post> posts;
            List<Comment> comments;
            Post temp;
            List<Rating> rating;

            while ((posts = PostDB.getTop10RecentPostsOfUser(userName)) != null) {
                if (posts.isEmpty() == false) {

                    while (posts.isEmpty() == false) {
                        temp = posts.remove(0);

                        comments = CommentDB.getComments(temp.getPostID());
                        while (comments.isEmpty() == false) {

                            CommentDB.deleteComment(comments.remove(0));
                        }


                        rating = RatingDB.getRatings(temp.getPostID());
                        while (rating.isEmpty() == false) {

                            RatingDB.deleteRating(rating.remove(0));
                        }


                        PostDB.deletePost(temp);
                    }

                } else {
                    break;
                }

            }
            System.out.println("All the posts from user " + userName + " have been deleted");
            UserDB.deleteUser(userName);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);

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
            Logger.getLogger(deleteUserServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(deleteUserServlet.class.getName()).log(Level.SEVERE, null, ex);
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
