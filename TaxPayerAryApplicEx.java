/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taxpayeraryapplicex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class TaxPayerAryApplicEx extends JFrame {
     // Prompts for user input
    private JLabel label1, label2, label3, label4, label5, label6, label7;
    // Mechanisms for user input
    private JTextField textfield1, textfield2, textfield3, textfield4, textfield5;
    private JComboBox typeJCB;  // The JComboBox for type selection
    private JComboBox typeOCC;
        
    // Mechanism for output display
    private JTextArea textDisplay;        // Output display area
    private int textDisplayWidth = 25;    // Width of the JTextArea
    private int textDisplayHeight = 18;   // Height of the JTextArea
    private boolean cannotAddFlag = false; // Indicates whether taxpayer can be added
    
    private final int MAX_TAXPAYERS = 10;  // The maximum number of taxpayers allowed
    private int countTaxpayers = 0;        // The number of taxpayers added to array
    private Taxpayer taxpayerArray [] = new Taxpayer[MAX_TAXPAYERS];
    
    private String typeNames[] = {"Weekly","Bi-weekly","Monthly"};
    private String selectedType = "Weekly";
    
    private String occNames [] = {"Accountant", "Contractor", "Dentist", "Doctor", "Electrician",
        "Food Service", "Lawyer", "Mechanic", "Plumber", "Programmer", "Salesman", "Teacher"};
    private String selectedOcc = "Accountant";
    private double grossPay = 0.0;
    
    // The main method required for an application program
    public static void main( String[] args )  {
        JFrame frame =  new TaxPayerAryApplicEx(); // Construct the window
        frame.setSize( new Dimension( 360, 470 ) );     // Set its size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible( true );   // Make the window visible
    }
    
    public TaxPayerAryApplicEx() {
        super("GUI Demonstration");
        //Container c = getContentPane();
        setLayout( new BorderLayout() );
        
        JPanel jpan = new JPanel();
        jpan.setLayout(new GridLayout(7,2));
        jpan.setBorder(new EtchedBorder());
        add(jpan,BorderLayout.NORTH);
        
        // Create prompt and input mechanism to get taxpayer number from user
        label1 = new JLabel("Number of Taxpayers:");
        jpan.add( label1);
        
        textfield1 = new JTextField( 3 );
        textfield1.setEditable(false);
        textfield1.setText(Integer.toString(countTaxpayers));
        jpan.add( textfield1 );  // put input JTextField on JPanel
        
        label2 = new JLabel("Max # of Taxpayers:");
        jpan.add( label2 );  
        textfield2 = new JTextField( 3);
        textfield2.setText(Integer.toString(MAX_TAXPAYERS));
        textfield2.setEditable(false);
        jpan.add( textfield2 );  // put input JTextField on JPanel
        
        // Create prompt and input mechanism to get taxpayer type from user
        label6 = new JLabel("Taxpayer Type:");
        jpan.add( label6 );  
        typeJCB = new JComboBox(typeNames);
        typeJCB.setMaximumRowCount(3);
        jpan.add( typeJCB );  // put input JTextField on JPanel
        
        label5 = new JLabel("Occupation:");
        jpan.add(label5);
        typeOCC = new JComboBox(occNames);
        typeOCC.setMaximumRowCount (12);
        jpan.add(typeOCC);
        
        //typeJCB = new JComboBox

        // Create prompt and input mechanism to get taxpayer name from user
        label3 = new JLabel("Name:");
        jpan.add( label3 );  
        textfield3 = new JTextField( 15 );
        jpan.add( textfield3 );  // put input JTextField on JPanel       
        
        // Create prompt and input mechanism to get taxpayer number from user
        label4 = new JLabel("Taxpayer SSN Number:");
        jpan.add( label4 );  
        textfield4 = new JTextField( Integer.toString(countTaxpayers+2), 15 );
        jpan.add( textfield4 );   // put input JTextField on JPanel        

        label7 = new JLabel("Taxpayer gross pay:");
        jpan.add(label7);
        textfield5 = new JTextField(15);
        jpan.add(textfield5);
        

        // Set up JTextArea to display information on all the taxpayers
        textDisplay = new JTextArea( getDataStringAllTaxpayers(),
                textDisplayHeight,textDisplayWidth);
        JScrollPane scrollPane = new JScrollPane(textDisplay);
        

        textDisplay.setLineWrap( true );
        textDisplay.setWrapStyleWord( true );
        textDisplay.setBorder(new TitledBorder("Taxpayer List"));
        
        
        
        
        add(scrollPane,BorderLayout.CENTER);
        
        MyActionListener myActListener;
        myActListener = new MyActionListener();
        textfield3.addActionListener( myActListener);
        textfield4.addActionListener( myActListener );
        textfield5.addActionListener( myActListener);
   
        // Listener will respond to a user selecting from JComboBox
        MyItemListener myItemListener = new MyItemListener();
        typeJCB.addItemListener( myItemListener);  
        typeOCC.addItemListener(myItemListener);
        
        // Display data on all Taxpayers in the JTextArea
        displayTaxpayerData();
    }    
    
    private void displayTaxpayerData(){
        setDisplayFields();
        textDisplay.setText(getDataStringAllTaxpayers());
    }
    
    // Create and insert the new Taxpayer into array
    private void addTaxpayerToArray( String nam, String ssn, double gp, String typ, String occ){
        if ( countTaxpayers < MAX_TAXPAYERS ) {
            Taxpayer tp = null;
            if (typ.equals(typeNames[0])) {
                tp = new WeeklyTaxpayer(nam, Integer.parseInt(ssn), gp, typ, occ);
            } else if (typ.equals(typeNames[1])) {
                tp = new BiweeklyTaxpayer(nam, Integer.parseInt(ssn), gp, typ, occ);
            } else if (typ.equals(typeNames[2])) {
                tp = new MonthlyTaxpayer(nam, Integer.parseInt(ssn), gp, typ, occ);
            }
            //Taxpayer tp = new Taxpayer(nam, Integer.parseInt(ssn), gp, typ, occ);
            taxpayerArray[countTaxpayers] = tp;
            countTaxpayers++;
        }
    }
    
    //  Return the taxpayer instance (object) correponding to the tpNumb parameter
    private Taxpayer getTaxpayerFromArray( int tpNumb ) {
        Taxpayer tp = null;
        if ( tpNumb < countTaxpayers ) {
            tp = taxpayerArray[tpNumb];
        }
        return tp;
    }
    
    // Set the contents of the JTextFields
    private void setDisplayFields() {
        textfield1.setText( Integer.toString(countTaxpayers) );
        textfield2.setText( Integer.toString(MAX_TAXPAYERS) );
        textfield3.setText("");
        textfield4.setText("");
        textfield5.setText(Double.toString(grossPay));
    }
    
    // Get the string comprised of all the data on all taxpayers
    private String getDataStringAllTaxpayers() {
        String displayStr = "";
        
        // Add the information on all taxpayers to the display string
        displayStr = displayStr + "\n  PAYROLL LISTING:\n\n ";
        for ( int i = 0; i < countTaxpayers; i++ ) {
            displayStr += (i+1) + ". " + taxpayerArray[i].toString() + "\n ";
        }
        if ( cannotAddFlag )
            displayStr += "\nCannot add taxpayers - the array is full\n";
        return displayStr;
    }
    
    class MyActionListener implements ActionListener {
        // Process user's action in JTextField input
        public void actionPerformed( ActionEvent e) {
                if ( countTaxpayers < MAX_TAXPAYERS )  {                    
                    String name = textfield3.getText();  
                    String ssnNumber = textfield4.getText();
                    grossPay = Double.parseDouble(textfield5.getText());
                    addTaxpayerToArray(name, ssnNumber, grossPay, selectedType, selectedOcc );
                    grossPay = 0.0;
                } else {
                    cannotAddFlag = true;
                }                    
                    
            displayTaxpayerData();
        }
    }
    
    class MyItemListener implements ItemListener {
        public void itemStateChanged(ItemEvent e) {
            
            // If one of the items in the JComboBox was selected,
            // set the selectedDept variable to the user's selection
            if (e.getSource() == typeJCB) {
                selectedType = typeNames[typeJCB.getSelectedIndex()];
                selectedOcc = occNames[typeOCC.getSelectedIndex()];
            } 
        }        
    }
}
