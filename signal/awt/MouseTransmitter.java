package signal.awt;

import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import signal.TabledTransmitter;
import signal.Receiver;

import util.Identifier;

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// MOUSE TRANSMITTER CLASS ++++++++++++++++++++++++++++++++++++++++++++++++++++
// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

public class MouseTransmitter
extends TabledTransmitter
implements MouseListener
{
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // CONSTRUCTORS -----------------------------------------------------------
    
    public MouseTransmitter()
    {
        // empty
    }
    
    // MOUSE CLICKED ----------------------------------------------------------
    
    @Override
    public void mouseClicked( MouseEvent event )
    {
        transmitAll( MOUSE_CLICKED, event );
    }
    
    // MOUSE ENTERED ----------------------------------------------------------
    
    @Override
    public void mouseEntered( MouseEvent event )
    {
        transmitAll( MOUSE_ENTERED, event );
    }
    
    // MOUSE EXITED -----------------------------------------------------------
    
    @Override
    public void mouseExited( MouseEvent event )
    {
        transmitAll( MOUSE_EXITED, event );
    }
    
    // MOUSE PRESSED ----------------------------------------------------------
    
    @Override
    public void mousePressed( MouseEvent event )
    {
        transmitAll( MOUSE_PRESSED, event );
    }
    
    // MOUSE RELEASED ---------------------------------------------------------
    
    @Override
    public void mouseReleased( MouseEvent event )
    {
        transmitAll( MOUSE_RELEASED, event );
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PROTECTED METHODS ++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // GET IDENTIFIERS --------------------------------------------------------
    
    @Override
    public Set< Identifier > getIdentifiers()
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
    
    public static final Identifier MOUSE_CLICKED     = new Identifier();
    public static final Identifier MOUSE_ENTERED     = new Identifier();
    public static final Identifier MOUSE_EXITED     = new Identifier();
    public static final Identifier MOUSE_PRESSED     = new Identifier();
    public static final Identifier MOUSE_RELEASED     = new Identifier();
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE DATA +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // STATIC DATA ------------------------------------------------------------
    
    // Note: ID_SET should never be modified. Synchronisation is unnecessary.
    
    private static final HashSet< Identifier > ID_SET
        = new HashSet< Identifier >();
    
    static
    {
        ID_SET.add( MOUSE_CLICKED );
        ID_SET.add( MOUSE_ENTERED );
        ID_SET.add( MOUSE_EXITED );
        ID_SET.add( MOUSE_PRESSED );
        ID_SET.add( MOUSE_RELEASED );
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
}

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
