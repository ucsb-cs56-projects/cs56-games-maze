package edu.ucsb.cs56.projects.games.cs56_games_maze;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;


public class MazeSettingsPanel extends JPanel{

    public GridLayout layout;
    public MazeSettings settings;

    private JTextField genChainLengthField;
    private JTextField genChainLengthFluxField;
    private JTextField stepGenDistanceField;
    private JTextField rowsField;
    private JTextField colsField;
    private JTextField cellWidthField;
    private JTextField startRowField;
    private JTextField startColField;
    private JTextField endRowField;
    private JTextField endColField;
    private JTextField genTypeField;
    private JTextField progRevealRadiusField;
    private JCheckBox progRevealCB;

    private PlainDocument doc;
    private IntegerDocumentFilter intDocumentFilter;

    public MazeSettingsPanel(MazeSettings settings){
	super();
	this.settings=settings;
	this.layout = new GridLayout(0,2);
	this.layout.setVgap(10);
	this.setLayout(this.layout);

	initFields();
	updateFieldValues();
    }

    /** writes changes in UI elements back to settings file
     */
    public void writeback(){
	this.settings.genChainLength = Integer.parseInt(genChainLengthField.getText());
	this.settings.genChainLengthFlux = Integer.parseInt(genChainLengthFluxField.getText());
	this.settings.stepGenDistance = Integer.parseInt(stepGenDistanceField.getText());
	this.settings.rows = Integer.parseInt(rowsField.getText());
	this.settings.cols = Integer.parseInt(colsField.getText());
	this.settings.cellWidth = Integer.parseInt(cellWidthField.getText());
	this.settings.startRow = Integer.parseInt(startRowField.getText());
	this.settings.startCol = Integer.parseInt(startColField.getText());
	this.settings.endRow = Integer.parseInt(endRowField.getText());
	this.settings.endCol = Integer.parseInt(endColField.getText());
	this.settings.genType = Integer.parseInt(genTypeField.getText());
	this.settings.progRevealRadius = Integer.parseInt(progRevealRadiusField.getText());
    }

    private void initFields(){
	intDocumentFilter = new IntegerDocumentFilter();

	this.add(new JLabel("Gen Chain Length"));

	genChainLengthField = new JTextField(5);
	doc = (PlainDocument)genChainLengthField.getDocument();
	doc.setDocumentFilter(intDocumentFilter);
	this.add(genChainLengthField);

	this.add(new JLabel("Gen Chain Length Flux"));

	genChainLengthFluxField = new JTextField(5);
	doc = (PlainDocument)genChainLengthFluxField.getDocument();
	doc.setDocumentFilter(intDocumentFilter);
	this.add(genChainLengthFluxField);

	this.add(new JLabel("Step Gen Distance"));

	stepGenDistanceField = new JTextField(5);
	doc = (PlainDocument)stepGenDistanceField.getDocument();
	doc.setDocumentFilter(intDocumentFilter);
	this.add(stepGenDistanceField);

	this.add(new JLabel("Rows"));

	rowsField = new JTextField(5);
	doc = (PlainDocument)rowsField.getDocument();
	doc.setDocumentFilter(intDocumentFilter);
	this.add(rowsField);

	this.add(new JLabel("Columns"));

	colsField = new JTextField(5);
	doc = (PlainDocument)colsField.getDocument();
	doc.setDocumentFilter(intDocumentFilter);
	this.add(colsField);

	this.add(new JLabel("Cell Width"));

	cellWidthField = new JTextField(5);
	doc = (PlainDocument)cellWidthField.getDocument();
	doc.setDocumentFilter(intDocumentFilter);
	this.add(cellWidthField);

	this.add(new JLabel("Start Row"));

	startRowField = new JTextField(5);
	doc = (PlainDocument)startRowField.getDocument();
	doc.setDocumentFilter(intDocumentFilter);
	this.add(startRowField);

	this.add(new JLabel("Start Column"));

	startColField = new JTextField(5);
	doc = (PlainDocument)startColField.getDocument();
	doc.setDocumentFilter(intDocumentFilter);
	this.add(startColField);

	this.add(new JLabel("End Row"));

	endRowField = new JTextField(5);
	doc = (PlainDocument)endRowField.getDocument();
	doc.setDocumentFilter(intDocumentFilter);
	this.add(endRowField);

	this.add(new JLabel("End Column"));

	endColField = new JTextField(5);
	doc = (PlainDocument)endColField.getDocument();
	doc.setDocumentFilter(intDocumentFilter);
	this.add(endColField);

	this.add(new JLabel("Generator Type"));

	genTypeField = new JTextField(5);
	doc = (PlainDocument)genTypeField.getDocument();
	doc.setDocumentFilter(intDocumentFilter);
	this.add(genTypeField);

	this.add(new JLabel("Progressive Reveal Radius"));

	progRevealRadiusField = new JTextField(5);
	doc = (PlainDocument)progRevealRadiusField.getDocument();
	doc.setDocumentFilter(intDocumentFilter);
	this.add(progRevealRadiusField);
    }

    private void updateFieldValues(){
	genChainLengthField.setText(String.valueOf(settings.genChainLength));
	genChainLengthFluxField.setText(String.valueOf(settings.genChainLengthFlux));
	stepGenDistanceField.setText(String.valueOf(settings.stepGenDistance));
	rowsField.setText(String.valueOf(settings.rows));
	colsField.setText(String.valueOf(settings.cols));
	cellWidthField.setText(String.valueOf(settings.cellWidth));
	startRowField.setText(String.valueOf(settings.startRow));
	startColField.setText(String.valueOf(settings.startCol));
	endRowField.setText(String.valueOf(settings.endRow));
	endColField.setText(String.valueOf(settings.endCol));
	genTypeField.setText(String.valueOf(settings.genType));
	progRevealRadiusField.setText(String.valueOf(settings.progRevealRadius));
    }

    public void paintComponent(Graphics g){
	Graphics2D g2 = (Graphics2D)g;


	super.paintComponent(g);	
    }
}