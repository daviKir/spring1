package ru.kirakosyan;

import ru.kirakosyan.persist.Product;
import ru.kirakosyan.persist.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/products/*")
public class ProductServlet extends HttpServlet {

    private ProductRepository productRepository;

    @Override
    public void init() throws ServletException {
        this.productRepository = new ProductRepository();
        this.productRepository.insert(new Product("Pants", 1150));
        this.productRepository.insert(new Product("Jacket", 5000));
        this.productRepository.insert(new Product("Dress", 3890));
        this.productRepository.insert(new Product("Hat", 590));
        this.productRepository.insert(new Product("Shoes", 11090));
        this.productRepository.insert(new Product("Pullover", 2500));
        this.productRepository.insert(new Product("T-shirt", 1599));
        this.productRepository.insert(new Product("Sneakers", 12190));
        this.productRepository.insert(new Product("Socks", 350));
        this.productRepository.insert(new Product("Coat", 24860));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String contextPath = request.getContextPath();

        PrintWriter writer = response.getWriter();

        if (pathInfo == null || pathInfo.equals("/")) {
            writer.println("<table>");
            writer.println("<tr>");
            writer.println("<th>id</th>");
            writer.println("<th>title</th>");
            writer.println("<th>cost</th>");
            writer.println("</tr>");

            for (Product product : productRepository.findAll()) {
                writer.println("<tr style=\"cursor: pointer;\">");
                writer.println("<td>" + product.getId() + "</td>");
                writer.println("<td><a href=" + getServletContext().getContextPath() + "/products/" + product.getId() + ">" + product.getTitle() + "</a></td>");
                writer.println("<td>" + product.getCost() + "</td>");
                writer.println("</tr>");
            }

            writer.println("</table>");
        } else {
            String[] parts = pathInfo.split("/");

            if (parts.length == 2) {
                try {
                    long id = Long.parseLong(parts[1]);
                    Product product = productRepository.findById(id);

                    if (product == null) {
                        this.productNotFound(writer, id);
                        return;
                    }

                    writer.println("<h1>" + product.getTitle() + "</h1>");
                    writer.println("<div style=\"display: flex; justify-content: space-between; width: 300px;\">");
                    writer.println("<p style=\"font-weight: bold;\">Cost:</p>");
                    writer.println("<p>" + product.getCost() + " руб</p>");
                    writer.println("</div>");
                } catch (NumberFormatException error) {
                    this.pageNotFound(writer, pathInfo, contextPath);
                }
            } else {
                this.pageNotFound(writer, pathInfo, contextPath);
            }
        }
    }

    private void pageNotFound(PrintWriter writer, String pathInfo, String contextPath) {
        writer.println(getErrorText("404 Page not found", "Can't resolve page " + contextPath + pathInfo));
    }

    private void productNotFound(PrintWriter writer, long id) {
        writer.println(getErrorText("404 Product not found", "Can't find product by id " + id));
    }

    private String getErrorText(String title, String message) {
        return "<div style=\"width: 100%; height: 100%; display: flex; flex-direction: column; justify-content: center; align-items: center; text-align: center;\"><h1>"+ title +"</h1><p>" + message + "</p></div>";
    }
}
