package signal.awt;

import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;

import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;

import signal.TabledTransmitter;
import signal.Receiver;

import util.Identifier;

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// FOCUS TRANSMITTER CLASS ++++++++++++++++++++++++++++++++++++++++++++++++++++
// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

public class FocusTransmitter
extends TabledTransmitter
implements FocusListener
{
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // CONSTRUCTORS -----------------------------------------------------------
    
    public FocusTransmitter()
    {
        // empty
    }
    
    // FOCUS GAINED -----------------------------------------------------------
    
    @Override
    public void focusGained( FocusEvent event )
    {
        transmitAll( FOCUS_GAINED, event );
    }
    
    // FOCUS LOST -------------------------------------------------------------
    
    @Override
    public void focusLost( FocusEvent event )
    {
        transmitAll( FOCUS_LOST, event );
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
    
    private void transmitAll( Identifier id, FocusEvent event )
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
            this.transmit( new FocusSignal( id,
                                            this,
                                            it.next(),
                                            event ) );
        }
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC DATA ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // STATIC DATA ------------------------------------------------------------
    
    public static final Identifier FOCUS_GAINED     = new Identifier();
    public static final Identifier FOCUS_LOST         = new Identifier();
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE DATA +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // STATIC DATA ------------------------------------------------------------
    
    // Note: ID_SET should never be modified. Synchronisation is unnecessary.
    
    private static final HashSet< Identifier > ID_SET
        = new HashSet< Identifier >();
    
    static
    {
        ID_SET.add( FOCUS_GAINED );
        ID_SET.add( FOCUS_LOST );
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
}

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
