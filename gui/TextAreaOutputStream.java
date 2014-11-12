package gui;

import java.io.OutputStream;
import javax.swing.JTextArea;

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// TEXT AREA OUTPUT STREAM CLASS ++++++++++++++++++++++++++++++++++++++++++++++
// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

public class TextAreaOutputStream
extends OutputStream
{
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // CONSTRUCTORS -----------------------------------------------------------
    
    public TextAreaOutputStream()
    {
        textArea = null;
    }
    
    public TextAreaOutputStream( JTextArea textArea )
    {
        this.textArea = textArea;
    }
    
    // GET TEXT AREA ----------------------------------------------------------
    
    public synchronized JTextArea getTextArea()
    {
        return textArea;
    }
    
    // SET TEXT AREA ----------------------------------------------------------
    
    public synchronized void setTextArea( JTextArea textArea )
    {
        this.textArea = textArea;
    }
    
    // CLOSE ------------------------------------------------------------------
    
    @Override
    public synchronized void close()
    {
        textArea = null;
    }
    
    // FLUSH ------------------------------------------------------------------
    
    @Override
    public void flush()
    {
        // empty
    }
    
    // WRITE ------------------------------------------------------------------
    
    @Override
    public synchronized void write( byte[] bytes )
    {
        if( textArea == null ) return;
        
        textArea.append( new String( bytes ) );
    }
    
    @Override
    public synchronized void write( byte[] bytes, int offset, int length )
    {
        if( textArea == null ) return;
        
        textArea.append( new String( bytes, offset, length ) );
    }
    
    @Override
    public synchronized void write( int i )
    {
        if( textArea == null ) return;
        
        textArea.append( new String( new char[]{ ( char )i } ) );
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE DATA +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    private JTextArea textArea;
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
}

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
