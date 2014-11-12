package util.tests;

import java.util.Set;

import junit.framework.*;

import util.BiMultiHashMap;

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// BIDIRECTIONAL MULTI HASH MAP TEST ++++++++++++++++++++++++++++++++++++++++++
// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

public class BiMultiHashMapTest extends TestCase
{
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // CONSTRUCTORS -----------------------------------------------------------
    
    public BiMultiHashMapTest( String name )
    {
        super( name );
    }
    
    // TEST CONTAINS ----------------------------------------------------------
    
    @org.junit.Test
    public void testContains()
    {
        // Create map
        
        BiMultiHashMap< Integer, String > map
            = new BiMultiHashMap< Integer, String >();
        
        map.put( 1, "One" );
        map.put( 2, "Two" );
        map.put( 3, "Three" );
        
        // Test contains
        
        assertTrue( "01. Map contains key.", map.containsKey( 1 ) );
        assertTrue( "02. Map contains value.", map.containsValue( "One" ) );
        assertTrue( "03. Map contains entry.", map.contains( 1, "One" ) );
        
        map.put( 2, "One" );
        map.put( 1, "Two" );
        
        assertTrue( "04. Map contains entry.", map.contains( 1, "One" ) );
        assertTrue( "05. Map contains entry.", map.contains( 1, "Two" ) );
        assertTrue( "06. Map contains entry.", map.contains( 2, "One" ) );
        assertTrue( "07. Map contains entry.", map.contains( 2, "Two" ) );
    }
    
    // TEST HASH CODE ---------------------------------------------------------
    
    @org.junit.Test
    public void testHashCode()
    {
        // Create map
        
        BiMultiHashMap< Integer, String > map
            = new BiMultiHashMap< Integer, String >();
        
        map.put( 1, "One" );
        map.put( 2, "Two" );
        map.put( 3, "Three" );
        
        // Test hash code
        
        assertTrue( "01. Correct hash code.", map.hashCode() == 6 );
        
        map.put( 1, "Two" );
        
        assertTrue( "02. Correct hash code.", map.hashCode() == 7 );
        
        map.put( 1, "Two" );
        
        assertTrue( "03. Correct hash code.", map.hashCode() == 7 );
        
        map.remove( 2, "Two" );
        
        assertTrue( "04. Correct hash code.", map.hashCode() == 5 );
    }
    
    // TEST EQUALS ------------------------------------------------------------
    
    @org.junit.Test
    public void testEquals()
    {
        // Create maps
        
        BiMultiHashMap< Integer, String > map1
            = new BiMultiHashMap< Integer, String >();
        
        map1.put( 1, "One" );
        map1.put( 2, "Two" );
        map1.put( 3, "Three" );
        
        BiMultiHashMap< Integer, String > map2
            = new BiMultiHashMap< Integer, String >();
        
        map2.putAll( map1 );
        
        // Test equals
        
        assertTrue( "01. Maps are equal.", map1.equals( map2 ) );
        
        map2.remove( 3 );
        
        assertTrue( "02. Maps are not equal.", !( map1.equals( map2 ) ) );
    }
    
    // TEST REMOVE ------------------------------------------------------------
    
    @org.junit.Test
    public void testRemove()
    {
        // Create map
        
        BiMultiHashMap< Integer, String > map
            = new BiMultiHashMap< Integer, String >();
        
        map.put( 1, "One" );
        map.put( 2, "Two" );
        map.put( 3, "Three" );
        
        assertTrue( "01. Map is correct size.", map.size() == 3 );
        
        map.put( 2, "One" );
        map.put( 1, "Two" );
        
        assertTrue( "02. Map is correct size.", map.size() == 5 );
        
        Set< String > values = map.remove( 1 );
        
        assertTrue( "03. Correct values returned.", values.contains( "One" ) );
        assertTrue( "04. Correct values returned.", values.contains( "Two" ) );
        assertTrue( "05. Correct values returned.", values.size() == 2 );
        
        assertTrue( "06. Map is correct size.", map.size() == 3 );
        
        map.put( 1, "One" );
        
        boolean removed = map.remove( 2, "One" );
        
        assertTrue( "07. Entry removed.", removed );
        assertTrue( "08. Map doesn't contain entry.", !( map.contains( 1, "Two" ) ) );
        assertTrue( "09. Map doesn't contain entry.", !( map.contains( 2, "One" ) ) );
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
}

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
