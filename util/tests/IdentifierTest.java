package util.tests;

import util.Identifier;
import junit.framework.*;

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// IDENTIFIER TEST CLASS ++++++++++++++++++++++++++++++++++++++++++++++++++++++
// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

public class IdentifierTest extends TestCase
{
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // CONSTRUCTORS -----------------------------------------------------------
    
    public IdentifierTest( String name )
    {
        super( name );
    }
    
    // TEST CREATE ------------------------------------------------------------
    
    @org.junit.Test
    public void testCreate()
    {
        Identifier id1 = new Identifier();
        Identifier id2 = new Identifier();
        Identifier id3 = id1;
        
        assertTrue( "IDs are equal", id1.equals( id3 ) );
        assertTrue( "IDs are not equal", !id1.equals( id2 ) );
        assertTrue( "ID to long is 0L", id1.toLong() == 0L );
        assertTrue( "ID to long is 1L", id2.toLong() == 1L );
        assertTrue( "Correct ID comparison", id1.compareTo( id2 ) < 0 );
        assertTrue( "Correct ID comparison", id2.compareTo( id1 ) > 0 );
        assertTrue( "Correct ID comparison", id1.compareTo( id3 ) == 0 );
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
}

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
