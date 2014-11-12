package signal;

import util.Identifier;

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// TRANSMITTER INTERFACE ++++++++++++++++++++++++++++++++++++++++++++++++++++++
// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

public interface Transmitter
{
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // REGISTER ---------------------------------------------------------------
    
    public boolean register( Receiver receiver );
    
    public boolean register( Receiver receiver, Identifier id );
    
    // UNREGISTER -------------------------------------------------------------
    
    public boolean unregister( Receiver receiver );
    
    public boolean unregister( Receiver receiver, Identifier id );
    
    // IS REGISTERED ----------------------------------------------------------
    
    public boolean isRegistered( Receiver receiver );
    
    public boolean isRegistered( Receiver receiver, Identifier id );
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
}

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
