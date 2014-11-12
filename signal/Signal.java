package signal;

import util.Identifier;

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// SIGNAL CLASS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

public class Signal
{
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // CONSTRUCTORS -----------------------------------------------------------
    
    public Signal( Identifier id, Transmitter transmitter, Receiver receiver )
    throws NullPointerException
    {
        this.id             = id;
        this.transmitter     = transmitter;
        this.receiver         = receiver;
        
        if( id == null || transmitter == null || receiver == null )
        {
            throw new NullPointerException();
        }
    }
    
    public Signal( Signal signal )
    throws NullPointerException
    {
        this( signal.id, signal.transmitter, signal.receiver );
    }
    
    // GET ID -----------------------------------------------------------------
    
    public Identifier getID()
    {
        return id;
    }
    
    // GET TRANSMITTER --------------------------------------------------------
    
    public Transmitter getTransmitter()
    {
        return transmitter;
    }
    
    // GET RECEIVER -----------------------------------------------------------
    
    public Receiver getReceiver()
    {
        return receiver;
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE DATA +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    private Identifier        id                = null;
    private Transmitter        transmitter        = null;
    private Receiver        receiver        = null;
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
}

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
