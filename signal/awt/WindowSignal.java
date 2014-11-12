package signal.awt;

import java.awt.event.WindowEvent;

import signal.Transmitter;
import signal.Receiver;
import signal.Signal;

import util.Identifier;

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// WINDOW SIGNAL CLASS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

public class WindowSignal
extends Signal
{
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // CONSTRUCTORS -----------------------------------------------------------
    
    public WindowSignal( Identifier id,
                         Transmitter transmitter,
                         Receiver receiver,
                         WindowEvent event )
    throws NullPointerException
    {
        // Call parent constructor
        
        super( id, transmitter, receiver );
        
        // Set window event
        
        this.event = event;
        
        if( event == null )
        {
            throw new NullPointerException();
        }
    }
    
    public WindowSignal( WindowSignal signal )
    throws NullPointerException
    {
        // Call parent constructor
        
        super( signal );
        
        // Set window event
        
        event = signal.event;
        
        if( event == null )
        {
            throw new NullPointerException();
        }
    }
    
    // GET EVENT --------------------------------------------------------------
    
    public WindowEvent getEvent()
    {
        return event;
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE DATA +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    private WindowEvent event;
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
}

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
