package signal.awt;

import java.awt.event.FocusEvent;

import signal.Transmitter;
import signal.Receiver;
import signal.Signal;

import util.Identifier;

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// FOCUS SIGNAL CLASS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
public class FocusSignal
extends Signal
{
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // CONSTRUCTORS -----------------------------------------------------------
    
    public FocusSignal( Identifier id,
                        Transmitter transmitter,
                        Receiver receiver,
                        FocusEvent event )
    throws NullPointerException
    {
        // Call parent constructor
        
        super( id, transmitter, receiver );
        
        // Set focus event
        
        this.event = event;
        
        if( event == null )
        {
            throw new NullPointerException();
        }
    }
    
    public FocusSignal( FocusSignal signal )
    throws NullPointerException
    {
        // Call parent constructor
        
        super( signal );
        
        // Assign event
        
        event = signal.event;
        
        if( event == null )
        {
            throw new NullPointerException();
        }
    }
    
    // GET EVENT --------------------------------------------------------------
    
    public FocusEvent getEvent()
    {
        return event;
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE DATA +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    private FocusEvent event;
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
}

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
