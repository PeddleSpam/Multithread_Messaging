package util.tests;

import util.BiHashMap;

import junit.framework.*;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// BIDIRECTIONAL HASH MAP TEST CLASS ++++++++++++++++++++++++++++++++++++++++++
// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

public class BiHashMapTest extends TestCase
{
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // NESTED CLASSES +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // ENTRY SET TEST CLASS ---------------------------------------------------
    
    public static class EntrySetTest extends TestCase
    {
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // PUBLIC METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        
        // CONSTRUCTORS -------------------------------------------------------
        
        public EntrySetTest( String name )
        {
            super( name );
        }
        
        // TEST CONTAINS ------------------------------------------------------
        
        @org.junit.Test
        public void testContains()
        {
            BiHashMap< Integer, String > map = new BiHashMap< Integer, String >();
        
            map.put( 1, "One" );
            map.put( 2, "Two" );
            map.put( 3, "Three" );
            
            Set< Map.Entry< Integer, String > > set = map.entrySet();
            
            AbstractMap.SimpleEntry< Integer, String > entry1 = null;
            AbstractMap.SimpleEntry< Integer, String > entry2 = null;
            AbstractMap.SimpleEntry< Integer, String > entry3 = null;
            AbstractMap.SimpleEntry< Integer, String > entry4 = null;
            
            entry1 = new AbstractMap.SimpleEntry< Integer, String >( 1, "One" );
            entry2 = new AbstractMap.SimpleEntry< Integer, String >( 2, "Two" );
            entry3 = new AbstractMap.SimpleEntry< Integer, String >( 3, "Three" );
            entry4 = new AbstractMap.SimpleEntry< Integer, String >( 4, "Four" );
            
            // Test contains
            
            assertTrue( "01. Set contains entry.", set.contains( entry1 ) );
            assertTrue( "02. Set contains entry.", set.contains( entry2 ) );
            assertTrue( "03. Set contains entry.", set.contains( entry3 ) );
            assertTrue( "04. Set doesn't contain entry.", !( set.contains( entry4 ) ) );
        }
        
        // TEST CONTAINS ALL --------------------------------------------------
        
        @org.junit.Test
        public void testContainsAll()
        {
            BiHashMap< Integer, String > map = new BiHashMap< Integer, String >();
        
            map.put( 1, "One" );
            map.put( 2, "Two" );
            map.put( 3, "Three" );
            
            Set< Map.Entry< Integer, String > > set = map.entrySet();
            
            AbstractMap.SimpleEntry< Integer, String > entry1 = null;
            AbstractMap.SimpleEntry< Integer, String > entry2 = null;
            AbstractMap.SimpleEntry< Integer, String > entry3 = null;
            AbstractMap.SimpleEntry< Integer, String > entry4 = null;
            
            entry1 = new AbstractMap.SimpleEntry< Integer, String >( 1, "One" );
            entry2 = new AbstractMap.SimpleEntry< Integer, String >( 2, "Two" );
            entry3 = new AbstractMap.SimpleEntry< Integer, String >( 3, "Three" );
            entry4 = new AbstractMap.SimpleEntry< Integer, String >( 4, "Four" );
            
            // Test contains all
        
            HashSet< AbstractMap.SimpleEntry< Integer, String > > hashSet = 
                new HashSet< AbstractMap.SimpleEntry< Integer, String > >();
            
            hashSet.add( entry1 );
            hashSet.add( entry2 );
            hashSet.add( entry3 );
            
            assertTrue( "01. Set contains all entries.", set.containsAll( hashSet ) );
            
            hashSet.add( entry4 );
            
            assertTrue( "02. Set doesn't contain all entries.", !( set.containsAll( hashSet ) ) );
        }
        
        // TEST EQUALS --------------------------------------------------------
        
