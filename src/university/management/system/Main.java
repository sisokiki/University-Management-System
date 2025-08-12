package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main extends JFrame {

    private JDesktopPane desktop;

    public Main() {
        super("University Management System");
        // This sets the small icon for the main window's title bar and taskbar
        setIconImage(new ImageIcon(ClassLoader.getSystemResource("icons/AppLogo.png")).getImage());
        setSize(1540, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/Maroon Professional University Profile Presentation (1).png"));
        Image i2 = i1.getImage().getScaledInstance(1540, 850, Image.SCALE_SMOOTH);
        desktop = new JDesktopPane() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(i2, 0, 0, getWidth(), getHeight(), this);
            }
        };
        add(desktop, BorderLayout.CENTER);

        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        menuPanel.setOpaque(false);
        JMenuBar menuBar = new JMenuBar();
        menuPanel.add(menuBar);
        desktop.add(menuPanel, JLayeredPane.PALETTE_LAYER);
        menuPanel.setBounds(0, 0, 1540, 40);

        JMenu infoMenu = new JMenu("New Information"); menuBar.add(infoMenu);
        JMenuItem facultyInfo = new JMenuItem("New Faculty Information"); facultyInfo.addActionListener(e -> openFrame(new AddFaculty())); infoMenu.add(facultyInfo);
        JMenuItem studentInfo = new JMenuItem("New Student Information"); studentInfo.addActionListener(e -> openFrame(new AddStudent())); infoMenu.add(studentInfo);

        JMenu detailsMenu = new JMenu("View Details"); menuBar.add(detailsMenu);
        JMenuItem facultyDetails = new JMenuItem("View Faculty Details"); facultyDetails.addActionListener(e -> openFrame(new TeacherDetails())); detailsMenu.add(facultyDetails);
        JMenuItem studentDetails = new JMenuItem("View Student Details"); studentDetails.addActionListener(e -> openFrame(new StudentDetails())); detailsMenu.add(studentDetails);

        JMenu leaveMenu = new JMenu("Apply Leave"); menuBar.add(leaveMenu);
        JMenuItem facultyLeave = new JMenuItem("Faculty Leave"); facultyLeave.addActionListener(e -> openFrame(new TeacherLeave())); leaveMenu.add(facultyLeave);
        JMenuItem studentLeave = new JMenuItem("Student Leave"); studentLeave.addActionListener(e -> openFrame(new StudentLeave())); leaveMenu.add(studentLeave);

        JMenu leaveView = new JMenu("View Leave"); menuBar.add(leaveView);
        JMenuItem facultyLeaveView = new JMenuItem("View Faculty Leave"); facultyLeaveView.addActionListener(e -> openFrame(new TeacherLeaveDetails())); leaveView.add(facultyLeaveView);
        JMenuItem studentLeaveView = new JMenuItem("View Student Leave"); studentLeaveView.addActionListener(e -> openFrame(new StudentLeaveDetails())); leaveView.add(studentLeaveView);

        JMenu exam = new JMenu("Examination"); menuBar.add(exam);
        JMenuItem examDetails = new JMenuItem("Examination Details"); examDetails.addActionListener(e -> openFrame(new ExaminationDetails())); exam.add(examDetails);
        JMenuItem examMarks = new JMenuItem("Enter Examination Marks"); examMarks.addActionListener(e -> openFrame(new EnterMarks())); exam.add(examMarks);

        JMenu updateInfo = new JMenu("Update Details"); menuBar.add(updateInfo);
        JMenuItem updateFacultyInfo = new JMenuItem("Update Faculty Information"); updateFacultyInfo.addActionListener(e -> openFrame(new UpdateTeacher())); updateInfo.add(updateFacultyInfo);
        JMenuItem updateStudentInfo = new JMenuItem("Update Student Information"); updateStudentInfo.addActionListener(e -> openFrame(new UpdateStudent())); updateInfo.add(updateStudentInfo);

        JMenu fees = new JMenu("Fee Details"); menuBar.add(fees);
        JMenuItem feeStructure = new JMenuItem("Fee Structure"); feeStructure.addActionListener(e -> openFrame(new FeeStructure())); fees.add(feeStructure);
        JMenuItem feeForm = new JMenuItem("Student Fee Form"); feeForm.addActionListener(e -> openFrame(new StudentFeeForm())); fees.add(feeForm);

        JMenu utility = new JMenu("Utility"); menuBar.add(utility);
        JMenuItem calculator = new JMenuItem("Calculator"); calculator.addActionListener(e -> { try { Runtime.getRuntime().exec("calc.exe"); } catch (IOException ex) { JOptionPane.showMessageDialog(this, "Could not open Calculator."); } }); utility.add(calculator);
        JMenuItem notePad = new JMenuItem("Notepad"); notePad.addActionListener(e -> { try { Runtime.getRuntime().exec("notepad.exe"); } catch (IOException ex) { JOptionPane.showMessageDialog(this, "Could not open Notepad."); } }); utility.add(notePad);

        JMenu about = new JMenu("About"); menuBar.add(about);
        JMenuItem aboutItem = new JMenuItem("About"); aboutItem.addActionListener(e -> openFrame(new About())); about.add(aboutItem);

        JMenu exit = new JMenu("Exit"); menuBar.add(exit);
        JMenuItem exitItem = new JMenuItem("Exit"); exitItem.addActionListener(e -> System.exit(0)); exit.add(exitItem);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                menuPanel.setBounds(0, 0, getWidth(), 40);
            }
        });

        setVisible(true);
    }

    private void openFrame(JInternalFrame frame) {
        desktop.add(frame);
        frame.setVisible(true);
        try {
            frame.setMaximum(true);
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}