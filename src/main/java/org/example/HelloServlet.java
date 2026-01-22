package org.example;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/cookie")
public class HelloServlet extends HttpServlet {

    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");

        PrintWriter writer = resp.getWriter();

        Cookie cookies[] = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(FIRST_NAME) && (firstName == null || firstName.isEmpty())) {
                    firstName = cookie.getValue();
                }
                if (cookie.getName().equals(LAST_NAME) && (lastName == null || lastName.isEmpty())) {
                    lastName = cookie.getValue();
                }
            }
        }

        if (firstName == null || firstName.isEmpty() || lastName == null || lastName.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.write("Необходимо ввести имя и фамилию!");
        }

        if (firstName != null && !firstName.isEmpty()) {
            Cookie firstNameCookie = new Cookie(FIRST_NAME, firstName);
            resp.addCookie(firstNameCookie);
        }

        if (lastName != null && !lastName.isEmpty()) {
            Cookie lastNameCookie = new Cookie(LAST_NAME, lastName);
            resp.addCookie(lastNameCookie);
        }

        writer.write("Hello " + firstName + " " + lastName);

    }
    //http://localhost:8080/Cookie-1.0/cookie?firstName=Kris&lastName=Kuz
}
