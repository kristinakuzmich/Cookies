package org.example;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServletException, IOException {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        if ((firstName == null || firstName.isEmpty()) || (lastName == null || lastName.isEmpty())) {
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (LAST_NAME.equals(cookie.getName()) && (firstName == null || firstName.isEmpty())) {
                        firstName = cookie.getValue();}
                    if (LAST_NAME.equals(cookie.getName()) && (lastName == null || lastName.isEmpty())) {
                        lastName = cookie.getValue();}}}
        }
        if (firstName == null || firstName.isEmpty() || lastName == null || lastName.isEmpty()) {
            resp.setContentType("text/html;charset=UTF-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try (PrintWriter out = resp.getWriter()) {
                out.println("<html><body>");
                out.println("<h2>Error, check firstName and lastName.</h2>");
                out.println("</body></html>");}
            return;
        }
        if (req.getParameter("firstName") != null) {
            Cookie firstNameCookie = new Cookie(FIRST_NAME, firstName);
            firstNameCookie.setMaxAge(60 * 60 * 24 * 30);
            resp.addCookie(firstNameCookie);
        }
        if (req.getParameter("lastName") != null) {
            Cookie lastNameCookie = new Cookie(LAST_NAME, lastName);
            lastNameCookie.setMaxAge(60 * 60 * 24 * 30);
            resp.addCookie(lastNameCookie);
        }
        resp.setContentType("test/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<html><body>");
            out.printf("<h1>Hello, %s %s!</h1>%n", firstName, lastName);
            out.println("</body></html>");}}}