        @org.junit.Test
        public void testEquals()
        {
            BiHashMap< Integer, String > map = new BiHashMap< Integer, String >();
        
            map.put( 1, "One" );
            map.put( 2, "Two" );
            map.put( 3, "Three" );
            
            Set< Map.Entry< Integer, String > > set = map.entrySet();
            
            AbstractMap.SimpleEntry< Integer, String > entry1 = null;
            AbstractMap.SimpleEntry< Integer, String > entry2 = null;
            AbstractMap.SimpleEntry< Integer, String > entry3 = null;
            AbstractMap.SimpleEntry< Integer, String > entry4 = null;
            
            entry1 = new AbstractMap.SimpleEntry< Integer, String >( 1, "One" );
            entry2 = new AbstractMap.SimpleEntry< Integer, String >( 2, "Two" );
            entry3 = new AbstractMap.SimpleEntry< Integer, String >( 3, "Three" );
            entry4 = new AbstractMap.SimpleEntry< Integer, String >( 4, "Four" );
            
            HashSet< AbstractMap.SimpleEntry< Integer, String > > hashSet = 
            new HashSet< AbstractMap.SimpleEntry< Integer, String > >();
        
            hashSet.add( entry1 );
            hashSet.add( entry2 );
            hashSet.add( entry3 );
            
            // Test equals
        
            assertTrue( "01. Sets are equal.", set.equals( hashSet ) );
            
            hashSet.remove( entry3 );
            
            assertTrue( "02. Sets are not equal.", !( set.equals( hashSet ) ) );
            
            hashSet.add( entry4 );
            
            assertTrue( "03. Sets are not equal.", !( set.equals( hashSet ) ) );
        }
        
        // TEST HASH CODE -----------------------------------------------------
        
        @org.junit.Test
        public void testHashCode()
        {
            BiHashMap< Integer, String > map = new BiHashMap< Integer, String >();
        
            map.put( 1, "One" );
            map.put( 2, "Two" );
            map.put( 3, "Three" );
            
            Set< Map.Entry< Integer, String > > set = map.entrySet();
            
            // Test hash code
        
            assertTrue( "01. Hash code is correct.", map.hashCode() == set.hashCode() );
        }
        
        // TEST SIZE ----------------------------------------------------------
        
        @org.junit.Test
        public void testSize()
        {
            BiHashMap< Integer, String > map = new BiHashMap< Integer, String >();
            
            Set< Map.Entry< Integer, String > > set = map.entrySet();
            
            // Test empty
            
            assertTrue( "01. Set is empty.", set.isEmpty() );
            assertTrue( "02. Set is empty.", set.size() == 0 );
            
            // Add elements
            
            map.put( 1, "One" );
            map.put( 2, "Two" );
            map.put( 3, "Three" );
            
            // Test size
            
            assertTrue( "03. Set is correct size.", set.size() == 3 );
            
            set.remove( new AbstractMap.SimpleEntry< Integer, String >( 1, "One" ) );
            
            assertTrue( "04. Set is correct size.", set.size() == 2 );
        }
        
        // TEST ITERATOR ------------------------------------------------------
        
        @org.junit.Test
        public void testIterator()
        {
            BiHashMap< Integer, String > map = new BiHashMap< Integer, String >();
        
            map.put( 1, "One" );
            map.put( 2, "Two" );
            map.put( 3, "Three" );
            
            Set< Map.Entry< Integer, String > > set = map.entrySet();
            
            // Test iteration
            
            Iterator< Map.Entry< Integer, String > > it = set.iterator();
        
            int elementCount = 0;
            
            while( it.hasNext() )
            {
                it.next();
                elementCount += 1;
            }
            
            assertTrue( "01. Correct iteration count.", elementCount == 3 );
            
            // Test remove
            
            Map.Entry< Integer, String > entry = null;
            
            it = set.iterator();
            
            assertTrue( "02. Iterator has next element.", it.hasNext() );
            
            entry = it.next();
            it.remove();
                
            assertTrue( "03. Correct entry removed.", !( set.contains( entry ) ) );
        }
        
