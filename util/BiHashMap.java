package util;

import java.util.NoSuchElementException;
import java.util.Collection;
import java.util.Iterator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// BIDIRECTIONAL HASH MAP CLASS +++++++++++++++++++++++++++++++++++++++++++++++
// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

public class BiHashMap< K, V >
implements BiMap< K, V >
{
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // NESTED CLASSES +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // ENTRY SET CLASS --------------------------------------------------------
    
    protected class EntrySet
    implements Set< Map.Entry< K, V > >
    {
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // NESTED CLASSES +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        
        // ENTRY SET ITERATOR CLASS -------------------------------------------
        
        protected class EntrySetIterator
        implements Iterator< Map.Entry< K, V > >
        {
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-
            // PUBLIC METHODS +++++++++++++++++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            
            // CONSTRUCTORS ---------------------------------------------------
            
            public EntrySetIterator()
            {
                this.iterator = BiHashMap.this.mapKV.entrySet().iterator();
                this.lastEntry = null;
                this.nextCalled = false;
            }
            
            // HAS NEXT -------------------------------------------------------
            
            @Override
            public boolean hasNext()
            {
                return iterator.hasNext();
            }
            
            // NEXT -----------------------------------------------------------
            
            @Override
            public Map.Entry< K, V > next()
            throws NoSuchElementException
            {
                if( iterator.hasNext() )
                {
                    lastEntry = iterator.next();
                    nextCalled = true;
                    
                    return lastEntry;
                }
                else
                {
                    throw new NoSuchElementException();
                }
            }
            
            // REMOVE ---------------------------------------------------------
            
            @Override
            public void remove()
            throws IllegalStateException
            {
                if( !nextCalled )
                {
                    throw new IllegalStateException();
                }
                
                EntrySet.this.remove( lastEntry );
                nextCalled = false;
                lastEntry = null;
            }
            
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            // PRIVATE DATA +++++++++++++++++++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            
            private boolean nextCalled;
            
            private Map.Entry< K, V > lastEntry;
            
            private Iterator< Map.Entry< K, V > > iterator;
            
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        }
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // PUBLIC METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        
        // CONSTRUCTORS -------------------------------------------------------
        
        public EntrySet()
        {
            // empty
        }
        
        // ADD ----------------------------------------------------------------
        
        @Override
        public boolean add( Map.Entry< K, V > entry )
        {
            return false;
        }
        
        // ADD ALL ------------------------------------------------------------
        
        @Override
        public boolean addAll( Collection< ? extends Map.Entry< K, V > > collection )
        {
            return false;
        }
        
        // CLEAR --------------------------------------------------------------
        
        @Override
        public void clear()
        {
            BiHashMap.this.clear();
        }
        
        // CONTAINS -----------------------------------------------------------
        
        @Override
        @SuppressWarnings( "unchecked" )
        public boolean contains( Object object )
        {
            Map.Entry< K, V > entry = null;
                
            K key = null;
            V value = null;
            
            // Get key and value from entry
            
            try
            {
                entry = ( Map.Entry< K, V > )object;
                
                key = entry.getKey();
                value = entry.getValue();
            }
            catch( Throwable t )
            {
                return false;
            }
            
            // Check for key in map
                
            if( BiHashMap.this.containsKey( key ) )
            {
                // Get mapped value for key
                
                V mappedValue = BiHashMap.this.get( key );
                
                // Return true if values are equivalent
                
                if( value == mappedValue || ( value != null && value.equals( mappedValue ) ) )
                {
                    return true;
                }
            }
            
            return false;
        }
        
        // CONTAINS ALL -------------------------------------------------------
        
        @Override
        @SuppressWarnings( "unchecked" )
        public boolean containsAll( Collection< ? > collection )
        {
            // Obtain iterator for collection
            
            Iterator< Map.Entry< K, V > > it = null;
            
            try
            {
                it = ( Iterator< Map.Entry< K, V > > )collection.iterator();
            }
            catch( Throwable t )
            {
                return false;
            }
            
            // Check every entry against this set
            
            while( it.hasNext() )
            {
                if( !( this.contains( it.next() ) ) )
                {
                    return false;
                }
            }
            
            // All entries were found, return true
            
            return true;
        }
        
        // EQUALS -------------------------------------------------------------
        
        @Override
        @SuppressWarnings( "unchecked" )
        public boolean equals( Object object )
        {
            // Check for null parameter
            
            if( object == null )
            {
                return false;
            }
            
            // Cast parameter to required type
            
            Collection< Map.Entry< K, V > > entries = null;
            
            try
            {
                entries = ( Collection< Map.Entry< K, V > > )object;
            }
            catch( Throwable t )
            {
                return false;
            }
            
            // Check sizes match
            
            if( entries.size() != BiHashMap.this.size() )
            {
                return false;
            }
            
            // Check all elements match and return result
            
            return this.containsAll( entries );
        }
        
        // HASH CODE ----------------------------------------------------------
        
        @Override
        public int hashCode()
        {
            return BiHashMap.this.hashCode();
        }
        
        // IS EMPTY -----------------------------------------------------------
        
        @Override
        public boolean isEmpty()
        {
            return BiHashMap.this.isEmpty();
        }
        
        // ITERATOR -----------------------------------------------------------
        
        @Override
        public Iterator< Map.Entry< K, V > > iterator()
        {
            return new EntrySetIterator();
        }
        
        // REMOVE -------------------------------------------------------------
        
        @Override
        @SuppressWarnings( "unchecked" )
        public boolean remove( Object object )
        {
            // Ensure entry exists in map
            
            if( !( this.contains( object ) ) )
            {
                return false;
            }
            
            // Remove entry from map
            
            BiHashMap.this.remove( ( ( Map.Entry< K, V > )object ).getKey() );
            
            return true;
        }
        
        // REMOVE ALL ---------------------------------------------------------
        
        @Override
        @SuppressWarnings( "unchecked" )
        public boolean removeAll( Collection< ? > collection )
        {
            // Obtain iterator for collection
            
            Iterator< Map.Entry< K, V > > it = null;
            
            try
            {
                it = ( Iterator< Map.Entry< K, V > > )collection.iterator();
            }
            catch( Throwable t )
            {
                return false;
            }
            
            // Remove any entries found in this set
            
            int removeCount = 0;
            
            while( it.hasNext() )
            {
                // Increment counter if entry was removed
                
                if( this.remove( it.next() ) )
                {
                    removeCount += 1;
                }
            }
            
            // Return true if any entries were removed
            
            if( removeCount > 0 )
            {
                return true;
            }
            
            return false;
        }
        
        // RETAIN ALL ---------------------------------------------------------
        
        @Override
        @SuppressWarnings( "unchecked" )
        public boolean retainAll( Collection< ? > collection )
        {
            // Check for null parameter
            
            if( collection == null )
            {
                return false;
            }
            
            // Cast collection to required type
            
            Collection< Map.Entry< K, V > > entries = null;
            
            try
            {
                entries = ( Collection< Map.Entry< K, V > > )collection;
            }
            catch( Throwable t )
            {
                return false;
            }
            
            // Create set to store found entries
            
            HashSet< Map.Entry< K, V > > foundEntries = new HashSet< Map.Entry< K, V > >();
            
            // Obtain iterator to map's entry set
            
            Iterator< Map.Entry< K, V > > it = BiHashMap.this.mapKV.entrySet().iterator();
            
            // Iterate over entries in map
            
            Map.Entry< K, V > entry = null;
            
            while( it.hasNext() )
            {
                // Get next entry
                
                entry = it.next();
                
                // Add entry to found set if not in collection
                
                if( !( entries.contains( entry ) ) )
                {
                    foundEntries.add( entry );
                }
            }
            
            // Remove found entries from this set and return result
            
            return this.removeAll( foundEntries );
        }
        
        // SIZE ---------------------------------------------------------------
        
        @Override
        public int size()
        {
            return BiHashMap.this.size();
        }
        
        // TO ARRAY -----------------------------------------------------------
        
        @Override
        public Object[] toArray()
        {
            return BiHashMap.this.mapKV.entrySet().toArray();
        }
        
        @Override
        public < T > T[] toArray( T[] array )
        {
            return BiHashMap.this.mapKV.entrySet().toArray( array );
        }
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    }
    
    // KEY SET CLASS ----------------------------------------------------------
    
    protected class KeySet
    implements Set< K >
    {
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // NESTED CLASSES +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        
        // KEY SET ITERATOR CLASS ---------------------------------------------
        
        protected class KeySetIterator
        implements Iterator< K >
        {
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            // PUBLIC METHODS +++++++++++++++++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            
            // CONSTRUCTORS ---------------------------------------------------
            
            public KeySetIterator()
            {
                nextCalled = false;
                iterator = BiHashMap.this.mapKV.keySet().iterator();
                lastKey = null;
            }
            
            // HAS NEXT -------------------------------------------------------
            
            @Override
            public boolean hasNext()
            {
                return iterator.hasNext();
            }
            
            // NEXT -----------------------------------------------------------
            
            @Override
            public K next()
            throws NoSuchElementException
            {
                if( iterator.hasNext() )
                {
                    lastKey = iterator.next();
                    nextCalled = true;
                    
                    return lastKey;
                }
                else
                {
                    throw new NoSuchElementException();
                }
            }
            
            // REMOVE ---------------------------------------------------------
            
            @Override
            public void remove()
            throws IllegalStateException
            {
                if( !nextCalled )
                {
                    throw new IllegalStateException();
                }
                
                BiHashMap.this.remove( lastKey );
                nextCalled = false;
                lastKey = null;
            }
            
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            // PRIVATE DATA +++++++++++++++++++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            
            private K lastKey;
            
            private boolean nextCalled;
            
            private Iterator< K > iterator;
            
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        }
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // PUBLIC METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        
        // CONSTRUCTORS -------------------------------------------------------
        
        public KeySet()
        {
            // empty
        }
        
        // ADD ----------------------------------------------------------------
        
        @Override
        public boolean add( K key )
        {
            return false;
        }
        
        // ADD ALL ------------------------------------------------------------
        
        @Override
        public boolean addAll( Collection< ? extends K > collection )
        {
            return false;
        }
        
        // CLEAR --------------------------------------------------------------
        
        @Override
        public void clear()
        {
            BiHashMap.this.clear();
        }
        
        // CONTAINS -----------------------------------------------------------
        
        @Override
        public boolean contains( Object key )
        {
            return BiHashMap.this.containsKey( key );
        }
        
        // CONTAINS ALL -------------------------------------------------------
        
        @Override
        @SuppressWarnings( "unchecked" )
        public boolean containsAll( Collection< ? > collection )
        {
            // Obtain iterator for collection
            
            Iterator< K > it = null;
            
            try
            {
                it = ( Iterator< K > )collection.iterator();
            }
            catch( Throwable t )
            {
                return false;
            }
            
            // Iterate through keys in collection
            
            while( it.hasNext() )
            {
                // Return false if key was not found in map
                
                if( !( BiHashMap.this.containsKey( it.next() ) ) )
                {
                    return false;
                }
            }
            
            return true;
        }
        
        // EQUALS -------------------------------------------------------------
        
        @Override
        @SuppressWarnings( "unchecked" )
        public boolean equals( Object object )
        {
            // Check for null parameter
            
            if( object == null )
            {
                return false;
            }
            
            // Cast object to required type
            
            Collection< K > keys = null;
            
            try
            {
                keys = ( Collection< K > )object;
            }
            catch( Throwable t )
            {
                return false;
            }
            
            // Check sizes match
            
            if( keys.size() != BiHashMap.this.size() )
            {
                return false;
            }
            
            // Check all elements match and return result
            
            return this.containsAll( keys );
        }
        
        // HASH CODE ----------------------------------------------------------
        
        @Override
        public int hashCode()
        {
            return BiHashMap.this.hashCode();
        }
        
        // IS EMPTY -----------------------------------------------------------
        
        @Override
        public boolean isEmpty()
        {
            return BiHashMap.this.isEmpty();
        }
        
        // ITERATOR -----------------------------------------------------------
        
        @Override
        public Iterator< K > iterator()
        {
            return new KeySetIterator();
        }
        
        // REMOVE -------------------------------------------------------------
        
        @Override
        public boolean remove( Object object )
        {
            if( BiHashMap.this.containsKey( object ) )
            {
                BiHashMap.this.remove( object );
                
                return true;
            }
            
            return false;
        }
        
        // REMOVE ALL ---------------------------------------------------------
        
        @Override
        @SuppressWarnings( "unchecked" )
        public boolean removeAll( Collection< ? > collection )
        {
            // Obtain iterator for collection
            
            Iterator< K > it = null;
            
            try
            {
                it = ( Iterator< K > )collection.iterator();
            }
            catch( Throwable t )
            {
                return false;
            }
            
            // Iterate through keys in collection
            
            int removeCount = 0;
            
            while( it.hasNext() )
            {
                // Increment counter if key was removed
                
                if( this.remove( it.next() ) )
                {
                    removeCount += 1;
                }
            }
            
            // Return true if any keys were removed
            
            if( removeCount > 0 )
            {
                return true;
            }
            
            return false;
        }
        
        // RETAIN ALL ---------------------------------------------------------
        
        @Override
        @SuppressWarnings( "unchecked" )
        public boolean retainAll( Collection< ? > collection )
        {
            // Check for null parameter
            
            if( collection == null )
            {
                return false;
            }
            
            // Cast collection to required type
            
            Collection< K > keys = null;
            
            try
            {
                keys = ( Collection< K > )collection;
            }
            catch( Throwable t )
            {
                return false;
            }
            
            // Create set to store found keys
            
            HashSet< K > foundKeys = new HashSet< K >();
            
            // Obtain iterator to map's key set
            
            Iterator< K > it = BiHashMap.this.mapKV.keySet().iterator();
            
            // Iterate over keys in map
            
            K key = null;
            
            while( it.hasNext() )
            {
                // Get next key
                
                key = it.next();
                
                // Add key to found set if not found in collection
                
                if( !( keys.contains( key ) ) )
                {
                    foundKeys.add( key );
                }
            }
            
            // Remove found keys from this set and return result
            
            return this.removeAll( foundKeys );
        }
        
        // SIZE ---------------------------------------------------------------
        
        @Override
        public int size()
        {
            return BiHashMap.this.size();
        }
        
        // TO ARRAY -----------------------------------------------------------
        
        @Override
        public Object[] toArray()
        {
            return BiHashMap.this.mapKV.keySet().toArray();
        }
        
        @Override
        public < T > T[] toArray( T[] array )
        {
            return BiHashMap.this.mapKV.keySet().toArray( array );
        }
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    }
    
    // VALUE COLLECTION CLASS -------------------------------------------------
    
    protected class ValueCollection
    implements Collection< V >
    {
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // NESTED CLASSES +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        
        // VALUE COLLECTION ITERATOR CLASS ------------------------------------
        
        protected class ValueCollectionIterator
        implements Iterator< V >
        {
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            // PUBLIC METHODS +++++++++++++++++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            
            // CONSTRUCTORS ---------------------------------------------------
            
            public ValueCollectionIterator()
            {
                lastValue = null;
                nextCalled = false;
                iterator = BiHashMap.this.mapVK.keySet().iterator();;
            }
            
            // HAS NEXT -------------------------------------------------------
            
            @Override
            public boolean hasNext()
            {
                return iterator.hasNext();
            }
            
            // NEXT -----------------------------------------------------------
            
            @Override
            public V next()
            throws NoSuchElementException
            {
                // Check for next element
                
                if( iterator.hasNext() )
                {
                    lastValue = iterator.next();
                    nextCalled = true;
                    return lastValue;
                }
                else
                {
                    throw new NoSuchElementException();
                }
            }
            
            // REMOVE ---------------------------------------------------------
            
            @Override
            public void remove()
            throws IllegalStateException
            {
                if( nextCalled )
                {
                    ValueCollection.this.remove( lastValue );
                    nextCalled = false;
                    lastValue = null;
                }
                else
                {
                    throw new IllegalStateException();
                }
            }
            
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            // PRIVATE DATA +++++++++++++++++++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            
            private Iterator< V > iterator;
            
            private boolean nextCalled;
            
            private V lastValue;
            
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        }
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // PUBLIC METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        
        // CONSTRUCTORS -------------------------------------------------------
        
        public ValueCollection()
        {
            inverseMap = BiHashMap.this.inverse();
        }
        
        // ADD ----------------------------------------------------------------
        
        @Override
        public boolean add( V val )
        {
            return false;
        }
        
        // ADD ALL ------------------------------------------------------------
        
        @Override
        public boolean addAll( Collection< ? extends V > collection )
        {
            return false;
        }
        
        // CLEAR --------------------------------------------------------------
        
        @Override
        public void clear()
        {
            inverseMap.clear();
        }
        
        // CONTAINS -----------------------------------------------------------
        
        @Override
        public boolean contains( Object object )
        {
            return inverseMap.containsKey( object );
        }
        
        // CONTAINS ALL -------------------------------------------------------
        
        @Override
        @SuppressWarnings( "unchecked" )
        public boolean containsAll( Collection< ? > collection )
        {
            // Obtain iterator for collection
            
            Iterator< V > it = null;
            
            try
            {
                it = ( Iterator< V > )collection.iterator();
            }
            catch( Throwable t )
            {
                return false;
            }
            
            // Check all values in collection against map
            
            while( it.hasNext() )
            {
                if( !( inverseMap.containsKey( it.next() ) ) )
                {
                    return false;
                }
            }
            
            // All values were found in map, return true
            
            return true;
        }
        
        // EQUALS -------------------------------------------------------------
        
        @Override
        @SuppressWarnings( "unchecked" )
        public boolean equals( Object object )
        {
            // Check for null parameter
            
            if( object == null )
            {
                return false;
            }
            
            // Cast object to required type
            
            Collection< V > values = null;
            
            try
            {
                values = ( Collection< V > )object;
            }
            catch( Throwable t )
            {
                return false;
            }
            
            // Check sizes match
            
            if( values.size() != inverseMap.size() )
            {
                return false;
            }
            
            // Check all elements match and return result
            
            return this.containsAll( values );
        }
        
        // HASH CODE ----------------------------------------------------------
        
        @Override
        public int hashCode()
        {
            return inverseMap.hashCode();
        }
        
        // IS EMPTY -----------------------------------------------------------
        
        @Override
        public boolean isEmpty()
        {
            return inverseMap.isEmpty();
        }
        
        // ITERATOR -----------------------------------------------------------
        
        @Override
        public Iterator< V > iterator()
        {
            return new ValueCollectionIterator();
        }
        
        // REMOVE -------------------------------------------------------------
        
        @Override
        public boolean remove( Object object )
        {
            if( inverseMap.containsKey( object ) )
            {
                inverseMap.remove( object );
                
                return true;
            }
            
            return false;
        }
        
        // REMOVE ALL ---------------------------------------------------------
        
        @Override
        @SuppressWarnings( "unchecked" )
        public boolean removeAll( Collection< ? > collection )
        {
            // Get iterator for collection
            
            Iterator< V > it = null;
            
            try
            {
                it = ( Iterator< V > )collection.iterator();
            }
            catch( Throwable t )
            {
                return false;
            }
            
            // Iterate over values in collection
            
            int removeCount = 0;
            
            while( it.hasNext() )
            {
                // Increment remove count if value was removed
                
                if( this.remove( it.next() ) )
                {
                    removeCount += 1;
                }
            }
            
            // Return true if any values were removed
            
            if( removeCount > 0 )
            {
                return true;
            }
            
            return false;
        }
        
        // RETAIN ALL ---------------------------------------------------------
        
        @Override
        @SuppressWarnings( "unchecked" )
        public boolean retainAll( Collection< ? > collection )
        {
            // Check for null parameter
            
            if( collection == null )
            {
                return false;
            }
            
            // Cast collection to required type
            
            Collection< V > values = null;
            
            try
            {
                values = ( Collection< V > )collection;
            }
            catch( Throwable t )
            {
                return false;
            }
            
            // Create set to store found values
            
            HashSet< V > foundValues = new HashSet< V >();
            
            // Obtain iterator to map's value set
            
            Iterator< V > it = inverseMap.keySet().iterator();
            
            // Iterate over all values in map
            
            V value = null;
            
            while( it.hasNext() )
            {
                // Get next value
                
                value = it.next();
                
                // Add value to found set if not in collection
                
                if( !( values.contains( value ) ) )
                {
                    foundValues.add( value );
                }
            }
            
            // Remove all found values from map and return result
            
            return this.removeAll( foundValues );
        }
        
        // SIZE ---------------------------------------------------------------
        
        @Override
        public int size()
        {
            return inverseMap.size();
        }
        
        // TO ARRAY -----------------------------------------------------------
        
        @Override
        public Object[] toArray()
        {
            return BiHashMap.this.mapKV.values().toArray();
        }
        
        @Override
        public < T > T[] toArray( T[] array )
        {
            return BiHashMap.this.mapKV.values().toArray( array );
        }
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // PRIVATE DATA +++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        
        private BiMap< V, K > inverseMap;
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // CONSTRUCTORS -----------------------------------------------------------
    
    public BiHashMap()
    {
        kHash = new Integer( 0 );
        vHash = new Integer( 0 );
        
        mapKV = new HashMap< K, V >();
        mapVK = new HashMap< V, K >();
    }
    
    public BiHashMap( int initialCapacity )
    throws IllegalArgumentException
    {
        kHash = new Integer( 0 );
        vHash = new Integer( 0 );
        
        mapKV = new HashMap< K, V >( initialCapacity );
        mapVK = new HashMap< V, K >( initialCapacity );
    }
    
    public BiHashMap( int initialCapacity, float loadFactor )
    throws IllegalArgumentException
    {
        kHash = new Integer( 0 );
        vHash = new Integer( 0 );
        
        mapKV = new HashMap< K, V >( initialCapacity, loadFactor );
        mapVK = new HashMap< V, K >( initialCapacity, loadFactor );
    }
    
    public BiHashMap( BiHashMap< K, V > map )
    throws NullPointerException
    {
        // Check for null parameter
        
        if( map == null )
        {
            kHash = new Integer( 0 );
            vHash = new Integer( 0 );
            
            mapKV = new HashMap< K, V >();
            mapVK = new HashMap< V, K >();
            
            throw new NullPointerException();
        }
        else
        {
            kHash = new Integer( map.kHash );
            vHash = new Integer( map.vHash );
            
            mapKV = new HashMap< K, V >( map.mapKV );
            mapVK = new HashMap< V, K >( map.mapVK );
        }
    }
    
    // CLEAR ------------------------------------------------------------------
    
    @Override
    public void clear()
    {
        kHash = new Integer( 0 );
        vHash = new Integer( 0 );
        
        mapKV.clear();
        mapVK.clear();
    }
    
    // CONTAINS KEY -----------------------------------------------------------
    
    @Override
    public boolean containsKey( Object key )
    {
        return mapKV.containsKey( key );
    }
    
    // CONTAINS VALUE ---------------------------------------------------------
    
    @Override
    public boolean containsValue( Object value )
    {
        return mapVK.containsKey( value );
    }
    
    // ENTRY SET --------------------------------------------------------------
    
    @Override
    public Set< Map.Entry< K, V > > entrySet()
    {
        return new EntrySet();
    }
    
    // EQUALS -----------------------------------------------------------------
    
    @Override
    @SuppressWarnings( "unchecked" )
    public boolean equals( Object object )
    {
        try
        {
            return this.entrySet().equals( ( ( Map< K, V > )object ).entrySet() );
        }
        catch( Throwable t )
        {
            return false;
        }
    }
    
    // GET --------------------------------------------------------------------
    
    @Override
    public V get( Object key )
    {
        return mapKV.get( key );
    }
    
    // HASH CODE --------------------------------------------------------------
    
    @Override
    public int hashCode()
    {
        return kHash.intValue();
    }
    
    // IS EMPTY ---------------------------------------------------------------
    
    @Override
    public boolean isEmpty()
    {
        return mapKV.isEmpty();
    }
    
    // KEY SET ----------------------------------------------------------------
    
    @Override
    public Set< K > keySet()
    {
        return new KeySet();
    }
    
    // PUT --------------------------------------------------------------------
    
    @Override
    public V put( K key, V value )
    {
        V existingValue = null;
        
        // Check for key in map, else check for value
        
        if( mapKV.containsKey( key ) )
        {
            // Get existing value for key
            
            existingValue = mapKV.get( key );
            
            // Return if key-value pair exists in map
            
            if( value == existingValue || ( value != null && value.equals( existingValue ) ) )
            {
                return existingValue;
            }
            
            // Remove existing value-key mapping
            
            mapVK.remove( existingValue );
            
            // Update value hash code
            
            if( existingValue != null )
            {
                vHash -= existingValue.hashCode();
            }
        }
        else if( mapVK.containsKey( value ) )
        {
            // Get existing key for value
            
            K existingKey = mapVK.get( value );
            
            // Remove existing key-value mapping
            
            mapKV.remove( existingKey );
            
            // Update key hash code
            
            if( existingKey != null )
            {
                kHash -= existingKey.hashCode();
            }
        }
        
        // Add new mappings
        
        mapKV.put( key, value );
        mapVK.put( value, key );
        
        // Update hash codes
        
        if( key != null )
        {
            kHash += key.hashCode();
        }
        
        if( value != null )
        {
            vHash += value.hashCode();
        }
        
        // Return previously mapped value
        
        return existingValue;
    }
    
    // PUT ALL ----------------------------------------------------------------
    
    @Override
    public void putAll( Map< ? extends K, ? extends V > map )
    {
        // Obtain iterator to parameter map's entry set
        
        Iterator< ? extends Map.Entry< ? extends K, ? extends V > > it = null;
        
        try
        {
            it = map.entrySet().iterator();
        }
        catch( Throwable t )
        {
            return;
        }
        
        Map.Entry< ? extends K, ? extends V > entry = null;
        
        // Iterate over entries in map
        
        while( it.hasNext() )
        {
            // Get next entry
            
            entry = it.next();
            
            // Add entry to this map
            
            this.put( entry.getKey(), entry.getValue() );
        }
    }
    
    // REMOVE -----------------------------------------------------------------
    
    @Override
    public V remove( Object key )
    {
        V value = null;
        
        // Check for key in map
        
        if( mapKV.containsKey( key ) )
        {
            // Remove entries
            
            value = mapKV.remove( key );
            mapVK.remove( value );
            
            // Update hash codes
            
            if( key != null )
            {
                kHash -= key.hashCode();
            }
            
            if( value != null )
            {
                vHash -= value.hashCode();
            }
        }
        
        // Return previously mapped value
        
        return value;
    }
    
    // SIZE -------------------------------------------------------------------
    
    @Override
    public int size()
    {
        return mapKV.size();
    }
    
    // TO STRING --------------------------------------------------------------
    
    @Override
    public String toString()
    {
        return mapKV.toString();
    }
    
    // VALUES -----------------------------------------------------------------
    
    @Override
    public Collection< V > values()
    {
        return new ValueCollection();
    }
    
    // INVERSE ----------------------------------------------------------------
    
    @Override
    public BiMap< V, K > inverse()
    {
        try
        {
            return new BiHashMap< V, K >( mapVK, mapKV, vHash, kHash );
        }
        catch( Throwable t )
        {
            return null;
        }
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PROTECTED METHODS ++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // CONSTRUCTORS -----------------------------------------------------------
    
    protected BiHashMap( HashMap< K, V > mapKV,
                         HashMap< V, K > mapVK,
                         Integer kHash,
                         Integer vHash )
    throws NullPointerException
    {
        if( mapKV == null || mapVK == null || kHash == null || vHash == null )
        {
            this.mapKV = null;
            this.mapVK = null;
            this.kHash = null;
            this.vHash = null;
            
            throw new NullPointerException();
        }
        
        this.mapKV = mapKV;
        this.mapVK = mapVK;
        this.kHash = kHash;
        this.vHash = vHash;
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE DATA +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // HASH CODES -------------------------------------------------------------
    
    private Integer kHash;
    private Integer vHash;
    
    // HASH MAPS --------------------------------------------------------------
    
    private HashMap< K, V > mapKV;
    private HashMap< V, K > mapVK;
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
}

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
