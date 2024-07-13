<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.hightech.bean.Transfer" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="com.hightech.util.ConnectionFactory" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Statement</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }

        .container {
            max-width: 800px;
            margin: auto;
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        table, th, td {
            border: 1px solid #ddd;
        }

        th, td {
            padding: 12px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

        tr:hover {
            background-color: #f5f5f5;
        }

        .pdf-link {
            display: block;
            text-align: center;
            margin-top: 20px;
        }

        .pdf-link a {
            display: inline-block;
            padding: 10px 20px;
            background-color: #007bff;
            color: #fff;
            text-decoration: none;
            border-radius: 4px;
            transition: background-color 0.3s ease;
        }

        .pdf-link a:hover {
            background-color: #0056b3;
        }
    </style>
    <!-- Include jsPDF -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.4.0/jspdf.umd.min.js"></script>
</head>
<body>
    <div class="container">
    <%int accno = (int) session.getAttribute("accNo"); %>
        <h1>Statement for Account Number: <%= session.getAttribute("accNo") %></h1>

        <table id="statementTable">
            <thead>
                <tr>
                    <th>Transaction ID</th>
                    <th>Sender Account Number</th>
                    <th>Amount</th>
                    <th>type</th>
                    <th>Timestamp</th>
                </tr>
            </thead>
            <tbody>
                <%  
                    Connection conn = null;
                    PreparedStatement stmt = null;
                    ResultSet rs = null;
                    
                    try {
                        conn = ConnectionFactory.getConnection();
                         // Retrieve accno from session
                        String query = "SELECT * FROM transfer WHERE sender_acc_no = ? ORDER BY timestamp DESC LIMIT 10";
                        stmt = conn.prepareStatement(query);
                        stmt.setInt(1, accno);
                        rs = stmt.executeQuery();
                        
                        while (rs.next()) {
                            int transferId = rs.getInt("transfer_id");
                            int senderAccNo = rs.getInt("sender_acc_no");
                            int amount = rs.getInt("amount");
                            String type = "deposit";
                            if(amount < 0){
                            	type = "withdraw";
                            }
                            LocalDateTime timestamp = rs.getObject("timestamp", LocalDateTime.class);
                %>
                <tr>
                    <td><%= transferId %></td>
                    <td><%= senderAccNo %></td>
                    <td><%= Math.abs(amount) %></td>
                     <td><%= type %></td>
                    <td><%= timestamp.toString() %></td>
                </tr>
                <%
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (rs != null) {
                            try {
                                rs.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if (stmt != null) {
                            try {
                                stmt.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if (conn != null) {
                            try {
                                conn.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                %>
            </tbody>
        </table>

        <div class="pdf-link">
            <a href="#" id="downloadPdf">Download as PDF</a>
        </div>
    </div>

    <script>
        document.getElementById('downloadPdf').addEventListener('click', function(event) {
            event.preventDefault();

            const doc = new jsPDF();
            doc.autoTable({ html: '#statementTable' });

            // Download the PDF
            doc.save('statement.pdf');
        });
    </script>
</body>
</html>
