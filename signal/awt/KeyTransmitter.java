package signal.awt;

import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import signal.TabledTransmitter;
import signal.Receiver;

import util.Identifier;

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// KEY TRANSMITTER CLASS ++++++++++++++++++++++++++++++++++++++++++++++++++++++
// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

public class KeyTransmitter
extends TabledTransmitter
implements KeyListener
{
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // CONSTRUCTORS -----------------------------------------------------------
    
    public KeyTransmitter()
    {
        // empty
    }
    
    // KEY PRESSED ------------------------------------------------------------
    
    @Override
    public void keyPressed( KeyEvent event )
    {
        transmitAll( KEY_PRESSED, event );
    }
    
    // KEY RELEASED -----------------------------------------------------------
    
    @Override
    public void keyReleased( KeyEvent event )
    {
        transmitAll( KEY_RELEASED, event );
    }
    
    // KEY TYPED --------------------------------------------------------------
    
    @Override
    public void keyTyped( KeyEvent event )
    {
        transmitAll( KEY_TYPED, event );
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
    
    private void transmitAll( Identifier id, KeyEvent event )
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
            this.transmit( new KeySignal( id,
                                          this,
                                          it.next(),
                                          event ) );
        }
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC DATA ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // STATIC DATA ------------------------------------------------------------
    
    public static final Identifier KEY_PRESSED         = new Identifier();
    public static final Identifier KEY_RELEASED     = new Identifier();
    public static final Identifier KEY_TYPED         = new Identifier();
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE DATA +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // STATIC DATA ------------------------------------------------------------
    
    // Note: ID_SET should never be modified. Synchronisation is unnecessary.
    
    private static final HashSet< Identifier > ID_SET
        = new HashSet< Identifier >();
    
    static
    {
        ID_SET.add( KEY_PRESSED );
        ID_SET.add( KEY_RELEASED );
        ID_SET.add( KEY_TYPED );
    }
}

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
