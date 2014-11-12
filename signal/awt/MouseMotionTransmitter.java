package signal.awt;

import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;

import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

import signal.TabledTransmitter;
import signal.Receiver;

import util.Identifier;

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// MOUSE MOTION LISTENER CLASS ++++++++++++++++++++++++++++++++++++++++++++++++
// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

public class MouseMotionTransmitter
extends TabledTransmitter
implements MouseMotionListener
{
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // CONSTRUCTORS -----------------------------------------------------------
    
    public MouseMotionTransmitter()
    {
        // empty
    }
    
    // MOUSE DRAGGED ----------------------------------------------------------
    
    @Override
    public void mouseDragged( MouseEvent event )
    {
        transmitAll( MOUSE_DRAGGED, event );
    }
    
    // MOUSE MOVED ------------------------------------------------------------
    
    @Override
    public void mouseMoved( MouseEvent event )
    {
        transmitAll( MOUSE_MOVED, event );
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PROTECTED METHODS ++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // GET IDENTIFIERS --------------------------------------------------------
    
    @Override
    protected Set< Identifier > getIdentifiers()
    {
        return ID_SET;
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE METHODS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // TRANSMIT ALL -----------------------------------------------------------
    
    private void transmitAll( Identifier id, MouseEvent event )
    {
        // Get set of receivers for ID
        
        Set< Receiver > receivers = getReceivers( id );
        
        // Get iterator to receivers
        
        Iterator< Receiver > it = null;
        
        try
        {
            it = receivers.iterator();
        }
        catch( Throwable t )
        {
            return;
        }
        
        // Transmit signal for each receiver
        
        while( it.hasNext() )
        {
            this.transmit( new MouseSignal( id,
                                            this,
                                            it.next(),
                                            event ) );
        }
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC DATA ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // STATIC DATA ------------------------------------------------------------
    
    public static final Identifier MOUSE_DRAGGED     = new Identifier();
    public static final Identifier MOUSE_MOVED         = new Identifier();
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE DATA +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // STATIC DATA ------------------------------------------------------------
    
    // Note: ID_SET should never be modified. Synchronisation is unnecessary.
    
    private static final HashSet< Identifier > ID_SET
        = new HashSet< Identifier >();
    
    static
    {
        ID_SET.add( MOUSE_DRAGGED );
        ID_SET.add( MOUSE_MOVED );
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
}

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