        // TEST REMOVE --------------------------------------------------------
        
        @org.junit.Test
        public void testRemove()
        {
            BiHashMap< Integer, String > map = new BiHashMap< Integer, String >();
        
            map.put( 1, "One" );
            map.put( 2, "Two" );
            map.put( 3, "Three" );
            
            boolean success = false;
            
            Set< Map.Entry< Integer, String > > set = map.entrySet();
            
            AbstractMap.SimpleEntry< Integer, String > entry1 = null;
            AbstractMap.SimpleEntry< Integer, String > entry4 = null;
            
            entry1 = new AbstractMap.SimpleEntry< Integer, String >( 1, "One" );
            entry4 = new AbstractMap.SimpleEntry< Integer, String >( 4, "Four" );
            
            // Test remove
        
            success = set.remove( entry1 );
            
            assertTrue( "01. Entry was removed.", success == true && !( set.contains( entry1 ) ) );
            
            success = set.remove( entry4 );
            
            assertTrue( "02. Entry was not removed.", success == false && !( set.contains( entry4 ) ) );
        }
        
        // TEST REMOVE ALL ----------------------------------------------------
        
        @org.junit.Test
        public void testRemoveAll()
        {
            BiHashMap< Integer, String > map = new BiHashMap< Integer, String >();
        
            map.put( 1, "One" );
            map.put( 2, "Two" );
            map.put( 3, "Three" );
            
            boolean success = false;
            
            Set< Map.Entry< Integer, String > > set = map.entrySet();
            
            AbstractMap.SimpleEntry< Integer, String > entry1 = null;
            AbstractMap.SimpleEntry< Integer, String > entry2 = null;
            AbstractMap.SimpleEntry< Integer, String > entry3 = null;
            AbstractMap.SimpleEntry< Integer, String > entry4 = null;
            
            entry1 = new AbstractMap.SimpleEntry< Integer, String >( 1, "One" );
            entry2 = new AbstractMap.SimpleEntry< Integer, String >( 2, "Two" );
            entry3 = new AbstractMap.SimpleEntry< Integer, String >( 3, "Three" );
            entry4 = new AbstractMap.SimpleEntry< Integer, String >( 4, "Four" );
            
            HashSet< AbstractMap.SimpleEntry< Integer, String > > hashSet = 
            new HashSet< AbstractMap.SimpleEntry< Integer, String > >();
        
            hashSet.add( entry1 );
            hashSet.add( entry3 );
            
            // Test remove all
            
            success = set.removeAll( hashSet );
        
            assertTrue( "01. All entries removed.", success == true );
            assertTrue( "02. All entries removed.", !( set.contains( entry1 ) ) );
            assertTrue( "03. All entries removed.", !( set.contains( entry3 ) ) );
            assertTrue( "04. All entries removed.", !( set.contains( entry4 ) ) );
            assertTrue( "05. All entries removed.", set.contains( entry2 ) );
            assertTrue( "06. All entries removed.", set.size() == 1 );
            assertTrue( "07. All entries removed.", map.size() == 1 );
        }
        
        // TEST RETAIN ALL ----------------------------------------------------
        
        @org.junit.Test
        public void testRetainAll()
        {
            BiHashMap< Integer, String > map = new BiHashMap< Integer, String >();
        
            map.put( 1, "One" );
            map.put( 2, "Two" );
            map.put( 3, "Three" );
            
            boolean success = false;
            
            Set< Map.Entry< Integer, String > > set = map.entrySet();
            
            AbstractMap.SimpleEntry< Integer, String > entry1 = null;
            AbstractMap.SimpleEntry< Integer, String > entry2 = null;
            AbstractMap.SimpleEntry< Integer, String > entry3 = null;
            
            entry1 = new AbstractMap.SimpleEntry< Integer, String >( 1, "One" );
            entry2 = new AbstractMap.SimpleEntry< Integer, String >( 2, "Two" );
            entry3 = new AbstractMap.SimpleEntry< Integer, String >( 3, "Three" );
            
            HashSet< AbstractMap.SimpleEntry< Integer, String > > hashSet = 
            new HashSet< AbstractMap.SimpleEntry< Integer, String > >();
        
            hashSet.add( entry1 );
            hashSet.add( entry3 );
            
            // Test retain all
            
            success = set.retainAll( hashSet );
        
            assertTrue( "01. Correct entries retained.", success );
            assertTrue( "22. Correct entries retained.", !( set.contains( entry2 ) ) );
            assertTrue( "23. Correct entries retained.", set.size() == 2 );
        }
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // CONSTRUCTORS -----------------------------------------------------------
    
