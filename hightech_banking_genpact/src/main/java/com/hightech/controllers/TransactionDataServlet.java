package com.hightech.controllers;

import com.hightech.bean.Transaction;
import com.hightech.dao.TransactionDao;

import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

//@WebServlet("/TransactionDataServlet")
public class TransactionDataServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            TransactionDao transactionDao = new TransactionDao();

            // Fetch current transaction (latest transaction)
            Transaction currentTransaction = transactionDao.getCurrentTransaction();

            // Fetch last 10 transactions
            List<Transaction> last10Transactions = transactionDao.getLast10Transactions();

            // Prepare JSON response
            String json = "{";
            json += "\"currentTransaction\":" + transactionToJson(currentTransaction) + ",";
            json += "\"last10Transactions\":" + transactionsToJson(last10Transactions);
            json += "}";

            // Send JSON response back to the client
            out.print(json);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching transactions");
        }
    }

    // Convert Transaction object to JSON string
    private String transactionToJson(Transaction transaction) {
        if (transaction == null) {
            return "null";
        }
        return "{"
            + "\"transaction_id\":" + transaction.getTransactionId() + ","
            + "\"acc_no\":\"" + transaction.getAccNo() + "\","
            + "\"type\":\"" + transaction.getType() + "\","
            + "\"amount\":" + transaction.getAmount() + ","
            + "\"updated_balance\":" + transaction.getUpdatedBalance() + ","
            + "\"timestamp\":\"" + transaction.getTimestamp() + "\""
            + "}";
    }

    // Convert List<Transaction> to JSON array string
    private String transactionsToJson(List<Transaction> transactions) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            json.append(transactionToJson(transaction));
            if (i < transactions.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
