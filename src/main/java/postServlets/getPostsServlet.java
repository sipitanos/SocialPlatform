/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postServlets;

import gr.csd.uoc.cs359.winter2020.photobook.db.CommentDB;
import gr.csd.uoc.cs359.winter2020.photobook.db.PostDB;
import gr.csd.uoc.cs359.winter2020.photobook.model.Comment;
import gr.csd.uoc.cs359.winter2020.photobook.model.Post;
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
@WebServlet(name = "getPostsServlet", urlPatterns = {"/getPostsServlet"})
public class getPostsServlet extends HttpServlet {

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
        String returnString = "";
        String username = request.getParameter("username");

        response.setContentType("text/html;charset=UTF-8");
        List<Post> posts = PostDB.getTop10RecentPostsOfUser(username);
        List<Comment> comments;

        response.setContentType("text/html;charset=UTF-8");
        for (Post postIt : posts) {


            returnString = returnString + "<p>Post Text:<br> <span id='TextSpan'>" + postIt.getDescription()
                    + " </span> <br> Longtitute:<br> <span id='lon'>"
                    + postIt.getLongitude() + "</span> <br> Latitutde:<br> <span id='lat'>"
                    + postIt.getLatitude() + "</span> <br> Posted at:<br> <span id='timestamp'>"
                    + postIt.getCreatedAt() + "</span> <br>"
                    + "<button type =\"button\" onclick=\"showPostMap(" + postIt.getLatitude() + "," + postIt.getLongitude() + ");\">Show Post's Map</button><br>"
                    + "<button type =\"button\" onclick=\"closeMap();\">Close the Map</button><br>"
                    + "<hr>"
                    + "Comments Section:<br>";

            comments = CommentDB.getComments(postIt.getPostID());
            for (Comment com : comments) {
                returnString = returnString + "User:<span id='comUname'> " + com.getUserName() + "</span> at: <span id ='comTime'>" + com.getCreatedAt() + "</span> Wrote: <br>"
                        + "<span id='comText'>" + com.getComment() + "</span><br>"
                        + "<button type=\"button\" onclick=\"deleteComment(" + com.getID() + ");\">Delete this comment</button><br><hr>";
            }
            returnString = returnString + "<textarea rows=\"4\" cols=\"32\" name=\"new_comment\" id=\"postComment\" maxlength=\"500\" placeholder=\"Add your comment here.\"></textarea><br>"
                    + "<button type =\"button\" onclick=\"sendComment(" + postIt.getPostID() + ");\">Send Comment</button><br>"
                    + "</p><br><br>";
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
            Logger.getLogger(getPostsServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(getPostsServlet.class.getName()).log(Level.SEVERE, null, ex);
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
