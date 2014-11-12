package util;

import java.util.concurrent.ConcurrentLinkedQueue;

import java.lang.Comparable;

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// IDENTIFIER CLASS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

public class Identifier
implements Comparable< Identifier >
{
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // CONSTRUCTORS -----------------------------------------------------------
    
    public Identifier()
    {
        // Check for unused IDs in queue
        
        synchronized( queue )
        {
            if( !queue.isEmpty() )
            {
                myID = queue.poll();
            }
            else
            {
                myID = nextID;
                nextID += 1;
            }
        }
    }
    
    // COMPARE TO -------------------------------------------------------------
    
    public int compareTo( Identifier i )
    {
        if( this == i ) return 0;
        
        if( this.myID < i.myID ) return -1;
        if( this.myID > i.myID ) return 1;
        
        return 0;
    }
    
    // EQUALS -----------------------------------------------------------------
    
    public boolean equals( Object other )
    {
        if( this == other ) return true;
        
        return false;
    }
    
    // HASH CODE --------------------------------------------------------------
    
    public int hashCode()
    {
        return myID.intValue();
    }
    
    // TO STRING --------------------------------------------------------------
    
    public String toString()
    {
        return myID.toString();
    }
    
    // TO LONG ----------------------------------------------------------------
    
    public long toLong()
    {
        return myID.longValue();
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PROTECTED METHODS ++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // FINALIZE ---------------------------------------------------------------
    
    protected void finalize() throws Throwable
    {
        synchronized( queue )
        {
            queue.offer( myID );
        }
        
        super.finalize();
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE DATA +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // STATIC DATA ------------------------------------------------------------
    
    private static final ConcurrentLinkedQueue< Long > queue
        = new ConcurrentLinkedQueue< Long >();
    
    private static Long nextID = 0L;
    
    // INSTANCE DATA ----------------------------------------------------------
    
    private Long myID;
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
}

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
