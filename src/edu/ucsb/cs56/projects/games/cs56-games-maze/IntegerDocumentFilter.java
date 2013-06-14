package edu.ucsb.cs56.projects.games.cs56_games_maze;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

/** DocumentFilter that allows only integers
    From: http://stackoverflow.com/questions/11093326/restricting-jtextfield-input-to-integers
 */

public class IntegerDocumentFilter extends DocumentFilter{
    
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException{
	Document doc = fb.getDocument();
	StringBuilder sb = new StringBuilder();
	sb.append(doc.getText(0, doc.getLength()));
	sb.insert(offset, string);

	if(isValidInt(sb.toString()))
	    super.insertString(fb, offset, string, attr);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
	Document doc = fb.getDocument();
	StringBuilder sb = new StringBuilder();
	sb.append(doc.getText(0, doc.getLength()));
	sb.replace(offset, offset + length, text);
	
	if (isValidInt(sb.toString())) 
	    super.replace(fb, offset, length, text, attrs);	
    }

    private boolean isValidInt(String text) {
	try {
	    Integer.parseInt(text);
	    return true;
	} catch (NumberFormatException e) {
	    return false;
	}
    }
}
