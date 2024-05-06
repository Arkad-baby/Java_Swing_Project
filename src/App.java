import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class App {
    public static void main(String[] args) {

            String jdbcUrl = "jdbc:mysql://localhost:3306/school";
          String username = "root";
          String password = "root";
          JFrame frame=new JFrame("MY first Java Swing Application");
          frame.setSize(600,600);
          frame.setVisible(true);
          frame.setLayout(new FlowLayout());
          try {
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connected to the database!");
            myForm(connection,frame );
            showInfo(connection,frame );
         
          } catch (Exception e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
          }
          
        }
   
       
      
     private static void showInfo(Connection connection,JFrame frame){
        try {
            String query="SELECT * FROM students;";
            // Creating a statement
            Statement statement = connection.createStatement();
         
            ResultSet resultSet=statement.executeQuery(query);
    
            // Iterating over the result set
            frame.add(new JLabel("ID          | FirstName       | LastName          | Address            | Phone Number    |  "));
            while (resultSet.next()) {
              // Process each row here
              int id = resultSet.getInt("id");
              String fname = resultSet.getString("fname");
              String lname = resultSet.getString("lname");
              String addresss = resultSet.getString("address");
              String phone = resultSet.getString("phone");
          
          JButton Edit=new JButton("<Edit Me>");
          JButton delete=new JButton("<Delete Me>");
            JLabel info=new JLabel(id +"    "+fname+"    "+lname+"    "+addresss+"    "+phone );
            JPanel panel=new JPanel();
            panel.add(info);
            panel.add(Edit);
            panel.add(delete);
            panel.setLayout(new FlowLayout());
              frame.add(panel);
              delete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    deleteRow(connection, frame, id);
                }
            });
            Edit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateRow(connection,frame, id,fname,lname,addresss,phone);
                }
            });
            }
            statement.close();
            resultSet.close();

           
        } catch (SQLException e) {
            System.err.println("An error occured: " + e.getMessage());
        }    
        }


private static void deleteRow(Connection connection,JFrame frame,int id){
    try {
        String query="DELETE FROM students WHERE id =" +id +";";
        Statement statement = connection.createStatement();
        int rowsAffected=statement.executeUpdate(query);
        if(rowsAffected>0){
            JPanel panel=new JPanel();
            panel.add(new JLabel("Data deleted successfully."));
            frame.add(panel);
        }else{
            JPanel panel=new JPanel();
            panel.add(new JLabel("An error occuredd."));
            frame.add(panel);  
        }
        showInfo(connection, frame);
        statement.close();
    } catch (Exception e) {
        System.err.println("An error occured: " + e.getMessage());
    }
}

private static void updateRow(Connection connection,JFrame frame, int id,String fname,String lname,String address,String phone){
    JFrame frame2=new JFrame("Update your Info!");
    frame2.setSize(500,500);
    frame2.setVisible(true);
    frame2.setLayout(new FlowLayout());
    JPanel panel=new JPanel();
    panel.add(new JLabel("Please edit your Infromation!"));
    frame2.add(panel);

    JLabel firstName=new JLabel("First Name:");
    JLabel lastName=new JLabel("Last Name:");
    JLabel addresss=new JLabel("Address:");
    JLabel phone_Number=new JLabel("Phone Number:");

    JTextField t1=new JTextField(20);
    JTextField t2=new JTextField(20);
    JTextField t3=new JTextField(20);
    JTextField t4=new JTextField(20);
    t1.setText(fname);
    t2.setText(lname);
    t3.setText(address);
    t4.setText(phone);

    JButton button=new JButton("Update");

    JPanel panel1 = new JPanel();
    panel1.add(firstName);
    panel1.add(t1);

    JPanel panel2 = new JPanel();
    panel2.add(lastName);
    panel2.add(t2);

    JPanel panel3 = new JPanel();
    panel3.add(addresss);
    panel3.add(t3);

    JPanel panel4 = new JPanel();
    panel4.add(phone_Number);
    panel4.add(t4);
    
    frame2.add(panel1);
    frame2.add(panel2);
    frame2.add(panel3);
    frame2.add(panel4);
    frame2.add(button);

    button.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            
            // Get the text from text fields and print it
            String firstNameText = t1.getText();
            String lastNameText = t2.getText();
            String addressText = t3.getText();
            String phoneNumberText = t4.getText();
            String query = "UPDATE students SET " +
            "fname = '" + firstNameText + "', " +
            "lname = '" + lastNameText + "', " +
            "address = '" + addressText + "', " +
            "phone = '" + phoneNumberText + "' " +
            "WHERE id = " + id + ";";
            try {
          
                Statement statement = connection.createStatement();
                int rowsAffected=statement.executeUpdate(query);

                if(rowsAffected>0){
                    JPanel panel=new JPanel();
                    panel.add(new JLabel("Your data was updated successfully."));
                    frame2.add(panel);  
                }else{
                    JPanel panel=new JPanel();
                    panel.add(new JLabel("An error occured."));
                    frame2.add(panel);   
                }
               
                statement.close();
           
    
            } catch (Exception error) {
                System.err.println("An error occured: " + error.getMessage());
            }
               t1.setText("");
               t2.setText("");
               t3.setText("");
               t4.setText("");
               frame2.dispose();
               JPanel panel=new JPanel();
               panel.add(new JLabel("Your data was updated successfully."));
               frame.add(panel);  
               showInfo(connection, frame);
             
        }
    });


}


private static void myForm(Connection connection,JFrame frame){
  
    JLabel firstName=new JLabel("First Name:");
    JLabel lastName=new JLabel("Last Name:");
    JLabel address=new JLabel("Address:");
    JLabel phone_Number=new JLabel("Phone Number:");

    JTextField t1=new JTextField(20);
    JTextField t2=new JTextField(20);
    JTextField t3=new JTextField(20);
    JTextField t4=new JTextField(20);

    JButton button=new JButton("Submit");

    JPanel panel1 = new JPanel();
    panel1.add(firstName);
    panel1.add(t1);

    JPanel panel2 = new JPanel();
    panel2.add(lastName);
    panel2.add(t2);

    JPanel panel3 = new JPanel();
    panel3.add(address);
    panel3.add(t3);

    JPanel panel4 = new JPanel();
    panel4.add(phone_Number);
    panel4.add(t4);
    
    frame.add(panel1);
    frame.add(panel2);
    frame.add(panel3);
    frame.add(panel4);
    frame.add(button);
  


    //Inserting data while submitting the form
    button.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            
            // Get the text from text fields and print it
            String firstNameText = t1.getText();
            String lastNameText = t2.getText();
            String addressText = t3.getText();
            String phoneNumberText = t4.getText();
            String query = "INSERT INTO students(fname,lname,address,phone) VALUES('" + firstNameText + "','" + lastNameText + "','" + addressText + "','" + phoneNumberText + "');";
            try {
          
                Statement statement = connection.createStatement();
                int rowsAffected=statement.executeUpdate(query);

                if(rowsAffected>0){
                    JPanel panel=new JPanel();
                    panel.add(new JLabel("Data added successfully."));
                    frame.add(panel);  
                }else{
                    JPanel panel=new JPanel();
                    panel.add(new JLabel("An error occured."));
                    frame.add(panel);   
                }
                showInfo(connection, frame);
                statement.close();
           
    
            } catch (Exception error) {
                System.err.println("An error occured: " + error.getMessage());
            }
               t1.setText("");
               t2.setText("");
               t3.setText("");
               t4.setText("");
             
        }
    });
}

};