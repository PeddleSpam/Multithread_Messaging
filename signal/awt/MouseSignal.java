package signal.awt;

import java.awt.event.MouseEvent;

import signal.Transmitter;
import signal.Receiver;
import signal.Signal;

import util.Identifier;

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// MOUSE SIGNAL CLASS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

public class MouseSignal
extends Signal
{
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // CONSTRUCTORS -----------------------------------------------------------
    
    public MouseSignal( Identifier id,
                        Transmitter transmitter,
                        Receiver reciever,
                        MouseEvent event )
    throws NullPointerException
    {
        // Call parent constructor
        
        super( id, transmitter, reciever );
        
        // Assign event
        
        this.event = event;
        
        if( event == null )
        {
            throw new NullPointerException();
        }
    }
    
    public MouseSignal( MouseSignal signal )
    throws NullPointerException
    {
        // Call parent constructor
        
        super( signal );
        
        // Assign event
        
        this.event = signal.getEvent();
        
        if( event == null )
        {
            throw new NullPointerException();
        }
    }
    
    // GET EVENT --------------------------------------------------------------
    
    public MouseEvent getEvent()
    {
        return event;
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE DATA +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    private MouseEvent event;
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
}

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
