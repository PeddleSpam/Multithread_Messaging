package signal;

import java.util.Iterator;
import java.util.Set;

import util.BiMultiHashMap;
import util.BiMultiMap;
import util.Identifier;

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// TABLED TRANSMITTER CLASS +++++++++++++++++++++++++++++++++++++++++++++++++++
// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

public abstract class TabledTransmitter
extends AsyncTransmitter
{
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // CONSTRUCTORS -----------------------------------------------------------
    
    public TabledTransmitter()
    {
        // Create receiver table
        
        receiverTable = new BiMultiHashMap< Identifier, Receiver >();
        
        // Get inverse table
        
        inverseTable = receiverTable.inverse();
    }
    
    // REGISTER ---------------------------------------------------------------
    
    @Override
    public boolean register( Receiver receiver )
    {
        // Check parameter
        
        if( receiver == null )
        {
            return false;
        }
        
        // Get iterator to ID set
        
        Iterator< Identifier > it = getIdentifiers().iterator();
        
        // Add receiver to table for each ID
        
        while( it.hasNext() )
        {
            receiverTable.put( it.next(), receiver );
        }
        
        return true;
    }
    
    @Override
    public boolean register( Receiver receiver, Identifier id )
    {
        // Check for null receiver
        
        if( receiver == null )
        {
            return false;
        }
        
        // Check ID is valid
        
        if( getIdentifiers().contains( id ) )
        {
            // Add entry to table
            
            receiverTable.put( id, receiver );
            
            return true;
        }
        
        return false;
    }
    
    // UNREGISTER -------------------------------------------------------------
    
    @Override
    public boolean unregister( Receiver receiver )
    {
        // Check to see if receiver exists
        
        if( inverseTable.containsKey( receiver ) )
        {
            // Remove receiver from table
        
            inverseTable.remove( receiver );
            
            return true;
        }
        
        return false;
    }
    
    @Override
    public boolean unregister( Receiver receiver, Identifier id )
    {
        return receiverTable.remove( id, receiver );
    }
    
    // IS REGISTERED ----------------------------------------------------------
    
    @Override
    public boolean isRegistered( Receiver receiver )
    {
        return inverseTable.containsKey( receiver );
    }
    
    @Override
    public boolean isRegistered( Receiver receiver, Identifier id )
    {
        return receiverTable.contains( id, receiver );
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PROTECTED METHODS ++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // GET IDENTIFIERS --------------------------------------------------------
    
    protected abstract Set< Identifier > getIdentifiers();
    
    // GET RECEIVERS ----------------------------------------------------------
    
    protected Set< Receiver > getReceivers( Identifier id )
    {
        return receiverTable.get( id );
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE DATA +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    private BiMultiHashMap< Identifier, Receiver > receiverTable;
    
    private BiMultiMap< Receiver, Identifier > inverseTable;
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
}

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
