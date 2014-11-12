package signal.awt;

import java.awt.event.KeyEvent;

import signal.Transmitter;
import signal.Receiver;
import signal.Signal;

import util.Identifier;

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// KEY SIGNAL CLASS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
public class KeySignal
extends Signal
{
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // CONSTRUCTORS -----------------------------------------------------------
    
    public KeySignal( Identifier id,
                      Transmitter transmitter,
                      Receiver receiver,
                      KeyEvent event )
    throws NullPointerException
    {
        // Call parent constructor
        
        super( id, transmitter, receiver );
        
        // Set key event
        
        this.event = event;
        
        if( event == null )
        {
            throw new NullPointerException();
        }
    }
    
    public KeySignal( KeySignal signal )
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
    
    public KeyEvent getEvent()
    {
        return event;
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE DATA +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    private KeyEvent event;
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
}

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
