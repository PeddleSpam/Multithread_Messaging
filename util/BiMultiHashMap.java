package util;

import java.util.NoSuchElementException;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Iterator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.lang.IllegalStateException;
import java.lang.reflect.Array;

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// BIDIRECTIONAL MULTI HASH MAP CLASS +++++++++++++++++++++++++++++++++++++++++
// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

public class BiMultiHashMap< K, V >
implements BiMultiMap< K, V >
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
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            // PUBLIC METHODS +++++++++++++++++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            
            // CONSTRUCTORS ---------------------------------------------------
            
            public EntrySetIterator()
            {
                entry = null;
                value = null;
                nextCalled = false;
                entryIterator = BiMultiHashMap.this.mapKV.entrySet().iterator();
                
                if( entryIterator.hasNext() )
                {
                    entry = entryIterator.next();
                    valueIterator = entry.getValue().iterator();
                }
                else
                {
                    valueIterator = new HashSet< V >().iterator();
                }
            }
            
            // HAS NEXT -------------------------------------------------------
            
            @Override
            public boolean hasNext()
            {
                return ( valueIterator.hasNext() || entryIterator.hasNext() );
            }
            
            // NEXT -----------------------------------------------------------
            
            @Override
            public Map.Entry< K, V > next()
            throws NoSuchElementException
            {
                // Check for next value
                
                if( valueIterator.hasNext() )
                {
                    // Get next value
                    
                    value = valueIterator.next();
                }
                else if( entryIterator.hasNext() )
                {
                    // Get next entry
                    
                    entry = entryIterator.next();
                    
                    // Get value iterator
                    
                    valueIterator = entry.getValue().iterator();
                    
                    // Get next value
                    
                    value = valueIterator.next();
                }
                else
                {
                    // No more elements exist, throw exception
                
                    throw new NoSuchElementException();
                }
                
                // Set flag to true
                
                nextCalled = true;
                
                // Return new entry
                    
                return new AbstractMap.SimpleEntry< K, V >( entry.getKey(), value );
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
                
                // Remove entry from map
                
                BiMultiHashMap.this.remove( entry.getKey(), value );
            }
            
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            // PRIVATE DATA +++++++++++++++++++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            
            private V value;
            
            private boolean nextCalled;
            
            private Iterator< V > valueIterator;
            
            private Map.Entry< K, HashSet< V > > entry;
            
            private Iterator< Map.Entry< K, HashSet< V > > > entryIterator;
            
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
            BiMultiHashMap.this.clear();
        }
        
        // CONTAINS -----------------------------------------------------------
        
        @Override
        @SuppressWarnings( "unchecked" )
        public boolean contains( Object object )
        {
            // Check for null parameter
            
            if( object == null )
            {
                return false;
            }
            
            // Cast object to required type
            
            Map.Entry< K, V > entry = null;
            
            try
            {
                entry = ( Map.Entry< K, V > )object;
            }
            catch( Throwable t )
            {
                return false;
            }
            
            // Check map for entry and return result
            
            return BiMultiHashMap.this.contains( entry.getKey(), entry.getValue() );
        }
        
        // CONTAINS ALL -------------------------------------------------------
        
        @Override
        @SuppressWarnings( "unchecked" )
        public boolean containsAll( Collection< ? > collection )
        {
            // Get iterator to entry collection
            
            Iterator< Map.Entry< K, V > > it = null;
            
            try
            {
                it = ( Iterator< Map.Entry< K, V > > )collection.iterator();
            }
            catch( Throwable t )
            {
                return false;
            }
            
            // Check each entry in collection against map
            
            Map.Entry< K, V > entry = null;
            
            while( it.hasNext() )
            {
                // Get next entry
                
                entry = it.next();
                
                // Return false if entry was not found in map
                
                if( !( BiMultiHashMap.this.contains( entry.getKey(), entry.getValue() ) ) )
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
            
            // Cast object to required type
            
            Set< Map.Entry< K, V > > entries = null;
            
            try
            {
                entries = ( Set< Map.Entry< K, V > > )object;
            }
            catch( Throwable t )
            {
                return false;
            }
            
            // Check sizes match
            
            if( entries.size() != BiMultiHashMap.this.size() )
            {
                return false;
            }
            
            // Check all entries against map and return result
            
            return this.containsAll( entries );
        }
        
        // HASH CODE ----------------------------------------------------------
        
        @Override
        public int hashCode()
        {
            return BiMultiHashMap.this.hashCode();
        }
        
        // IS EMPTY -----------------------------------------------------------
        
        @Override
        public boolean isEmpty()
        {
            return BiMultiHashMap.this.isEmpty();
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
            // Check for null parameter
            
            if( object == null )
            {
                return false;
            }
            
            // Cast object to required type
            
            Map.Entry< K, V > entry = null;
            
            try
            {
                entry = ( Map.Entry< K, V > )object;
            }
            catch( Throwable t )
            {
                return false;
            }
            
            // Remove entry from map and return result
            
            return BiMultiHashMap.this.remove( entry.getKey(), entry.getValue() );
        }
        
        // REMOVE ALL ---------------------------------------------------------
        
        @Override
        @SuppressWarnings( "unchecked" )
        public boolean removeAll( Collection< ? > collection )
        {
            // Get iterator to collection
            
            Iterator< Map.Entry< K, V > > it = null;
            
            try
            {
                it = ( Iterator< Map.Entry< K, V > > )collection.iterator();
            }
            catch( Throwable t )
            {
                return false;
            }
            
            Map.Entry< K, V > entry = null;
            
            // Iterate through entries in collection
            
            int removeCount = 0;
            
            while( it.hasNext() )
            {
                // Get next entry
                
                entry = it.next();
                
                // Increment remove counter if entry was removed
                
                if( BiMultiHashMap.this.remove( entry.getKey(), entry.getValue() ) )
                {
                    removeCount += 1;
                }
            }
            
            // Return true if any entries were removed
            
            return ( removeCount > 0 );
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
            
            // Get iterator to entries
            
            Iterator< Map.Entry< K, V > > it = this.iterator();
            
            Map.Entry< K, V > entry = null;
            
            // Create found entry set
            
            HashSet< Map.Entry< K, V > > foundEntries
                = new HashSet< Map.Entry< K, V > >();
            
            // Iterate through all entries
            
            while( it.hasNext() )
            {
                // Get next entry
                
                entry = it.next();
                
                // Add entry to found set if not found in collection
                    
                if( !( entries.contains( entry ) ) )
                {
                    foundEntries.add( entry );
                }
            }
            
            // Remove found entries and return result
            
            return this.removeAll( foundEntries );
        }
        
        // SIZE ---------------------------------------------------------------
        
        @Override
        public int size()
        {
            return BiMultiHashMap.this.size();
        }
        
        // TO ARRAY -----------------------------------------------------------
        
        @Override
        public Object[] toArray()
        {
            // Create object array to store entries
            
            Object[] array = new Object[ this.size() ];
            
            // Get iterator to entries
            
            Iterator< Map.Entry< K, V > > it = this.iterator();
            
            int index = 0;
            
            // Iterate through all entries
            
            while( it.hasNext() )
            {
                // Add next entry to array
                
                array[ index ] = it.next();
                    
                // Increment array index
                    
                index += 1;
            }
            
            // Return the new array
            
            return array;
        }
        
        @Override
        @SuppressWarnings( "unchecked" )
        public < T > T[] toArray( T[] array )
        throws ArrayStoreException
        {
            // Check parameter validity
            
            if( array == null || array.length < BiMultiHashMap.this.size() )
            {
                try
                {
                    array = ( T[] )Array.newInstance( array.getClass().getComponentType(),
                                                      BiMultiHashMap.this.size() );
                }
                catch( Throwable t )
                {
                    throw new ArrayStoreException();
                }
            }
            
            // Get iterator to entries
            
            Iterator< Map.Entry< K, V > > it = this.iterator();
            
            int index = 0;
            
            // Iterate through all entries
            
            try
            {
                while( it.hasNext() )
                {
                    // Add next entry to array
                    
                    array[ index ] = ( T )it.next();
                        
                    // Increment array index
                        
                    index += 1;
                }
            }
            catch( Throwable t )
            {
                throw new ArrayStoreException();
            }
            
            // Return the new array
            
            return array; 
        }
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    }
    
    // VALUE SET CLASS --------------------------------------------------------
    
    protected class ValueSet
    implements Set< V >
    {
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // NESTED CLASSES +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        
        // VALUE SET ITERATOR CLASS -------------------------------------------
        
        protected class ValueSetIterator
        implements Iterator< V >
        {
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            // PUBLIC METHODS +++++++++++++++++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            
            // CONSTRUCTORS ---------------------------------------------------
            
            public ValueSetIterator( K key )
            {
                lastValue = null;
                nextCalled = false;
                
                // Get value set for key
                
                Set< V > values = BiMultiHashMap.this.mapKV.get( key );
                
                // If value set does not exist, create empty set
                
                if( values == null )
                {
                    values = new HashSet< V >();
                }
                
                // Get iterator to value set
                
                iterator = values.iterator();
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
                if( !nextCalled )
                {
                    throw new IllegalStateException();
                }
                
                BiMultiHashMap.this.remove( lastValue );
                nextCalled = false;
                lastValue = null;
            }
            
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            // PRIVATE DATA +++++++++++++++++++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            
            private V lastValue;
            
            private boolean nextCalled;
            
            private Iterator< V > iterator;
            
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        }
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // PUBLIC METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        
        // CONSTRUCTORS -------------------------------------------------------
        
        public ValueSet( K key )
        {
            this.key = key;
        }
        
        // ADD ----------------------------------------------------------------
        
        @Override
        public boolean add( V value )
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
            BiMultiHashMap.this.remove( key );
        }
        
        // CONTAINS -----------------------------------------------------------
        
        @Override
        public boolean contains( Object value )
        {
            return BiMultiHashMap.this.contains( key, value );
        }
        
        // CONTAINS ALL -------------------------------------------------------
        
        @Override
        @SuppressWarnings( "unchecked" )
        public boolean containsAll( Collection< ? > collection )
        {
            // Check for key in map
            
            if( BiMultiHashMap.this.containsKey( key ) )
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
                
                // Get value set for key
                
                Set< V > values = BiMultiHashMap.this.get( key );
                
                // Check all values in collection against set
                
                while( it.hasNext() )
                {
                    if( !( values.contains( it.next() ) ) )
                    {
                        return false;
                    }
                }
                
                // All values were found, return true
                
                return true;
            }
            
            // Key does not exist, return false
            
            return false;
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
            
            if( values.size() != this.size() )
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
            // Check for key in map
            
            if( BiMultiHashMap.this.containsKey( key ) )
            {
                // Return hash code for key's value set
                
                return BiMultiHashMap.this.get( key ).hashCode();
            }
            
            // Key not found, return zero
            
            return 0;
        }
        
        // IS EMPTY -----------------------------------------------------------
        
        @Override
        public boolean isEmpty()
        {
            // Note: key cannot exist without any value mappings
            
            return !( BiMultiHashMap.this.containsKey( key ) );
        }
        
        // ITERATOR -----------------------------------------------------------
        
        @Override
        public Iterator< V > iterator()
        {
            return new ValueSetIterator( key );
        }
        
        // REMOVE -------------------------------------------------------------
        
        @Override
        public boolean remove( Object object )
        {
            return BiMultiHashMap.this.remove( key, object );
        }
        
        // REMOVE ALL ---------------------------------------------------------
        
        @Override
        @SuppressWarnings( "unchecked" )
        public boolean removeAll( Collection< ? > collection )
        {
            // Check map contains key
            
            if( !( BiMultiHashMap.this.containsKey( key ) ) )
            {
                return false;
            }
            
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
            
            // Remove all values in collection
            
            int removeCount = 0;
            
            while( it.hasNext() )
            {
                if( BiMultiHashMap.this.remove( key, it.next() ) )
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
            
            Collection< V > valueCollection = null;
            
            try
            {
                valueCollection = ( Collection< V > )collection;
            }
            catch( Throwable t )
            {
                return false;
            }
            
            // Check key exists in map
            
            if( !( BiMultiHashMap.this.containsKey( key ) ) )
            {
                return false;
            }
            
            // Create found set to store values for removal
            
            HashSet< V > foundValues = new HashSet< V >();
            
            // Get iterator to key's value set
            
            Iterator< V > it = BiMultiHashMap.this.get( key ).iterator();
            
            // Iterate over values in set
            
            V value = null;
            
            while( it.hasNext() )
            {
                // Get next value
                
                value = it.next();
                
                // If collection doesn't contain value, add it to found set
                
                if( !( valueCollection.contains( value ) ) )
                {
                    foundValues.add( value );
                }
            }
            
            // Remove found values from set and return result
            
            return this.removeAll( foundValues );
        }
        
        // SIZE ---------------------------------------------------------------
        
        @Override
        public int size()
        {
            // Check for key in map
            
            if( BiMultiHashMap.this.containsKey( key ) )
            {
                // Return size of key's value set
                
                return BiMultiHashMap.this.get( key ).size();
            }
            
            // Key not found in map, return zero
            
            return 0;
        }
        
        // TO ARRAY -----------------------------------------------------------
        
        @Override
        public Object[] toArray()
        {
            if( BiMultiHashMap.this.containsKey( key ) )
            {
                return BiMultiHashMap.this.get( key ).toArray();
            }
                    
            return null;
        }
        
        @Override
        public < T > T[] toArray( T[] array )
        {
            if( BiMultiHashMap.this.containsKey( key ) )
            {
                return BiMultiHashMap.this.get( key ).toArray( array );
            }
            
            return null;
        }
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // PRIVATE DATA +++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        
        K key;
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    }
    
    // KEY SET ----------------------------------------------------------------
    
    protected class KeySet
    implements Set< K >
    {
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // NESTED CLASSES +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        
        // KEY SET ITERATOR ---------------------------------------------------
        
        protected class KeySetIterator
        implements Iterator< K >
        {
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            // PUBLIC METHODS +++++++++++++++++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            
            // CONSTRUCTORS ---------------------------------------------------
            
            public KeySetIterator()
            {
                lastKey = null;
                nextCalled = false;
                iterator = BiMultiHashMap.this.mapKV.keySet().iterator();
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
                
                BiMultiHashMap.this.remove( lastKey );
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
            BiMultiHashMap.this.clear();
        }
        
        // CONTAINS -----------------------------------------------------------
        
        @Override
        public boolean contains( Object key )
        {
            return BiMultiHashMap.this.containsKey( key );
        }
        
        // CONTAINS ALL -------------------------------------------------------
        
        @Override
        @SuppressWarnings( "unchecked" )
        public boolean containsAll( Collection< ? > collection )
        {
            // Get iterator to collection
            
            Iterator< K > it = null;
            
            try
            {
                it = ( Iterator< K > )collection.iterator();
            }
            catch( Throwable t )
            {
                return false;
            }
            
            // Check all keys against map
            
            while( it.hasNext() )
            {
                if( !( BiMultiHashMap.this.containsKey( it.next() ) ) )
                {
                    return false;
                }
            }
            
            // All keys found in map, return true
            
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
            
            if( keys.size() != this.size() )
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
            return BiMultiHashMap.this.hashCode();
        }
        
        // IS EMPTY -----------------------------------------------------------
        
        @Override
        public boolean isEmpty()
        {
            return BiMultiHashMap.this.isEmpty();
        }
        
        // ITERATOR -----------------------------------------------------------
        
        @Override
        public Iterator< K > iterator()
        {
            return new KeySetIterator();
        }
        
        // REMOVE -------------------------------------------------------------
        
        @Override
        public boolean remove( Object key )
        {
            // Check map contains key
            
            if( BiMultiHashMap.this.containsKey( key ) )
            {
                BiMultiHashMap.this.remove( key );
                
                return true;
            }
            
            return false;
        }
        
        // REMOVE ALL ---------------------------------------------------------
        
        @Override
        @SuppressWarnings( "unchecked" )
        public boolean removeAll( Collection< ? > collection )
        {
            // Get iterator to collection
            
            Iterator< K > it = null;
            
            try
            {
                it = ( Iterator< K > )collection.iterator();
            }
            catch( Throwable t )
            {
                return false;
            }
            
            // Remove all keys from set
            
            int removeCount = 0;
            
            while( it.hasNext() )
            {
                if( this.remove( it.next() ) )
                {
                    removeCount += 1;
                }
            }
            
            // Return true if nay keys were removed
            
            return ( removeCount > 0 );
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
            
            // Get iterator to this set
            
            Iterator< K > it = this.iterator();
            
            K key = null;
            
            // Check all elements against collection
            
            while( it.hasNext() )
            {
                // Get next key
                
                key = it.next();
                
                // Add key to found set if not in collection
                
                if( !( keys.contains( key ) ) )
                {
                    foundKeys.add( key );
                }
            }
            
            // Remove any found keys and return result
            
            return this.removeAll( foundKeys );
        }
        
        // SIZE ---------------------------------------------------------------
        
        @Override
        public int size()
        {
            return BiMultiHashMap.this.mapKV.size();
        }
        
        // TO ARRAY -----------------------------------------------------------
        
        @Override
        public Object[] toArray()
        {
            // Create array to store keys
            
            Object[] array = new Object[ this.size() ];
            
            int index = 0;
            
            // Get iterator to this set
            
            Iterator< K > it = this.iterator();
            
            // Iterate through keys and add to array
            
            while( it.hasNext() )
            {
                array[ index ] = it.next();
                
                index += 1;
            }
            
            // Return the filled array
            
            return array;
        }
        
        @Override
        @SuppressWarnings( "unchecked" )
        public < T > T[] toArray( T[] array )
        throws ArrayStoreException
        {
            // Check parameter validity
            
            if( array == null || array.length < BiMultiHashMap.this.size() )
            {
                try
                {
                    array = ( T[] )Array.newInstance( array.getClass().getComponentType(),
                                                      BiMultiHashMap.this.size() );
                }
                catch( Throwable t )
                {
                    throw new ArrayStoreException();
                }
            }
            
            // Get key iterator for this set
            
            Iterator< K > it = this.iterator();
            
            int index = 0;
            
            // Add all keys to array
            
            try
            {
                while( it.hasNext() )
                {
                    // Add next entry to array
                    
                    array[ index ] = ( T )it.next();
                        
                    // Increment array index
                        
                    index += 1;
                }
            }
            catch( Throwable t )
            {
                throw new ArrayStoreException();
            }
            
            // Return the array
            
            return array;
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
                iterator = BiMultiHashMap.this.mapVK.keySet().iterator();
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
                if( iterator.hasNext() )
                {
                    lastValue = iterator.next();
                    nextCalled = true;
                    
                    return lastValue;
                }
                
                // No next element exists, throw exception
                
                throw new NoSuchElementException();
            }
            
            // REMOVE ---------------------------------------------------------
            
            @Override
            public void remove()
            throws IllegalStateException
            {
                if( nextCalled )
                {
                    ValueCollection.this.inverseMap.remove( lastValue );
                    nextCalled = false;
                }
                else
                {
                    // Next has not been called, throw exception
                
                    throw new IllegalStateException();
                }
            }
            
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            // PRIVATE DATA +++++++++++++++++++++++++++++++++++++++++++++++++++
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            
            private V lastValue;
            
            private boolean nextCalled;
            
            private Iterator< V > iterator;
            
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        }
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // PUBLIC METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        
        // CONSTRUCTORS -------------------------------------------------------
        
        public ValueCollection()
        {
            inverseMap = BiMultiHashMap.this.inverse();
        }
        
        // ADD ----------------------------------------------------------------
        
        @Override
        public boolean add( V value )
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
        public boolean contains( Object value )
        {
            return inverseMap.containsKey( value );
        }
        
        // CONTAINS ALL -------------------------------------------------------
        
        @Override
        @SuppressWarnings( "unchecked" )
        public boolean containsAll( Collection< ? > collection )
        {
            // Get iterator to collection
            
            Iterator< V > it = null;
            
            try
            {
                it = ( Iterator< V > )collection.iterator();
            }
            catch( Throwable t )
            {
                return false;
            }
            
            // Check all values against map
            
            while( it.hasNext() )
            {
                if( !( inverseMap.containsKey( it.next() ) ) )
                {
                    return false;
                }
            }
            
            // All entries found in map, return true
            
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
            
            if( values.size() != this.size() )
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
        public boolean remove( Object value )
        {
            if( inverseMap.containsKey( value ) )
            {
                inverseMap.remove( value );
                
                return true;
            }
            
            return false;
        }
        
        // REMOVE ALL ---------------------------------------------------------
        
        @Override
        @SuppressWarnings( "unchecked" )
        public boolean removeAll( Collection< ? > collection )
        {
            // Obtain iterator to collection
            
            Iterator< V > it = null;
            
            try
            {
                it = ( Iterator< V > )collection.iterator();
            }
            catch( Throwable t )
            {
                return false;
            }
            
            V value = null;
            
            // Remove values (keys) from inverse map
            
            int removeCount = 0;
            
            while( it.hasNext() )
            {
                // Get next value
                
                value = it.next();
                
                // Remove value if contained in map
                
                if( inverseMap.containsKey( value ) )
                {
                    inverseMap.remove( value );
                    removeCount += 1;
                }
            }
            
            // Return true if any values were removed
            
            return ( removeCount > 0 );
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
            
            // Get iterator to this collection
            
            Iterator< V > it = this.iterator();
            
            // Iterate through values in this collection
            
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
            
            // Remove all found values and return result
            
            return this.removeAll( foundValues );
        }
        
        // SIZE ---------------------------------------------------------------
        
        @Override
        public int size()
        {
            return BiMultiHashMap.this.mapVK.size();
        }
        
        // TO ARRAY -----------------------------------------------------------
        
        @Override
        public Object[] toArray()
        {
            return BiMultiHashMap.this.mapVK.keySet().toArray();
        }
        
        @Override
        public < T > T[] toArray( T[] array )
        throws ArrayStoreException
        {
            return BiMultiHashMap.this.mapVK.keySet().toArray( array );
        }
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // PRIVATE DATA +++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        
        private BiMultiMap< V, K > inverseMap;
        
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PUBLIC METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // CONSTRUCTORS -----------------------------------------------------------
    
    public BiMultiHashMap()
    {
        size = new Integer( 0 );
        
        kHash = new Integer( 0 );
        vHash = new Integer( 0 );
        
        mapKV = new HashMap< K, HashSet< V > >();
        mapVK = new HashMap< V, HashSet< K > >();
    }
    
    public BiMultiHashMap( int initialCapacity )
    throws IllegalArgumentException
    {
        size = new Integer( 0 );
        
        kHash = new Integer( 0 );
        vHash = new Integer( 0 );
        
        mapKV = new HashMap< K, HashSet< V > >( initialCapacity );
        mapVK = new HashMap< V, HashSet< K > >( initialCapacity );
    }
    
    public BiMultiHashMap( int initialCapacity, float loadFactor )
    throws IllegalArgumentException
    {
        size = new Integer( 0 );
        
        kHash = new Integer( 0 );
        vHash = new Integer( 0 );
        
        mapKV = new HashMap< K, HashSet< V > >( initialCapacity, loadFactor );
        mapVK = new HashMap< V, HashSet< K > >( initialCapacity, loadFactor );
    }
    
    public BiMultiHashMap( BiMultiHashMap< K, V > map )
    {
        if( map == null )
        {
            size = new Integer( 0 );
            
            kHash = new Integer( 0 );
            vHash = new Integer( 0 );
            
            mapKV = new HashMap< K, HashSet< V > >();
            mapVK = new HashMap< V, HashSet< K > >();
            
            throw new NullPointerException();
        }
        
        size = new Integer( map.size );
            
        kHash = new Integer( map.kHash );
        vHash = new Integer( map.vHash );
        
        mapKV = new HashMap< K, HashSet< V > >( map.mapKV );
        mapVK = new HashMap< V, HashSet< K > >( map.mapVK );
    }
    
    // CLEAR ------------------------------------------------------------------
    
    @Override
    public void clear()
    {
        size = new Integer( 0 );
        
        kHash = new Integer( 0 );
        vHash = new Integer( 0 );
        
        mapVK.clear();
        mapKV.clear();
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
    
    // CONTAINS ---------------------------------------------------------------
    
    @Override
    public boolean contains( Object key, Object value )
    {
        if( mapKV.containsKey( key ) )
        {
            // Get set of values the key maps to
            
            Set< V > values = mapKV.get( key );
            
            // Check set for given value
            
            if( values.contains( value ) )
            {
                return true;
            }
        }
        
        return false;
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
            return this.entrySet().equals( ( ( MultiMap< K, V > )object ).entrySet() );
        }
        catch( Throwable t )
        {
            return false;
        }
    }
    
    // GET --------------------------------------------------------------------
    
    @Override
    @SuppressWarnings( "unchecked" )
    public Set< V > get( Object object )
    {
        // Cast object to required type
        
        K key = null;
        
        try
        {
            key = ( K )object;
        }
        catch( Throwable t )
        {
            return null;
        }
        
        // Check map contains key
        
        if( mapKV.containsKey( key ) )
        {
            // Return value set for key
            
            return new ValueSet( key );
        }
        
        return null;
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
        return ( size == 0 );
    }
    
    // KEY SET ----------------------------------------------------------------
    
    @Override
    public Set< K > keySet()
    {
        return new KeySet();
    }
    
    // PUT --------------------------------------------------------------------
    
    @Override
    public boolean put( K key, V value )
    {
        HashSet< K > keys = null;
        HashSet< V > values = null;
        
        // Check for key and/or value in map
        
        if( mapKV.containsKey( key ) )
        {
            // Key exists in map, get it's value set
            
            values = mapKV.get( key );
            
            // Check for value in map
            
            if( mapVK.containsKey( value ) )
            {
                // Check for mapping
                
                if( values.contains( value ) )
                {
                    // Mapping already exists, return false
                    
                    return false;
                }
                
                // Get key set for value
                
                keys = mapVK.get( value );
            }
            else
            {
                // Value doesn't exist, add it to map
                
                keys = new HashSet< K >();
                
                mapVK.put( value, keys );
            }
        }
        else if( mapVK.containsKey( value ) )
        {
            // Key doesn't exist, so add it to map
            
            values = new HashSet< V >();
            
            mapKV.put( key, values );
            
            // Get key set for value
            
            keys = mapVK.get( value );
        }
        else
        {
            // Neither key nor value exists in map, add both
            
            keys = new HashSet< K >();
            values = new HashSet< V >();
            
            mapKV.put( key, values );
            mapVK.put( value, keys );
        }
        
        // Add mappings
            
        keys.add( key );
        values.add( value );
        
        // Update hash codes
        
        if( key != null )
        {
            kHash += key.hashCode();
        }
        
        if( value != null )
        {
            vHash += value.hashCode();
        }
        
        // Increment size counter
        
        size += 1;
        
        return true;
    }
    
    // PUT ALL ----------------------------------------------------------------
    
    @Override
    public void putAll( MultiMap< ? extends K, ? extends V > map )
    {
        // Get iterator to map's entry set
        
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
        
        // Iterate over map's entry set
        
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
    public Set< V > remove( Object key )
    {
        // Check to see if map contains key
        
        if( mapKV.containsKey( key ) )
        {
            // Remove key and get value set
            
            Set< V > values = mapKV.remove( key );
            
            // Get iterator for value set
            
            Iterator< V > it = values.iterator();
            
            // Iterate over all values in set
            
            V value = null;
            Set< K > keys = null;
            
            while( it.hasNext() )
            {
                // Get next value
                
                value = it.next();
                
                // Get key set for value
                
                keys = mapVK.get( value );
                
                // Remove key from key set
                
                keys.remove( key );
                
                // Remove value if no more mappings exist
                
                if( keys.isEmpty() )
                {
                    mapVK.remove( value );
                }
                
                // Update value hash code
                
                if( value != null )
                {
                    vHash -= value.hashCode();
                }
                
                // Update size counter
                
                size -= 1;
            }
            
            // Update key hash code
            
            if( key != null )
            {
                kHash -= ( key.hashCode() * values.size() );
            }
            
            // Return value set previously assigned to key
            
            return values;
        }
        
        // Key not found, return null
        
        return null;
    }
    
    // REMOVE ALL -------------------------------------------------------------
    
    @Override
    public boolean remove( Object key, Object value )
    {
        // Check to see if key exists
        
        if( mapKV.containsKey( key ) )
        {
            // Get value set for key
            
            Set< V > values = mapKV.get( key );
            
            // Check for value in set
            
            if( values.contains( value ) )
            {
                // Remove value from set
                
                values.remove( value );
                
                // Remove key if no more mappings exist
                
                if( values.isEmpty() )
                {
                    mapKV.remove( key );
                }
                
                // Get key set for value
                
                Set< K > keys = mapVK.get( value );
                
                // Remove key from set
                
                keys.remove( key );
                
                // Remove value if no more mappings exist
                
                if( keys.isEmpty() )
                {
                    mapVK.remove( value );
                }
                
                // Update hash codes
                
                if( key != null )
                {
                    kHash -= key.hashCode();
                }
                
                if( value != null )
                {
                    vHash -= value.hashCode();
                }
                
                // Decrement size counter
                
                size -= 1;
                
                return true;
            }
        }
        
        return false;
    }
    
    // SIZE -------------------------------------------------------------------
    
    @Override
    public int size()
    {
        return size.intValue();
    }
    
    // VALUES -----------------------------------------------------------------
    
    @Override
    public Collection< V > values()
    {
        return new ValueCollection();
    }
    
    // INVERSE ----------------------------------------------------------------
    
    @Override
    public BiMultiMap< V, K > inverse()
    {
        return new BiMultiHashMap< V, K >( this.mapVK, this.mapKV,
                                           this.vHash, this.kHash,
                                           this.size );
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PROTECTED METHODS ++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // CONSTRUCTORS -----------------------------------------------------------
    
    protected BiMultiHashMap( HashMap< K, HashSet< V > > mapKV,
                              HashMap< V, HashSet< K > > mapVK,
                              Integer kHash,
                              Integer vHash,
                              Integer size )
    throws NullPointerException
    {
        if( mapKV == null || mapVK == null ||
            kHash == null || vHash == null || size == null )
        {
            this.size = new Integer( 0 );
        
            this.kHash = new Integer( 0 );
            this.vHash = new Integer( 0 );
            
            this.mapKV = new HashMap< K, HashSet< V > >();
            this.mapVK = new HashMap< V, HashSet< K > >();
            
            throw new NullPointerException();
        }
        else
        {
            this.size = size;
        
            this.kHash = kHash;
            this.vHash = vHash;
            
            this.mapKV = mapKV;
            this.mapVK = mapVK;
        }
    }
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE DATA +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    // SIZE -------------------------------------------------------------------
    
    private Integer size;
    
    // HASH CODES -------------------------------------------------------------
    
    private Integer kHash;
    private Integer vHash;
    
    // MAPS -------------------------------------------------------------------
    
    private HashMap< K, HashSet< V > > mapKV;
    private HashMap< V, HashSet< K > > mapVK;
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
}

// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