    public BiHashMapTest( String name )
    {
        super( name );
    }
    
    // TEST PUT ---------------------------------------------------------------
    
    @org.junit.Test
    public void testPut()
    {
        BiHashMap< Integer, String > map = new BiHashMap< Integer, String >();
        
        Integer key = null;
        String value = null;
        
        // Add entry to map and ensure its existence
        
        value = map.put( 1, "One" );
        
        assertTrue( "01. Old value is null.", value == null );
        assertTrue( "02. Map contains key.", map.containsKey( 1 ) );
        assertTrue( "03. Map contains value.", map.containsValue( "One" ) );
        
        value = map.get( 1 );
        key = map.inverse().get( "One" );
        
        assertTrue( "04. Correct value for key.", value != null && value.equals( "One" ) );
        assertTrue( "05. Correct key for value.", key != null && key.equals( 1 ) );
        
        // Add overriding entry to map and test again
        
        value = map.put( 1, "Two" );
        
        assertTrue( "06. Old value returned.", value != null && value.equals( "One" ) );
        assertTrue( "07. Map contains key.", map.containsKey( 1 ) );
        assertTrue( "08. Map contains value.", map.containsValue( "Two" ) );
        assertTrue( "09. Map doesn't contain value.", !( map.containsValue( "One" ) ) );
        
        value = map.get( 1 );
        key = map.inverse().get( "Two" );
        
        assertTrue( "10. Correct value for key.", value != null && value.equals( "Two" ) );
        assertTrue( "11. Correct key for value.", key != null && key.equals( 1 ) );
        
        // Add overriding entry to map and test again
        
        value = map.put( 2, "Two" );
        
        assertTrue( "12. Old value is null.", value == null );
        assertTrue( "13. Map contains key.", map.containsKey( 2 ) );
        assertTrue( "14. Map contains value.", map.containsValue( "Two" ) );
        assertTrue( "15. Map doesn't contain key.", !( map.containsKey( 1 ) ) );
        
        value = map.get( 2 );
        key = map.inverse().get( "Two" );
        
        assertTrue( "16. Correct value for key.", value != null && value.equals( "Two" ) );
        assertTrue( "17. Correct key for value.", key != null && key.equals( 2 ) );
    }
    
    // TEST PUT ALL -----------------------------------------------------------
    
    @org.junit.Test
    public void testPutAll()
    {
        BiHashMap< Integer, String > biMap = new BiHashMap< Integer, String >();
        HashMap< Integer, String > map = new HashMap< Integer, String >();
        
        // Add entries to [map]
        
        map.put( 1, "One" );
        map.put( 2, "Two" );
        map.put( 3, "Three" );
        
        String value = null;
        
        // Put all entries in [biMap]
        
        biMap.putAll( map );
        
        // Ensure all entries exist in [biMap]
        
        value = biMap.get( 1 );
        
        assertTrue( "01. Correct value for key.", value != null && value.equals( "One" ) );
        
        value = biMap.get( 2 );
        
        assertTrue( "02. Correct value for key.", value != null && value.equals( "Two" ) );
        
        value = biMap.get( 3 );
        
        assertTrue( "03. Correct value for key.", value != null && value.equals( "Three" ) );
        assertTrue( "04. Map is correct size.", biMap.size() == 3 );
    }
    
