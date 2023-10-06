/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
//This program allows users to draw using lines, rectangles and ovals
//Users have the ability to pick color, gradient, line thickness,  and dashes
package drawingapp;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author Abhishek Desai/Professor Alan Verbanec
 */
public class DrawingApplicationFrame extends JFrame
{
    // Create the panels for the top of the application. One panel for each
    // line and one to contain both of those panels.
    private final JPanel topPanel = new JPanel();
    private final JPanel panelLine1 = new JPanel();
    private final JPanel panelLine2 = new JPanel();
    private DrawPanel drawPanel = new DrawPanel();
    private final JPanel statusPanel = new JPanel();
    
    // create the widgets for the firstLine Panel.
    private final JLabel shapesLabel = new JLabel("Shape:");
    public JComboBox shapesComboBox = new JComboBox<>(new String[] {"Line", "Rectangle", "Oval"});
    private final JButton colorButton1 = new JButton("1st Color");
    private final JButton colorButton2 = new JButton("2nd Color");
    private final JButton undoButton = new JButton("Undo");
    private final JButton clearButton = new JButton("Clear");
    
    //create the widgets for the secondLine Panel.
    private final JLabel optionsLabel = new JLabel("Options:");
    private final JCheckBox filledCheckBox = new JCheckBox("Filled");
    private final JCheckBox gradientCheckBox = new JCheckBox("Use Gradient");
    private final JCheckBox dashCheckBox = new JCheckBox("Dashed");
    JSpinner dashSpinner = new JSpinner();
    private final JLabel widthLabel = new JLabel("Line Width:");
    JSpinner widthSpinner= new JSpinner();
    private final JLabel dashLabel = new JLabel("Dash Length:");
    static JLabel mousePos = new JLabel("( , )");

    // Variables for drawPanel.
    static ArrayList<MyShapes> itemsDrawn;
    private Color color1 = Color.BLACK;
    private Color color2 = Color.WHITE;

    // add status label

    // Constructor for DrawingApplicationFrame
    public DrawingApplicationFrame()
    {

        // add topPanel to North, drawPanel to Center, and statusLabel to South
        topPanel.setLayout(new BorderLayout());
        panelLine1.setLayout(new FlowLayout());
        panelLine2.setLayout(new FlowLayout());
        drawPanel.setLayout(new BorderLayout());
        statusPanel.setLayout(new BorderLayout());

        // add widgets to panels
        
        // firstLine widgets
        panelLine1.add(shapesLabel); //JLabel
        panelLine1.add(shapesComboBox); //JCombo
        panelLine1.add(colorButton1);
        panelLine1.add(colorButton2);
        panelLine1.add(undoButton);
        panelLine1.add(clearButton);

        // secondLine widgets
        panelLine2.add(optionsLabel);
        panelLine2.add(filledCheckBox);
        panelLine2.add(gradientCheckBox);
        panelLine2.add(dashCheckBox);
        panelLine2.add(widthLabel);
        panelLine2.add(widthSpinner);
        panelLine2.add(dashLabel);
        panelLine2.add(dashSpinner);

        panelLine1.setBackground(Color.CYAN);
        panelLine2.setBackground(Color.CYAN);

        // add top panel of two panels
        topPanel.add(panelLine1, "North");
        topPanel.add(panelLine2, "South");
        drawPanel.setVisible(true);
        drawPanel.setBackground(Color.WHITE);
        statusPanel.add(mousePos, BorderLayout.WEST);
        statusPanel.setVisible(true);
        super.add(topPanel, "North");
        super.add(drawPanel, "Center");
        super.add(statusPanel, "South");

        //add listeners and event handlers
        clearButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        itemsDrawn.clear(); //deletes all elements from arraylist
                        drawPanel.repaint(); //updates canvas
                    }
                }
        );

        undoButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (itemsDrawn.size() != 0) { //Canvas cannot be empty 
                            itemsDrawn.remove(itemsDrawn.size() - 1); //Removes the last item from the list
                            drawPanel.repaint(); //updates canvas
                        }
                    }
                }
        );
        //Allows user to change primary color
        colorButton1.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        color1 = JColorChooser.showDialog(colorButton1, 
                                "Select color: ", Color.BLACK); 
                        if (color1 == null){
                            color1 = Color.BLACK;
                        }

                    }
                }
        );
        //Allows user to change secondary color
        colorButton2.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                color2 = JColorChooser.showDialog(colorButton2, "Select color: "
                        + "", color2);
             //in case user cancels color selection
            if (color2 == null){
                color2 = Color.WHITE;
            }
            
            }
        });

    }
        // Create a private inner class for the DrawPanel.
        private class DrawPanel extends JPanel
        {

            //Initializes list of shapes and mouse event listeners
            public DrawPanel()
            {
                itemsDrawn = new ArrayList<MyShapes>();
                MouseHandler mouseHandler = new MouseHandler();
                this.addMouseListener(mouseHandler);
                this.addMouseMotionListener(mouseHandler);
            }

            //Draws shapes
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                //loop through and draw each shape in the shapes arraylist
                for (MyShapes myShape: itemsDrawn){
                    myShape.draw(g2d);
                }
                g2d.dispose();
            }


            private class MouseHandler extends MouseAdapter implements MouseMotionListener
            {
                private Paint paint;
                private Stroke stroke;
                private Point start;
                private MyShapes shapes;

                public void mousePressed(MouseEvent event)
                {
                    if (dashCheckBox.isSelected() && (Integer)dashSpinner.getValue() > 0)
                    {
                        //creates stroke with dash
                        int len = (Integer)dashSpinner.getValue();
                        float f[] = { ((float) len) };
                        stroke = new BasicStroke((Integer)widthSpinner.getValue(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10, f, 0);
                    }
                    else
                    {
                        //normal stroke
                        stroke = new BasicStroke((Integer)widthSpinner.getValue(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
                    }

                    if (gradientCheckBox.isSelected()){
                        //creates gradient paint obj
                        paint = new GradientPaint(0, 0, color1, 50, 50, color2, true);
                    }
                    else 
                    {
                        paint = new GradientPaint(0, 0, color1, 50, 50, color1, true);
                    }


                    if(shapesComboBox.getSelectedItem() == "Line"){
                        start = event.getPoint();
                        //creates line as mouse is dragged
                        //start start because mouseDragged method is constantly setting endpoint as start value
                        shapes = new MyLine(start, start, paint, stroke);
                        //adds to arraylist to be displayed on panel        
                    }
                    else if (shapesComboBox.getSelectedItem() == "Oval"){
                        start = event.getPoint();
                        shapes = new MyOval(start, start, paint, stroke, filledCheckBox.isSelected());               
                    }
                    else if (shapesComboBox.getSelectedItem() == "Rectangle"){
                        start = event.getPoint();
                        shapes = new MyRectangle(start, start, paint, stroke, filledCheckBox.isSelected());                 
                    }
                    itemsDrawn.add(shapes);
                }


                @Override
                public void mouseDragged(MouseEvent event)
                {
                    Point moveMouse = event.getPoint();
                    shapes.setStartPoint(start);
                    shapes.setEndPoint(moveMouse);
                    //sets 2nd point of shape to where cursor is dragged
                    drawPanel.repaint();
                    String mousePosition = "( " + moveMouse.x + ", " + moveMouse.y + " ) ";
                    mousePos.setText(mousePosition);
                }

                @Override
                public void mouseMoved(MouseEvent event)
                {
                    //Updates cursor position
                    String mousePosition = " ( " + event.getPoint().x + ", " + event.getPoint().y + " ) ";
                    mousePos.setText(mousePosition);
                }
            }
        }
    }