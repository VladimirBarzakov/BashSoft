package test.bg.softuni.dataStructures;

import main.bg.softuni.contracts.SimpleOrderedBag;
import main.bg.softuni.data_structures.SimpleSortedList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SimpleOrderedBagTests {

    private SimpleOrderedBag<String> names;

    @Before
    public void setUp(){
        this.names=new SimpleSortedList<>(String.class);
    }

    @Test
    public void testEmptyCtor(){
        this.names=new SimpleSortedList<>(String.class);
        Assert.assertEquals(16, this.names.capacity());
        Assert.assertEquals(0,this.names.size());
    }

    @Test
    public void testCtorWithInitialCapacity(){
        this.names=new SimpleSortedList<>(String.class, 20);
        Assert.assertEquals(20,this.names.capacity());
        Assert.assertEquals(0,this.names.size());
    }

    @Test
    public void testCtorWithInitialComparer(){
        this.names=new SimpleSortedList<>(String.class, String.CASE_INSENSITIVE_ORDER);
        Assert.assertEquals(16,this.names.capacity());
        Assert.assertEquals(0,this.names.size());
    }

    @Test
    public void testCtorWithAllParams(){
        this.names=new SimpleSortedList<>(
                String.class,
                String.CASE_INSENSITIVE_ORDER,
                30);
        Assert.assertEquals(30,this.names.capacity());
        Assert.assertEquals(0,this.names.size());
    }

    @Test
    public void testAddIncreasesSize(){
        this.names.add("Nasko");
        Assert.assertEquals(1,this.names.size());
    }

    @Test(expected =IllegalArgumentException.class)
    public void testAddNullThrowsException(){
        this.names.add(null);
    }

    @Test
    public void testAddUnsortedDataIsHeldSorted(){
        this.names.add("Rosen");
        this.names.add("Georgi");
        this.names.add("Balkan");
        List<String> values =new ArrayList<>();
        for (String name : this.names) {
            values.add(name);
        }
        Assert.assertEquals("Balkan",values.get(0));
        Assert.assertEquals("Georgi",values.get(1));
        Assert.assertEquals("Rosen",values.get(2));
    }

    @Test
    public void testAddingMoreThanInitialCapacity (){
        for (int i = 0; i < 17; i++) {
            this.names.add("a");
        }
        Assert.assertEquals(17,this.names.size());
        Assert.assertTrue(this.names.capacity()!=16);

    }

    @Test
    public void testAddingAllFromCollectionIncreasesSize (){
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        this.names.addAll(list);
        Assert.assertEquals(2,this.names.size());

    }

    @Test(expected =IllegalArgumentException.class)
    public void testAddingAllFromNullThrowsException (){
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add(null);
        this.names.addAll(list);
        Assert.assertEquals(2,this.names.size());
    }

    @Test
    public void testAddAllKeepsSorted (){
        List<String> list = new ArrayList<>();
        list.add("Rosen");
        list.add("Georgi");
        list.add("Balkan");
        this.names.addAll(list);
        List<String> values =new ArrayList<>();
        for (String name : this.names) {
            values.add(name);
        }
        Assert.assertEquals("Balkan",values.get(0));
        Assert.assertEquals("Georgi",values.get(1));
        Assert.assertEquals("Rosen",values.get(2));
    }

    @Test
    public void testRemoveValidElementDecreasesSize (){
        this.names.add("a");
        this.names.remove("a");
        Assert.assertEquals(0,this.names.size());
    }

    @Test
    public void testRemoveValidElementRemovesSelectedOne (){
        this.names.add("Ivan");
        this.names.add("Nasko");
        this.names.remove("Ivan");
        Assert.assertTrue(!this.names.remove("Ivan"));
    }

    @Test(expected =IllegalArgumentException.class)
    public void testRemovingNullThrowsException (){
        this.names.remove(null);
    }

    @Test(expected =IllegalArgumentException.class)
    public void testJoinWithNull  (){
        this.names.add("Ivan");
        this.names.add("Nasko");
        this.names.joinWith(null);
    }

    @Test
    public void testJoinWorksFine  (){
        this.names.add("Ivan");
        this.names.add("Nasko");
        this.names.add("Gosho");
        this.names.add("Maria");
        Assert.assertEquals("Gosho Ivan Maria Nasko",this.names.joinWith(" "));
    }
}