    // TEST EQUALS ------------------------------------------------------------
    
    @org.junit.Test
    public void testEquals()
    {
        BiHashMap< Integer, String > map1 = new BiHashMap< Integer, String >();
        map1.put( 1, "One" );
        map1.put( 2, "Two" );
        
        BiHashMap< Integer, String > map2 = new BiHashMap< Integer, String >();
        map2.put( 1, "One" );
        map2.put( 2, "Two" );
        
        assertTrue( "01. Maps are equal", map1.equals( map2 ) );
        assertTrue( "02. Maps are equal", map2.equals( map1 ) );
        
        BiHashMap< Integer, String > map3
            = new BiHashMap< Integer, String >( map1 );
        
        assertTrue( "03. Maps are equal", map2.equals( map3 ) );
    }
    
    // TEST GET ---------------------------------------------------------------
    
    @org.junit.Test
    public void testGet()
    {
        BiHashMap< Integer, String > map = new BiHashMap< Integer, String >();
        
        Integer key = null;
        String value = null;
        
        map.put( 1, "One" );
        
        key = map.inverse().get( "One" );
        value = map.get( 1 );
        
        assertTrue( "01. Correct key returned.", key != null && key.equals( 1 ) );
        assertTrue( "02. Correct value returned.", value != null && value.equals( "One" ) );
    }
    
    // TEST HASH CODE ---------------------------------------------------------
    
    @org.junit.Test
    public void testHashCode()
    {
        BiHashMap< Integer, String > map = new BiHashMap< Integer, String >();
        
        assertTrue( "01. Correct hash code.", map.hashCode() == 0 );
        
        map.put( 1, "One" );
        
        assertTrue( "02. Correct hash code.", map.hashCode() == 1 );
        
        map.put( 2, "Two" );
        
        assertTrue( "03. Correct hash code.", map.hashCode() == 3 );
        
        map.remove( 1 );
        
        assertTrue( "04. Correct hash code.", map.hashCode() == 2 );
    }
    
    // TEST SIZE --------------------------------------------------------------
    
    @org.junit.Test
    public void testSize()
    {
        BiHashMap< Integer, String > map = new BiHashMap< Integer, String >();
        
        assertTrue( "01. Map is empty.", map.isEmpty() );
        
        map.put( 1, "One" );
        
        assertTrue( "02. Correct element count.", map.size() == 1 );
        
        map.put( 1, "Two" );
        
        assertTrue( "03. Correct element count.", map.size() == 1 );
        
        map.put( 2, "Two" );
        map.put( 1, "One" );
        
        assertTrue( "04. Correct element count.", map.size() == 2 );
        
        map.put( 3, "Three" );
        
        assertTrue( "05. Correct element count.", map.size() == 3 );
        
        map.remove( 1 );
        
        assertTrue( "06. Correct element count.", map.size() == 2 );
    }
    
    // TEST REMOVE ------------------------------------------------------------
    
    @org.junit.Test
    public void testRemove()
    {
        BiHashMap< Integer, String > map = new BiHashMap< Integer, String >();
        
        String value = null;
        Integer key = null;
        
        // Add entries
        
        map.put( 1, "One" );
        map.put( 2, "Two" );
        map.put( 3, "Three" );
        
        // Remove and test contents
        
        value = map.remove( 1 );
        
        assertTrue( "01. Correct value returned.", value != null && value.equals( "One" ) );
        
        value = map.get( 2 );
        key = map.inverse().get( "Two" );
        
        assertTrue( "02. Correct value returned.", value != null && value.equals( "Two" ) );
        assertTrue( "03. Correct key returned.", key != null && key.equals( 2 ) );
        
        map.put( 1, "One" );
        
        key = map.inverse().remove( "Two" );
        
        assertTrue( "04. Correct key returned.", key != null && key.equals( 2 ) );
        assertTrue( "05. Map doesn't contain value.", !( map.containsValue( "Two" ) ) );
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
}

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
