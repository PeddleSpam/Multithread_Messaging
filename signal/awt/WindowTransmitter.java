package signal.awt;

import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;

import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;

import signal.TabledTransmitter;
import signal.Receiver;

import util.Identifier;

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// WINDOW TRANSMITTER CLASS +++++++++++++++++++++++++++++++++++++++++++++++++++
// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

public class WindowTransmitter
extends TabledTransmitter
implements WindowListener
{
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // CONSTRUCTORS -----------------------------------------------------------
    
    public WindowTransmitter()
    {
        // empty
    }
    
    // WINDOW ACTIVATED -------------------------------------------------------
    
    @Override
    public void windowActivated( WindowEvent event )
    {
        transmitAll( WINDOW_ACTIVATED, event );
    }
    
    // WINDOW CLOSED ----------------------------------------------------------
    
    @Override
    public void windowClosed( WindowEvent event )
    {
        transmitAll( WINDOW_CLOSED, event );
    }
    
    // WINDOW CLOSING ---------------------------------------------------------
    
    @Override
    public void windowClosing( WindowEvent event )
    {
        transmitAll( WINDOW_CLOSING, event );
    }
    
    // WINDOW DEACTIVATED -----------------------------------------------------
    
    @Override
    public void windowDeactivated( WindowEvent event )
    {
        transmitAll( WINDOW_DEACTIVATED, event );
    }
    
    // WINDOW DEICONIFIED -----------------------------------------------------
    
    @Override
    public void windowDeiconified( WindowEvent event )
    {
        transmitAll( WINDOW_DEICONIFIED, event );
    }
    
    // WINDOW ICONIFIED -------------------------------------------------------
    
    @Override
    public void windowIconified( WindowEvent event )
    {
        transmitAll( WINDOW_ICONIFIED, event );
    }
    
    // WINDOW OPENED ----------------------------------------------------------
    
    @Override
    public void windowOpened( WindowEvent event )
    {
        transmitAll( WINDOW_OPENED, event );
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PROTECTED METHODS ++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // GET IDENTIFIERS --------------------------------------------------------
    
    protected Set< Identifier > getIdentifiers()
    {
        return ID_SET;
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE METHODS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // TRANSMIT ALL -----------------------------------------------------------
    
    private void transmitAll( Identifier id, WindowEvent event )
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
            this.transmit( new WindowSignal( id,
                                             this,
                                             it.next(),
                                             event ) );
        }
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC DATA ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // STATIC DATA ------------------------------------------------------------
    
    public static final Identifier WINDOW_ACTIVATED     = new Identifier();
    public static final Identifier WINDOW_CLOSED         = new Identifier();
    public static final Identifier WINDOW_CLOSING         = new Identifier();
    public static final Identifier WINDOW_DEACTIVATED     = new Identifier();
    public static final Identifier WINDOW_DEICONIFIED     = new Identifier();
    public static final Identifier WINDOW_ICONIFIED     = new Identifier();
    public static final Identifier WINDOW_OPENED         = new Identifier();
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE DATA +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // STATIC DATA ------------------------------------------------------------
    
    // Note: ID_SET should never be modified. Synchronisation is unnecessary.
    
    private static final HashSet< Identifier > ID_SET
        = new HashSet< Identifier >();
    
    static
    {
        ID_SET.add( WINDOW_ACTIVATED );
        ID_SET.add( WINDOW_CLOSED );
        ID_SET.add( WINDOW_CLOSING );
        ID_SET.add( WINDOW_DEACTIVATED );
        ID_SET.add( WINDOW_DEICONIFIED );
        ID_SET.add( WINDOW_ICONIFIED );
        ID_SET.add( WINDOW_OPENED );
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
}

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
