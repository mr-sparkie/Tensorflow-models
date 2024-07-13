<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Panel</title>
    <link rel="stylesheet" type="text/css" href="Dashboard.css">
    <style>
        /* General styling */
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 800px;
            margin: 20px auto;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            border-radius: 5px;
        }
        h2 {
            text-align: center;
            margin-bottom: 20px;
        }
        .tabs {
            display: flex;
            justify-content: center;
            margin-bottom: 20px;
        }
        .tablinks {
            background-color: #333;
            color: #fff;
            border: none;
            padding: 10px 20px;
            cursor: pointer;
            transition: background-color 0.3s;
            margin: 0 10px;
            border-radius: 5px 5px 0 0;
        }
        .tablinks:hover {
            background-color: #555;
        }
        .tabcontent {
            display: none;
            padding: 20px;
            border: 1px solid #ccc;
            border-top: none;
            border-radius: 0 0 5px 5px;
        }
        .active {
            background-color: #555;
        }
        form {
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
        }
        input[type="text"], input[type="number"], input[type="email"], input[type="date"] {
            width: calc(100% - 22px);
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
            transition: border-color 0.3s;
        }
        input[type="text"]:hover, input[type="number"]:hover, input[type="email"]:hover, input[type="date"]:hover {
            border-color: #555;
        }
        input[type="submit"] {
            background-color: #555;
            color: #fff;
            border: none;
            padding: 12px 20px;
            cursor: pointer;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        input[type="submit"]:hover {
            background-color: #777;
        }
    </style>
    <script>
        function openTab(evt, tabName) {
            var i, tabcontent, tablinks;
            tabcontent = document.getElementsByClassName("tabcontent");
            for (i = 0; i < tabcontent.length; i++) {
                tabcontent[i].style.display = "none";
            }
            tablinks = document.getElementsByClassName("tablinks");
            for (i = 0; i < tablinks.length; i++) {
                tablinks[i].classList.remove("active");
            }
            document.getElementById(tabName).style.display = "block";
            evt.currentTarget.classList.add("active");
        }
    </script>
</head>
<body>
    <div class="container">
        <h2>Admin Panel</h2>
        <div class="tabs">
            <button id="insert-tab" class="tablinks active" onclick="openTab(event, 'insert')">Insert User</button>
            <button id="update-tab" class="tablinks" onclick="openTab(event, 'update')">Update User</button>
            <button id="delete-tab" class="tablinks" onclick="openTab(event, 'delete')">Delete User</button>
        </div>
        
        <!-- Insert User Form -->
        <div id="insert" class="tabcontent" style="display:block;">
            <h3>Insert User</h3>
            <form action="newuser" method="post">
                <input type="hidden" name="action" value="add">
                <label for="acc_no">Account Number:</label>
                <input type="number" id="acc_no" name="acc_no" required><br>
                <label for="full_name">Full Name:</label>
                <input type="text" id="full_name" name="full_name" required><br>
                <label for="address">Address:</label>
                <input type="text" id="address" name="address" required><br>
                <label for="mobile_no">Mobile Number:</label>
                <input type="text" id="mobile_no" name="mobile_no" required><br>
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required><br>
                <label for="acc_type">Account Type:</label>
                <input type="text" id="acc_type" name="acc_type" required><br>
                <label for="dob">Date of Birth:</label>
                <input type="date" id="dob" name="dob" required><br>
                <label for="id_proof">ID Proof:</label>
                <input type="text" id="id_proof" name="id_proof" required><br>
                <input type="submit" value="Submit">
            </form>
        </div>

        <!-- Update User Form -->
        <div id="update" class="tabcontent">
            <h3>Update User</h3>
            <form action="newuser" method="post" id="updateUserForm">
                <input type="hidden" name="action" value="fetch">
                <label for="update_acc_no">Account Number:</label>
                <input type="number" id="update_acc_no" name="acc_no" required><br>
                <input type="submit" value="Fetch User">
            </form>

            <!-- Display fetched user details for update -->
            <c:if test="${not empty user}">
                <form action="newuser" method="post" id="updateDetailsForm">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="acc_no" value="${user.accNo}">
                    <label for="update_full_name">Full Name:</label>
                    <input type="text" id="update_full_name" name="full_name" value="${user.fullName}" required><br>
                    <label for="update_address">Address:</label>
                    <input type="text" id="update_address" name="address" value="${user.address}" required><br>
                    <label for="update_mobile_no">Mobile Number:</label>
                    <input type="text" id="update_mobile_no" name="mobile_no" value="${user.mobileNo}" required><br>
                    <label for="update_email">Email:</label>
                    <input type="email" id="update_email" name="email" value="${user.email}" required><br>
                    <label for="update_acc_type">Account Type:</label>
                    <input type="text" id="update_acc_type" name="acc_type" value="${user.accType}" required><br>
                    <label for="update_dob">Date of Birth:</label>
                    <input type="date" id="update_dob" name="dob" value="${user.dob}" required><br>
                    <label for="update_id_proof">ID Proof:</label>
                    <input type="text" id="update_id_proof" name="id_proof" value="${user.idProof}" required><br>
                    <input type="submit" value="Update User">
                </form>
            </c:if>
        </div>

        <!-- Delete User Form -->
        <div id="delete" class="tabcontent">
            <h3>Delete User</h3>
            <form action="newuser" method="post">
                <input type="hidden" name="action" value="delete">
                <label for="delete_acc_no">Account Number:</label>
                <input type="number" id="delete_acc_no" name="acc_no" required><br>
                <input type="submit" value="Delete User">
            </form>
        </div>

        <script>
            // JavaScript function to initialize the tab behavior
            document.addEventListener("DOMContentLoaded", function() {
                var activeTab = "${activeTab}"; // Get the active tab from the server
                
                // Open the corresponding tab based on the activeTab value
                if (activeTab === "update") {
                    document.getElementById("update-tab").click();
                } else if (activeTab === "delete") {
                    document.getElementById("delete-tab").click();
                } else {
                    document.getElementById("insert-tab").click(); // Default to insert tab
                }
            });
        </script>
    </div>
</body>
</html>
